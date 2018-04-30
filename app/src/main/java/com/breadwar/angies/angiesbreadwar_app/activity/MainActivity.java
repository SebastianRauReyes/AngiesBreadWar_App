package com.breadwar.angies.angiesbreadwar_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.breadwar.angies.angiesbreadwar_app.R;


public class MainActivity extends AppCompatActivity {

   private TextView currentUser;
   private Button reportarincidencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUser = findViewById(R.id.textview_current_user);
        reportarincidencia = findViewById(R.id.button_reportar_incidencia);

        if(this.getIntent().getExtras() != null) {
            if(this.getIntent().getExtras().get("name") != null) {
                String username = this.getIntent().getExtras().getString("name");
                currentUser.setText(username);
            }
        }

    }
    public void hacerreporte(View view){
        Intent intent = new Intent(this, Report1Activity.class);
        startActivity(intent);

    }
    public void hacerreservacion(View view){
        Intent intent = new Intent(this, ReservationActivity.class);
        startActivity(intent);

    }


}
