package camila.davi.isabelly.yasmin.domos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.model.LoginViewModel;
import camila.davi.isabelly.yasmin.domos.util.Config;

public class LoginActivity extends AppCompatActivity {

    static int RESULT_REQUEST_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // permissões
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        checkForPermissions(permissions);


        TextView etCpfSenha = findViewById(R.id.etCpfSenha);
        TextView etSenhaLogin = findViewById(R.id.etSenhaLogin);
        TextView tvCadastraUsuario = findViewById(R.id.tvCadastraUsuario);
        TextView tvEsqueceuSenha = findViewById(R.id.tvEsqueceuSenha);

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String login = etCpfSenha.getText().toString();
                final String password = etSenhaLogin.getText().toString();

                LiveData<Boolean> resultLD = loginViewModel.login(login, password);

                resultLD.observe(LoginActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                        if(aBoolean) {

                            // guarda os dados de login e senha dentro da app
                            Config.setLogin(LoginActivity.this, login);
                            Config.setPassword(LoginActivity.this, password);

                            // exibe uma mensagem indicando que o login deu certo
                            Toast.makeText(LoginActivity.this, "Login realizado com sucesso", Toast.LENGTH_LONG).show();

                            // Navega para tela principal
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                        }
                        else {

                            Toast.makeText(LoginActivity.this, "Não foi possível realizar o login na aplicação", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        tvCadastraUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(i);
            }
        });

        tvEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RecuperaSenhaActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Verifica se as permissões necessárias já foram concedidas. Caso contrário, o usuário recebe
     * uma janela pedindo para conceder as permissões
     * @param permissions lista de permissões que se quer verificar
     */
    private void checkForPermissions(List<String> permissions) {
        List<String> permissionsNotGranted = new ArrayList<>();

        for(String permission : permissions) {
            if( !hasPermission(permission)) {
                permissionsNotGranted.add(permission);
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(permissionsNotGranted.size() > 0) {
                requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]),RESULT_REQUEST_PERMISSION);
            }
        }
    }

    /**
     * Verifica se uma permissão já foi concedida
     * @param permission
     * @return true caso sim, false caso não.
     */
    private boolean hasPermission(String permission) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(LoginActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    /**
     * Método chamado depois que o usuário já escolheu as permissões que quer conceder. Esse método
     * indica o resultado das escolhas do usuário.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final List<String> permissionsRejected = new ArrayList<>();
        if(requestCode == RESULT_REQUEST_PERMISSION) {

            for(String permission : permissions) {
                if(!hasPermission(permission)) {
                    permissionsRejected.add(permission);
                }
            }
        }

        if(permissionsRejected.size() > 0) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                    new AlertDialog.Builder(LoginActivity.this).
                            setMessage("Para usar essa app é preciso conceder essas permissões").
                            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                                }
                            }).create().show();
                }
            }
        }
    }
}