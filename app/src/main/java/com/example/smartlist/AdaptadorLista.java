package com.example.smartlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorLista extends BaseAdapter {
    private ArrayList<Lista1> lista;
    private Context context;

    public AdaptadorLista(Context context, ArrayList<Lista1> lista){
        this.lista = lista;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Lista1 lista = (Lista1) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_lista, null);
        TextView nombre = convertView.findViewById(R.id.Nombre);
        TextView cantidad = convertView.findViewById(R.id.Cantidad);

        nombre.setText(lista.getNombre());
        cantidad.setText(lista.getCantidad() + "");


        return convertView;
    }
}
