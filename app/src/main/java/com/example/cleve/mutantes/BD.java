package com.example.cleve.mutantes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BD extends SQLiteOpenHelper {
    public final static String MUTANTES = "mutantes";
    public final static String MUTANTE_ID = "id";
    public final static String MUTANTE_NOME = "nome";
    public final static String PODERES = "poderes";
    public final static String PODER_NOME = "poder";

    public final static String DATABASE_NAME = "mutantes.db";
    public final static int DATABASE_VERSION = 1;

    public final static String CREATE_MUTANTES = "CREATE TABLE "+MUTANTES+"( "
            + MUTANTE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MUTANTE_NOME+ " TEXT NOT NULL UNIQUE);";

    public final static String CREATE_PODERES  = "CREATE TABLE "+ PODERES+"( " +
            MUTANTE_ID+" INTEGER," +
            PODER_NOME + " TEXT NOT NULL," +
            "FOREIGN KEY ("+MUTANTE_ID+") REFERENCES " +MUTANTES+"("+MUTANTE_ID+") ON DELETE CASCADE);";

    public BD(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MUTANTES);
        db.execSQL(CREATE_PODERES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MUTANTES);
        db.execSQL("DROP TABLE IF EXISTS " + PODERES);
        this.onCreate(db);
    }
}

