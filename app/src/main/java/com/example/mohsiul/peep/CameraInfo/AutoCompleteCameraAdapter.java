package com.example.mohsiul.peep.CameraInfo;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohsiul.peep.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class AutoCompleteCameraAdapter extends ArrayAdapter<CameraLocation> {
    private List<CameraLocation> countryListFull;

    public AutoCompleteCameraAdapter(@NonNull Context context, @NonNull List<CameraLocation> countryList) {
        super(context, 0, countryList);
        countryListFull = new ArrayList<CameraLocation>(countryList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.auto_complete_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.text_view_name);
        TextView textViewName_camera = convertView.findViewById(R.id.text_view_name_camera);

        CameraLocation cameraItem = getItem(position);

        if (cameraItem != null) {
            textViewName.setText(cameraItem.getTitle());
            cameraItem.getLatLng();
           // imageViewFlag.setImageResource(R.drawable.peep_logo);

            textViewName_camera.setText(cameraItem.getCameraNo());
        }

        return convertView;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<CameraLocation> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(countryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CameraLocation item : countryListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((CameraLocation) resultValue).getTitle();
        }
    };
}
