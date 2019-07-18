package com.example.dietaflex.banco_de_dados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexaoRefeicao extends SQLiteOpenHelper {


    public ConexaoRefeicao(Context context) {
        super(context, "REFEICAO", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

            db.execSQL(TabelaSqlRefeicao.criarTabela());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
