package com.example.cleve.mutantes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class MutantesAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    List<Mutante> mutantes;


    public MutantesAdapter(Context contexto, List<Mutante> mutantes) {

        this.inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mutantes = mutantes;
    }

    public void novosDados(List<Mutante> mutantes){
        this.mutantes = mutantes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mutantes.size();
    }

    @Override
    public Object getItem(int position) {
        return mutantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = this.inflater.inflate(R.layout.item_mutante, null);
        ((TextView)(view.findViewById(R.id.nome))).setText(mutantes.get(position).getNome());
        ((ImageButton)(view.findViewById(R.id.mais))).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mais(mutantes.get(position));
            }
        });
        ((ImageButton)(view.findViewById(R.id.remover))).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                remove(mutantes.get(position));
            }
        });
        return view;
    }

    public void mais(Mutante mutante){}

    public void remove(Mutante mutante) {}


    }
