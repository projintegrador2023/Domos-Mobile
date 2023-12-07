package camila.davi.isabelly.yasmin.domos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.bd.Anuncio;
import camila.davi.isabelly.yasmin.domos.util.ImageCache;

public class AnunciosAdapter extends PagingDataAdapter<Anuncio, MyViewHolder> {

    public AnunciosAdapter(@NonNull DiffUtil.ItemCallback<Anuncio> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_anuncio, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Anuncio anuncio = this.getItem(position);

        // preenche o campo de nome
        TextView tvTituloItemAnuncio = holder.itemView.findViewById(R.id.tvTituloItemAnuncio);
        tvTituloItemAnuncio.setText(anuncio.titulo);

        TextView tvDataItemAnuncio = holder.itemView.findViewById(R.id.tvDataItemAnuncio);
        tvDataItemAnuncio.setText(anuncio.dataHoraPostagem);

        // preenche o campo de preço
        TextView tvDescricaoItemAnuncio = holder.itemView.findViewById(R.id.tvDescricaoItemAnuncio);
        tvDescricaoItemAnuncio.setText(anuncio.descricao);

        TextView tvTagItemAnuncio = holder.itemView.findViewById(R.id.tvTagItemAnuncio);
        if (anuncio.tag == 1){
            tvTagItemAnuncio.setText("Alimentação");
        } else if (anuncio.tag == 2){
            tvTagItemAnuncio.setText("Vestuário");
        } else if (anuncio.tag == 3){
            tvTagItemAnuncio.setText("Eletrônicos");
        } else if (anuncio.tag == 4){
            tvTagItemAnuncio.setText("Beleza");
        } else if (anuncio.tag == 5){
            tvTagItemAnuncio.setText("Decoração");
        } else if (anuncio.tag == 6){
            tvTagItemAnuncio.setText("Pet-Shop");
        } else if (anuncio.tag == 7){
            tvTagItemAnuncio.setText("Serviços");
        }

        TextView tvNomeAvisos = holder.itemView.findViewById(R.id.tvNomeAvisos);
        tvNomeAvisos.setText(anuncio.usuario);

        TextView tvDivisao = holder.itemView.findViewById(R.id.tvDivisao);
        tvDivisao.setText(anuncio.divisao);

        TextView tvNum = holder.itemView.findViewById(R.id.tvNum);
        tvNum.setText(anuncio.num);

        ImageView imvImagemAnuncio = holder.itemView.findViewById(R.id.imvImagemAnuncio);
        ImageCache.loadImageUrlToImageView(holder.itemView.getContext(), anuncio.img, imvImagemAnuncio, 150, 150);
    }
}
