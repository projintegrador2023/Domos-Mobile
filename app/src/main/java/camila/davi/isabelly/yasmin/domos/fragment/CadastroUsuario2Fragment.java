package camila.davi.isabelly.yasmin.domos.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.activity.CadastroUsuarioActivity;
import camila.davi.isabelly.yasmin.domos.activity.HomeActivity;
import camila.davi.isabelly.yasmin.domos.bd.Usuario;
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
        btnCriarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String apartamento = spAptoCadastro.getSelectedItem().toString();
                String divisao = spDivisaoCadastro.getSelectedItem().toString();
                if (!apartamento.equals("Número do apartamento") && !divisao.equals("Divisão")){
                    // O ViewModel possui o método register, que envia as informações para o servidor web.
                    // O servidor web recebe as infos e cadastra um novo usuário. Se o usuário foi cadastrado
                    // com sucesso, a app recebe o valor true. Se não o servidor retorna o valor false.
                    //
                    // O método de register retorna um LiveData, que na prática é um container que avisa
                    // quando o resultado do servidor chegou.
                    LiveData<Boolean> resultLD = CadastroUsuarioViewModel.register(, );

                    // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado indicando
                    // se o cadastro deu certo ou não será guardado dentro do LiveData. Neste momento o
                    // LiveData avisa que o resultado chegou chamando o método onChanged abaixo.
                    resultLD.observe((CadastroUsuarioActivity) getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            // aBoolean contém o resultado do cadastro. Se aBoolean for true, significa
                            // que o cadastro do usuário foi feito corretamente. Indicamos isso ao usuário
                            // através de uma mensagem do tipo toast e finalizamos a Activity. Quando
                            // finalizamos a Activity, voltamos para a tela de login.
                            if(aBoolean) {
                                Toast.makeText(RegisterActivity.this, "Novo usuario registrado com sucesso", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else {
                                // Se o cadastro não deu certo, apenas continuamos na tela de cadastro e
                                // indicamos com uma mensagem ao usuário que o cadastro não deu certo.
                                Toast.makeText(RegisterActivity.this, "Erro ao registrar novo usuário", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    Intent i = new Intent((CadastroUsuarioActivity) getActivity(), HomeActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getActivity(), "Selecione opções válidas", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}