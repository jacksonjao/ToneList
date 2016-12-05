package it.save.tonelist.control;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import it.save.tonelist.R;

public class LeerQr extends AppCompatActivity {

    EditText etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_qr);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        etCode = (EditText) findViewById(R.id.et_escribeCodigo);

    }

    public void validar(View v){
        String code = etCode.getText().toString();
        startActivity(new Intent(this, ListaPrincipal.class).putExtra("code", code));

    }
}
