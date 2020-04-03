package com.example.smartlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Login extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<Producto> listaItem;

    private EditText etUser,etPassoword;
    private Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUser = findViewById(R.id.etUser);
        etPassoword = findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnIngresar);



        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CFabrica fabrica = new CFabrica(etUser.getText().toString(), etPassoword.getText().toString());

                boolean evaluar = fabrica.tipoUsuario();
                Intent myIntent;
                if(evaluar){
                    myIntent = new Intent(getApplicationContext(), AdministradorActivity.class);
                }else{
                    myIntent = new Intent(getApplicationContext(), MainActivity.class);
                }

                startActivity(myIntent);
            }
        });
    }


}
