package com.example.smartlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AgregarDialog extends AppCompatDialogFragment {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private EditText etNombreP, etPrecioP, etMarcaP, etDescripcionP;
    private TextView tvIdP;
    private Button btnAgregarP, btnCancelarP;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_agregar, null);

        builder.setView(view)
                .setTitle("Agregar Producto");

        tvIdP = view.findViewById(R.id.tvIdP);
        etNombreP = view.findViewById(R.id.etAgregarNombreP);
        etPrecioP = view.findViewById(R.id.etAgregarPrecioP);
        etMarcaP = view.findViewById(R.id.etAgregarMarcaP);
        etDescripcionP = view.findViewById(R.id.etAgregarDescripcionP);
        btnAgregarP = view.findViewById(R.id.btnAgregarP);
        btnCancelarP = view.findViewById(R.id.btnCancelarP);

        // Write a message to the database
        initicializarFirebase();



        btnCancelarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnAgregarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               agregarProducto();
               limpiarEditText();
            }
        });
        return builder.create();
    }

    private  void initicializarFirebase(){
        FirebaseApp.initializeApp(getContext());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    myRef.child("Productos").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Producto producto = snapshot.getValue(Producto.class);
                           // Toast.makeText(getContext(), "hahahahahaahahha", Toast.LENGTH_SHORT).show();

                            if(tvIdP.getText().toString() == producto.getId()){
                                idExiste = true;
                                Toast.makeText(getContext(), "Ya este producto ha sido creado", Toast.LENGTH_SHORT).show();
                                tvIdP.setText(producto.getId());
                                etNombreP.setText(producto.getNombre());
                                etMarcaP.setText(producto.getMarca());
                                etDescripcionP.setText(producto.getDescripcion());
                                etPrecioP.setText(producto.getPrecio() + "");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private boolean idExiste = false;
    private void agregarProducto(){
        String id = tvIdP.getText().toString();
        String nombre = etNombreP.getText().toString();
        String marca = etMarcaP.getText().toString();
        String descripcion = etDescripcionP.getText().toString();
        double precio = 0;
        if(!etPrecioP.getText().toString().isEmpty()){
            precio = Double.parseDouble(etPrecioP.getText().toString());
        }

        if(!id.isEmpty() && !nombre.isEmpty() && !descripcion.isEmpty() && !marca.isEmpty() && precio != 0){
            final Producto producto = new Producto(id,nombre,descripcion, marca,precio);

            if(!idExiste){
                myRef.child("Productos").child(producto.getId()).setValue(producto);
                Toast.makeText(getContext(), "SE HA AGREGADO EL PRODUCTO", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Ya este producto ha sido creado", Toast.LENGTH_SHORT).show();
            }

            idExiste = false;

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

}

