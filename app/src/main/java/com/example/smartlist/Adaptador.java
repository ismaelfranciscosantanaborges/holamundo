package com.example.smartlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<ProductosComprados> lista;
    Context context;

    public Adaptador(Context context, ArrayList<ProductosComprados> lista) {
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

            ProductosComprados p = (ProductosComprados) getItem(position);

            convertView = LayoutInflater.from(context).inflate(R.layout.item2, null);

            TextView nombreP2 = convertView.findViewById(R.id.tvNombreP2);
            TextView tvCantidadProducto = convertView.findViewById(R.id.cantidadDeProducto);
            TextView tvTotalProducto = convertView.findViewById(R.id.totalProducto);
            TextView tvPrecio = convertView.findViewById(R.id.productoUnidad);

            nombreP2.setText(p.getNombre());
            tvTotalProducto.setText(p.getPrecioTotal() + "");
            tvCantidadProducto.setText(p.getCantidad() + "");
            tvPrecio.setText(p.getPrecio() + "");




        return convertView;
    }
}
