package com.example.mohsiul.peep.mapActivities;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohsiul.peep.CameraInfo.AutoCompleteCameraAdapter;
import com.example.mohsiul.peep.CameraInfo.CameraLocation;
import com.example.mohsiul.peep.CameraInfo.CameraLocationData;
import com.example.mohsiul.peep.R;
import com.example.mohsiul.peep.SnapShotActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,GoogleMap.OnMarkerClickListener{


    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Toast.makeText(this.getApplicationContext(), "Map is Ready", LENGTH_LONG).show();
        Log.d(TAG, "onMapReady: Map is Ready j l");
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();

        }

    }

    private static final String TAG=".MapActivity";

    private static final String FINE_LOCATION=Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int PLACE_PICKER_REQUEST = 1;
    private boolean mLocationPermissionGranted=false;


    private static final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    private static final LatLngBounds LAT_LNG_BOUNDS=new LatLngBounds(
            new LatLng(-40,-168),new LatLng(71,136));



    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    public static final float DEFAULT_ZOOM=15f;

   AutoCompleteTextView mSearchText;
    //private SearchView mserachText;
    ImageView mGps,mPlacePicker;

    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private AutoCompleteCameraAdapter autoCompleteCameraAdapter;
    private GeoDataClient mGeoDataClient;
    private  GoogleApiClient mGoogleApiClient;

    private Button btnMarker,btnMirpur;
    List<String> markersId = new ArrayList<String>();





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mSearchText=(AutoCompleteTextView) findViewById(R.id.input1);
        mGps=(ImageView)findViewById(R.id.ic_gps);
        mPlacePicker=(ImageView)findViewById(R.id.place_picker);
        //SearchView searchView = findViewById(R.id.searchView);
        btnMirpur=findViewById(R.id.btnMirpur);




        getLocationPermission();

        //init();


        btnMarker=findViewById(R.id.btnMarker);
        //btnMarker.setText(markersId.size());
        btnMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //countMarker();
                //Toast.makeText(getApplicationContext(),"HHH:"+markersId,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MapActivity.this, SnapShotActivity.class);
                i.putStringArrayListExtra("markersId", (ArrayList<String>) markersId);
                startActivity(i);



            }
        });
    






    }



    public  void setCameraLocationMarker(){
        mMap.setOnMarkerClickListener(this);

        for (int i = 0; i< CameraLocationData.getCameraLocation().size(); i++){
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(CameraLocationData.getCameraLocation().get(i).getLatLng())
                    .title(CameraLocationData.getCameraLocation().get(i).getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.camera_marker)));
            marker.setTag(0);
        }

    }


    private void init()
    {

        setCameraLocationMarker();
        Log.d(TAG, "searching");
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);


        ArrayList<CameraLocation> cameraInfos=new ArrayList<CameraLocation>();


        final AutoCompleteCameraAdapter completeCameraAdapter = new AutoCompleteCameraAdapter(this, CameraLocationData.getCameraLocation());

        mSearchText.setAdapter(completeCameraAdapter);






        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_SEARCH||i==EditorInfo.IME_ACTION_DONE||
                        keyEvent.getAction()==KeyEvent.ACTION_DOWN || keyEvent.getAction()==KeyEvent.KEYCODE_ENTER)
                {
                    geoLocate();


                }
                return false;
            }
        });
        mSearchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


               // CameraLocation cameraLocation=new CameraLocation();

                LatLng adapterAddress=CameraLocationData.getCameraLocation().get(i).getLatLng();

                //geoLocate();
                moveCamera(adapterAddress,DEFAULT_ZOOM,CameraLocationData.getCameraLocation().get(i).getTitle());



              //  CameraLocationData.getCameraLocation().get(i);

              ;



//                Toast.makeText(getApplicationContext(),"i="+CameraLocationData.getCameraLocation().get(i).getTitle()+l,LENGTH_LONG).show();
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

        mPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MapActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: Google Place picker Repairable Exception"+e.getMessage() );
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: Google Place Picker not available"+e.getMessage() );
                }
            }
        });

        hideSoftKeyboard();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, LENGTH_LONG).show();

                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                final LatLng location = place.getLatLng();

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, place.getId());

                // Location lova=(Location)plac
                //moveCamera(new LatLng(placeResult.getLatitude(),location.getLongitude()),DEFAULT_ZOOM, location.getAddressLine(0));
                //moveCamera(new LatLng(place.get));
                //Location currentlocation=(Location) place.getResult();
                //moveCamera(new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude()), DEFAULT_ZOOM,"My Location
                MarkerOptions options1 = new MarkerOptions().position(location);
                // mMap.addMarker(options1);



            }
        }
    }

    private void geoLocate(){
        hideSoftKeyboard();
        String searchString=mSearchText.getText().toString();

        Geocoder geocoder=new Geocoder(MapActivity.this);
        List<Address> list=new ArrayList<>();
        try{

            list=geocoder.getFromLocationName(searchString,1);
        }catch (IOException e)
        {
            Log.e(TAG,"No located"+e.getMessage());
        }
        if(list.size()>0)
        {
            Address address=list.get(0);
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM, address.getAddressLine(0));


        }


    }

    private void getDeviceLocation(){
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        try{

            if(mLocationPermissionGranted)
            {
                Task location=mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Location currentlocation=(Location) task.getResult();
                            moveCamera(new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude()), DEFAULT_ZOOM,"My Location");
                        }
                        else

                        {
                            Toast.makeText(MapActivity.this, "Unable to find location", LENGTH_LONG).show();
                        }
                    }
                });

            }

        }catch (SecurityException e)
        {
            Log.e(TAG,"Location Error"+e.getMessage());
        }

    }

    public void moveCamera(LatLng latLng, float zoom,String title) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        //mMap.moveCamera(animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom)));

        if (title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);

            // mMap.addMarker(options);
        }
        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions().position(latLng).title(title);

            // mMap.addMarker(options);




        }
        hideSoftKeyboard();
    }



    private void initMap(){
        SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(MapActivity.this);


    }



    public void getLocationPermission()
    {
        String [] permissions={Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted=true;
                initMap();
            }
            else
            {
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }

        }else
        {
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted=false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0){
                    for(int i=0;i<grantResults.length;i++)
                    {
                        if (grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                        {
                            mLocationPermissionGranted=false;
                            return;
                        }
                    }
                    mLocationPermissionGranted=true;
                    initMap();

                }
            }
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Integer clickCount = (Integer) marker.getTag();


        //       Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            // Toast.makeText(this,marker.getTitle() + " has been clicked " + clickCount + " times.",Toast.LENGTH_SHORT).show();
        }


        if(clickCount%2==1)
        {
           // Toast.makeText(this,marker.getTitle()+"has been selected.",Toast.LENGTH_SHORT).show();
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map));
            markersId.add(marker.getTitle());




        }
        else if(clickCount%2==0)
        {
          //  Toast.makeText(this,marker.getTitle()+"has been Canceled.",Toast.LENGTH_SHORT).show();
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.camera_marker));
            markersId.remove(marker.getTitle());

        }


        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }




    public void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }




}
