package com.breadwar.angies.angiesbreadwar_app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.breadwar.angies.angiesbreadwar_app.R;
import com.breadwar.angies.angiesbreadwar_app.interfaces.ApiService;
import com.breadwar.angies.angiesbreadwar_app.interfaces.ApiServiceGenerator;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseMessage;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseMessage2;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Report2Activity extends AppCompatActivity {


    private static final String TAG = Report2Activity.class.getSimpleName();

    private ImageView foto;
    private EditText comentario;
    private TextView info;

    private static final int CAPTURE_IMAGE_REQUEST = 300;
    private static int CAPTURE_IMAGE_DATOS;
    private Uri mediaFileUri;

    private String user_id = "1";
    private String maquinaria_id;
    private String aula_id;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            setContentView(R.layout.activity_report2);
            foto = findViewById(R.id.imageView_foto);
            comentario = findViewById(R.id.imageView_comentario);
            info = findViewById(R.id.TextView_info);


    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------
    public void takePicture(View view) {
        CAPTURE_IMAGE_DATOS = 0;
        try {

            if (!permissionsGranted()) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, PERMISSIONS_REQUEST);
                return;
            }

          // Creando el directorio de imágenes (si no existe)
            File mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    throw new Exception("Failed to create directory");
                }
            }
          /// Definiendo la ruta destino de la captura (Uri)
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            mediaFileUri = Uri.fromFile(mediaFile);

            // Iniciando la captura
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mediaFileUri);
            startActivityForResult(intent, CAPTURE_IMAGE_REQUEST);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
            Toast.makeText(this, "Error en captura: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------
    public void capturarValor(View view){
        CAPTURE_IMAGE_DATOS = 301;
        try {

            if (!permissionsGranted()) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, PERMISSIONS_REQUEST);
                return;
            }

            // Creando el directorio de imágenes (si no existe)
            File mediaStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    throw new Exception("Failed to create directory");
                }
            }
            /// Definiendo la ruta destino de la captura (Uri)
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            mediaFileUri = Uri.fromFile(mediaFile);

            // Iniciando la captura
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mediaFileUri);
            startActivityForResult(intent, CAPTURE_IMAGE_REQUEST);


        } catch (Exception e) {
            Log.e(TAG, e.toString());
            Toast.makeText(this, "Error en captura: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_REQUEST) {
            // Resultado en la captura de la foto
            if (resultCode == RESULT_OK) {
                try {
                     Log.d(TAG, "Procesando...");
                    // Toast.makeText(this, "Image saved to: " + mediaFileUri.getPath(), Toast.LENGTH_LONG).show();

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mediaFileUri);
                    // Reducir la imagen a 800px solo si lo supera
                    bitmap = scaleBitmapDown(bitmap, 1000);

                    if( CAPTURE_IMAGE_DATOS == 301){
                        Bitmap bitmapPersonal = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.muestra_lab);
                       getTextFromImage(bitmapPersonal);
                        CAPTURE_IMAGE_DATOS = 0;
                        Toast.makeText(this, "Leyendo Imagen...", Toast.LENGTH_LONG).show();
                        info.setText("Aula: "+aula_id+" - Equipo: " + maquinaria_id);
                    }else{
                        foto.setImageBitmap(bitmap);
                        Toast.makeText(this, "Guardando captura...", Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                    Toast.makeText(this, "Error al procesar imagen: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "ResultCode: RESULT_CANCELED");
            } else {
                Log.d(TAG, "ResultCode: " + resultCode);
            }
        }
    }
    /**
     * Permissions handler
     */

    private static final int PERMISSIONS_REQUEST = 200;

    private static String[] PERMISSIONS_LIST = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private boolean permissionsGranted() {
        for (String permission : PERMISSIONS_LIST) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST: {
                for (int i = 0; i < grantResults.length; i++) {
                    Log.d(TAG, "" + grantResults[i]);
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, PERMISSIONS_LIST[i] + " permiso rechazado!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Toast.makeText(this, "Permisos concedidos, intente nuevamente.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Redimensionar una imagen bitmap
    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }



    public void Reportar(View view){
        String coment = comentario.getText().toString();
        String maquina_id = maquinaria_id;
        String aulaid = aula_id;

        if (coment.isEmpty() && maquina_id.isEmpty() && aulaid.isEmpty()) {
            Toast.makeText(this, "Completar campos requeridos", Toast.LENGTH_SHORT).show();
            return;

        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<ResponseMessage2> call = null;

            File file = new File(mediaFileUri.getPath());
            Log.d(TAG, "File: " + file.getPath() + " - exists: " + file.exists());

            // Podemos enviar la imagen con el tamaño original, pero lo mejor será comprimila antes de subir (byteArray)
            // RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);

            Bitmap bitmap = BitmapFactory.decodeFile(mediaFileUri.getPath());

            // Reducir la imagen a 800px solo si lo supera
            bitmap = scaleBitmapDown(bitmap, 1000);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            RequestBody requestuser_id = RequestBody.create(MultipartBody.FORM, user_id );
            RequestBody requestMaquinaria_id = RequestBody.create(MultipartBody.FORM, maquinaria_id );
            RequestBody requesAula_id = RequestBody.create(MultipartBody.FORM, aula_id);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), byteArray);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", file.getName(), requestFile);
            RequestBody comentarioPart = RequestBody.create(MultipartBody.FORM, coment);

            call = service.createReporte(requestuser_id, requestMaquinaria_id, requesAula_id, comentarioPart, imagenPart);

        call.enqueue(new Callback<ResponseMessage2>() {
            @Override
            public void onResponse(Call<ResponseMessage2> call, Response<ResponseMessage2> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        ResponseMessage2 responseMessage2 = response.body();
                        Log.d(TAG, "responseMessage: " + responseMessage2);
                        Toast.makeText(Report2Activity.this, responseMessage2.getMessage(), Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        //Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage2> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
               // Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
    public void regresar(View view){
        finish();
    }

    //-------------------------------------------------------------------------------------------------------------------------------------

    public void getTextFromImage(Bitmap bitmap){
        try {
            Log.d(TAG, "TextRecognizer");

            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

            if (!textRecognizer.isOperational()) {

                Toast.makeText(getApplicationContext(), "No se pudo obtener el texto", Toast.LENGTH_SHORT).show();

            } else {
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> items = textRecognizer.detect(frame);

                TextBlock InfoLAB = items.valueAt(2);
                String LABORATORIO = InfoLAB.getValue().substring(3,6);
                String MAQUINA = InfoLAB.getValue().substring(7,9);

                maquinaria_id  = MAQUINA;
                aula_id = LABORATORIO;

                Log.d(TAG, "LABORATORIO : " + LABORATORIO);
                Log.d(TAG, "MAQUINA : " + MAQUINA);
            }
        }catch (Throwable t){
            Toast.makeText(this, "Error:" + t, Toast.LENGTH_LONG).show();
            Log.d(TAG, "t:" + t, t);
        }

    }

}









