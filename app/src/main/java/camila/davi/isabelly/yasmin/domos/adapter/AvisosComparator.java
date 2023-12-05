package camila.davi.isabelly.yasmin.domos.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import camila.davi.isabelly.yasmin.domos.bd.Aviso;

public class AvisosComparator extends DiffUtil.ItemCallback<Aviso> {
    @Override
    public boolean areItemsTheSame(@NonNull Aviso oldItem, @NonNull Aviso newItem) {
        return oldItem.codigoPostagem.equals(newItem.codigoPostagem);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Aviso oldItem, @NonNull Aviso newItem) {
        return oldItem.codigoPostagem.equals(newItem.codigoPostagem) &&
                oldItem.titulo.equals(newItem.titulo) &&
                oldItem.usuario.equals(newItem.usuario);
    }
}
