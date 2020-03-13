package com.example.smartlist;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaDeCompra extends Fragment {

    private View rootView;
    private ArrayList<Lista1> listaItem;
    private FloatingActionButton floatingActionButton;
    private Adatador adaptador;
    private ListView listView;
    private CheckBox chkAll;
    private int cantidadTotal;
    private Context context;

    public ListaDeCompra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lista_de_compra, container, true);


        listView = rootView.findViewById(R.id.listView1);
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //UtilizandoButtonSheet bottonSheet = new UtilizandoButtonSheet();
                //bottonSheet.show(getFragmentManager(), "exampleBottonSheet");
            }
        });

        adaptador = new Adatador(getContext(), getArrayList(), cantidadTotal);

        listView.setAdapter(adaptador);


        return rootView;
    }

    private ArrayList<Lista1> getArrayList(){

        listaItem = new ArrayList<Lista1>();

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

        cantidadTotal = listaItem.size();
        return listaItem;
    }

}
