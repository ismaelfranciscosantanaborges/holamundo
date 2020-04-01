package com.example.smartlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ComprarDialog extends AppCompatDialogFragment {

    private DatabaseHelperCompra databaseHelperCompra;
    private Adaptador adaptador;
    private TextView tvNombre, tvPrecio, tvTotal;
    private ImageView imgDismiss;
    private EditText etCantidad;
    private Button btnAgregar,btnCancelar;
    private double total;
    private TextView tvTotalGeneral;
    Context context;
    private boolean existe;
    ListView listView;
    private ArrayList<String> todosLosNombre;
    ArrayList<ProductosComprados> listaItem;
    ProductosComprados producto;

    public ComprarDialog setMessage(Context context, ListView listView, ArrayList<ProductosComprados> listaItem,
                                    ArrayList<String> todoLosNombre, ProductosComprados producto, TextView tvTotalGeneral, boolean existe) {
        this.context = context;
        this.listaItem = listaItem;
        this.tvTotalGeneral = tvTotalGeneral;
        this.todosLosNombre = todoLosNombre;
        this.existe = existe;
        this.listView = listView;
        this.producto = producto;

        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_comprar, null);

        builder.setView(view);

        init(view);




        return builder.create();
    }
    private double precioDelProducto;
    private int cantidadDeProducto;
    private void init(View view){
        databaseHelperCompra = DatabaseHelperCompra.obtenerConexion(getContext());
        LimpiarYRellenarLista();
        tvNombre = view.findViewById(R.id.tvNombrePC);
        tvPrecio = view.findViewById(R.id.tvPrecioPC);
        tvTotal = view.findViewById(R.id.tvTotalPC);
        etCantidad = view.findViewById(R.id.etCantidadPC);
        btnAgregar = view.findViewById(R.id.btnAgregarPC);
        btnCancelar = view.findViewById(R.id.btnCancelarPC);
        imgDismiss = view.findViewById(R.id.imgDismiss);

        tvNombre.setText(producto.getNombre());
        tvPrecio.setText(producto.getPrecio() + "");
        etCantidad.setText("1");

        if(existe){
            btnAgregar.setText("AUCTUALIZAR");
            btnCancelar.setText("ELIMINAR");
            tvTotal.setText(producto.getPrecioTotal() + "");
            etCantidad.setText(producto.getCantidad() + "");



        }else{
            btnAgregar.setText("AGREGAR");
        }

        precioDelProducto = Double.parseDouble(tvPrecio.getText().toString());
        cantidadDeProducto = Integer.parseInt(etCantidad.getText().toString());

        if(!existe)
            tvTotal.setText(tvPrecio.getText().toString());



        etCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!etCantidad.getText().toString().isEmpty()){
                    cantidadDeProducto = Integer.parseInt(etCantidad.getText().toString());
                    tvTotal.setText((precioDelProducto * cantidadDeProducto) + "");
                }else{
                    tvTotal.setText("");
                }
                etCantidad.setBackgroundColor(Color.WHITE);
            }
        });



        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etCantidad.getText().toString().isEmpty()){
                    int cantidad = Integer.parseInt(etCantidad.getText().toString());
                    if(cantidad > 0){
                        agregarProducto();
                    }
                    else{
                        Toast.makeText(getContext(), "LA CANTIDAD NO PUEDE SER 0", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getContext(), "DEBE ESPECIFICAR UNA CANTIDAD", Toast.LENGTH_SHORT).show();
                    etCantidad.setBackgroundColor(Color.RED);
                    etCantidad.setFocusable(true);
                }


            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(existe){
                    databaseHelperCompra.deleteEntidad(producto);
                    LimpiarYRellenarLista();
                    Toast.makeText(getContext(), "Se a eliminado CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });

        imgDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void agregarProducto(){
        double precioTotal = 0;
        int cantidad = 0;
        if(!tvTotal.getText().toString().isEmpty()){
            precioTotal = Double.parseDouble(tvTotal.getText().toString());
        }
        if(!etCantidad.getText().toString().isEmpty()){
            cantidad = Integer.parseInt(etCantidad.getText().toString());
        }
        if(precioTotal != 0 && cantidad != 0){
            producto.setPrecioTotal(precioTotal);
            producto.setCantidad(cantidad);
            if(existe){
                databaseHelperCompra.updateEntidad(producto);
                Toast.makeText(getContext(), "SE HA ACTUALIZADO A LA COMPRA", Toast.LENGTH_SHORT).show();
            }
            else{
                databaseHelperCompra.addEntidad(producto);
                Toast.makeText(getContext(), "SE HA AGREGADO A LA COMPRA", Toast.LENGTH_SHORT).show();
            }

            //todosLosNombre.add(producto.getNombre());
            LimpiarYRellenarLista();
            dismiss();
        }else{
            Toast.makeText(getContext(), "LOS CAMPOS NO ESTAN CORRECTOS :(", Toast.LENGTH_SHORT).show();
        }
    }

    private void LimpiarYRellenarLista(){
        listaItem.clear();
        listaItem = databaseHelperCompra.getAllEntidad();
        total = 0;
        for(ProductosComprados p: listaItem){
            total += p.getPrecioTotal();
            Log.e("los precios", total + "");
        }

        tvTotalGeneral.setText(total + "");

        adaptador = new Adaptador(context,listaItem);

        listView.setAdapter(adaptador);
    }


}
