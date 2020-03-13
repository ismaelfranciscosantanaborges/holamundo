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

import java.util.HashMap;
import java.util.Map;

public class AgregarDialog extends AppCompatDialogFragment {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private EditText etNombreP, etPrecioP;
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


        // Write a message to the database
        initicializarFirebase();


        tvIdP = view.findViewById(R.id.tvIdP);
        etNombreP = view.findViewById(R.id.etAgregarNombreP);
        etPrecioP = view.findViewById(R.id.etAgregarPrecioP);
        btnAgregarP = view.findViewById(R.id.btnAgregarP);
        btnCancelarP = view.findViewById(R.id.btnCancelarP);

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
        String id = tvIdP.getText().toString();
        String nombre = etNombreP.getText().toString();
        double precio = 0;
        if(!etPrecioP.getText().toString().isEmpty()){
            precio = Double.parseDouble(etPrecioP.getText().toString());
        }


        if(!id.isEmpty() && !nombre.isEmpty() && precio != 0){
            Producto producto = new Producto(id,nombre,precio);

            myRef.child("Productos").child(producto.getId()).setValue(producto);
            Toast.makeText(getContext(), "SE HA AGREGADO EL PRODUCTO", Toast.LENGTH_SHORT).show();


        }else{

            Toast.makeText(getContext(), "No se pudo guardar el producto", Toast.LENGTH_SHORT).show();
        }
    }

}

