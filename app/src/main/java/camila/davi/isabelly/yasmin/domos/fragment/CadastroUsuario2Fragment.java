package camila.davi.isabelly.yasmin.domos.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.model.CadastroUsuarioViewModel;

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

    CadastroUsuarioViewModel cadastroUsuarioViewModel;

    public CadastroUsuario2Fragment() {
        // Required empty public constructor
    }
    public CadastroUsuario2Fragment(CadastroUsuarioViewModel cadastroUsuarioViewModel) {
       this.cadastroUsuarioViewModel = cadastroUsuarioViewModel;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CadastrUsuario2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadastroUsuario2Fragment newInstance(CadastroUsuarioViewModel cadastroUsuarioViewModel) {
        return new CadastroUsuario2Fragment(cadastroUsuarioViewModel);
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
        btnCriarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String apartamento = spAptoCadastro.getSelectedItem().toString();
                String divisao = spDivisaoCadastro.getSelectedItem().toString();
                System.out.println(apartamento + divisao);
            }
        });
    }
}