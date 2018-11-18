package com.example.cleve.mutantes;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class Listar extends ListActivity {

    private MutantesAdapter mt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        List<Mutante> mutantes = this.listarMutantes();
        this.mt = new MutantesAdapter(this, mutantes) {
            @Override
            public void mais(Mutante mutante) {
                Intent it = new Intent(getApplicationContext(), Editar.class);
                Bundle mut = new Bundle();
                mut.putLong("id", mutante.getId());
                mut.putString("nome", mutante.getNome());
                it.putExtras(mut);
                startActivityForResult(it, 1);
            }

            @Override
            public void remove(Mutante mutante) {
                deletarMutante(mutante);
            }

        };
        if(mutantes.size() > 0){
            setListAdapter(this.mt);
        }
        this.mt.novosDados(mutantes);

    }
    private List<Mutante> listarMutantes(){

        List<Mutante> mutantes = null;
        OpsBD op = new OpsBD(this);
        try{
            op.open();
            mutantes = op.listaMutantes();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            op.close();
        }
        return(mutantes);
    }

    public void deletarMutante(Mutante mutante){
        OpsBD op = new OpsBD(this);
        try{
            op.open();
            op.removeMutante(mutante);
            List<Mutante> mutantes = this.listarMutantes();
            this.mt.novosDados(mutantes);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            op.close();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if (resultCode ==1){
                this.mt.novosDados(this.listarMutantes());
            }

        }
    }

}
