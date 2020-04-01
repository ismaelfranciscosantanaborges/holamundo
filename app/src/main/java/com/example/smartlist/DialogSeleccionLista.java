package com.example.smartlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DialogSeleccionLista extends AppCompatDialogFragment {
    private DatabaseHelper databaseHelper;
    private ArrayList<Lista1> listaItem;
    private ListView listView;
    private RecyclerView.LayoutManager layoutManager;
    private ListaDeCompra listener;
    private RViewAdapter adaptadorRV;
    private Button btnCancelar;
    private Context context;
    private RecyclerView recyclerView;
    private Adatador adaptadorP;
    private ListView listViewP;
    private AdaptadorSL adaptador;
    private String nombrelista;

    public DialogSeleccionLista setMessage(Context context,ListView listViewP, RecyclerView recyclerView , ArrayList<Lista1> listaItem) {
        this.context = context;
        this.listaItem = listaItem;
        this.listViewP = listViewP;
        this.recyclerView = recyclerView;

        return this;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_eleccionar_lista, null);

        builder.setView(view);

        init(view);


        return builder.create();
    }
    private ArrayList<String> arrayNombreLista;
    private void init(View view){
        listView = view.findViewById(R.id.listViewSL);
        btnCancelar = view.findViewById(R.id.btnCancelarSL);

        databaseHelper = DatabaseHelper.obtenerConexion(getContext());

        arrayNombreLista = databaseHelper.getTodosLosNombreLista();

        adaptador = new AdaptadorSL(getContext(), arrayNombreLista);
        listView.setAdapter(adaptador);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listaItem.clear();

                String nombreLista = arrayNombreLista.get(position);

                listaItem = databaseHelper.getAllNombreLista(nombreLista);

                recyclerView.setVisibility(View.GONE);
                listViewP.setVisibility(View.VISIBLE);
                adaptadorP = new Adatador(context, listaItem, listaItem.size());
                listViewP.setAdapter(adaptadorP);
                dismiss();
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
