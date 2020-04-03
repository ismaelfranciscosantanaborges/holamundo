package com.example.smartlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterAdmin extends BaseAdapter {
    ArrayList<Producto> lista;
    Context context;

    public AdapterAdmin(Context context, ArrayList<Producto> lista){
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

        Producto p = (Producto) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_productos, null);

        TextView nombre = convertView.findViewById(R.id.tvNombreP2);
        TextView id = convertView.findViewById(R.id.tvIdP);
        TextView precio = convertView.findViewById(R.id.tvPrecioP);

        nombre.setText(p.getMarca());
        id.setText(p.getId());
        precio.setText(p.getPrecio() + "");


        return convertView;
    }
}
