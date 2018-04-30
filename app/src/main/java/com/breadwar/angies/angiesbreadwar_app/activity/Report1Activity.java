package com.breadwar.angies.angiesbreadwar_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.breadwar.angies.angiesbreadwar_app.R;

public class Report1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report1);

    }

    public void regresar(View view){
        finish();
    }

    public void reportarEquipos(View view){
        Intent intent = new Intent(this, Report2Activity.class);
        startActivity(intent);
    }
    public void reportarOtros(View view){
        Intent intent = new Intent(this, Report2Activity.class);
        startActivity(intent);

    }




}
