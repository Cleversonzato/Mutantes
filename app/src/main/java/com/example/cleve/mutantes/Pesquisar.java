package com.example.cleve.mutantes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Pesquisar extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private List<String> lista = new ArrayList();
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar);
        this.adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.lista);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(this.adapter);

    }
    public void pesquisar(View view) {
        String busca = ((EditText) findViewById(R.id.editText)).getText().toString();
        if (busca.isEmpty()) {
            Toast.makeText(this, "Digite uma chave de busca", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton rb = (RadioButton) findViewById(R.id.rb1);
            OpsBD op = new OpsBD(this);
            if (rb.isChecked()) {
                String nome = null;
                try {
                    op.open();
                    nome = op.buscaMutanteNome(busca, this);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    op.close();
                }
                if (nome != null) {
                    this.lista.clear();
                    this.lista.add(nome);
                }
            }else {
                List<String> nomes = null;
                try {
                    op.open();
                    nomes = op.buscaMutantePorPoder(busca, this);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    op.close();
                }
                if (nomes != null) {
                    this.lista.clear();
                    for (String n: nomes){
                        this.lista.add(n);
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
        }
    }
}
