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
import camila.davi.isabelly.yasmin.domos.bd.Aviso;

public class AvisosAdapter extends PagingDataAdapter<Aviso, MyViewHolder>  {

    public AvisosAdapter(@NonNull DiffUtil.ItemCallback<Aviso> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_aviso, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Aviso aviso = this.getItem(position);

        // preenche o campo de nome
        TextView tvTituloAviso = holder.itemView.findViewById(R.id.tvTituloAviso);
        tvTituloAviso.setText(aviso.titulo);

        TextView tvDataHoraAviso = holder.itemView.findViewById(R.id.tvDataHoraAviso);
        tvDataHoraAviso.setText(aviso.dataHoraPostagem);

        // preenche o campo de preço
        TextView tvDescAviso = holder.itemView.findViewById(R.id.tvDescAviso);
        tvDescAviso.setText(aviso.descricao);

        TextView tvImportAviso = holder.itemView.findViewById(R.id.tvImportAviso);
        tvImportAviso.setText(aviso.importancia);


    }
}
