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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Scanner_ListaDelTotal extends Fragment {



    private View rootView;
    private ArrayList<Producto> listaItem;
    private FloatingActionButton floatingActionButton;
    private Adaptador adaptador;
    private ListView listView;
    private CheckBox chkAll;
    private int cantidadTotal;
    private TextView tvCodigoLeido;

    public Scanner_ListaDelTotal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_scanner__lista_del_total, container, true);


        tvCodigoLeido = rootView.findViewById(R.id.codigoLeido);
        listView = rootView.findViewById(R.id.listView1);
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);




        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scaner();

                //UtilizandoButtonSheet bottonSheet = new UtilizandoButtonSheet();
                //bottonSheet.show(getFragmentManager(), "exampleBottonSheet");

                openDialog();


            }
        });

        adaptador = new Adaptador(getContext(), getArrayList());

        adaptador.notifyDataSetChanged();

        listView.setAdapter(adaptador);


        return rootView;
    }

    private void openDialog(){
        AgregarDialog agregarDialog = new AgregarDialog();
        agregarDialog.show(getFragmentManager(), "Agregar Producto");
    }

    private ArrayList<Producto> getArrayList(){

        listaItem = new ArrayList<Producto>();

        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));
        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));        listaItem.add(new Producto("13", "Pan de agua", "Este es un pan super bueno", "Pan man", 5));
        listaItem.add(new Producto("43","Arina de maiz", "Super Arina de maiz, buena y barata", "Arina el print", 30));
        listaItem.add(new Producto("13", "Arroz","El arroz numero 1 del mercado","Arroz primiun la garza", 60.3));
        listaItem.add(new Producto("43","Refresco 4ml", "Refresco grande, para toda la familia", "Cola-real", 80));


        cantidadTotal = listaItem.size();
        return listaItem;
    }

    private void scaner(){

        IntentIntegrator intent = new IntentIntegrator(getActivity());
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("ESCANEAR EL CODIGO");
        intent.setCameraId(0);
        intent.setOrientationLocked(false);
        intent.setCaptureActivity(CapturaActivityPortrait.class);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null){
            if(result.getContents() != null){
                Toast.makeText(getContext(), "EL CODIGO DE BARRA ES: " + result.getContents().toString(), Toast.LENGTH_LONG).show();

                tvCodigoLeido.setText(result.getContents());
                Log.e("ERRORRRRRRR", result.getContents() + "");
            }else{
                Toast.makeText(getContext(), "HAS CANCELADO EL SCANER", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode,data);
        }

        Toast.makeText(getContext(), "No paso nada", Toast.LENGTH_SHORT).show();

    }
}
