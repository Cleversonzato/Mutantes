package com.example.cleve.mutantes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cadastrar(View view){
        startActivity(new Intent(this, Cadastro.class));
    }
    public void listar(View view){
        startActivity(new Intent(this, ListarTodos.class));
    }
    public void pesquisar(View view){
        startActivity(new Intent(this, Pesquisar.class));
    }
    public void sair(View view){
        finish();
    }

}
