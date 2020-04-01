package com.example.smartlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListarDialog extends AppCompatDialogFragment {

    private DatabaseHelper databaseHelper;
    private AdaptadorLista adaptador;
    private RecyclerView listView;
    private ImageView imgDismiss;
    private ArrayList<Lista1> listaItem;
    private ListView listViewP;
    private EditText etNombre, etCantidad;
    private Button btnAgregar,btnCancelar;
    private Lista1 item;

    public ListarDialog setAdapter(ArrayList<Lista1> listaItem,ListView listViewP, RecyclerView listView, Lista1 item){
        this.listaItem = listaItem;
        this.listView = listView;
        this.item = item;
        this.listViewP = listViewP;
        return this;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_listar, null);

        etNombre = view.findViewById(R.id.etNombre);
        etCantidad = view.findViewById(R.id.etCantidad);
        btnAgregar = view.findViewById(R.id.btnAgregar);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        imgDismiss = view.findViewById(R.id.imgDismiss);

        listViewP.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);


        if(item != null){
            etNombre.setText(item.getNombre());
            etCantidad.setText(item.getCantidad() + "");
            btnAgregar.setText("MODIFICAR");
        }else{
            btnAgregar.setText("AGREGAR");
        }

        imgDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper = DatabaseHelper.obtenerConexion(getContext());
                boolean seActualizo = false;
                boolean existe = false;


                String nombre = etNombre.getText().toString();
                int cantidad = 0;
                if(!etCantidad.getText().toString().equalsIgnoreCase("")){
                    cantidad = Integer.parseInt(etCantidad.getText().toString());
                }


                if(!nombre.isEmpty() && cantidad != 0){
                    Lista1 lista1 = new Lista1(nombre,cantidad, false);

                    if(item != null){
                        item.setNombre(nombre);
                        item.setCantidad(cantidad);
                        seActualizo = databaseHelper.updateEntidad(item, true);
                    }else{
                        existe = databaseHelper.buscarSiExiste(lista1.getNombre());
                        if(existe){
                            seActualizo = databaseHelper.updateEntidad(lista1, false);
                        }else{
                            databaseHelper.addEntidad(lista1);
                        }
                    }

                    listaItem.clear();
                    listaItem.addAll(databaseHelper.getAllEntidad(true));
                    listView.invalidateItemDecorations();
                    listView.refreshDrawableState();


                    LimpiarEditText();

                    if (btnAgregar.getText().toString().equalsIgnoreCase("modificar")){
                        Toast.makeText(getContext(),"Se ha MODIFICADO en la Lista", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                    if(btnAgregar.getText().toString().equalsIgnoreCase("agregar") && existe)
                        Toast.makeText(getContext(),"Ya EXISTIA se ha MODIFICADO", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(),"Se ha AGREGADO a la Lista", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(),"Debe de llenar todos los campo", Toast.LENGTH_SHORT).show();
                }




            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }

    private void LimpiarEditText(){
        etCantidad.setText("");
        etNombre.setText("");
    }
}
