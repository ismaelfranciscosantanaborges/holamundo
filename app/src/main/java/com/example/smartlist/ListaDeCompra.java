package com.example.smartlist;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

public class ListaDeCompra extends Fragment implements
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    DatabaseHelper databaseHelper;

    private View rootView;
    private ArrayList<Lista1> listaItem;
    private Adatador adaptador2;
    private ListView listView;
    private RViewAdapter adaptador;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private EditText etBuscarProducto;
    private Button btnTerminar, btnHistorial, btnAgregar ;
    private ImageView btnCrearNuevo;
    private CheckBox chkAll;
    private int cantidadTotal;
    private Context context;
    private boolean seEliminoDeSqlite = false;
    private boolean paraModificar = false;
    private Lista1 itemParaModificar;

    public ListaDeCompra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lista_de_compra, container, true);



        init();

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adaptador);

        ItemTouchHelper.SimpleCallback simpleCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, ListaDeCompra.this);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView.getVisibility() == View.VISIBLE){
                    openDialog();
                }else{
                    Toast.makeText(getContext(),"TIENES QUE ESTAR EN UNA LISTA NUEVA", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(recyclerView.getVisibility() == View.VISIBLE){
                    if(listaItem.size() > 0){
                        openDialogGuardar();
                    }else{
                        Toast.makeText(getContext(), "LA LISTA NO PUEDE ESTAR VACIA", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(),"TIENES QUE ESTAR EN UNA LISTA NUEVA", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogSeleccionar();
            }
        });

        btnCrearNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                listaItem.clear();
                listaItem = databaseHelper.getAllEntidad(true);
                layoutManager = new LinearLayoutManager(getContext());
                adaptador = new RViewAdapter(getContext(), listaItem);
                recyclerView.setAdapter(adaptador);

                if(listaItem.size() > 0){
                    Toast.makeText(getContext(), "ESTA LISTA LA TIENES EN PROGRESO", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //databaseHelper.unicaVez();

        return rootView;
    }

    private void init(){

        recyclerView = rootView.findViewById(R.id.recyclerView);
        etBuscarProducto = rootView.findViewById(R.id.etBuscarLL);
        btnTerminar = rootView.findViewById(R.id.btnTerminarLL);
        btnHistorial = rootView.findViewById(R.id.btnHistorial);
        btnAgregar = rootView.findViewById(R.id.btnAgregarLL);
        btnCrearNuevo = rootView.findViewById(R.id.btnCrearNuevaLista);
        listView = rootView.findViewById(R.id.listView);

        listView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        databaseHelper = DatabaseHelper.obtenerConexion(getContext());
        listaItem = databaseHelper.getAllEntidad(true);
        layoutManager = new LinearLayoutManager(getContext());
        adaptador = new RViewAdapter(getContext(), listaItem);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "SELECCION: " +
                                listaItem.get(recyclerView.getChildAdapterPosition(v)).getNombre() + " ID: " +
                                listaItem.get(recyclerView.getChildAdapterPosition(v)).getId(),
                                Toast.LENGTH_LONG).show();

                itemParaModificar = listaItem.get(recyclerView.getChildAdapterPosition(v));

                paraModificar = true;
                openDialog();
                paraModificar = false;
            }
        });

        etBuscarProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nombre = etBuscarProducto.getText().toString();
                ArrayList<Lista1> listaBusqueda = new ArrayList<>();
                if(nombre.length() >= 0){
                    listaBusqueda.clear();

                    for(Lista1 p : listaItem){
                        String nombreP = p.getNombre().toLowerCase();
                        if(nombreP.contains(nombre.toLowerCase())){
                            listaBusqueda.add(p);
                        }
                    }


                    adaptador = new RViewAdapter(getContext(),listaBusqueda);
                    recyclerView.setAdapter(adaptador);
                }else{
                    adaptador = new RViewAdapter(getContext(),listaItem);
                    recyclerView.setAdapter(adaptador);
                }
            }
        });
    }

    private ArrayList<Lista1> getArrayList(){

        /* listaItem = new ArrayList<Lista1>();


       listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Arina de maiz", 10, false));
        listaItem.add(new Lista1("Arroz primiun", 1, false));
        listaItem.add(new Lista1("Refresco de lo grande", 4, false));
        listaItem.add(new Lista1("Pan de agua", 6, false));
        listaItem.add(new Lista1("Pan de agua", 9, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Pan de agua", 10, false));

        */
        cantidadTotal = listaItem.size();
        return listaItem;
    }

    private void openDialog(){
       ListarDialog listarDialog = new ListarDialog();
        if(paraModificar == true)
            listarDialog.setAdapter(listaItem,listView, recyclerView, itemParaModificar);
         else
            listarDialog.setAdapter(listaItem,listView, recyclerView, null);

        listarDialog.show(getFragmentManager(), "listar Dialog");
    }

    private void openDialogGuardar(){
        DialogGuardarLista dialogGuardarLista = new DialogGuardarLista();
        dialogGuardarLista.setMessage(getContext(),recyclerView,listaItem);
        dialogGuardarLista.show(getFragmentManager(),"Guardar");
    }

    private void openDialogSeleccionar(){
        DialogSeleccionLista dialogSeleccionLista = new DialogSeleccionLista();
        dialogSeleccionLista.setMessage(getContext(), listView, recyclerView,listaItem);
        dialogSeleccionLista.show(getFragmentManager(), "SELECCIONAR");
    }

    @Override
    public void onSwiper(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof  RViewAdapter.DataObjectHolder){

            String nombre = listaItem.get(position).getNombre();
            final Lista1 lista = listaItem.get(position);
            final int deletedIntex = viewHolder.getAdapterPosition();

            adaptador.removeItem(viewHolder.getAdapterPosition());

            recuperarCocheBorrado(viewHolder, nombre, lista, deletedIntex);


        }
    }
    private void recuperarCocheBorrado(RecyclerView.ViewHolder viewHolder, String nombre, final Lista1 productoBorrado, final int deletedIntex){
        Snackbar snackbar = Snackbar.make(((RViewAdapter.DataObjectHolder) viewHolder).layoutBorrar, nombre + " Eliminado", Snackbar.LENGTH_LONG);

        snackbar.setAction("Deshacer", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adaptador.restoreItem(productoBorrado, deletedIntex);
                seEliminoDeSqlite = true;
            }
        });

        snackbar.setActionTextColor(Color.GREEN);
        snackbar.show();

    }
}
