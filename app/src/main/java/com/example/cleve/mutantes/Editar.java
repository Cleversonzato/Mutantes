package com.example.cleve.mutantes;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Editar extends AppCompatActivity {

    private Mutante mutante = new Mutante();
    private TextView nome;
    private int cont =0;
    private EditText parent;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        this.parent =  (EditText) findViewById(R.id.nome);
        this.layout = (ConstraintLayout) findViewById(R.id.Clay);
        Intent it = getIntent();
        Bundle mut = it.getExtras();
        this.mutante.setId(mut.getLong("id"));
        TextView nome = (EditText) findViewById(R.id.nome);
        nome.setText(mut.getString("nome"));

        // banco de dados
        List<String> poderes = new ArrayList();
        OpsBD op = new OpsBD(this);
        try{
            op.open();
            poderes = op.poderesMutante(mutante.getId());
        } catch(Exception e){
            e.printStackTrace();
        }

        //parte dinâmica
       EditText et;
        for(String p : poderes) {
            et = this.adicionarCampo(null);
            et.setText(p);
        }

    }

    public void atualizar(View view){

        TextView nome = (EditText) findViewById(R.id.nome);
        this.mutante.setNome(nome.getText().toString());
        Boolean vazio = false;
        if(mutante.getNome().isEmpty()){
            vazio = true;
        }

        List<String> poderes = new ArrayList();
        EditText poder;

        for(int i = 1; i <= cont; i++){
            poder = (EditText) findViewById(i);
            if(poder.getText().toString().isEmpty()){
                vazio = true;
            }
            poderes.add(poder.getText().toString());
        }
        this.mutante.setPoderes(poderes);
        if(vazio){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        }else {

            OpsBD bd = new OpsBD(this);
            try {
                bd.open();
                bd.atualizaMutante(this.mutante, this);
                setResult(1);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                bd.close();
            }
        }
    }

    public EditText adicionarCampo(View view){
        cont++;

        EditText et = new EditText(this);
        et.setId(cont);
        et.setHint("Poder");
        et.setText("");

        ConstraintLayout.LayoutParams param = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(param);
        this.layout.addView(et);

        ConstraintSet cs = new ConstraintSet();
        cs.clone(this.layout);
        cs.connect((cont), ConstraintSet.TOP, parent.getId(), ConstraintSet.BOTTOM, 48);
        cs.connect((cont), ConstraintSet.LEFT, parent.getId(), ConstraintSet.LEFT, 0);
        cs.connect((cont), ConstraintSet.RIGHT, parent.getId(), ConstraintSet.RIGHT, 0);
        cs.applyTo(this.layout);
        this.parent = et;
        return et;

    }

    public void removerCampo(View view){
        if (cont == 1){
            Toast.makeText(this, "Deve ter ao menos um poder para ser um mutante!", Toast.LENGTH_LONG).show();
        } else{
            this.layout.removeView((EditText) findViewById(cont));
            if(cont==2){
                this.parent =(EditText) findViewById(R.id.nome);
            } else {
                this.parent = (EditText) findViewById(cont - 1);
            }
            cont--;
        }

    }

    public void voltar(View view){
        finish();
    }
}
