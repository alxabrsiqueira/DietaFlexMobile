package com.example.dietaflex.banco_de_dados;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dietaflex.recursos.Nutricional;

import java.util.ArrayList;
import java.util.List;

public class NutricionalBancoDados extends SQLiteOpenHelper {



    public static final String NOMEDB = "nutrientes_db";
    public static final String LOCALDB = "/data/data/com.example.dietaflex/databases/";
    public static final int VERSAO = 1;
    private Context mContext;
    private SQLiteDatabase conexao;


    public NutricionalBancoDados(Context context) {
        super(context, NOMEDB, null, VERSAO);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void openDataBase(){

        String dbPath = mContext.getDatabasePath(NOMEDB).getPath();
        if( conexao != null && conexao.isOpen()){

            return;
        }
        conexao = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);

    }

    // METODO FECHA BD USAR NO BOTAO DE SAIR DO APP
    public void closeDataBase(){

        if(conexao != null){

            conexao.close();

        }


    }

    public List<Nutricional> listarAlimentos(){

        openDataBase();
        conexao = this.getWritableDatabase();
        List<Nutricional> listNutrientes = new ArrayList<Nutricional>();
        String sql = "SELECT * FROM bdNutrientes ORDER BY alimento ASC";
        Cursor cursor = conexao.rawQuery(sql,null);

        if ( cursor.getCount()>0 ){
            if ( cursor.moveToFirst()){
                do{
                    Nutricional nutrientes = new Nutricional();

                    //PARA RETORNAR OS DADOS DA BASE
                    nutrientes.codigo      = cursor.getInt( cursor.getColumnIndexOrThrow("codigo") );
                    nutrientes.nome    = cursor.getString( cursor.getColumnIndexOrThrow("alimento") );
                    nutrientes.energia = cursor.getInt( cursor.getColumnIndexOrThrow("energia") );
                    nutrientes.proteinas =  cursor.getFloat( cursor.getColumnIndexOrThrow("proteina") );
                    nutrientes.gorduras =  cursor.getFloat( cursor.getColumnIndexOrThrow("carboidrato") ); // OBS: colunas carboidrato e gordura estao trocados no bd
                    nutrientes.carboidratos =    cursor.getFloat( cursor.getColumnIndexOrThrow("gordura") );
                    nutrientes.fibras =  cursor.getFloat( cursor.getColumnIndexOrThrow("fibra") );

                    listNutrientes.add(nutrientes);

                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        conexao.close();

        return listNutrientes;
    }


    public Nutricional buscarNutrientes (int codigo){

        Nutricional nutrientes = new Nutricional();

        //para montar consulta SQL
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT codigo, alimento, energia, proteina, gordura, carboidrato, fibra ");
        sql.append("   FROM bdNutrientes  ");

        //DEFINE QUAL É O LOCAL DA BUSCA POSSO USAR UM AND E COLOCAR OUTRA VARIAVEL
        sql.append("   WHERE codigo = ?   ");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        //retorna objeto tipo cursor
        Cursor cursor = conexao.rawQuery(sql.toString(),parametros);



        if (cursor.getCount() > 0){

            //percorrer o resgistro do inicio ao fim
            cursor.moveToFirst();

            //PARA RETORNAR OS DADOS DA BASE
            nutrientes.codigo  = codigo;
            nutrientes.nome    = cursor.getString( cursor.getColumnIndexOrThrow("alimento") );
            nutrientes.energia = cursor.getInt( cursor.getColumnIndexOrThrow("energia") );
            nutrientes.proteinas =  cursor.getFloat( cursor.getColumnIndexOrThrow("proteina") );
            nutrientes.gorduras =  cursor.getFloat( cursor.getColumnIndexOrThrow("carboidrato") ); // NOTA: BD FICOU COM COLUNAS CARBO E GORDURA TROCADOS
            nutrientes.carboidratos =   cursor.getFloat( cursor.getColumnIndexOrThrow("gorduras") );
            nutrientes.fibras =  cursor.getFloat( cursor.getColumnIndexOrThrow("fibra") );

            return nutrientes;
        }


        return null;
    }


}




/*






// ESSAS ARRAYS ABAIXO SAO PROVISORIAS E OS METODOS DEVERÃO ACESAR A LISTA DE OBJETOS OU O BANCO DE DADOS DIRETAMENTE



    private static int[] energia = {
            124,
            360,
            128,
            358,
            130,
            358,
            394,
            443,
            472,
            471
    };
    private static  float[] proteinas = {
            2.6f
            ,7.3f
            ,2.5f
            ,7.2f
            ,2.6f
            ,7.2f
            ,13.9f
            ,8.1f
            ,6.4f
            ,5.4f
    };

    private static float[] carboidratos = {
            2.6f
            ,7.3f
            ,2.5f
            ,7.2f
            ,2.6f
            ,7.2f
            ,13.9f
            ,8.1f
            ,6.4f
            ,5.7f
    };
    private static float[] gorduras = {
            2.6f
            ,7.3f
            ,2.5f
            ,7.2f
            ,2.6f
            ,7.2f
            ,13.9f
            ,8.1f
            ,6.4f
            ,5.7f
    };
    private static float[] fibras = {
            2.6f
            ,7.3f
            ,2.5f
            ,7.2f
            ,20.6f
            ,7.2f
            ,13.9f
            ,8.1f
            ,6.4f
            ,5.7f
    };


    public static String[] nome = {
            "Maxixe, cru"
            ,"Mostarda, folha, crua"
            ,"Nhoque, de batata, cozido"
            ,"Nabo, cru"
            ,"Palmito, juçara, em conserva"
            ,"Arroz, tipo 2, cru"
            ,"Aveia, flocos, crua"
            ,"Biscoito, doce, maisena"
            ,"Figo, enlatado, em calda"
            ,"Fruta-pão, crua"
    };

    public String getNome(int codigo){
        return nome[codigo];
    }

    public int getEnergia(int codigo) {
        return energia[codigo];
    }

    public float getProteinas(int codigo) {
        return proteinas[codigo];
    }

    public float getCarboidratos(int codigo) {
        return carboidratos[codigo];
    }

    public float getGorduras(int codigo) {
        return gorduras[codigo];
    }

    public float getFibras(int codigo) {
        return fibras[codigo];
    }
}

*/


