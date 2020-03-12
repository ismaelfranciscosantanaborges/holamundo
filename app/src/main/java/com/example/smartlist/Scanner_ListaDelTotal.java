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
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Scanner_ListaDelTotal extends Fragment {

    private View rootView;
    private ArrayList<Lista1> listaItem;
    private FloatingActionButton floatingActionButton;
    private Adatador adaptador;
    private ListView listView;
    private CheckBox chkAll;
    private int cantidadTotal;
    private Context context;

    public Scanner_ListaDelTotal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_scanner__lista_del_total, container, true);

        listView = rootView.findViewById(R.id.listView1);
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //scaner();

                //UtilizandoButtonSheet bottonSheet = new UtilizandoButtonSheet();
                //bottonSheet.show(getFragmentManager(), "exampleBottonSheet");


            }
        });

        adaptador = new Adatador(getContext(), getArrayList(), cantidadTotal);

        listView.setAdapter(adaptador);


        return rootView;
    }

    private ArrayList<Lista1> getArrayList(){

        listaItem = new ArrayList<Lista1>();

        listaItem.add(new Lista1("Pan de agua", 10, false));
        listaItem.add(new Lista1("Arina de maiz", 10, false));
        listaItem.add(new Lista1("Arroz primiun", 1, false));
        listaItem.add(new Lista1("Refresco de lo grande", 4, false));
        listaItem.add(new Lista1("Pan de agua", 6, false));
        listaItem.add(new Lista1("Pan de agua", 9, false));
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
                Log.d("ERRORRRRRRR", result.getContents());
            }else{
                Toast.makeText(getContext(), "HAS CANCELADO EL SCANER", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode,data);
        }


    }
}
