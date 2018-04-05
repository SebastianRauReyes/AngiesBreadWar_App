package com.breadwar.angies.angiesbreadwar_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static com.breadwar.angies.angiesbreadwar_app.LoginActivity.intentMain;

public class Report2Activity extends AppCompatActivity {

    private ImageView foto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report2);

        foto = findViewById(R.id.imageView_foto);


    }

    public void tomarFoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        foto.setImageBitmap(bitmap);
    }

    public void enviarFoto(View view){
        if(foto.getDrawable() == null){
            Toast.makeText(this, "Imagen vacia", Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(this, "Imagen Enviada", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(intentMain);

        }
    }
    public void regresar(View view){
        finish();
    }
}


