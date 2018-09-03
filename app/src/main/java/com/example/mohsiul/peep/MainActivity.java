package com.example.mohsiul.peep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.mohsiul.peep.mapActivities.MapActivity;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private final static int ERROR_DIALOG_REQUEST = 9001;

    Button btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


         btnMap=findViewById(R.id.btnsignIn);
         btnMap.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(MainActivity.this,MapActivity.class);
                 startActivity(intent);
             }
         });
    }



    }

