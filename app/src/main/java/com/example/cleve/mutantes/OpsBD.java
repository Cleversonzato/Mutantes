package com.example.cleve.mutantes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

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

    public Mutante addMutante(Mutante mutante) {
        ContentValues values = new ContentValues();
        values.put(BD.MUTANTES_NAME, mutante.getNome());

        long mutId = database.insert(BD.MUTANTES, null, values);
        mutante.setId(mutId);
        for (String poder : mutante.getPoderes()) {
            values = new ContentValues();
            values.put(BD.MUTANTES_ID, mutante.getId());
            values.put(BD.PODERES_NAME, poder);
            database.insert(BD.PODERES, null, values);
            //cursor?
        }
        System.out.println("mutante "+mutante.getNome()+" adicionado");
        return mutante;
    }

    public String removeMutante(Mutante mutante){
        //Os poderes são deletados pelo Cascade
        database.delete(BD.MUTANTES, BD.MUTANTES_ID + "=" + mutante.getId(),null );

        return("mutante "+mutante.getNome()+" removido");
    }

    public List<Mutante> listaMutantes(){
        List<Mutante> mutantes = new ArrayList();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(BD.MUTANTES +" LEFT INNER JOIN "+ BD.PODERES +
                " ON " + BD.MUTANTES+"."+BD.MUTANTES_ID+" = " + BD.PODERES+"."+BD.MUTANTES_ID  );

        Cursor cursor = qb.query(database, null, null, null, null, null, null);

        Mutante mutante;
        List<String> poderes;

        while(!cursor.isAfterLast()){
            mutante = new Mutante();
            mutante.setId(cursor.getLong(0));
            mutante.setNome(cursor.getString(1));
            poderes = new ArrayList<>();
            while(!cursor.isAfterLast() && mutante.getId() == cursor.getLong(0)){
                poderes.add(cursor.getString(3));
                cursor.moveToNext();
            }
            mutante.setPoderes(poderes);
            mutantes.add(mutante);
        }

        return mutantes;
    }

    public String atualizaMutante(Mutante mutante){
        //é um chuncho, mas é mais facil!
        this.removeMutante(mutante);
        this.addMutante(mutante);

        return("Mutante "+ mutante.getNome()+" atualizado");
    }

}
