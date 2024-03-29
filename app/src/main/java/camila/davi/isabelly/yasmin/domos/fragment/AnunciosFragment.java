package camila.davi.isabelly.yasmin.domos.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.activity.CriarAnuncioActivity;
import camila.davi.isabelly.yasmin.domos.activity.CriarAvisoActivity;
import camila.davi.isabelly.yasmin.domos.activity.HomeActivity;
import camila.davi.isabelly.yasmin.domos.adapter.AnunciosAdapter;
import camila.davi.isabelly.yasmin.domos.adapter.AnunciosComparator;
import camila.davi.isabelly.yasmin.domos.adapter.AvisosAdapter;
import camila.davi.isabelly.yasmin.domos.adapter.AvisosComparator;
import camila.davi.isabelly.yasmin.domos.bd.Anuncio;
import camila.davi.isabelly.yasmin.domos.bd.Aviso;
import camila.davi.isabelly.yasmin.domos.model.CriarAnuncioViewModel;
import camila.davi.isabelly.yasmin.domos.model.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnunciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnunciosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnunciosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AnunciosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnunciosFragment newInstance() {
        return new AnunciosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anuncios, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fabPostarAnuncio = view.findViewById(R.id.fabPostarAnuncio);
        Spinner spFiltroAnuncios = view.findViewById(R.id.spFiltroAnuncios);

        //CriarAnuncioViewModel criarAnuncioViewModel = new ViewModelProvider(this).get(CriarAnuncioViewModel.class);

        //LiveData<List<String>> resultLD = criarAnuncioViewModel.pegarTags();

        //resultLD.observe((HomeActivity) getActivity(), new Observer<List<String>>() {
            //@Override
            //public void onChanged(List<String> listaTag) {

                //if(listaTag != null) {
                    //ArrayAdapter adapterTag = new ArrayAdapter<String>((HomeActivity) getActivity(), android.R.layout.simple_spinner_item, listaTag);
                    //spFiltroAnuncios.setAdapter(adapterTag);

                //}
            //}
        //});

        fabPostarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent((HomeActivity) getActivity(), CriarAnuncioActivity.class);
                startActivity(i);
            }
        });

        RecyclerView rvAnuncios = view.findViewById(R.id.rvAnuncios);
        rvAnuncios.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((HomeActivity) getActivity());
        rvAnuncios.setLayoutManager(layoutManager);
        AnunciosAdapter anunciosAdapter = new AnunciosAdapter((HomeActivity) getActivity(), new AnunciosComparator());
        rvAnuncios.setAdapter(anunciosAdapter);

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        String tag = spFiltroAnuncios.getSelectedItem().toString();

        LiveData<PagingData<Anuncio>> anunciosLD = homeViewModel.getAnunciosLd(tag);

        anunciosLD.observe((HomeActivity) getActivity(), new Observer<PagingData<Anuncio>>() {
            @Override
            public void onChanged(PagingData<Anuncio> anuncioPagingData) {
                anunciosAdapter.submitData(getLifecycle(), anuncioPagingData);
            }
        });

        spFiltroAnuncios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tag = spFiltroAnuncios.getSelectedItem().toString();
                LiveData<PagingData<Anuncio>> anunciosLD = homeViewModel.getAnunciosLd(tag);
                AnunciosAdapter anunciosAdapter = new AnunciosAdapter((HomeActivity) getActivity(), new AnunciosComparator());
                rvAnuncios.setAdapter(anunciosAdapter);
                anunciosLD.observe((HomeActivity) getActivity(), new Observer<PagingData<Anuncio>>() {
                    @Override
                    public void onChanged(PagingData<Anuncio> anunciosPagingData) {
                        anunciosAdapter.submitData(getLifecycle(), anunciosPagingData);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}