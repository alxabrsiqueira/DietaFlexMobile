package com.example.dietaflex.banco_de_dados;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import com.example.dietaflex.recursos.Refeicao;

import java.util.ArrayList;
import java.util.List;

public class RefeicoesBancoDados  {

    private Context contexto;

    private SQLiteDatabase conexao;

   /* public  RefeicoesBancoDados(SQLiteDatabase conexao, Context contexto){
        this.conexao = conexao;
        this.contexto = contexto;
    }
*/
  /*  public void adicionarRefeicao(Refeicao refeicao){

        //recebe os valores que serao enviados para a base
        ContentValues contentValues = new ContentValues();
        contentValues.put("GRUPO", refeicao.grupo);
        contentValues.put("QUANTIDADE" , refeicao.quantidade);
        contentValues.put("CODIGO" , refeicao.codigo);
        contentValues.put("DATA" , refeicao.datahorario);
        conexao.insertOrThrow("REFEICAO", null, contentValues);

    }


    public void removerRefeicao(int id){

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        conexao.delete("REFEICAO","ID = ? ", parametros);

    }


    public void editarRefeicao(Refeicao refeicao){

        ContentValues contentValues = new ContentValues();
        contentValues.put("GRUPO", refeicao.grupo);
        contentValues.put("QUANTIDADE" , refeicao.quantidade);

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(refeicao.id);

        conexao.update("REFEICAO", contentValues, "ID = ? ", parametros);

    }


    public List<Refeicao> listarRefeicoes(){

        List<Refeicao> refeicoes = new ArrayList<Refeicao>();

        //para montar consulta SQL
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID, GRUPO, CODIGO, DATA, QUANTIDADE");
        sql.append("   FROM REFEICAO  ");
        sql.append("   WHERE datetime('DATA')   >   datetime('");
        sql.append(dataUltimoReset());
        sql.append("')");

        //retorna objeto tipo cursor
        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if (resultado.getCount() > 0){

            //percorrer o resgistro do inicio ao fim
            resultado.moveToFirst();

            do{
                Refeicao ref = new Refeicao();

                //PARA RETORNAR OS DADOS DA BASE
                ref.id         = resultado.getInt( resultado.getColumnIndexOrThrow("ID") );
                ref.grupo      = resultado.getInt( resultado.getColumnIndexOrThrow("GRUPO") );
                ref.codigo = resultado.getInt( resultado.getColumnIndexOrThrow("CODIGO") );
                ref.datahorario = resultado.getString( resultado.getColumnIndexOrThrow("DATA") );
                ref.quantidade = resultado.getFloat( resultado.getColumnIndexOrThrow("QUANTIDADE") );
                refeicoes.add(ref);

            }while(resultado.moveToNext());

        }


        return refeicoes;
    }


    public Refeicao buscarRefeicao (int id){

        Refeicao refeicao = new Refeicao();

        //para montar consulta SQL
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID, GRUPO, QUANTIDADE ");
        sql.append("   FROM REFEICAO  ");
        //DEFINE QUAL É O LOCAL DA BUSCA POSSO USAR UM AND E COLOCAR OUTRA VARIAVEL
        sql.append("   WHERE ID = ?   ");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(id);

        //retorna objeto tipo cursor
        Cursor resultado = conexao.rawQuery(sql.toString(),parametros);


        if (resultado.getCount() > 0){

            //percorrer o resgistro do inicio ao fim
            resultado.moveToFirst();

            //PARA RETORNAR OS DADOS DA BASE
            refeicao.id         = resultado.getInt( resultado.getColumnIndexOrThrow("ID") );
            refeicao.grupo      = resultado.getInt( resultado.getColumnIndexOrThrow("GRUPO") );
            refeicao.quantidade = resultado.getFloat( resultado.getColumnIndexOrThrow("QUANTIDADE") );

            return refeicao;
        }

        //ver
        return null;
    }

*/
// ABAIXO FUNCOES PROVISORIAS




    public static void adicionarRefeicao(Refeicao alimento) {
        //adciona ao BD a partir do objeto.
    }
    public static void editarRefeicao(Refeicao alimento) {
        //deleta do BD a partir do id.
    }
    public static void removerRefeicao(int id) {
        //deleta do BD a partir do id.
    }









    public static List <Refeicao> listarRefeicoes(){

        List <Refeicao> alimentos = new ArrayList <Refeicao>();

        for(int i = 0; i < 10; i++) {
           Refeicao aaaa = new Refeicao();
           aaaa.id = i;
            aaaa.codigo = i+1;
            aaaa.datahorario = "2019-06-23 20:10:00";
            aaaa.quantidade = (float)10+(i*2.1f);
            alimentos.add(aaaa);
        }

        return alimentos;
    }

    public String dataUltimoReset() {

    //Metas obj = new Metas(contexto);

        String horarioReset = "11:00"; // Metas.getReset(contexto);  trocar por esse depois
        String[] horarioResetArray = new String[2];
        horarioResetArray = horarioReset.split(":");
        int minR = Integer.parseInt(horarioResetArray[1]);
        int horaR = Integer.parseInt(horarioResetArray[0]);


        Calendar datahorarioAgora = Calendar.getInstance();
        Calendar datahorarioReset = Calendar.getInstance();

        float horaA =  datahorarioAgora.get(Calendar.HOUR_OF_DAY);
        float minA =  datahorarioAgora.get(Calendar.MINUTE);

        float horarioResetReal = horaR+ minR/60.0f;
        float horarioAgoraReal = horaA+ minA/60.0f;

        datahorarioReset.set(Calendar.HOUR_OF_DAY, horaR);
        datahorarioReset.set(Calendar.MINUTE, minR);
        datahorarioReset.set(Calendar.SECOND, 0);


        if(horarioResetReal > horarioAgoraReal) {
            datahorarioReset.add(datahorarioReset.DAY_OF_MONTH, -1);
        }

        SimpleDateFormat dataFormatada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return dataFormatada.format(datahorarioReset.getTime());

        // RETORNA NO TIPO DATE return datahorarioReset.getTime()

    }

}
