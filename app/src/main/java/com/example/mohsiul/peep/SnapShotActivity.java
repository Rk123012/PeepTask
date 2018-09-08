package com.example.mohsiul.peep;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnapShotActivity extends AppCompatActivity {
    private final static String TAG = "SnapShotActivity";
    TextView textSnap;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.recyclerViewSnap)  RecyclerView mRecyclerView;
    private List<Integer> cameraIds = new ArrayList<Integer>();
    public static List<String>imageUrls=new ArrayList<String>();
    public static String key;
    ImageView imageView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_shot);
        ButterKnife.bind(this);
        imageView=findViewById(R.id.imageView);
        textSnap=findViewById(R.id.textSnapshots);



        Intent intent=getIntent();
        ArrayList<String> markersId = intent.getStringArrayListExtra("markersId");
        textSnap.setText(String.valueOf(markersId));

        //new project to gitlab

        key=MainActivity.loginKeyExport;

        ArrayList<String> exampleList= new ArrayList<>(markersId);
        for (int i=0;i<exampleList.size();i++)
        {
            cameraIds.add(Integer.valueOf(markersId.get(i)));
        }

        MyApolloClient.getMyApolloClient().query(
                QueryImageQuery.builder().userIds(1).toekn(key).cameraIdss(cameraIds).build()
        ).enqueue(new ApolloCall.Callback<QueryImageQuery.Data>() {
            @Override
            public void onResponse(@Nonnull final Response<QueryImageQuery.Data> response) {
                // Log.d(TAG, "onResponse: flsajl");
                Log.d(TAG, "onResponse: " + response.data().toString());



               // imageView.setImageURI(Uri.parse(response.data().getScreenCaptures().screenshots().get(1).url));
                SnapShotActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        for (int j=0;j<response.data().getScreenCaptures().screenshots().size();j++)
                        {
                            imageUrls.add(response.data().getScreenCaptures().screenshots().get(j).url.toString());
                        }

                        GridView gridView = (GridView) findViewById(R.id.usage_example_gridview1);

                        gridView.setAdapter(
                                new SimpleImageListAdapter(
                                        SnapShotActivity.this,
                                        imageUrls


                                )
                        );

                    }
                });

            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {

                Log.d(TAG, "onFailure: "+e.getMessage());




            }
        });









/*
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

*/

    }


}
