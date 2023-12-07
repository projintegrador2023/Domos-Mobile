package camila.davi.isabelly.yasmin.domos.fragment;

import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.activity.CadastroUsuarioActivity;
import camila.davi.isabelly.yasmin.domos.activity.HomeActivity;
import camila.davi.isabelly.yasmin.domos.bd.Divisao;
import camila.davi.isabelly.yasmin.domos.bd.Moradia;
import camila.davi.isabelly.yasmin.domos.bd.NumDivCondominio;
import camila.davi.isabelly.yasmin.domos.bd.Usuario;
import camila.davi.isabelly.yasmin.domos.model.CadastroUsuarioViewModel;
import camila.davi.isabelly.yasmin.domos.model.DomosRepository;
import camila.davi.isabelly.yasmin.domos.util.Config;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CadastroUsuario2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroUsuario2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Usuario usuario;

    public CadastroUsuario2Fragment() {
        // Required empty public constructor
    }
    public CadastroUsuario2Fragment(Usuario usuario) {
       this.usuario = usuario;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CadastrUsuario2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadastroUsuario2Fragment newInstance(Usuario usuario) {
        return new CadastroUsuario2Fragment(usuario);
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
        return inflater.inflate(R.layout.fragment_cadastro_usuario2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spAptoCadastro = view.findViewById(R.id.spAptoCadastro2);
        Spinner spDivisaoCadastro = view.findViewById(R.id.spDivisaoCadastro2);

        Button btnCriarCadastro = view.findViewById(R.id.btnCriarCadastro);
        btnCriarCadastro.setEnabled(false);

        CadastroUsuarioViewModel cadastroUsuarioViewModel = new ViewModelProvider(getActivity()).get(CadastroUsuarioViewModel.class);

        LiveData<NumDivCondominio> resultLD = cadastroUsuarioViewModel.pegarNumDiv(usuario.codigoCondominio);

        resultLD.observe(getViewLifecycleOwner(), new Observer<NumDivCondominio>() {
            @Override
            public void onChanged(NumDivCondominio numDivCondominio) {

                if(numDivCondominio != null) {
                    ArrayAdapter adapterNums = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, numDivCondominio.getNumeros());
                    spAptoCadastro.setAdapter(adapterNums);

                    ArrayAdapter adapterDiv = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, numDivCondominio.getDivisoes());
                    spDivisaoCadastro.setAdapter(adapterDiv);
                    btnCriarCadastro.setEnabled(true);
                }
            }
        });


        btnCriarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String apartamento = spAptoCadastro.getSelectedItem().toString();
                String divisao = spDivisaoCadastro.getSelectedItem().toString();
                if (!apartamento.equals("Número do apartamento") && !divisao.equals("Divisão")) {
                    CadastroUsuarioActivity cadastroUsuarioActivity = (CadastroUsuarioActivity) getActivity();
                    cadastroUsuarioActivity.cadastrar(usuario.cpf, usuario.nome, usuario.email, usuario.senha, usuario.codigoCondominio, apartamento, divisao);
                }

            }
        });
    }
}