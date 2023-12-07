package camila.davi.isabelly.yasmin.domos.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.activity.EditarPerfilActivity;
import camila.davi.isabelly.yasmin.domos.activity.HomeActivity;
import camila.davi.isabelly.yasmin.domos.activity.LoginActivity;
import camila.davi.isabelly.yasmin.domos.activity.MeusAnunciosActivity;
import camila.davi.isabelly.yasmin.domos.bd.Usuario;
import camila.davi.isabelly.yasmin.domos.model.CriarAnuncioViewModel;
import camila.davi.isabelly.yasmin.domos.model.HomeViewModel;
import camila.davi.isabelly.yasmin.domos.model.PerfilViewModel;
import camila.davi.isabelly.yasmin.domos.util.Config;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance() {
        return new PerfilFragment();
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
        return inflater.inflate(R.layout.fragment_perfil, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView etNomePerfil = view.findViewById(R.id.etNomePerfil);
        TextView etCpfPerfil = view.findViewById(R.id.etCpfPerfil);
        TextView etEmailPerfil = view.findViewById(R.id.etEmailPerfil);
        TextView etAptoPerfil = view.findViewById(R.id.etAptoPerfil);
        TextView etDivisaoPerfil = view.findViewById(R.id.etDivisaoPerfil);

        PerfilViewModel perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        LiveData<Usuario> resultLD = perfilViewModel.loadPerfil();

        resultLD.observe((HomeActivity) getActivity(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {

                if(usuario != null) {
                    etNomePerfil.setText(usuario.nome);
                    etCpfPerfil.setText(usuario.cpf);
                    etEmailPerfil.setText(usuario.email);
                    etAptoPerfil.setText(usuario.num_moradia);
                    etDivisaoPerfil.setText(usuario.divisao);

                }
            }
        });

        Button btnEditarPerfil = view.findViewById(R.id.btnEditarPerfil);
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent((HomeActivity) getActivity(), EditarPerfilActivity.class);
                startActivity(i);

            }
        });

        Button bnt_MeusAnuncios = view.findViewById(R.id.bnt_MeusAnuncios);
        bnt_MeusAnuncios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent((HomeActivity) getActivity(), MeusAnunciosActivity.class);
                startActivity(i);
            }
        });

        Button btnEncerrarSessao = view.findViewById(R.id.btnEncerrarSessao);
        btnEncerrarSessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.setLogin(getActivity(), "");
                Config.setPassword(getActivity(), "");
            }
        });
    }
}