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
        String clientSecret = "gBd87ZSFdOvjM1WWQn3bYkrIkfKywk6z2FBhEvJr";

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
                        ResponseMessage responseMessage = response.body();

                        Log.d(TAG, "HTTP status code: " + responseMessage.getToken_type()+" "+responseMessage.getAccess_token());
                        Log.e(TAG, responseMessage.getName()+ "  "+responseMessage.getId());

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        boolean success = editor
                                .putString("name", responseMessage.getName())
                                .putInt("id", responseMessage.getId())
                                .putBoolean("islogged", true)
                                .commit();
                            goMainActivity();


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

    public void goMainActivity(){
        intentMain = new Intent(LoginActivity.this, MainActivity.class);
        intentMain.putExtra("name","Bienvenido");
        startActivity(intentMain);
        finish();
    }


}
