package com.breadwar.angies.angiesbreadwar_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    static Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.edittext_user);
        pass = findViewById(R.id.edittext_pass);

    }


    public void ingresar(View view){

        String username = user.getText().toString();
        String password = pass.getText().toString();

        if("".equals(username) || "".equals(password)){
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

       intentMain = new Intent(this, MainActivity.class);
        intentMain.putExtra("username", username);
        startActivity(intentMain);
    }
}
