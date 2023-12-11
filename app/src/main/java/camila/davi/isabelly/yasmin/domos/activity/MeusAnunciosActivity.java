package camila.davi.isabelly.yasmin.domos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.adapter.AnunciosAdapter;
import camila.davi.isabelly.yasmin.domos.adapter.AnunciosComparator;
import camila.davi.isabelly.yasmin.domos.adapter.MeusAnunciosAdapter;
import camila.davi.isabelly.yasmin.domos.bd.Anuncio;
import camila.davi.isabelly.yasmin.domos.model.HomeViewModel;

public class MeusAnunciosActivity  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meus_anuncios);

        RecyclerView rv_MeusAnuncios = findViewById(R.id.rv_MeusAnuncios);

        rv_MeusAnuncios.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_MeusAnuncios.setLayoutManager(layoutManager);
        MeusAnunciosAdapter meusAnunciosAdapter = new MeusAnunciosAdapter(this, new AnunciosComparator());
        rv_MeusAnuncios.setAdapter(meusAnunciosAdapter);

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        LiveData<PagingData<Anuncio>> anunciosLD = homeViewModel.getMeusAnunciosLd();

        anunciosLD.observe(this, new Observer<PagingData<Anuncio>>() {
            @Override
            public void onChanged(PagingData<Anuncio> anuncioPagingData) {
                meusAnunciosAdapter.submitData(getLifecycle(), anuncioPagingData);

            }
        });

    }
}
