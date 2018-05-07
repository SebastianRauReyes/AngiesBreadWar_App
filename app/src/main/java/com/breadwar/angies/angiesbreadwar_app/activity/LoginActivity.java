package com.breadwar.angies.angiesbreadwar_app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.breadwar.angies.angiesbreadwar_app.R;
import com.breadwar.angies.angiesbreadwar_app.interfaces.ApiService;
import com.breadwar.angies.angiesbreadwar_app.interfaces.ApiServiceGenerator;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseMessage;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    static String token = "";
    private EditText user;
    private EditText pass;
    static Intent intentMain;

    // SharedPreferences
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.edittext_user);
        pass = findViewById(R.id.edittext_pass);

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // username remember
        String username = sharedPreferences.getString("username", null);
        if(username != null){
            user.setText(username);
            pass.requestFocus();

        }
        // islogged remember
        if(sharedPreferences.getBoolean("islogged", false)){
            // Go to MainActivity
            goMainActivity();
        }

    }

    ApiService service = ApiServiceGenerator.createService(ApiService.class);

    public void ingresar(View view){

        String username = user.getText().toString();
        String password = pass.getText().toString();
        String gandType = "password";
        String clientId = "2";
        String clientSecret = "FSpUHHCCw6NuhpnAZyGeeWoDx28AjVkgMJlPX5mg";


        if("".equals(username) || "".equals(password)){
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }


        Call<ResponseMessage> call;
        call = service.login(username, password, gandType, clientId, clientSecret);


        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {
                        String authorization;
                        ResponseMessage responseMessage = response.body();
                        authorization = responseMessage.getToken_type()+" "+responseMessage.getAccess_token();
                        token = authorization;
                        Log.d(TAG, "HTTP status code: " + responseMessage.getToken_type()+" "+responseMessage.getAccess_token());
                        ObtenerDatosUsuario(authorization);



                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio: Mala Autentificacion");
                    }

                } catch (Throwable t) {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {

                    }
        });

    }

    public void ObtenerDatosUsuario(String autho){

        Call<ResponseUser> call2;
        call2 = service.detail(autho);
        call2.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                try {


                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);


                    if (response.isSuccessful()) {

                       /* ResponseUser responseUser = response.body();
                        Log.d(TAG, "responseUser: "+responseUser.getName() );*/
                            goMainActivity();
                        // Save to SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        boolean success = editor
                                .putString("username", user.getText().toString())
                                .putBoolean("islogged", true)
                                .commit();

                    } else {

                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio: Malos datos");
                    }

                }catch (Throwable t){
                    Log.e(TAG, "onThrowable: " + t.toString(), t);
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure (Call < ResponseUser > call, Throwable t){

            }


        });

    }

    public void goMainActivity(){
        intentMain = new Intent(LoginActivity.this, MainActivity.class);
        intentMain.putExtra("name","Bienvenido");
        startActivity(intentMain);
        finish();
    }


}
