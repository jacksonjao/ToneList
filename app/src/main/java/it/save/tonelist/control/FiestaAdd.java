package it.save.tonelist.control;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import it.save.tonelist.R;

public class FiestaAdd extends AppCompatActivity {
    EditText et_nacimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiesta_add);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        et_nacimiento = (EditText) findViewById(R.id.et_fecha);
    }



    public void calendario(View v) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View viewFecha = inflater.inflate(R.layout.dialog_calendario, null);
        builder.setView(viewFecha);
        final DatePicker picker_calendario = (DatePicker) viewFecha.findViewById(R.id.cv_calendario);
        Button btn_aceptar = (Button) viewFecha.findViewById(R.id.btn_aceptar);
        Button btn_cancelar = (Button) viewFecha.findViewById(R.id.btn_cancelar);

        final AlertDialog alertDialog = builder.create();

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_nacimiento.setText(picker_calendario.getDayOfMonth() + "/" + picker_calendario.getMonth() + "/" + picker_calendario.getYear());
                alertDialog.cancel();
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }
}
