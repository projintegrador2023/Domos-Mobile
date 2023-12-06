package camila.davi.isabelly.yasmin.domos.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.List;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.activity.CriarAvisoActivity;
import camila.davi.isabelly.yasmin.domos.activity.EditarPerfilActivity;
import camila.davi.isabelly.yasmin.domos.activity.HomeActivity;
import camila.davi.isabelly.yasmin.domos.activity.LoginActivity;
import camila.davi.isabelly.yasmin.domos.adapter.AvisosAdapter;
import camila.davi.isabelly.yasmin.domos.adapter.AvisosComparator;
import camila.davi.isabelly.yasmin.domos.bd.Aviso;
import camila.davi.isabelly.yasmin.domos.model.CriarAnuncioViewModel;
import camila.davi.isabelly.yasmin.domos.model.CriarAvisoViewModel;
import camila.davi.isabelly.yasmin.domos.model.HomeViewModel;
import camila.davi.isabelly.yasmin.domos.util.Config;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvisosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvisosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AvisosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AvisosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvisosFragment newInstance() {

        return new AvisosFragment();
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
        return inflater.inflate(R.layout.fragment_avisos, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fabPostarAviso = view.findViewById(R.id.fabPostarAviso);
        Spinner spFiltroAvisos = view.findViewById(R.id.spFiltroAvisos);

        CriarAvisoViewModel criarAvisoViewModel = new ViewModelProvider(this).get(CriarAvisoViewModel.class);


        LiveData<List<String>> resultLD = criarAvisoViewModel.pegarImportancia();

        resultLD.observe((HomeActivity) getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> listaImportancia) {

                if(listaImportancia != null) {
                    ArrayAdapter adapterTag = new ArrayAdapter<String>((HomeActivity) getActivity(), android.R.layout.simple_spinner_item, listaImportancia);
                    spFiltroAvisos.setAdapter(adapterTag);

                }
            }
        });

        fabPostarAviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent((HomeActivity) getActivity(), CriarAvisoActivity.class);
                startActivity(i);
            }
        });

        RecyclerView rvAvisos = view.findViewById(R.id.rvAvisos);
        rvAvisos.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((HomeActivity) getActivity());
        rvAvisos.setLayoutManager(layoutManager);
        AvisosAdapter avisosAdapter = new AvisosAdapter(new AvisosComparator());
        rvAvisos.setAdapter(avisosAdapter);

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        String importancia;
        if (spFiltroAvisos.getSelectedItem() == null) {
            importancia = "Crítico";
        } else {
            importancia = spFiltroAvisos.getSelectedItem().toString();
        }

        LiveData<PagingData<Aviso>> avisosLD = homeViewModel.getAvisosLd(importancia);

        avisosLD.observe((HomeActivity) getActivity(), new Observer<PagingData<Aviso>>() {
            @Override
            public void onChanged(PagingData<Aviso> avisoPagingData) {
                avisosAdapter.submitData(getLifecycle(),avisoPagingData);
            }
        });
    }
}