package it.save.tonelist.control;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;

import it.save.tonelist.R;

public class FiestaAdd extends AppCompatActivity {

    EditText etEvento;
    EditText etDireccion;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference listReference;
    FirebaseUser user;
    TextView tv_listaPrincipal;
    ImageButton btn_menu;
    RelativeLayout menu;
    DrawerLayout drawerLayout;
    ImageView iv_logo;

    public static Bitmap resizeImage(Bitmap resId, int w, int h) {
        // cargamos la imagen de origen
        Bitmap BitmapOrg = Bitmap.createBitmap(resId);
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        // calculamos el escalado de la imagen destino
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // para poder manipular la imagen
        // debemos crear una matriz
        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        // volvemos a crear la imagen con los nuevos valores
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0,
                width, height, matrix, true);
        // si queremos poder mostrar nuestra imagen tenemos que crear un
        // objeto drawable y así asignarlo a un botón, imageview...
        return resizedBitmap;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiesta_add);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        etEvento = (EditText) findViewById(R.id.et_evento);
        etDireccion = (EditText) findViewById(R.id.et_direccion);
        firebaseDatabase = FirebaseDatabase.getInstance();
        listReference = firebaseDatabase.getReference().child("lists");
        user = FirebaseAuth.getInstance().getCurrentUser();


        //controlo el menu desplegable
        btn_menu = (ImageButton) findViewById(R.id.bnt_menu);
        tv_listaPrincipal = (TextView) findViewById(R.id.tv_listaPrincipal);
        menu = (RelativeLayout) findViewById(R.id.dl_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_lista);
        drawerLayout.setScrimColor(Color.argb(230,0,0,0));
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        validarMenu();
    }

    public void anadirFiesta(View view) {

        FiestaSimple fs = new FiestaSimple();
        fs.creator = user.getEmail();
        fs.creationDate = System.currentTimeMillis();
        fs.name = etEvento.getText().toString();
        listReference.child(user.getEmail().split("@")[0] + ((int) (Math.random() * 9999))).setValue(fs);
        startActivity(new Intent(getApplicationContext(), MisFiestas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

public void addImage(View v){

    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(intent, 0);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            try {

               Bitmap bitmapGaleria = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                int alto = bitmapGaleria.getHeight();
                int ancho = bitmapGaleria.getWidth();
                Bitmap imagenProcesada= resizeImage(bitmapGaleria, 360, proporcionY(360, ancho, alto));
                BitmapDrawable imagen = new BitmapDrawable(getResources(), imagenProcesada);
                iv_logo.setBackground(imagen);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public int proporcionY(int scale, int w, int h) {
        int redimension = 0;
        redimension = (scale * h) / w;
        return redimension;
    }

    public void menu(View v) {
        drawerLayout.openDrawer(menu);

    }


    public void validarMenu(){
        tv_listaPrincipal.setText(getIntent().getStringExtra("name"));
    }

    public void misFiestas(View v){


        startActivity(new Intent(getApplicationContext(), MisFiestas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();

    }


    public void salir(View v) {
        startActivity(new Intent(getApplicationContext(), LeerQr.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void cerrarSesion(View v) {
        startActivity(new Intent(getApplicationContext(), Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void cambiarRol(View v) {
        startActivity(new Intent(getApplicationContext(), Rol.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MisFiestas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

}
