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
    ArrayList<Producto> lista;
    Context context;

    public Adaptador(Context context, ArrayList<Producto> lista) {
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

    TextView tvCantidadProducto;
    TextView tvTotalProducto;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            Producto p = (Producto) getItem(position);

            convertView = LayoutInflater.from(context).inflate(R.layout.item2, null);

            TextView nombreP2 = convertView.findViewById(R.id.tvNombreP2);
            ImageView btnImgDescrementar = convertView.findViewById(R.id.btnImgDescrementar);
            ImageView btnImgIncrementar = convertView.findViewById(R.id.btnImgIncrementar);
            tvCantidadProducto = convertView.findViewById(R.id.tvCantidadProducto);
            tvTotalProducto = convertView.findViewById(R.id.totalProducto);

            nombreP2.setText(p.getNombre());
            tvTotalProducto.setText(p.getPrecio() + "");

            btnImgIncrementar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cantidad = Integer.parseInt(tvCantidadProducto.getText().toString());
                    if(cantidad > 0){
                        cantidad++;
                        tvCantidadProducto.setText(cantidad + "");
                    }
                }
            });

            btnImgDescrementar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cantidad = Integer.parseInt(tvCantidadProducto.getText().toString());
                    if(cantidad > 0){
                        cantidad--;
                        tvCantidadProducto.setText(cantidad + "");
                    }
                }
            });



        return convertView;
    }
}
