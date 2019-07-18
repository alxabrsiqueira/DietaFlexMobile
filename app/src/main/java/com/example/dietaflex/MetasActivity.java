package com.example.dietaflex;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import com.example.dietaflex.recursos.Metas;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


public class MetasActivity extends AppCompatActivity {


    private EditText  campoEnergia ;
    private EditText  campoProteina ;
    private EditText  campoCarboidrato ;
    private EditText  campoGordura;
    private EditText  campoFibra ;
    private EditText  campoReset ;

    private TimePickerDialog picker;

    private String txtEnergia ;
    private String txtProteina ;
    private String txtCarboidrato ;
    private String txtGordura ;
    private String txtFibra ;
    private String txtReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        campoEnergia = findViewById(R.id.campo_energia);
        campoProteina = findViewById(R.id.campo_proteinas);
        campoCarboidrato = findViewById(R.id.campo_carboidratos);
        campoGordura = findViewById(R.id.campo_gorduras);
        campoFibra = findViewById(R.id.campo_fibras);
        campoReset = findViewById(R.id.campo_reset);

        Button botaoSalvar = findViewById(R.id.button_salvar);
        Button botaoSair = findViewById(R.id.button_sair);


        //Nessa parte são inseridos os valores previamente configurados nos campos
        campoEnergia.setText(String.valueOf(Metas.getEnergia(this)));
        campoProteina.setText(String.valueOf(Metas.getProteinas(this)));
        campoCarboidrato.setText(String.valueOf(Metas.getCarboidratos(this)));
        campoGordura.setText(String.valueOf(Metas.getGorduras(this)));
        campoFibra.setText(String.valueOf(Metas.getFibras(this)));
        campoReset.setText(Metas.getReset(this));


        // Botao sair sem salvar

        botaoSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


       // Botao de SALVAR

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // antes de salvar chama o metodo validaCampos que verifica cada campo se não está vazio, se tudo ok o metodo retorna true e entra no escopo do if
                  if(validaCampos()){

                    // salva os dados dos campos atraves da classe Metas
                  Metas.setEnergia(Integer.parseInt(txtEnergia), getBaseContext());
                    Metas.setProteinas(Float.parseFloat(txtProteina), getBaseContext());
                    Metas.setCarboidratos(Float.parseFloat(txtCarboidrato), getBaseContext());
                    Metas.setGorduras(Float.parseFloat(txtGordura), getBaseContext());
                    Metas.setFibras(Float.parseFloat(txtFibra), getBaseContext());
                    Metas.setReset(txtReset, getBaseContext());

                    Toast.makeText(getBaseContext(),  "Metas foram salvas com sucesso!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getBaseContext(), TotaisActivity.class));
                    finish(); // fecha a activity
                }
            }
        });
        //fim BOTAO SALVAR


        // PARTE DO TIMERPICKER

        campoReset.setInputType(InputType.TYPE_NULL);
        campoReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Pega o valor que está no campo de reset e separa em duas strings minutos e horas
                String[] horarioResetArray =  new String[2];
                horarioResetArray = campoReset.getText().toString().split(":");
                //Converte para inteiro as strings minutos e horas
                int minR = Integer.parseInt(horarioResetArray[1]);
                int horaR = Integer.parseInt(horarioResetArray[0]);

                // Cria janela timer picker
                picker = new TimePickerDialog(MetasActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            String txtHora, txtMin;

                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                if(sHour<10)
                                    txtHora = "0"+sHour;
                                else
                                    txtHora = String.valueOf(sHour);
                                if(sMinute<10)
                                    txtMin = "0"+sMinute;
                                else
                                    txtMin = String.valueOf(sMinute);
                                campoReset.setText(txtHora + ":" + txtMin);
                            }
                        }, horaR, minR, true);

                picker.show();
            }
        });

        //**** FIm do TIMERPICKER



    }


    //*********METODOS UTILITARIOS
    private boolean verificarCamposVazios(String valor){
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado; // retorna verdadeiro se tiver vazio
    }

    private boolean validaCampos() {

        boolean flag = false;

        txtEnergia = campoEnergia.getText().toString();
        txtProteina = campoProteina.getText().toString();
         txtCarboidrato = campoCarboidrato.getText().toString();
         txtGordura = campoGordura.getText().toString();
         txtFibra = campoFibra.getText().toString();
         txtReset = campoReset.getText().toString();

        String mensagem = "";


        if (verificarCamposVazios(txtEnergia)) {
            flag = true;
            campoEnergia.requestFocus();  // seleciona o campo
            mensagem = "Insira uma quantidade máxima de energia em kcal!";
        }
        else
        if(verificarCamposVazios(txtProteina)){
            flag = true;
            campoProteina.requestFocus();
            mensagem = "Insira uma quantidade máxima de proteinas em gramas!";
        }
        else
        if(verificarCamposVazios(txtCarboidrato)){
            flag = true;
            campoCarboidrato.requestFocus();
            mensagem = "Insira uma quantidade máxima de carboidratos em gramas!";
        }
        else
        if(verificarCamposVazios(txtGordura)){
            flag = true;
            campoGordura.requestFocus();
            mensagem = "Insira uma quantidade máxima de gorduras em gramas!";
        }
        else
        if(verificarCamposVazios(txtFibra)){
            flag = true;
            campoFibra.requestFocus();
            mensagem = "Insira uma quantidade máxima de fibras em gramas!";
        }
        else
        if(verificarCamposVazios(txtReset)){
            flag = true;
            campoReset.requestFocus();
            mensagem = "Insira o horário de reset entre 00:00 h e 23:59 h!";
        }

        if(flag){
            AlertDialog.Builder janela = new AlertDialog.Builder(this);
            janela.setMessage(mensagem);
            janela.setNegativeButton("Ok", null);
            janela.show();
            return false;
        }
        else
            return true;

    }

    //--------fim dos metodos utilitarios


    // AREA DO MENU DO TOPO
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);

        MenuItem m1 = menu.findItem(R.id.menu_configurar_metas);
        m1.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.menu_totais:
                startActivity(new Intent(getBaseContext(), TotaisActivity.class));
                break;
            case R.id.menu_listar_refeicoes:
                startActivity(new Intent(getBaseContext(), ListarRefeicoesActivity.class));
                break;
            case R.id.menu_configurar_metas:
                startActivity(new Intent(getBaseContext(), MetasActivity.class));
                break;
            case R.id.menu_sobre_nos:
                startActivity(new Intent(getBaseContext(), SobreNosActivity.class));
                break;
            case R.id.menu_adicionar_refeicao:
                startActivity(new Intent(getBaseContext(), AdicionarAlimentoActivity.class));
                break;
            case R.id.menu_fechar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Deseja realmente sair?")
                        .setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
// FIM DO MENU DO TOPO

}
