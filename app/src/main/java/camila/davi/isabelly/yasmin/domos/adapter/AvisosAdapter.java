package camila.davi.isabelly.yasmin.domos.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.activity.HomeActivity;
import camila.davi.isabelly.yasmin.domos.bd.Aviso;

public class AvisosAdapter extends PagingDataAdapter<Aviso, MyViewHolder>  {

    HomeActivity homeActivity;

    public AvisosAdapter(HomeActivity homeActivity, @NonNull DiffUtil.ItemCallback<Aviso> diffCallback) {
        super(diffCallback);
        this.homeActivity = homeActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_aviso, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Aviso aviso = this.getItem(position);

        TextView tvTituloAviso = holder.itemView.findViewById(R.id.tvTituloAviso);
        tvTituloAviso.setText(aviso.titulo);

        TextView tvDataHoraAviso = holder.itemView.findViewById(R.id.tvDataHoraAviso);
        tvDataHoraAviso.setText(aviso.dataHoraPostagem);

        TextView tvDescAviso = holder.itemView.findViewById(R.id.tvDescAviso);
        tvDescAviso.setText(aviso.descricao);

        TextView tvImportAviso = holder.itemView.findViewById(R.id.tvImportAviso);
        if (aviso.importancia == 1) {
            tvImportAviso.setText("Crítico");
        } else if (aviso.importancia == 2) {
            tvImportAviso.setText("Urgente");
        } else if (aviso.importancia == 3) {
            tvImportAviso.setText("Importante");
        }

    }
}
