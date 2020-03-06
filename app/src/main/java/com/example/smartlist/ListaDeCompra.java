package com.example.smartlist;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaDeCompra extends Fragment {

    private View rootView;
    private ArrayList<Lista1> listaItem;
    private Adatador adaptador;
    private ListView listView;
    private CheckBox chkAll;
    private int cantidadTotal;

    public ListaDeCompra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lista_de_compra, container, true);

        listView = rootView.findViewById(R.id.listView1);

        adaptador = new Adatador(getContext(), getArrayList(), cantidadTotal);

        listView.setAdapter(adaptador);

        return rootView;
    }

    private ArrayList<Lista1> getArrayList(){

        listaItem = new ArrayList<Lista1>();

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
