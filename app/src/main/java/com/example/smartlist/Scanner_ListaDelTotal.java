package com.example.smartlist;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Scanner_ListaDelTotal extends Fragment {


    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseHelperCompra databaseHelperCompra;
    private double total = 0;
    private View rootView;
    private ArrayList<ProductosComprados> listaItem;
    private ArrayList<String> todosLosNombres;
    private Button btnAgregarCL, btnTerminar;
    private Adaptador adaptador;
    private ListView listView;
    private CheckBox chkAll;
    private int cantidadTotal;
    private TextView tvCodigoLeido, tvTotalDeCompra;
    private EditText etBuscar;

    private ArrayList<ProductosComprados> listProducto;

    public Scanner_ListaDelTotal() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_scanner__lista_del_total, container, true);


        tvCodigoLeido = rootView.findViewById(R.id.codigoLeido);
        listView = rootView.findViewById(R.id.listView1);
        btnTerminar = rootView.findViewById(R.id.btnTerminar);
        btnAgregarCL = rootView.findViewById(R.id.btnAgregarCL);
        etBuscar = rootView.findViewById(R.id.etBuscarPC);
        tvTotalDeCompra = rootView.findViewById(R.id.tvTotalDeCompra);

        inicializacionFireBase();
        //Patron de Diseño Singleton
        databaseHelperCompra = DatabaseHelperCompra.obtenerConexion(getContext());

        btnAgregarCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaner();
            }
        });

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nombre = etBuscar.getText().toString();
                ArrayList<ProductosComprados> listaBusqueda = new ArrayList<>();

                if(nombre.length() >= 0){
                    listaBusqueda.clear();
                    for(ProductosComprados p : listaItem){
                        String nombreP = p.getNombre().toLowerCase();
                        if(nombreP.contains(nombre.toLowerCase())){
                            listaBusqueda.add(p);
                        }
                    }

                    adaptador = new Adaptador(getContext(),listaBusqueda);
                    listView.setAdapter(adaptador);
                }else{
                    listaItem = databaseHelperCompra.getAllEntidad();
                    adaptador = new Adaptador(getContext(),listaItem);
                    listView.setAdapter(adaptador);
                }
            }
        });

        btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AQUIII
                ArrayList<ProductosComprados> lista = databaseHelperCompra.getAllEntidad();
                String recorer = "";
                for(int i = 0; i < lista.size(); i++){
                    if(i == 0)
                        recorer += lista.get(i).getJSON();
                    else
                        recorer += "," + lista.get(i).getJSON();
                }
                String JSON = "{\"productos\":[" + recorer +"]}";

                openDialogGenerarCodigo(JSON);

            }
        });

        //listaItem.clear();
        listaItem = databaseHelperCompra.getAllEntidad();

        total = 0;
        for(ProductosComprados p: listaItem){
            total += p.getPrecioTotal();
        }

        tvTotalDeCompra.setText(total + "");

        adaptador = new Adaptador(getContext(), listaItem);

        adaptador.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                yaExiste = true;
                /*



                */

                listaItem.clear();
                listaItem = databaseHelperCompra.getAllEntidad();
                adaptador = new Adaptador(getContext(), listaItem);
                listView.setAdapter(adaptador);



                prod = listaItem.get(position);
                openDialog(prod,true);
                yaExiste = false;
            }
        });

        listView.setAdapter(adaptador);


        return rootView;
    }


    private void inicializacionFireBase(){
        FirebaseApp.initializeApp(getContext());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        todosLosNombres = new ArrayList<>();
    }

    private boolean idExiste = false;


    private Producto codigoExiste(){

       myRef.child("Productos").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                   Producto p = snapshot.getValue(Producto.class);

                   if(tvCodigoLeido.getText().toString().equalsIgnoreCase(p.getId())){
                       idExiste = true;
                       producto = p;
                   }
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        if (idExiste){
            return producto;
        }

        return new Producto(tvCodigoLeido.getText().toString(),"","","",0.0);
    }

    private void openDialog(ProductosComprados p, boolean existe){

        etBuscar.setText("");
        ComprarDialog comprarDialog = new ComprarDialog();
        comprarDialog.setMessage(getContext(), listView, getArrayList(), todosLosNombres, p, tvTotalDeCompra, existe);

        total = 0;
        for(ProductosComprados prod: listaItem){
            todosLosNombres.add(prod.getNombre());
            total += prod.getPrecioTotal();
        }

        tvTotalDeCompra.setText(total + "");

        comprarDialog.show(getFragmentManager(), "Comprar producto");

    }

    private void openDialogGenerarCodigo(String json){
        CodigoGeneradoDialog codigoGeneradoDialog = new CodigoGeneradoDialog();
        codigoGeneradoDialog.setJson(json);
        codigoGeneradoDialog.show(getFragmentManager(), "Obtener QR");
    }

    private ArrayList<ProductosComprados> getArrayList(){

        listaItem = new ArrayList<ProductosComprados>();

        listaItem.add(new ProductosComprados("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new ProductosComprados("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new ProductosComprados("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new ProductosComprados("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));

        total = 0;
        for(ProductosComprados p: listaItem){
            todosLosNombres.add(p.getNombre());
            total += p.getPrecioTotal();
        }

        tvTotalDeCompra.setText(total + "");
        cantidadTotal = listaItem.size();
        return listaItem;
    }

    private void scaner(){
        IntentIntegrator intent = new IntentIntegrator(getActivity());
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("ESCANEAR EL CODIGO");
        intent.setCameraId(0);
        intent.setOrientationLocked(false);
        intent.setCaptureActivity(CapturaActivityPortrait.class);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    private String codigoLeido;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if(result != null){
            if(result.getContents() != null){

                codigoLeido = result.getContents();

                Task11 task1 = new Task11();

                task1.execute();

                Log.e("ERRORRRRRRR", result.getContents() + "");
            }else{
                Toast.makeText(getContext(), "HAS CANCELADO EL SCANER", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode,data);
        }
    }



    private ProductosComprados prod;
    private Producto producto;
    private boolean yaExiste = false;

    class Task11 extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            myRef.child("Productos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    producto = null;
                    yaExiste = false;
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Producto p = snapshot.getValue(Producto.class);

                        if(codigoLeido.equalsIgnoreCase(p.getId())){
                            producto = p;
                            yaExiste = databaseHelperCompra.buscarSiExiste(p.getId() + "");
                        }
                    }

                    if(producto != null){
                        if(yaExiste){
                            ArrayList<ProductosComprados> listaProducto = databaseHelperCompra.getAllEntidad();

                            for(ProductosComprados p: listaProducto){
                                if(p.getId().equalsIgnoreCase(producto.getId() + "")){
                                    prod = p;
                                }
                            }
                        }else{
                            prod = new ProductosComprados(producto.getId(),producto.getNombre(),
                                    producto.getDescripcion(),producto.getMarca(),producto.getPrecio());
                        }

                    }else{
                        prod = null;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(prod != null){
                if(yaExiste){
                    openDialog(prod, true);
                    Toast.makeText(getContext(), "Este producto esta en la lista",Toast.LENGTH_SHORT).show();
                    yaExiste = false;
                }else{
                    openDialog(prod, false);
                }

            }else{
                Toast.makeText(getContext(), "ESTE PRODUCTO NO ESTA REGISTRADO", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
