package com.example.cleve.mutantes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpsBD {

    private BD bd;
    private SQLiteDatabase database;

    public OpsBD(Context contexto){
        bd = new BD(contexto);
    }

    public void open() throws SQLException{
        database = bd.getWritableDatabase();
    }
    public void close(){
        bd.close();
    }

    public Mutante addMutante(Mutante mutante, Context contexto) {
        ContentValues values = new ContentValues();
        values.put(BD.MUTANTE_NOME, mutante.getNome());

        long mutId = database.insert(BD.MUTANTES, null, values);
        if( mutId != -1) {
            mutante.setId(mutId);
            for (String poder : mutante.getPoderes()) {
                values = new ContentValues();
                values.put(BD.MUTANTE_ID, mutante.getId());
                values.put(BD.PODER_NOME, poder);
                database.insert(BD.PODERES, null, values);
            }
        }
        if( mutId != -1){
            Toast.makeText(contexto, "mutante " + mutante.getNome() + " salvo", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(contexto, "Falha ao adicionar o mutante", Toast.LENGTH_SHORT).show();
        }
        return mutante;
    }

    public String removeMutante(Mutante mutante){
        //Os poderes são deletados pelo Cascade
        database.delete(BD.MUTANTES, BD.MUTANTE_ID + "=" + mutante.getId(),null );

        return("mutante "+mutante.getNome()+" removido");
    }

    public List<Mutante> listaMutantes(){
        List<Mutante> mutantes = new ArrayList();
        Cursor cursor = database.query(BD.MUTANTES,new String[]{BD.MUTANTE_ID, BD.MUTANTE_NOME}, null, null, null, null, null);
        cursor.moveToFirst();
        Mutante mutante;

        while(!cursor.isAfterLast()){
            mutante = new Mutante();
            mutante.setId(cursor.getLong(0));
            mutante.setNome(cursor.getString(1));
            mutantes.add(mutante);
            cursor.moveToNext();
        }

        return mutantes;
    }

    public List<String> poderesMutante(long id){
        List<String> poderes = new ArrayList();
        Cursor cursor = database.query(BD.PODERES, new String[]{BD.PODER_NOME}, BD.MUTANTE_ID+" = "+id, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
           poderes.add(cursor.getString(0));
           cursor.moveToNext();
        }
        return poderes;
    }

    public String atualizaMutante(Mutante mutante, Context contexto){
        //é um chuncho, mas é mais facil!
        this.removeMutante(mutante);
        this.addMutante(mutante, contexto);

        return("Mutante "+ mutante.getNome()+" atualizado");
    }

    public String buscaMutanteNome(String nome, Context contexto){

        Cursor cursor = database.query(BD.MUTANTES, new String[]{BD.MUTANTE_NOME}, BD.MUTANTE_NOME+" = '"+nome+"'", null, null, null, null);
        cursor.moveToFirst();

        if(cursor.getCount() == 0 ) {
            Toast.makeText(contexto, "mutante não localizado", Toast.LENGTH_SHORT).show();
            return null;
        }
        else{
            return(cursor.getString(0));
        }

    }

    public List<String> buscaMutantePorPoder(String poder, Context contexto){

        List<String> lista = new ArrayList();
        Cursor cursor = database.query(BD.PODERES, new String[]{BD.MUTANTE_ID}, BD.PODER_NOME+" = '"+poder+"'", null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0 ) {
            Toast.makeText(contexto, "poder não existente", Toast.LENGTH_SHORT).show();
            return null;
        } else{
            while(!cursor.isAfterLast()) {
                Cursor cursor2 = database.query(BD.MUTANTES, new String[]{BD.MUTANTE_NOME}, BD.MUTANTE_ID + " = " + cursor.getLong(0), null, null, null, null);
                cursor2.moveToFirst();
                lista.add(cursor2.getString(0));
                cursor.moveToNext();
            }
        }
        return lista;
    }

}
