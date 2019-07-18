package com.example.dietaflex;


import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;


import com.example.dietaflex.recursos.Metas;
import com.example.dietaflex.recursos.Nutricional;
import com.example.dietaflex.banco_de_dados.RefeicoesBancoDados;
import com.example.dietaflex.recursos.Totalizacao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;



public class TotaisActivity extends AppCompatActivity {

 private   Calendar dataUltimoReset, dataHorarioAgora, horarioProximoReset;
 private   TextView txtViewContagemRegressiva;
 private   String data,contagemRegressiva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totais);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // botao adicionar refeicao
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AdicionarAlimentoActivity.class));
            }
        });

        //botao metas
        Button botaoIrMetas = findViewById(R.id.buttonMetas);
        botaoIrMetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), MetasActivity.class));
            }
        });
        //botao listar refeicoes
        Button botaoIrRefeicoes = findViewById(R.id.buttonListar);
        botaoIrRefeicoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ListarRefeicoesActivity.class));
            }
        });

        Totalizacao totalizacaoMacros = new Totalizacao(getBaseContext());
        Nutricional total = totalizacaoMacros.macrosGeral();

        int metaEnergia = Metas.getEnergia(getBaseContext()) ;
        int metaProteina = (int)Metas.getProteinas(getBaseContext());
        int metaCarboidrato=(int)Metas.getCarboidratos(getBaseContext());
        int metaGorduras=(int)Metas.getGorduras(getBaseContext());
        int metaFibras=(int)Metas.getFibras(getBaseContext());



            // ******* CONTAGEM REGRESSIVA***********

        Thread t = new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){
                    try {
                        Thread.sleep(1001);  //1000ms = 1 sec
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // converte string da data horario para tipo Calendar
                                dataUltimoReset = Calendar.getInstance();
                                try {
                                    data = new RefeicoesBancoDados(getBaseContext()).dataUltimoReset(); // objeto anonimo retorna uma sting com a datahorario do ultimo reset
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    dataUltimoReset.setTime(sdf.parse(data));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                // Pega o datahorario atual
                                dataHorarioAgora = Calendar.getInstance();
                                dataHorarioAgora.add(Calendar.HOUR_OF_DAY,RefeicoesBancoDados.fusoHorario); // corrige o fuso horario

                                // Calcula quanto falta para o proximo reset e salva no prorio objeto datahorarioAgora
                                dataUltimoReset.add(Calendar.HOUR_OF_DAY,+24); // soma 24h
                                dataUltimoReset.add(Calendar.HOUR_OF_DAY,-(dataHorarioAgora.get(Calendar.HOUR_OF_DAY)));
                                dataUltimoReset.add(Calendar.DAY_OF_MONTH,-(dataHorarioAgora.get(Calendar.DAY_OF_MONTH)));
                                dataUltimoReset.add(Calendar.MINUTE,-(dataHorarioAgora.get(Calendar.MINUTE)));
                                dataUltimoReset.add(Calendar.SECOND,-(dataHorarioAgora.get(Calendar.SECOND)));
                                // isso tudo acima faz datahorarioReset + 24hr - tempodataAgora = contagem regressiva para proximo reset

                                //formata em string em apenas horario
                                SimpleDateFormat dataFormatada = new SimpleDateFormat("HH:mm:ss");
                                contagemRegressiva = dataFormatada.format(dataUltimoReset.getTime());

                                txtViewContagemRegressiva = findViewById(R.id.textTempoRestante);
                                txtViewContagemRegressiva.setText(String.valueOf(contagemRegressiva)); // imprime o horario

                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

        //******* fim da contagem regressiva





        // ******* ENERGIA ***********

        TextView txtViewEnergiaTotal = findViewById(R.id.textEnergiaValor);
        TextView txtViewEnergiaFaltamRestam = findViewById(R.id.textFaltamExcedeuEnergia);
        TextView txtViewEnergiaResto = findViewById(R.id.textRestoEnergia);
        TextView txtViewMeta = findViewById(R.id.textMeta);
        TextView txtViewEnergiaPercentagem = findViewById(R.id.energia_percentagem);
        ProgressBar progressEnergia= findViewById(R.id.progressBarEnergia);
        ConstraintLayout  layoutEnergia = findViewById(R.id.layout_energia);

        int soma = total.energia;
        txtViewEnergiaResto.setText(String.valueOf(Math.abs(metaEnergia-soma))); // mostra diferença entre a meta e o valor consumido
        txtViewEnergiaTotal.setText(String.valueOf(soma));

        String fraseDasMetas = "em relação a meta de "+metaEnergia+" kcal.";
        txtViewMeta.setText(fraseDasMetas);

        String percentagem = (soma*100/metaEnergia)+"%";
        txtViewEnergiaPercentagem.setText(percentagem);

        progressEnergia.setMax(metaEnergia);
        progressEnergia.setProgress(soma);


        //se a meta for atingida muda a cor do fundo e a palavra resta/faltam
        if(metaEnergia<=soma) {
            txtViewEnergiaFaltamRestam.setText("Excederam");
            layoutEnergia.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
        }
        else{
            txtViewEnergiaFaltamRestam.setText("Faltam");
            layoutEnergia.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));
        }

        // ******* fim  energia ***********



        // ******* PROTEINAS ***********

        TextView txtViewProteinasTotal = findViewById(R.id.textproteinasValor);
        TextView txtViewProteinasFaltamRestam = findViewById(R.id.textFaltamExcedeuproteinas);
        TextView txtViewProteinasResto = findViewById(R.id.textRestoproteinas);
        TextView txtViewMetaProteina = findViewById(R.id.textMetaproteinas);
        TextView txtViewProteinasPercentagem = findViewById(R.id.proteinas_percentagem);
        ProgressBar progressProteinas= findViewById(R.id.progressBarproteinas);
        ConstraintLayout  layoutProteinas = findViewById(R.id.layout_proteinas);

        soma = (int) total.proteinas;
        txtViewProteinasResto.setText(String.valueOf(Math.abs(metaProteina-soma))); // mostra diferença entre a meta e o valor consumido
        txtViewProteinasTotal.setText(String.valueOf(soma));

        fraseDasMetas = "em relação a meta de "+metaProteina+" g.";
        txtViewMetaProteina.setText(fraseDasMetas);

        percentagem = (soma*100/metaProteina)+"%";
        txtViewProteinasPercentagem.setText(percentagem);

        progressProteinas.setMax(metaProteina);
        progressProteinas.setProgress(soma);


        //se a meta for atingida muda a cor do fundo e a palavra resta/faltam
        if(metaProteina<=soma) {
            txtViewProteinasFaltamRestam.setText("Excederam");
            layoutProteinas.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
        }
        else{
            txtViewProteinasFaltamRestam.setText("Faltam");
            layoutProteinas.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));
        }

        // ******* fim  proteinas ***********

        // ******* CARBOIDRATOS ***********

        TextView txtViewCarboidratosTotal = findViewById(R.id.textcarboidratosValor);
        TextView txtViewCarboidratosFaltamRestam = findViewById(R.id.textFaltamExcedeucarboidratos);
        TextView txtViewCarboidratosResto = findViewById(R.id.textRestocarboidratos);
        TextView txtViewMetaCarboidratos = findViewById(R.id.textMetacarboidratos);
        TextView txtViewCarboidratosPercentagem = findViewById(R.id.carboidratos_percentagem);
        ProgressBar progressCarboidratos= findViewById(R.id.progressBarcarboidratos);
        ConstraintLayout  layoutCarboidratos = findViewById(R.id.layout_carboidratos);

        soma = (int) total.carboidratos;
        txtViewCarboidratosResto.setText(String.valueOf(Math.abs(metaCarboidrato-soma))); // mostra diferença entre a meta e o valor consumido
        txtViewCarboidratosTotal.setText(String.valueOf(soma));

        fraseDasMetas = "em relação a meta de "+metaCarboidrato+" g.";
        txtViewMetaCarboidratos.setText(fraseDasMetas);

        percentagem = (soma*100/metaProteina)+"%";
        txtViewCarboidratosPercentagem.setText(percentagem);

        progressCarboidratos.setMax(metaCarboidrato);
        progressCarboidratos.setProgress(soma);


        //se a meta for atingida muda a cor do fundo e a palavra resta/faltam
        if(metaCarboidrato<=soma) {
            txtViewCarboidratosFaltamRestam.setText("Excederam");
            layoutCarboidratos.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
        }
        else{
            txtViewCarboidratosFaltamRestam.setText("Faltam");
            layoutCarboidratos.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));
        }

        // ******* fim  carboidratos ***********

        // ******* GORDURAS ***********

        TextView txtViewGordurasTotal = findViewById(R.id.textgordurasValor);
        TextView txtViewGordurasFaltamRestam = findViewById(R.id.textFaltamExcedeugorduras);
        TextView txtViewGordurasResto = findViewById(R.id.textRestogorduras);
        TextView txtViewMetaGorduras = findViewById(R.id.textMetagorduras);
        TextView txtViewGordurasPercentagem = findViewById(R.id.gorduras_percentagem);
        ProgressBar progressGorduras= findViewById(R.id.progressBargorduras);
        ConstraintLayout  layoutGorduras = findViewById(R.id.layout_gorduras);

        soma = (int) total.gorduras;
        txtViewGordurasResto.setText(String.valueOf(Math.abs(metaGorduras-soma))); // mostra diferença entre a meta e o valor consumido
        txtViewGordurasTotal.setText(String.valueOf(soma));

        fraseDasMetas = "em relação a meta de "+metaGorduras+" g.";
        txtViewMetaGorduras.setText(fraseDasMetas);

        percentagem = (soma*100/metaGorduras)+"%";
        txtViewGordurasPercentagem.setText(percentagem);

        progressGorduras.setMax(metaGorduras);
        progressGorduras.setProgress(soma);


        //se a meta for atingida muda a cor do fundo e a palavra resta/faltam
        if(metaGorduras<=soma) {
            txtViewGordurasFaltamRestam.setText("Excederam");
            layoutGorduras.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
        }
        else{
            txtViewGordurasFaltamRestam.setText("Faltam");
            layoutGorduras.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));
        }

        // ******* fim  gorduras ***********

        // ******* FIBRAS ***********

        TextView txtViewFibrasTotal = findViewById(R.id.textfibrasValor);
        TextView txtViewFibrasFaltamRestam = findViewById(R.id.textFaltamExcedeufibras);
        TextView txtViewFibrasResto = findViewById(R.id.textRestofibras);
        TextView txtViewMetaFibras = findViewById(R.id.textMetafibras);
        TextView txtViewFibrasPercentagem = findViewById(R.id.fibras_percentagem);
        ProgressBar progressFibras= findViewById(R.id.progressBarfibras);
        ConstraintLayout  layoutFibras = findViewById(R.id.layout_fibras);

        soma = (int) total.fibras;
        txtViewFibrasResto.setText(String.valueOf(Math.abs(metaFibras-soma))); // mostra diferença entre a meta e o valor consumido
        txtViewFibrasTotal.setText(String.valueOf(soma));

        fraseDasMetas = "em relação a meta de "+metaFibras+" g.";
        txtViewMetaFibras.setText(fraseDasMetas);

        percentagem = (soma*100/metaFibras)+"%";
        txtViewFibrasPercentagem.setText(percentagem);

        progressFibras.setMax(metaFibras);
        progressFibras.setProgress(soma);


        //se a meta for atingida muda a cor do fundo e a palavra resta/faltam
        if(metaGorduras<=soma) {
            txtViewFibrasFaltamRestam.setText("Excederam");
            layoutFibras.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
        }
        else{
            txtViewFibrasFaltamRestam.setText("Faltam");
            layoutFibras.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));
        }

        // ******* fim  gorduras ***********

        // QUANTIDADE TOTAL
        TextView txtViewTotalQuantidade = findViewById(R.id.textQuantidadeConsumida);
        soma = (int) total.quantidade;
        fraseDasMetas = "Total de "+soma+" gramas de alimentos consumidos.";
        txtViewTotalQuantidade.setText(fraseDasMetas);
        // ******* fim  quantidade ***********

    }// FIM DO ONCREATED



    // AREA DO MENU DO TOPO
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);

        MenuItem m1 = menu.findItem(R.id.menu_totais);
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
