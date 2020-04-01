package com.example.smartlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorSL extends BaseAdapter {
    private Context context;
    private ArrayList<String> listaItem;

    public AdaptadorSL(Context context, ArrayList<String> listaItem){
        this.listaItem = listaItem;
        this.context = context;
    }


    @Override
    public int getCount() {
        return listaItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listaItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String item = (String) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.item_seleccionar_lista, null);

        TextView nombreLista = convertView.findViewById(R.id.nombreLista);
        TextView fecha = convertView.findViewById(R.id.fecha);

        if(listaItem.size() > 0 && item != null){
            String[] array = item.split(",");
            nombreLista.setText(array[0]);
            fecha.setText(array[1]);
        }


        return convertView;
    }
}
