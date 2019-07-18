package com.example.dietaflex.recursos;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietaflex.AdicionarAlimentoActivity;
import com.example.dietaflex.R;


import java.util.List;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolderLista> {

    private List<Refeicao> dados;
    private Totalizacao totalizacao ;

    public ListaAdapter(List<Refeicao> dados){

        this.dados = dados;
    }

    private String truncar(float valor) {
        float retorno;
        if(valor >=10)
            retorno = Math.round(valor);
        else
            retorno = valor;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return  decimalFormat.format(retorno);
    }

    @Override
    public ListaAdapter.ViewHolderLista onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.listagem, parent, false);

        totalizacao = new Totalizacao(parent.getContext());

        ViewHolderLista retorno = new ViewHolderLista(view, parent.getContext());
        return retorno;
    }

    @Override
    public void onBindViewHolder (ListaAdapter.ViewHolderLista holder, int position) {

        if((dados!=null)&&(dados.size()>0)) {

            Refeicao valor = dados.get(position);
            Nutricional macros = totalizacao.macrosIndividual(valor.codigo,valor.quantidade);


            String  qnt =  String.valueOf((int)valor.quantidade);
            String  pro =  truncar(macros.proteinas);
            String  car =  truncar(macros.carboidratos);
            String  gor =  truncar(macros.gorduras);
            String  fib =  truncar(macros.fibras);
            String  ene =  String.valueOf(macros.energia);


            String textoSuperior = qnt+" g - "+macros.nome;
            String textoInferior = ene+" kcal | P: "+pro+" g | C: "+car+" g | G: "+gor+" g | F: "+fib+" g"  ;
            holder.txtSuperior.setText(textoSuperior);
            holder.txtInferior.setText(textoInferior);

        }

    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderLista extends RecyclerView.ViewHolder{

        public TextView txtSuperior;
        public TextView txtInferior;

        public ViewHolderLista( View itemView,final Context contexto) {
            super(itemView);
            txtSuperior = itemView.findViewById(R.id.texto_qnt_e_nome);
            txtInferior = itemView.findViewById(R.id.texto_energia_macros);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    if(dados.size() > 0){
                        Refeicao refeicao = dados.get(getLayoutPosition());

                    Intent aaaa = new Intent(contexto, AdicionarAlimentoActivity.class);
                    aaaa.putExtra("REFEICAO", refeicao);

                    ((AppCompatActivity)contexto).startActivityForResult(aaaa, 0);
                    }
                }
            });
        }
    }
}
