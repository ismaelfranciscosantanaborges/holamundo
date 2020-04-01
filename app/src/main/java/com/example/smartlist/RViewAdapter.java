package com.example.smartlist;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RViewAdapter extends RecyclerView.Adapter<RViewAdapter.DataObjectHolder> implements View.OnClickListener{

    private Context context;
    private ArrayList<Lista1> listaItem;

    DatabaseHelper databaseHelper;
    private View.OnClickListener listener;

    public RViewAdapter(Context context, ArrayList<Lista1> listaItem){
        this.context = context;
        this.listaItem = listaItem;
        databaseHelper = DatabaseHelper.obtenerConexion(context);
    }



    public class DataObjectHolder extends RecyclerView.ViewHolder{

        private TextView tvNombre,tvCantidad;
        public LinearLayout layoutBorrar;
        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.Nombre);
            tvCantidad = itemView.findViewById(R.id.Cantidad);
            layoutBorrar = itemView.findViewById(R.id.layoutBorrar);
        }
    }

    public void removeItem(int position){

        Integer resultado = databaseHelper.deleteEntidad(listaItem.get(position));
        listaItem.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Lista1 lista,int position){
        listaItem.add(position, lista);
        databaseHelper.addEntidad(lista);
        notifyItemInserted(position);
    }


    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_lista, null);
        view.setOnClickListener(this);
        return new DataObjectHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder holder, int position) {
        holder.tvNombre.setText(listaItem.get(position).getNombre());
        holder.tvCantidad.setText(listaItem.get(position).getCantidad() + "");
    }



    @Override
    public int getItemCount() {
        return listaItem.size();
    }
}
