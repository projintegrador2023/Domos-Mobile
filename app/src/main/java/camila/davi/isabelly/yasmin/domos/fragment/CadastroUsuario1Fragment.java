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

import java.util.InputMismatchException;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.activity.CadastroUsuarioActivity;
import camila.davi.isabelly.yasmin.domos.bd.Usuario;

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
        EditText etConfirmarSenhaCadastro = view.findViewById(R.id.etConfirmarSenhaCadastro2);
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
                    if (validaCPF(cpf)){
                        if (senha.equals(confirmarSenha)){
                            CadastroUsuarioActivity cadastroUsuarioActivity = (CadastroUsuarioActivity) getActivity();
                            Usuario usuario = new Usuario(cpf, nome, email, senha, codigoCondominio);
                            cadastroUsuarioActivity.setCadastroUsuario2Fragment(usuario);
                        } else {
                            Toast.makeText(getActivity(), "As senhas devem ser iguais.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Insira todos os dados corretamente", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean validaCPF(String cpf){
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11))
            return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}