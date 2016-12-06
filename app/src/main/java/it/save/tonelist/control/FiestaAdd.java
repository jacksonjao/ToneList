package it.save.tonelist.control;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

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
    byte[] bytesImagen;
    private StorageReference mStorageRef;

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
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //controlo el menu desplegable

        btn_menu = (ImageButton) findViewById(R.id.bnt_menu);
        tv_listaPrincipal = (TextView) findViewById(R.id.tv_listaPrincipal);
        menu = (RelativeLayout) findViewById(R.id.dl_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_lista);
        drawerLayout.setScrimColor(Color.argb(230, 0, 0, 0));
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        validarMenu();
    }

    public void anadirFiesta(View view) {
        registrarDatos(bytesImagen);


        System.out.println();
    }

    public void addImage(View v) {

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
                Bitmap b = Bitmap.createScaledBitmap(bitmapGaleria, 240, proporcionY(240, ancho, alto), false);
                iv_logo.setImageBitmap(b);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byteArray = stream.toByteArray();
                bytesImagen = byteArray;

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }


    public void registrarDatos(byte[] file) {

        StorageReference riversRef = mStorageRef.child("images/" + Calendar.getInstance().getTime() + ".jpg");

        riversRef.putBytes(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        System.out.println(taskSnapshot.getDownloadUrl());


                        FiestaSimple fs = new FiestaSimple();
                        fs.creator = user.getEmail();
                        fs.creationDate = System.currentTimeMillis();
                        fs.name = etEvento.getText().toString();
                        fs.direccion = etDireccion.getText().toString();
                        fs.imgUrl = downloadUrl.toString();
                        listReference.child(user.getEmail().split("@")[0] + ((int) (Math.random() * 9999))).setValue(fs);
                        startActivity(new Intent(getApplicationContext(), MisFiestas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }


    public void menu(View v) {
        drawerLayout.openDrawer(menu);

    }


    public void validarMenu() {
        tv_listaPrincipal.setText(getIntent().getStringExtra("name"));
    }

    public void misFiestas(View v) {
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

    public int proporcionY(int scale, int w, int h) {
        int redimension = 0;
        redimension = (scale * h) / w;
        return redimension;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MisFiestas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}