package com.example.dietaflex;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dietaflex.recursos.Metas;
import com.example.dietaflex.recursos.Nutricional;
import com.example.dietaflex.banco_de_dados.NutricionalBancoDados;

import com.example.dietaflex.recursos.Refeicao;
import com.example.dietaflex.banco_de_dados.RefeicoesBancoDados;
import com.example.dietaflex.recursos.Totalizacao;

import java.util.List;


public class AdicionarAlimentoActivity extends AppCompatActivity {



    private int id ;
    private float quantidade ;
    private int codigo;
    private boolean eEditar = false; // true se for para editar, false se nova inclusão
    private String nome;
    private String txtQuantidade;
    private String txtAlimento;

    private int energia;
    private float proteinas;
    private float carboidratos;
    private float gorduras;
    private float fibras;

    private EditText  campoQuantidade ;
    private AutoCompleteTextView campoNomeAlimento;
    private ImageButton botaoLimparCampo;
    private Button botaoSair;
    private Button botaoSalvar;
    private Button botaoExcluir ;


    private ConstraintLayout layoutMacros;
    private ConstraintLayout layoutMensagem;
    private TextView txtViewEnergia  ;
    private TextView txtViewMacros  ;

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

    private NutricionalBancoDados nutricionalBancoDados;
    private  List<Nutricional> listaNutricionalAlimentos ;
    private Totalizacao totalizacaoMacros;
    private Nutricional total;

    private RefeicoesBancoDados refeicoesBancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_alimento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        campoQuantidade = findViewById(R.id.campo_quantidade);
        campoNomeAlimento = findViewById(R.id.campo_nome_alimento);

        layoutMacros= findViewById(R.id.layout_macros);
        layoutMensagem= findViewById(R.id.layout_mensagem);

        txtViewEnergia = findViewById(R.id.txt_energia_valor);
        txtViewMacros = findViewById(R.id.txt_valores_macros_individual);

        nutricionalBancoDados = new NutricionalBancoDados(this);
        listaNutricionalAlimentos = nutricionalBancoDados.listarAlimentos();

        totalizacaoMacros = new Totalizacao(getBaseContext());
        total = totalizacaoMacros.macrosGeral();

        refeicoesBancoDados = new RefeicoesBancoDados(getBaseContext());

        botaoExcluir = findViewById(R.id.botao_excluir);
        botaoLimparCampo = findViewById(R.id.botaoLimpar);
        botaoSalvar = findViewById(R.id.botao_salvar);
        botaoSair = findViewById(R.id.botao_sair);

        verificaParametro(); // verifica se tem dados provindos de outras activities para editar

        //****** MODO EDITAR

        if(eEditar){
            campoQuantidade.setText(String.valueOf(quantidade));
            campoNomeAlimento.setText(nome);
            this.setTitle("Editar Alimento");
            botaoExcluir.setVisibility(Button.VISIBLE); //mostra o botao de excluir
        }

        atualizarValoresMacros(); // atualiza os valores e o painel

        //*********BOTAO EXCLUIR
        botaoExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    refeicoesBancoDados.removerRefeicao(id); // REMOVE DO BD

                    Toast.makeText(getBaseContext(), "\""+nome+"\" Foi Removido!", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getBaseContext(), ListarRefeicoesActivity.class));
                    finish();
                }
                catch (Exception e){
                    Toast.makeText(getBaseContext(),  "ERRO AO EXCLUIR", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getBaseContext(), ListarRefeicoesActivity.class));
                    finish();
                }
            }
        });

        //*********BOTAO LIMPAR CAMPO
            botaoLimparCampo();
        botaoLimparCampo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            campoNomeAlimento.setText("");
            }
        });

        //*********BOTAO SAIR

        botaoSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        //*********BOTAO SALVAR

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validaCampos()){
                    try {
                        Calendar datahorarioAgora = Calendar.getInstance();
                        datahorarioAgora.add(Calendar.HOUR_OF_DAY,RefeicoesBancoDados.fusoHorario);
                        SimpleDateFormat dataFormatada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Refeicao objeto = new Refeicao();
                        objeto.grupo = 1;
                        objeto.quantidade = Float.parseFloat(campoQuantidade.getText().toString());
                        objeto.datahorario = dataFormatada.format(datahorarioAgora.getTime());
                        objeto.codigo = codigo;
                        objeto.id = id;

                        if(eEditar){

                            refeicoesBancoDados.editarRefeicao(objeto); // EDITA O BD

                            Toast.makeText(getBaseContext(),  "\""+nome+"\" foi Editado! ", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getBaseContext(), ListarRefeicoesActivity.class));
                            finish();

                        }
                        else {

                            refeicoesBancoDados.adicionarRefeicao(objeto); // SALVA NOVO ITEM

                            Toast.makeText(getBaseContext(), "\""+nome+"\" foi Adicionado!", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getBaseContext(), ListarRefeicoesActivity.class));
                            finish();
                        }
                    }
                    catch (Exception e){

                        Toast.makeText(getBaseContext(),  R.string.alerta_erro_ao_salvar, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getBaseContext(), ListarRefeicoesActivity.class));
                        finish();

                    }
                }
            }
        });
        //----------fim BOTAO SALVAR

        //*********PARTE DO AUTO COMPLETE

        ArrayAdapter<Nutricional> adapter = new ArrayAdapter<Nutricional>(this,
                android.R.layout.simple_dropdown_item_1line,  listaNutricionalAlimentos);
        campoNomeAlimento.setThreshold(1);
        campoNomeAlimento.setAdapter(adapter);

        //-----------fim auto complete



        //********* PAINEL DE MACROS INDIVIDUAL
        campoNomeAlimento.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                atualizarValoresMacros();
                botaoLimparCampo ();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        campoQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                atualizarValoresMacros();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // ------fim do painel individual

    } // FIM DO ONCREATED

    // *******METODO pARA RECEBER DADOS PASSADOS PELA ACTIVITY LISTA DE REFEICOES
    private void verificaParametro(){
        try{
            Bundle bundle= getIntent().getExtras();
            if ((bundle != null) && (bundle.containsKey("REFEICAO"))){
                Refeicao refeicao = (Refeicao) bundle.getSerializable("REFEICAO");
                this.id = refeicao.id ;
                this.codigo = refeicao.codigo ;
                this.quantidade = refeicao.quantidade ;
                eEditar=true;
                for (Nutricional temp : listaNutricionalAlimentos) {
                    if (temp.codigo == this.codigo) {
                        this.nome = temp.nome;
                        break;
                    }
                }
            }
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),  "ERRO AO RECEBER DADOS", Toast.LENGTH_SHORT).show();
        }
    }
    //-------fim funcao editar

    private void atualizarValoresMacros() {
        txtQuantidade = campoQuantidade.getText().toString();
        if(verificarCamposVazios(txtQuantidade))
            txtQuantidade = "0";
        quantidade = Float.parseFloat(txtQuantidade);
        txtAlimento = campoNomeAlimento.getText().toString();

        Nutricional totalizacao = totalizacaoMacros.macrosIndividual(txtAlimento, quantidade);
        codigo = totalizacao.codigo;
        energia = totalizacao.energia;
        proteinas = totalizacao.proteinas;
        carboidratos = totalizacao.carboidratos;
        gorduras = totalizacao.gorduras;
        fibras = totalizacao.fibras;
        nome =  txtAlimento;
        atualizarQuadroMacros();

    }
    //*******FUnçÃO ATUALIZAR MACRO NUTRIENTES

    private void atualizarQuadroMacros(){


        if(codigo < 0){
            layoutMacros.setVisibility(View.GONE);
            layoutMensagem.setVisibility(View.VISIBLE);
        }
        else
        {
            String  pro =  truncar(proteinas);
            String  car =  truncar(carboidratos);
            String  gor =  truncar(gorduras);
            String  fib =  truncar(fibras);

            String textoMacros = pro+"\n"+car+"\n"+gor+"\n"+fib ;
            txtViewEnergia.setText(String.valueOf(energia));
            txtViewMacros.setText(textoMacros);

            layoutMacros.setVisibility(View.VISIBLE);
            layoutMensagem.setVisibility(View.GONE);
        }
        atualizarPainelTotal();
    }
    //-------fim funcao ATUALIZAR MACRONUTRIENTES


    // ********** ATUALIZAR O PAINEL DE MACROSNUTRIENTES GERAL
    private void atualizarPainelTotal(){
        boolean flaglayoutMacros = false;

        // atualizacao da parte energia
        int soma = total.energia+energia;
        txtViewEnergiaTotal.setText(String.valueOf(soma));
        txtViewEnergiaMeta.setText(String.valueOf(metaEnergia));
        String percentagem = (soma*100/metaEnergia)+"%";
        txtViewEnergiaPercentagem.setText(percentagem);
        progressEnergia.setMax(metaEnergia);
        progressEnergia.setProgress(soma);

        if(metaEnergia<=soma) {
            layoutEnergia.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
            flaglayoutMacros= true;
        }
        else
            layoutEnergia.setBackgroundColor(Color.parseColor(getString(R.color.fundoclaro)));

        // atualizacao da parte proteinas
        soma = (int)(total.proteinas+proteinas);
        int meta = Math.round(metaProteina);
        txtViewProteinasTotal.setText(String.valueOf(soma));
        txtViewProteinasMeta.setText(String.valueOf(meta));
        percentagem = (soma*100/meta)+"%";
        txtViewProteinasPercentagem.setText(percentagem);
        progressProteinas.setMax(meta);
        progressProteinas.setProgress(soma);

        if(meta<=soma) {
            layoutProteinas.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
            flaglayoutMacros= true;
        }
        else
            layoutProteinas.setBackgroundColor(Color.parseColor(getString(R.color.fundoclaro)));


        // atualizacao da parte carboidratos
        soma = (int)(total.carboidratos+carboidratos);
        meta = Math.round(metaCarboidrato);
        txtViewCarboidratosTotal.setText(String.valueOf(soma));
        txtViewCarboidratosMeta.setText(String.valueOf(meta));
        percentagem = (soma*100/meta)+"%";
        txtViewCarboidratosPercentagem.setText(percentagem);
        progressCarboidratos.setMax(meta);
        progressCarboidratos.setProgress(soma);

        if(meta<=soma) {
            layoutCarboidratos.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
            flaglayoutMacros= true;
        }
        else
            layoutCarboidratos.setBackgroundColor(Color.parseColor(getString(R.color.fundoclaro)));

        // atualizacao da parte gorduras
        soma = (int)(total.gorduras+gorduras);
        meta = Math.round(metaGorduras);
        txtViewGordurasTotal.setText(String.valueOf(soma));
        txtViewGordurasMeta.setText(String.valueOf(meta));
        percentagem = (soma*100/meta)+"%";
        txtViewGordurasPercentagem.setText(percentagem);
        progressGorduras.setMax(meta);
        progressGorduras.setProgress(soma);

        if(meta<=soma) {
            layoutGorduras.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
            flaglayoutMacros= true;
        }
        else
            layoutGorduras.setBackgroundColor(Color.parseColor(getString(R.color.fundoclaro)));

        // atualizacao da parte fibras
        soma = (int)(total.fibras+fibras);
        meta = Math.round(metaFibras);
        txtViewFibrasTotal.setText(String.valueOf(soma));
        txtViewFibrasMeta.setText(String.valueOf(meta));
        percentagem = (soma*100/meta)+"%";
        txtViewFibrasPercentagem.setText(percentagem);
        progressFibras.setMax(meta);
        progressFibras.setProgress(soma);

        if(meta<=soma) {
            layoutFibras.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
            flaglayoutMacros= true;
        }
        else
            layoutFibras.setBackgroundColor(Color.parseColor(getString(R.color.fundoclaro)));


        // verifica se algum passou da meta e muda cor da caixa de msg
        if(flaglayoutMacros) {
            layoutMacros.setBackgroundColor(Color.parseColor(getString(R.color.destaque_alerta)));
            flaglayoutMacros = false;
        }
        else
            layoutMacros.setBackgroundColor(Color.parseColor(getString(R.color.fundomenosclaro)));
    }


    //-----------fim da area do painel

    //********MOSTRA BOTAO DE LIMPAR CAMPO
    private void botaoLimparCampo (){
        if (verificarCamposVazios(campoNomeAlimento.getText().toString())) {
            botaoLimparCampo.setVisibility(ImageButton.GONE);
        }else{
            botaoLimparCampo.setVisibility(ImageButton.VISIBLE);
        }
    }

    //*********METODOS UTILITARIOS
    private boolean verificarCamposVazios(String valor){
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado; // retorna verdadeiro se tiver vazio
    }
    private boolean verificarCodigo(String valor){//retorna false se não houver na BD
        if (!verificarCamposVazios(valor)) {
            for(Nutricional temp : listaNutricionalAlimentos) {
                if(temp.nome.equals(valor)) {
                    this.codigo= temp.codigo;
                    return true;
                }
            }
        }
        return false;
    }
    private boolean validaCampos() {

        boolean flag = false;
        txtQuantidade = campoQuantidade.getText().toString();
        txtAlimento = campoNomeAlimento.getText().toString();
        String mensagem = "";


        if (flag = verificarCamposVazios(txtAlimento)) {
            campoNomeAlimento.requestFocus();
            mensagem = getString(R.string.msg_alimento_nao_especificado);
        }
        else
        if(!verificarCodigo(txtAlimento)){
            flag =true;
            campoNomeAlimento.requestFocus();
            mensagem = getString(R.string.msg_alimento_nao_consta);
        }
        else
        if (flag = verificarCamposVazios(txtQuantidade)){
            campoQuantidade.requestFocus();
            mensagem = getString(R.string.msg_quatidade_nao_especificada);
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

    private String truncar(float valor) {
        float retorno;
        if(valor >=10)
            retorno = Math.round(valor);
        else
            retorno = valor;
        DecimalFormat decimalFormat = new DecimalFormat("0.#");
        return  decimalFormat.format(retorno);
    }

    //--------fim dos metodos utilitarios



   //******* AREA DO MENU DO TOPO
   public boolean onCreateOptionsMenu(Menu menu){
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.menu_toolbar,menu);

       MenuItem m1 = menu.findItem(R.id.menu_adicionar_refeicao);
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
// -------- FIM DO MENU DO TOPO

}


