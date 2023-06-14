package camila.davi.isabelly.yasmin.domos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import camila.davi.isabelly.yasmin.domos.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLogin();
            }
        }, 2000);
    }

    private void mostrarLogin(){
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }
}