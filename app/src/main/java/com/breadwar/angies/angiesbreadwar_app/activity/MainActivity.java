package com.breadwar.angies.angiesbreadwar_app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.breadwar.angies.angiesbreadwar_app.R;


public class MainActivity extends AppCompatActivity {

   private TextView currentUser;
   private Button reportarincidencia;
   private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentUser = findViewById(R.id.textview_current_user);
        reportarincidencia = findViewById(R.id.button_reportar_incidencia);

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        /*if(this.getIntent().getExtras() != null) {
            if(this.getIntent().getExtras().get("name") != null) {
                String username = this.getIntent().getExtras().getString("name");
                currentUser.setText(username);
            }
        }*/

    }
    public void hacerreporte(View view){
        Intent intent = new Intent(this, Report1Activity.class);
        startActivity(intent);

    }
    public void hacerreservacion(View view){
        Intent intent = new Intent(this, ReservationActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_logout:
                callLogout();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void callLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("islogged").apply();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


}
