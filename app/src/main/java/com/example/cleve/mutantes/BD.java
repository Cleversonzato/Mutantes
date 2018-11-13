package com.example.cleve.mutantes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BD extends SQLiteOpenHelper {
    public final static String MUTANTES = "mutantes";
    public final static String MUTANTES_ID = "id";
    public final static String MUTANTES_NAME = "nome";
    public final static String PODERES = "poderes";
    public final static String PODERES_NAME = "poder";

    public final static String DATABASE_NAME = "mutantes.db";
    public final static int DATABASE_VERSION = 1;

    public final static String CREATE_MUTANTES = "CREATE TABLE " +MUTANTES+
            "(" +MUTANTES_ID+ "INTEGER PRIMARY KEY AUTOINCREMENT," +
            MUTANTES_NAME+ "TEXT NOT NULL);";

    public final static String CREATE_PODERES  = "CREATE TABLE "+ PODERES+
            "(" +MUTANTES_ID+ "INTEGER,"+
            PODERES_NAME + "TEXT NOT NULL," +
            "FOREIGN KEY ("+MUTANTES_ID+") REFERENCES " +MUTANTES+"("+MUTANTES_ID+") ON DELETE CASCADE);";

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
        onCreate(db);
    }
}

