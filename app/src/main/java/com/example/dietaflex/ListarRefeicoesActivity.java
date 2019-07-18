package com.example.dietaflex;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.dietaflex.banco_de_dados.NutricionalBancoDados;
import com.example.dietaflex.recursos.ListaAdapter;
import com.example.dietaflex.recursos.Metas;
import com.example.dietaflex.recursos.Nutricional;
import com.example.dietaflex.recursos.Refeicao;
import com.example.dietaflex.banco_de_dados.RefeicoesBancoDados;
import com.example.dietaflex.recursos.Totalizacao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class ListarRefeicoesActivity extends AppCompatActivity {
    private RecyclerView listaDadosView;
    private ListaAdapter listaAdapter;

    private ConstraintLayout   layoutEnergia ;
    private TextView txtViewEnergiaTotal  ;
    private TextView txtViewEnergiaMeta ;
    private TextView txtViewEnergiaPercentagem  ;
    private ProgressBar progressEnergia;

    private ConstraintLayout   layoutProteinas ;
    private TextView txtViewProteinasTotal  ;
    private TextView txtViewProteinasMeta ;
    private TextView txtViewProteinasPercentagem  ;
    private ProgressBar progressProteinas;

    private ConstraintLayout   layoutCarboidratos ;
    private TextView txtViewCarboidratosTotal  ;
    private TextView txtViewCarboidratosMeta ;
    private TextView txtViewCarboidratosPercentagem  ;
    private ProgressBar progressCarboidratos;

    private ConstraintLayout   layoutGorduras;
    private TextView txtViewGordurasTotal  ;
    private TextView txtViewGordurasMeta ;
    private TextView txtViewGordurasPercentagem  ;
    private ProgressBar progressGorduras;

    private ConstraintLayout   layoutFibras;
    private TextView txtViewFibrasTotal  ;
    private TextView txtViewFibrasMeta ;
    private TextView txtViewFibrasPercentagem  ;
    private ProgressBar progressFibras;


    private int metaEnergia = 2000;
    private float metaProteina = 100;
    private float metaCarboidrato= 100;
    private float metaGorduras= 100;
    private float metaFibras= 50;

    Totalizacao totalizacaoMacros;
    Nutricional total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_refeicoes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AdicionarAlimentoActivity.class));
            }
        });

        totalizacaoMacros = new Totalizacao(getBaseContext());
        total = totalizacaoMacros.macrosGeral();

        metaEnergia = Metas.getEnergia(getBaseContext()) ;
        metaProteina = Metas.getProteinas(getBaseContext());
        metaCarboidrato=Metas.getCarboidratos(getBaseContext());
        metaGorduras=Metas.getGorduras(getBaseContext());
        metaFibras=Metas.getFibras(getBaseContext());


        txtViewEnergiaTotal = findViewById(R.id.energia_total);
        txtViewEnergiaMeta = findViewById(R.id.energia_meta);
        txtViewEnergiaPercentagem = findViewById(R.id.energia_percentagem);
        progressEnergia= findViewById(R.id.progressBarEnergia);
        layoutEnergia = findViewById(R.id.layout_energia);

        txtViewProteinasTotal = findViewById(R.id.proteinas_total);
        txtViewProteinasMeta = findViewById(R.id.proteinas_meta);
        txtViewProteinasPercentagem = findViewById(R.id.proteinas_percentagem);
        progressProteinas= findViewById(R.id.progressBarProteinas);
        layoutProteinas = findViewById(R.id.layout_proteinas);

        txtViewCarboidratosTotal= findViewById(R.id.carboidratos_total);
        txtViewCarboidratosMeta = findViewById(R.id.carboidratos_meta);
        txtViewCarboidratosPercentagem = findViewById(R.id.carboidratos_percentagem);
        progressCarboidratos= findViewById(R.id.progressBarCarboidratos);
        layoutCarboidratos= findViewById(R.id.layout_carboidratos);

        txtViewGordurasTotal= findViewById(R.id.gorduras_total);
        txtViewGordurasMeta = findViewById(R.id.gorduras_meta);
        txtViewGordurasPercentagem = findViewById(R.id.gorduras_percentagem);
        progressGorduras= findViewById(R.id.progressBarGorduras);
        layoutGorduras = findViewById(R.id.layout_gorduras);

        txtViewFibrasTotal= findViewById(R.id.fibras_total);
        txtViewFibrasMeta = findViewById(R.id.fibras_meta);
        txtViewFibrasPercentagem= findViewById(R.id.fibras_percentagem);
        progressFibras= findViewById(R.id.progressBarFibras);
        layoutFibras= findViewById(R.id.layout_fibras);

        atualizarPainelTotal(); // ATUALIZA O PAINEL


        //*********AREA DA LISTAGEM

        List <Refeicao> lista = new RefeicoesBancoDados(getBaseContext()).listarRefeicoes();
        Collections.reverse(lista);
        listaDadosView = findViewById(R.id.lista_refeicoes_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listaDadosView.setHasFixedSize(true);
        listaDadosView.setLayoutManager(linearLayoutManager);
        listaAdapter = new ListaAdapter(lista);
        listaDadosView.setAdapter(listaAdapter);

        //-----Fim da area da listagem
    }// FIM DO ONCREATED



    // AREA DO MENU DO TOPO
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);

        MenuItem m1 = menu.findItem(R.id.menu_listar_refeicoes);
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
// ********** ATUALIZAR O PAINEL DE MACROSNUTRIENTES GERAL
    private void atualizarPainelTotal(){

        // atualizacao da parte energia
        int soma = total.energia;
        txtViewEnergiaTotal.setText(String.valueOf(soma));
        txtViewEnergiaMeta.setText(String.valueOf(metaEnergia));
        String percentagem = (soma*100/metaEnergia)+"%";
        txtViewEnergiaPercentagem.setText(percentagem);
        progressEnergia.setMax(metaEnergia);
        progressEnergia.setProgress(soma);

        if(metaEnergia<=soma) {
            layoutEnergia.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
        }
        else
            layoutEnergia.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));

        // atualizacao da parte proteinas
        soma = (int)(total.proteinas);
        int meta = Math.round(metaProteina);
        txtViewProteinasTotal.setText(String.valueOf(soma));
        txtViewProteinasMeta.setText(String.valueOf(meta));
        percentagem = (soma*100/meta)+"%";
        txtViewProteinasPercentagem.setText(percentagem);
        progressProteinas.setMax(meta);
        progressProteinas.setProgress(soma);

        if(meta<=soma) {
            layoutProteinas.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
        }
        else
            layoutProteinas.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));


        // atualizacao da parte carboidratos
        soma = (int)(total.carboidratos);
        meta = Math.round(metaCarboidrato);
        txtViewCarboidratosTotal.setText(String.valueOf(soma));
        txtViewCarboidratosMeta.setText(String.valueOf(meta));
        percentagem = (soma*100/meta)+"%";
        txtViewCarboidratosPercentagem.setText(percentagem);
        progressCarboidratos.setMax(meta);
        progressCarboidratos.setProgress(soma);

        if(meta<=soma) {
            layoutCarboidratos.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
        }
        else
            layoutCarboidratos.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));

        // atualizacao da parte gorduras
        soma = (int)(total.gorduras);
        meta = Math.round(metaGorduras);
        txtViewGordurasTotal.setText(String.valueOf(soma));
        txtViewGordurasMeta.setText(String.valueOf(meta));
        percentagem = (soma*100/meta)+"%";
        txtViewGordurasPercentagem.setText(percentagem);
        progressGorduras.setMax(meta);
        progressGorduras.setProgress(soma);

        if(meta<=soma) {
            layoutGorduras.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
        }
        else
            layoutGorduras.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));

        // atualizacao da parte fibras
        soma = (int)(total.fibras);
        meta = Math.round(metaFibras);
        txtViewFibrasTotal.setText(String.valueOf(soma));
        txtViewFibrasMeta.setText(String.valueOf(meta));
        percentagem = (soma*100/meta)+"%";
        txtViewFibrasPercentagem.setText(percentagem);
        progressFibras.setMax(meta);
        progressFibras.setProgress(soma);

        if(meta<=soma) {
            layoutFibras.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
        }
        else
            layoutFibras.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));




        //-----------fim da area do painel
    }
}
