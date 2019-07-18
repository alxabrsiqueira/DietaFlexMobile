package com.example.dietaflex.recursos;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*

METODOS E ATRIBUTOS STATIC - NÂO PRECISAM INSTANCIAR OBJETO

 ******** ATRIBUTOS VALOR PADRAO **********
    Meta.energiaDefault
    Meta.proteinasDefault
    Meta.carboidratosDefault
    Meta.gordurasDefault
    Meta.fibrasDefault
    Meta.resetDefault

******* METODOS SET e GET ********  this é o contexto, trocar por getBaseContext se der pau
*
    Metas.setPadrao(this) // SETA TUDO PARA VALOR PADRAO

    Metas.getProteinas(this)

    Metas.setProteinas(float valor, this)

    Metas.getCarboidratos(this)

    Meta.setCarboidratos(float valor, this)

    Meta.getGorduras(this)

    Meta.setGorduras(float valor, this)

    Meta.getFibras(this)

    Meta.setFibras(float valor, this)

    Meta.getReset(this)

    Meta.setReset(String valor, this)

    Meta.getEnergia(this)

    Meta.setEnergia( int valor, this)

*/


public final class Metas  {

    public static final int energiaDefault = 2000 ;
    public static final float proteinasDefault= 75.0f;
    public static final float carboidratosDefault= 300.0f ;
    public static final float gordurasDefault= 55.0f ;
    public static final float fibrasDefault= 25.0f ;
    public static final String resetDefault= "00:00" ;



    private static void mensagemErroGet(Context contexto){
        Toast.makeText(contexto, "Erro:\n Não foi possível recuperar dados ", Toast.LENGTH_LONG).show();
    }
    private static void mensagemErroSet(Context contexto){
        Toast.makeText(contexto, "Erro:\n Não foi possível salvar dados", Toast.LENGTH_LONG).show();
    }

    public  static void  setPadrao(Context contexto){

        setProteinas(proteinasDefault, contexto);
        setEnergia(energiaDefault, contexto);
        setCarboidratos(carboidratosDefault, contexto);
        setGorduras(gordurasDefault, contexto);
        setFibras(fibrasDefault, contexto);
        setReset(resetDefault, contexto );
    }


    public static float getProteinas(Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            return config.getFloat("proteinas", proteinasDefault);
        }
        catch (Exception e){
            mensagemErroGet(context);
        }
        return proteinasDefault;
    }

    public static void setProteinas(float proteinas, Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            SharedPreferences.Editor metas = config.edit();
            metas.putFloat("proteinas", proteinas);
            metas.apply();
        }
        catch (Exception e){
            mensagemErroSet(context);
        }
    }

    public static float getCarboidratos(Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            return config.getFloat("carboidratos",carboidratosDefault);
        }
        catch (Exception e){
            mensagemErroGet(context);
        }
        return carboidratosDefault;
    }

    public static void setCarboidratos(float carboidratos, Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            SharedPreferences.Editor metas = config.edit();
            metas.putFloat("carboidratos", carboidratos);
            metas.apply();    }
        catch (Exception e){
            mensagemErroSet(context);
        }
    }

    public static float getGorduras(Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            return config.getFloat("gorduras",gordurasDefault);
        }
        catch (Exception e){
            mensagemErroGet(context);
        }
        return gordurasDefault;
    }

    public static void setGorduras(float gorduras, Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            SharedPreferences.Editor metas = config.edit();
            metas.putFloat("gorduras", gorduras);
            metas.apply();
        }
        catch (Exception e){
            mensagemErroSet(context);
        }

    }

    public static float getFibras(Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            return config.getFloat("fibras",fibrasDefault);
        }
        catch (Exception e){
            mensagemErroGet(context);
        }
        return fibrasDefault;
    }

    public static void setFibras(float fibras, Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            SharedPreferences.Editor metas = config.edit();
            metas.putFloat("fibras", fibras);
            metas.apply();            }
        catch (Exception e){
            mensagemErroSet(context);
        }
    }

    public static String getReset(Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            return config.getString("reset",resetDefault);
        }
        catch (Exception e){
            mensagemErroGet(context);
        }
        return resetDefault;
    }

    public static void setReset(String reset, Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            SharedPreferences.Editor metas = config.edit();
            metas.putString("reset", reset);
            metas.apply();            }
        catch (Exception e){
            mensagemErroSet(context);
        }
    }

    public static int getEnergia(Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            return config.getInt("energia",energiaDefault);
        }
        catch (Exception e){
            mensagemErroGet(context);
        }
        return energiaDefault;
    }

    public static void setEnergia( int energia, Context context) {
        try {
            SharedPreferences config = context.getSharedPreferences("configDietaFlex", Context.MODE_PRIVATE);
            SharedPreferences.Editor metas = config.edit();
            metas.putInt("energia", energia);
            metas.apply();            }
        catch (Exception e){
            mensagemErroSet(context);
        }
    }

}