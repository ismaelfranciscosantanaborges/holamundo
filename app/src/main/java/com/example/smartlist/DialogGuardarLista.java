package com.example.smartlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DialogGuardarLista extends AppCompatDialogFragment {

    private DatabaseHelper databaseHelper;
    private EditText etNombre;
    private Button btnGuardar, btnCancelar;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private RecyclerView listView;
    private ArrayList<Lista1> listaItem;
    private RViewAdapter adaptador;

    public DialogGuardarLista setMessage(Context context, RecyclerView listView, ArrayList<Lista1> listaItem) {
        this.context = context;
        this.listaItem = listaItem;
        this.listView = listView;

        return this;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_guardar_lista, null);

       init(view);

        builder.setView(view);

        return builder.create();

    }

    private void init(View view){
        etNombre = view.findViewById(R.id.etNombreListaG);
        btnCancelar = view.findViewById(R.id.btnCancelarG);
        btnGuardar = view.findViewById(R.id.btnGuardarG);

        databaseHelper = DatabaseHelper.obtenerConexion(getContext());
        layoutManager = new LinearLayoutManager(getContext());


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etNombre.getText().toString().length() >= 3){
                    if(!databaseHelper.buscarNombreLista(etNombre.getText().toString())){

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        String formattedDate = df.format(c);
                        String nombreWithFecha = etNombre.getText().toString() + "," + formattedDate;

                        databaseHelper.updateNombreLista(nombreWithFecha);
                        Toast.makeText(getContext(), "Se ha guardado CORRECTAMETE", Toast.LENGTH_SHORT).show();

                        listaItem = databaseHelper.getAllEntidad(true);

                        adaptador = new RViewAdapter(getContext(), listaItem);
                        listView.setLayoutManager(layoutManager);
                        listView.setAdapter(adaptador);


                        dismiss();
                    }else{
                        Toast.makeText(getContext(), "Ya este nombre esta en uso", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "El nombre es muy corto", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
