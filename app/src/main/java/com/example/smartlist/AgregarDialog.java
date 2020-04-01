package com.example.smartlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgregarDialog extends AppCompatDialogFragment {

    private AdapterAdmin adaptador;
    private Context context;
    private DialogListener listener;
    private View view;
    private ImageView imgDismiss;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ListView listView;
    private ArrayList<Producto> listaItem;
    private EditText etNombreP, etPrecioP, etMarcaP, etDescripcionP;
    private TextView tvIdP;
    private Button btnAgregarP, btnCancelarP;

    private Producto producto;

    public AgregarDialog setMessage(Context context, ListView listView, ArrayList<Producto> listaItem, Producto producto) {
        this.context = context;
        this.view = view;
        this.listaItem = listaItem;
        this.listView = listView;
        this.producto = producto;

        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.layout_agregar, null);

        builder.setView(view)
                .setTitle("Agregar Producto");

        tvIdP = view.findViewById(R.id.tvIdP);
        etNombreP = view.findViewById(R.id.etAgregarNombreP);
        etPrecioP = view.findViewById(R.id.etAgregarPrecioP);
        etMarcaP = view.findViewById(R.id.etAgregarMarcaP);
        etDescripcionP = view.findViewById(R.id.etAgregarDescripcionP);
        btnAgregarP = view.findViewById(R.id.btnAgregarP);
        btnCancelarP = view.findViewById(R.id.btnCancelarP);
        imgDismiss = view.findViewById(R.id.imgDismiss);

        // Write a message to the database
        initicializarFirebase();
        String nombre = producto.getNombre();
        String marca = producto.getMarca();
        if(!nombre.equalsIgnoreCase("")){
            tvIdP.setText(producto.getId());
            etNombreP.setText(producto.getNombre());
            etPrecioP.setText(producto.getPrecio() + "");
            etMarcaP.setText(producto.getMarca());
            etDescripcionP.setText(producto.getDescripcion());
            btnAgregarP.setText("MODIFICAR");
            btnCancelarP.setText("ELIMINAR");
            btnCancelarP.setBackgroundColor(Color.RED);
        }else {
            tvIdP.setText(producto.getId());
        }

        imgDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        btnCancelarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCancelarP.getText().toString().equalsIgnoreCase("ELIMINAR")){
                    myRef.child("Productos").child(producto.getId()).removeValue();
                    listener.obtenerProductoEliminado(producto);
                    dismiss();
                }else{
                    dismiss();
                }
            }
        });

        btnAgregarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(btnAgregarP.getText().toString().equalsIgnoreCase("AGREGAR")){

                agregarProducto();
                limpiarEditText();

            }else{
                dismiss();
                Toast.makeText(getContext(), "SE HA MODIFICADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            }

            }
        });


        return builder.create();
    }

    private  void initicializarFirebase(){
        FirebaseApp.initializeApp(getContext());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }


    private void agregarProducto(){
        String id;
        String nombre;
        String marca;
        String descripcion;
        double precio = 0;
        try{
            id = tvIdP.getText().toString();
            nombre = etNombreP.getText().toString();
            marca = etMarcaP.getText().toString();
            descripcion = etDescripcionP.getText().toString();
            precio = 0;
            if(!etPrecioP.getText().toString().isEmpty()){
                precio = Double.parseDouble(etPrecioP.getText().toString());
            }
        }catch (Exception e){
            id = producto.getId();
            nombre = producto.getNombre();
            marca = producto.getMarca();
            descripcion = producto.getDescripcion();
            precio = producto.getPrecio();
        }


        if(!id.isEmpty() && !nombre.isEmpty() && !descripcion.isEmpty() && !marca.isEmpty() && precio != 0){
            final Producto producto = new Producto(id,nombre,descripcion, marca,precio);

                myRef.child("Productos").child(producto.getId()).setValue(producto);
                Toast.makeText(getContext(), "SE HA AGREGADO EL PRODUCTO", Toast.LENGTH_SHORT).show();


            listaItem.clear();
            Task1 task1 = new Task1();
            task1.execute();

        }else{

            Toast.makeText(getContext(), "No se pudo guardar el producto", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarEditText(){
        etNombreP.setText("");
        etDescripcionP.setText("");
        etMarcaP.setText("");
        etPrecioP.setText("");
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

    class Task1 extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            myRef.child("Productos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listaItem.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                        Producto p = snapshot.getValue(Producto.class);

                        listaItem.add(p);
                        Log.e("No es un error", p.getNombre() +" " + p.getMarca() + " "+ p.getId());
                    }


                    adaptador = new AdapterAdmin(context,listaItem);

                    listView.setAdapter(adaptador);
                    //listView.invalidateViews();
                    //listView.refreshDrawableState();


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
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (DialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " debes de implementar el DialogListener ");

        }

    }

    public interface DialogListener{
        void obtenerProductoEliminado(Producto p);
    }
}

