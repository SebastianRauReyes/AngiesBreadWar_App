package com.breadwar.angies.angiesbreadwar_app.activity;


import android.util.Log;
import com.breadwar.angies.angiesbreadwar_app.interfaces.ApiService;
import com.breadwar.angies.angiesbreadwar_app.interfaces.ApiServiceGenerator;
import com.breadwar.angies.angiesbreadwar_app.model.ResponseMessage;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static org.junit.Assert.*;

public class LoginActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private  String name_string = null;
    private  String email_string = null;

    @Test
    public void login_isCorrect() throws Exception {

        String username = "108205";
        String password = "tecsup";
        String gandType = "password";
        String clientId = "2";
        String clientSecret = "gBd87ZSFdOvjM1WWQn3bYkrIkfKywk6z2FBhEvJr";
        Call<ResponseMessage> call = null;

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        call = service.login(username, password, gandType, clientId, clientSecret);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try {

                    int statusCode = response.code();
                        Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {
                        ResponseMessage responseMessage = response.body();
                        name_string = responseMessage.getName();
                        email_string =  responseMessage.getEmail();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio: Mala Autentificacion");
                    }

                } catch (Throwable t) {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                }
            }
            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

            }
        });

        assertEquals("sebas", name_string);
        assertEquals("sebas@tecsup.com", email_string);
    }

}
