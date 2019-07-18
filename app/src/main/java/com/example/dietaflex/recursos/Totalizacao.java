package com.example.dietaflex.recursos;

import android.content.Context;

import com.example.dietaflex.banco_de_dados.NutricionalBancoDados;
import com.example.dietaflex.banco_de_dados.RefeicoesBancoDados;

import java.util.List;
/*
//***************************************
    Totalizacao(Context contexto)  CONSTRUTOR USAR this COMO CONTEXTO

    Nutricional macrosGeral() RETORNA UM OBJETO COM OS VALORES TOTAIS DE MACRONUTRIENTES

    Nutricional macrosIndividual(int codigo, float quantidade)  RETORNA UM OBJETO COM OS VALORES PROPORCIONAIS DE MACRONUTRIENTES

    Nutricional macrosIndividual(String nome, float quantidade)

//***************************************
*/
public class Totalizacao {

    public int id ;
    public int grupo;
    public float quantidade ;
    public String datahorario;
    public int codigo;
    private int energia;
    private float proteinas;
    private float carboidratos;
    private float gorduras;
    private float fibras;
    private String nome ;
    private Context contexto;
    private List<Nutricional> listaNutricionalAlimentos ;
    private RefeicoesBancoDados refeicoesBancoDados;


    public Totalizacao(Context contexto){
        this.contexto = contexto;
        NutricionalBancoDados nutricionalBancoDados = new NutricionalBancoDados(contexto);
        listaNutricionalAlimentos = nutricionalBancoDados.listarAlimentos();
        refeicoesBancoDados = new RefeicoesBancoDados(contexto);
    }



    public static int proporcao(float quantidade, int valor){

        return (int)(valor*quantidade)/100; // proporacao baseada em 100g
    }
    public static float proporcao(float quantidade, float valor){
        return (valor*quantidade)/100.0f; // proporacao baseada em 100g
    }



      /*  public static float truncar(float valor) {
            DecimalFormat decimalFormat = new DecimalFormat("#,#0.0");
            return  Float.parseFloat(decimalFormat.format(valor)) ;
        }
*/

//*********RETORNA UM OBJETO COM OS VALORES PROPORCIONAIS DE MACRONUTRIENTES
    public   Nutricional macrosIndividual(int codigo, float quantidade) {
        Nutricional retorno = new Nutricional();

        for (Nutricional temp : listaNutricionalAlimentos ) {
            if (codigo == temp.codigo) {
                retorno.nome = temp.nome;
                retorno.energia = proporcao(quantidade, temp.energia);
                retorno.proteinas = proporcao(quantidade, temp.proteinas);
                retorno.carboidratos = proporcao(quantidade, temp.carboidratos);
                retorno.gorduras = proporcao(quantidade, temp.gorduras);
                retorno.fibras = proporcao(quantidade, temp.fibras);
                retorno.quantidade = quantidade;
                retorno.codigo = codigo;
                return retorno;
            }
        }
        retorno.nome = "Item não encontrado";
        retorno.energia = 0;
        retorno.proteinas = 0;
        retorno.carboidratos = 0;
        retorno.gorduras = 0;
        retorno.fibras = 0;
        retorno.quantidade = 0;
        return retorno;
    }
    //*********RETORNA UM OBJETO COM OS VALORES PROPORCIONAIS DE MACRONUTRIENTES
    public   Nutricional macrosIndividual(String nome, float quantidade) {

        Nutricional retorno = new Nutricional();

        for (Nutricional temp : listaNutricionalAlimentos ) {
            if (temp.nome.equals(nome)) {
                retorno.nome = temp.nome;
                retorno.codigo = temp.codigo;
                retorno.energia = proporcao(quantidade, temp.energia);
                retorno.proteinas = proporcao(quantidade, temp.proteinas);
                retorno.carboidratos = proporcao(quantidade, temp.carboidratos);
                retorno.gorduras = proporcao(quantidade, temp.gorduras);
                retorno.fibras = proporcao(quantidade, temp.fibras);
                retorno.quantidade = quantidade;
                return retorno;
            }
        }
        retorno.nome = "Item não encontrado";
        retorno.energia = 0;
        retorno.proteinas = 0;
        retorno.carboidratos = 0;
        retorno.gorduras = 0;
        retorno.fibras = 0;
        retorno.quantidade = 0;
        retorno.codigo = -1;
        return retorno;
    }



    //*********RETORNA UM OBJETO COM OS VALORES TOTAIS DE MACRONUTRIENTES
    public   Nutricional macrosGeral() {
        Nutricional retorno = new Nutricional();
        for (Refeicao temp : refeicoesBancoDados.listarRefeicoes() ) {

            Nutricional nutriTemp =  macrosIndividual(temp.codigo, temp.quantidade);

            retorno.energia += nutriTemp.energia;
            retorno.proteinas += nutriTemp.proteinas;
            retorno.carboidratos += nutriTemp.carboidratos;
            retorno.gorduras += nutriTemp.gorduras;
            retorno.fibras += nutriTemp.fibras;
            retorno.quantidade += nutriTemp.quantidade;
        }
        retorno.nome= "Valores Totais de Macros";
        return retorno;
    }


}
