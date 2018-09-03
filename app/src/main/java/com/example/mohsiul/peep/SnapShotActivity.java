package com.example.mohsiul.peep;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;


import com.example.mohsiul.peep.mapActivities.ExampleAdapter;
import com.example.mohsiul.peep.mapActivities.MapActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnapShotActivity extends AppCompatActivity {
    TextView textSnap;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.recyclerViewSnap)  RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_shot);
        ButterKnife.bind(this);

        textSnap=findViewById(R.id.textSnapshots);

        Intent intent=getIntent();
        ArrayList<String> markersId = intent.getStringArrayListExtra("markersId");
        textSnap.setText(String.valueOf(markersId));

        ArrayList<String> exampleList= new ArrayList<>(markersId);

        //       exampleList.add(new CameraLocation("C-2", 23.809591, 90.367447));
        //       exampleList.add(new CameraLocation("C-3", 23.771176, 90.37145));
        //       exampleList.add(new CameraLocation("C-1", 23.777176, 90.399452));


/*
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
 */

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter=new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        findViewById(R.id.backHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SnapShotActivity.this, MapActivity.class);
                startActivity(intent);

            }
        });


    }
}
