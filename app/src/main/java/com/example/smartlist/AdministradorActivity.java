package com.example.smartlist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class AdministradorActivity extends AppCompatActivity implements IEvaluable {

    private AdapterAdmin adaptador;
    private ArrayList<Producto> listaItem;
    private FloatingActionButton floatingActionButton;
    private TextView tvCodigoLeido;
    private ListView listViewAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        listViewAdmin = findViewById(R.id.listViewAdmin);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        adaptador = new AdapterAdmin(getApplicationContext(), getArrayList());

        listViewAdmin.setAdapter(adaptador);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scaner();

                //UtilizandoButtonSheet bottonSheet = new UtilizandoButtonSheet();
                //bottonSheet.show(getFragmentManager(), "exampleBottonSheet");

                openDialog();


            }
        });

    }

    private void openDialog(){
        AgregarDialog agregarDialog = new AgregarDialog();
        agregarDialog.show(getSupportFragmentManager(), "Agregar Producto");
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

        return listaItem;
    }

    private void scaner(){

        IntentIntegrator intent = new IntentIntegrator(this);
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
                Toast.makeText(getApplicationContext(), "EL CODIGO DE BARRA ES: " + result.getContents().toString(), Toast.LENGTH_LONG).show();

                tvCodigoLeido.setText(result.getContents());
                Log.e("ERRORRRRRRR", result.getContents() + "");
            }else{
                Toast.makeText(getApplicationContext(), "HAS CANCELADO EL SCANER", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode,data);
        }

        Toast.makeText(getApplicationContext(), "No paso nada", Toast.LENGTH_SHORT).show();

    }

}


