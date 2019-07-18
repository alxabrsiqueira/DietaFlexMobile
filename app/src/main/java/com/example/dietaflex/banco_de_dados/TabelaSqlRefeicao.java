package com.example.dietaflex.banco_de_dados;

public class TabelaSqlRefeicao {


    public static String criarTabela(){


        //SCRIPT UTILIZADO NA OUTRA CLASSE PARA A CRIAÇÃO DO BANCO
        String sql = "CREATE TABLE IF NOT EXISTS REFEICAO (" +
                " ID INTEGER PRIMARY KEY, " +
                " GRUPO INTEGER, " +
                " CODIGO INTEGER, " +
                " DATA TEXT, " +
                " QUANTIDADE REAL, " +
                ");";


        return  sql;

    }
}

/*        sql.append("    CREATE TABLE IF NOT EXISTS REFEICAO ( ");
        sql.append("    ID           INTEGER   PRIMARY KEY, ");
        sql.append("    GRUPO        INTEGER   NOT NULL,   ");
        sql.append("    CODIGO       INTEGER   NOT NULL,   ");
        sql.append("    DATA         VARCHAR (25)   NOT NULL,   ");
        sql.append("    QUANTIDADE   REAL      NOT NULL )  ");


        */