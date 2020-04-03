package com.example.smartlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class AdministradorActivity extends AppCompatActivity implements IEvaluable, AgregarDialog.DialogListener {

    private FirebaseDatabase database;

    private Context context;
    private ArrayList<String> nombreDeProductos;
    private DatabaseReference myRef;
    private TextView titulo;
    private EditText etBuscarProducto;
    private AdapterAdmin adaptador;
    private ArrayList<Producto> listaItem;
    private FloatingActionButton floatingActionButton;
    private View view;
    private ListView listViewAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        listViewAdmin = findViewById(R.id.listViewAdmin);
        titulo = findViewById(R.id.codigoLeido);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        etBuscarProducto = findViewById(R.id.etBuscarProducto);


        inicializacionFireBase();

        etBuscarProducto.setSaveEnabled(false);
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
                ArrayList<Producto> listaBusqueda = new ArrayList<>();
                if(nombre.length() >= 0){

                    for(Producto p : listaItem){
                        String nombreP = p.getMarca().toLowerCase();
                        if(nombreP.contains(nombre.toLowerCase())){
                            listaBusqueda.add(p);
                        }
                    }

                    adaptador = new AdapterAdmin(context,listaBusqueda);
                    listViewAdmin.setAdapter(adaptador);
                }else{
                    adaptador = new AdapterAdmin(context,listaBusqueda);
                    listViewAdmin.setAdapter(adaptador);
                }
            }
        });

        listViewAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Producto p = listaItem.get(position);
                openDialog(p);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scaner();

                //UtilizandoButtonSheet bottonSheet = new UtilizandoButtonSheet();
                //bottonSheet.show(getFragmentManager(), "exampleBottonSheet");

                //tvCodigoLeido.setText("283749173840");


            }
        });
    }

    private void inicializacionFireBase(){
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        listaItem = new ArrayList<>();
        context = this;
        nombreDeProductos = new ArrayList<>();

        Task2 task2 = new Task2();
        task2.execute();
    }

    Producto producto;
    private void openDialog(Producto p){
        AgregarDialog agregarDialog = new AgregarDialog();
        agregarDialog.setMessage(context,listViewAdmin, listaItem, p);
        agregarDialog.show(getSupportFragmentManager(), "Agregar Producto");
    }

    private ArrayList<Producto> getArrayList(){

        listaItem = new ArrayList<Producto>();

        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));
        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));

        return listaItem;
    }

    private void scaner(){
        IntentIntegrator intent = new IntentIntegrator(this);
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
    private Producto prod;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if(result != null){
            if(result.getContents() != null){
                Toast.makeText(getApplicationContext(), "EL CODIGO DE BARRA ES: " + result.getContents().toString(), Toast.LENGTH_LONG).show();

                codigoLeido = result.getContents();

                Task1 task1 = new Task1();

                task1.execute();

                Log.e("ERRORRRRRRR", result.getContents() + "");
            }else{
                Toast.makeText(getApplicationContext(), "HAS CANCELADO EL SCANER", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode,data);
        }
    }

    Producto productoEliminado;
    @Override
    public void obtenerProductoEliminado(Producto p) {
        productoEliminado = p;
        recuperarProcductoBorrado();
    }

    class Task1 extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            myRef.child("Productos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Producto p = snapshot.getValue(Producto.class);

                        if(codigoLeido.equalsIgnoreCase(p.getId())){
                            producto = p;
                        }
                    }

                    if(producto != null){
                        prod = producto;
                        producto = null;
                    }else{
                        prod = new Producto(codigoLeido, "", "", "", 0.0);
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
            titulo.setText("NOMBRE: "+ prod.getNombre() + " Marca: " + prod.getMarca() +" Id: "+ prod.getId());
            openDialog(prod);
        }
    }


    class Task2 extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            myRef.child("Productos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaItem.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Producto p = snapshot.getValue(Producto.class);

                        listaItem.add(p);
                        nombreDeProductos.add(p.getMarca());
                        adaptador = new AdapterAdmin(context, listaItem);
                        listViewAdmin.setAdapter(adaptador);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    private void recuperarProcductoBorrado(){
        Snackbar snackbar = Snackbar.make(view, productoEliminado.getNombre() + " Eliminado", Snackbar.LENGTH_LONG);

        snackbar.setAction("Deshacer", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(productoEliminado);
            }
        });

        snackbar.setActionTextColor(Color.GREEN);
        snackbar.show();
    }



}



