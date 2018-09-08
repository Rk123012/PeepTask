package com.example.mohsiul.peep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.mohsiul.peep.mapActivities.MapActivity;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private final static int ERROR_DIALOG_REQUEST = 9001;
    public static String loginKeyExport;

    Button btnMap;
    EditText userName,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        userName=findViewById(R.id.userName);
        password=findViewById(R.id.password);



         btnMap=findViewById(R.id.btnsignIn);



        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApolloClient.getMyApolloClient().query(
                        QueryLoginNewQuery.builder().email(userName.getText().toString()).password(password.getText().toString()).build()
                ).enqueue(new ApolloCall.Callback<QueryLoginNewQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull final Response<QueryLoginNewQuery.Data> response) {
                        // Log.d(TAG, "onResponse: flsajl");
                        Log.d(TAG, "onResponse: " + response.data().toString());
                        loginKeyExport=response.data().login().loginKey.toString();

                        runOnUiThread(new Runnable() {
                            public void run() {

                                if(response.data().login.equals(null))
                                {
                                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                                }
                                else {



                                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(MainActivity.this,MapActivity.class);
                                startActivity(intent);
                                }

                            }
                        });





                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {

                        Log.d(TAG, "onFailure: "+e.getMessage());




                    }
                });

            }
        });






    }



    }

