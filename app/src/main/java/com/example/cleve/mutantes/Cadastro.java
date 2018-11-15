package com.example.cleve.mutantes;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cadastro extends AppCompatActivity {

    private int cont = 1;
    private EditText parent;
    private ConstraintLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        this.parent =(EditText) findViewById(R.id.poderes);
        this.layout = (ConstraintLayout) findViewById(R.id.CLayout);
    }

    public void adicionarCampo(View view){
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
        cs.connect((cont), ConstraintSet.TOP, parent.getId(), ConstraintSet.BOTTOM, 8);
        cs.connect((cont), ConstraintSet.LEFT, parent.getId(), ConstraintSet.LEFT, 0);
        cs.connect((cont), ConstraintSet.RIGHT, parent.getId(), ConstraintSet.RIGHT, 0);
        cs.applyTo(this.layout);

        this.parent = et;

    }

    public void removerCampo(View view){
        if (cont == 1){
            Toast.makeText(this, "Deve ter ao menos um poder para ser um mutante!", Toast.LENGTH_LONG).show();
        } else{
            this.layout.removeView((EditText) findViewById(cont));
            if(cont==2){
                this.parent =(EditText) findViewById(R.id.poderes);
            } else {
                this.parent = (EditText) findViewById(cont - 1);
            }
            cont--;
        }

    }

    public void salvar(View view){
        Mutante mutante = new Mutante();
        TextView nome = (TextView) findViewById(R.id.nome);
        mutante.setNome(nome.getText().toString());

        int idPoder;
        List<String> poderes = new ArrayList();
        for(int i = 1; i <= cont; i++){
            idPoder = getResources().getIdentifier("poder"+ cont, "id", getPackageName());
            nome = (TextView) findViewById(idPoder);
            poderes.add(nome.getText().toString());
        }
        mutante.setPoderes(poderes);

       OpsBD bd = new OpsBD(this);
       try {
           bd.open();
           bd.addMutante(mutante);
       }catch (SQLException e){
           System.out.print(e);
       }finally {
           bd.close();
       }

    }

}
