package com.example.smartlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.Inflater;

import static androidx.core.content.ContextCompat.getSystemService;

public class Adatador extends BaseAdapter {


    private ArrayList<Lista1> lista1;
    private Context context;
    private LayoutInflater inflater;
    private boolean[] itemSelection;

    public Adatador(Context context, ArrayList<Lista1> lista1, int size) {
        this.lista1 = lista1;
        this.context = context;


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemSelection = new boolean[size];
    }

    public void addItem(final Lista1 item) {
        lista1.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lista1.size();
    }

    @Override
    public Object getItem(int position) {
        return lista1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Lista1 lista1 = (Lista1) getItem(position);

        convertView = inflater.inflate(R.layout.item1, null);

        final ViewHolder holder = new ViewHolder();

        holder.chkItem = (CheckBox) convertView.findViewById(R.id.checkbox);
        holder.chkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                itemSelection[position] = holder.chkItem.isChecked();
            }
        });

        holder.chkItem.setChecked(itemSelection[position]);
        convertView.setTag(holder);
        //holder.chkItem.setText(getItem(position));

        //-------------------

        TextView tvNombre = convertView.findViewById(R.id.tvNombre);
        TextView tvCantidad = convertView.findViewById(R.id.tvCantidad);
        CheckBox checkBox = convertView.findViewById(R.id.checkbox);


        tvNombre.setText(lista1.nombre);
        tvCantidad.setText(lista1.cantidad + "");


        return convertView;
    }

    public int getItemsLength() {
        if (itemSelection == null) return 0;
        return itemSelection.length;
    }

    public void set(int i, boolean b) {
        itemSelection[i] = b;
    }




    public static class ViewHolder{

        public CheckBox chkItem;

    }
}

