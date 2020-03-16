package com.example.smartlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.zip.Inflater;

public class Login extends AppCompatActivity {

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
