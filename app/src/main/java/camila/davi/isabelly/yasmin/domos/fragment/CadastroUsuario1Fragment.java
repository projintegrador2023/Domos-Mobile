package camila.davi.isabelly.yasmin.domos.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.activity.CadastroUsuarioActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CadastroUsuario1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CadastroUsuario1Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CadastroUsuario1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CadastroUsuario1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CadastroUsuario1Fragment newInstance() {
        return new CadastroUsuario1Fragment();
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
        return inflater.inflate(R.layout.fragment_cadastro_usuario1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // itens de interface
        Button btnContinuarCadastro = view.findViewById(R.id.btnContinuarCadastro);
        EditText etNomeCadastro = view.findViewById(R.id.etNomeCadastro);
        EditText etCpfCadastro = view.findViewById(R.id.etCpfCadastro);
        EditText etEmailCadastro = view.findViewById(R.id.etEmailCadastro);
        EditText etSenhaCadastro = view.findViewById(R.id.etSenhaCadastro);
        EditText etConfirmarSenhaCadastro = view.findViewById(R.id.etConfirmarSenhaCadastro);
        EditText etCodCond1 = view.findViewById(R.id.etCodCond1);

        // configura o que fazer quando clicar no botao de continuar
        btnContinuarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pega o texto inserido nos edit texts
                String nome = etNomeCadastro.getText().toString();
                String cpf = etCpfCadastro.getText().toString();
                String email = etEmailCadastro.getText().toString();
                String senha = etSenhaCadastro.getText().toString();
                String confirmarSenha = etConfirmarSenhaCadastro.getText().toString();
                String codigoCondominio = etCodCond1.getText().toString();

                if (nome.length() > 0 && cpf.length() > 0 && email.length() > 0 && senha.length() > 0 && confirmarSenha.length() > 0 && codigoCondominio.length() > 0){
                    if (senha.equals(confirmarSenha)){
                        CadastroUsuarioActivity cadastroUsuarioActivity = (CadastroUsuarioActivity) getActivity();
                        cadastroUsuarioActivity.setCadastroUsuario2Fragment();
                    } else {
                        Toast.makeText(getActivity(), "As senhas devem ser iguais.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Insira todos os dados corretamente", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}