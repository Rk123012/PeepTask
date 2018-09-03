package com.example.mohsiul.peep.mapActivities;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohsiul.peep.R;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<String> mExampleList;

    Context context;
    Activity activity;






    public ExampleAdapter(ArrayList<String> examplelist){
        mExampleList=examplelist;
        this.context=context;
        this.activity=activity;




    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.snapshot_recyclerview_item,parent,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, final int position) {
        final String currentItem=mExampleList.get(position);
        //final Example_Item itemPosition= Example_Item.get(position);
        // holder.mImageView.setImageResource(currentItem.getImageResource());
        //holder.mTextView1.setText(currentItem.getText1());
        //holder.mTextView2.setText(currentItem.getText2());
        //videoView1 = (VideoView) findViewById(R.id.videoView);
        holder.snapItem.setText("This is camera :"+currentItem);
        holder.snapImage.setImageResource(R.drawable.peep_logo);




    }

    public  class ExampleViewHolder extends RecyclerView.ViewHolder
    {

        TextView snapItem;
        ImageView snapImage;
        public LinearLayout mrelativeLayout;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            //mImageView=itemView.findViewById(R.id.imageView);
            //mTextView1=itemView.findViewById(R.id.textView1);
            //mTextView2=itemView.findViewById(R.id.textView2);
          //  videoView1 = itemView.findViewById(R.id.videoView);
            //surfaceView = itemView.findViewById(R.id.surfaceView);
           // mrelativeLayout=itemView.findViewById(R.id.layout1);

          //  mrelativeLayout=itemView.findViewById(R.id.snapItemLinearLayout);
            snapItem=itemView.findViewById(R.id.snapItem);
            snapImage=itemView.findViewById(R.id.snapImage);



        }


    }





    @Override
    public int getItemCount() {
        return mExampleList.size();
    }




}