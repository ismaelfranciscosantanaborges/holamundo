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
    private Context context;
    private String codigoLeido;
    private ImageView btnImgDescrementar,btnImgIncrementar;
    private TextView tvCantidadProducto, tvTotalProducto;

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

        listaItem.add(new Producto("13", "Pan de agua", 10.3));
        listaItem.add(new Producto("43","Arina de maiz", 10));
        listaItem.add(new Producto("13", "Arroz primiun", 100.3));
        listaItem.add(new Producto("43","Refresco de lo grande", 10));
        listaItem.add(new Producto("13", "Pan de agua", 104));
        listaItem.add(new Producto("43","Arina de maiz", 1000));
        listaItem.add(new Producto("13", "Arroz primiun", 3000));
        listaItem.add(new Producto("43","Refresco de lo grande", 75));
        listaItem.add(new Producto("13", "Pan de agua", 230));
        listaItem.add(new Producto("43","Arina de maiz", 550));
        listaItem.add(new Producto("13", "Arroz primiun", 492));
        listaItem.add(new Producto("43","Refresco de lo grande", 90));


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
                codigoLeido = result.getContents();
                Log.d("ERRORRRRRRR", result.getContents());
            }else{
                Toast.makeText(getContext(), "HAS CANCELADO EL SCANER", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode,data);
        }


    }
}
