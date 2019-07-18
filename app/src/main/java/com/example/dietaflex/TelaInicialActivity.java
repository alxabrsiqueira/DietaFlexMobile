package com.example.dietaflex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.dietaflex.banco_de_dados.NutricionalBancoDados;
import com.example.dietaflex.banco_de_dados.RefeicoesBancoDados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class TelaInicialActivity extends AppCompatActivity {

    private NutricionalBancoDados mBancoImport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_telainicial);

        getSupportActionBar().hide();

        //coloca tela em fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



                //aguarda o delay e roda uma  thread que inicia a activity
        new Handler(). postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(getBaseContext(), TotaisActivity.class));
                finish();

            }
        },2000);
    }




    private void inicializarBancoDados(){

        mBancoImport = new NutricionalBancoDados(this);

        File database = getApplicationContext().getDatabasePath(NutricionalBancoDados.NOMEDB);
        if ( database.exists() == false ){

            mBancoImport.getReadableDatabase();
            if ( copiaBanco(this) ){
                alert("Banco copiado com sucesso");
            }
            else{
                alert("Erro ao copiar o banco de dados");
            }
        }

    }

    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }


    private  boolean copiaBanco(Context context){
        try{
            InputStream inputStream = context.getAssets().open(NutricionalBancoDados.NOMEDB);
            String outFile = NutricionalBancoDados.LOCALDB + NutricionalBancoDados.NOMEDB;
            OutputStream outputStream = new FileOutputStream(outFile);
            byte[] buff = new byte[4096];//512000
            int lenght = 0;
            while ( (lenght = inputStream.read(buff)) > 0 ){
                outputStream.write(buff,0,lenght);
            }
            outputStream.flush();
            outputStream.close();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();   alert("ARQUIVO NAO ENCONTRADO");
            return false;
        } catch (IOException e) {
            e.printStackTrace();   alert("ERRO IOEXCEPTION");
            return false;
        }


    }

}





