package camila.davi.isabelly.yasmin.domos.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import camila.davi.isabelly.yasmin.domos.bd.Anuncio;

public class AnunciosComparator extends DiffUtil.ItemCallback<Anuncio> {
@Override
    public boolean areItemsTheSame(@NonNull Anuncio oldItem, @NonNull Anuncio newItem) {
        return oldItem.codigoPostagem.equals(newItem.codigoPostagem);
        }

    @Override
    public boolean areContentsTheSame(@NonNull Anuncio oldItem, @NonNull Anuncio newItem) {
        return oldItem.codigoPostagem.equals(newItem.codigoPostagem) &&
        oldItem.titulo.equals(newItem.titulo) &&
        oldItem.usuario.equals(newItem.usuario);
    }
}
