package com.example.mohsiul.peep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by norman on 4/10/15.
 */
public class SimpleImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private List<String> imageUrls;

    public SimpleImageListAdapter(Context context, List<String> imageUrls) {
        super(context, R.layout.listview_item_image, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listview_item_image, parent, false);
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide
                .with(context)
                .load(imageUrls.get(position))
                .apply(options)
                .into((ImageView) convertView);


        return convertView;
    }
}
