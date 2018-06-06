package memorygame.com.memorygame.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import memorygame.com.memorygame.Model.Record;
import memorygame.com.memorygame.R;
import memorygame.com.memorygame.RecordsActivity;

public class MyMapFragment extends Fragment implements OnMapReadyCallback {

    private int MY_LOCATION_REQUEST_CODE = 1;
    MapView mMapView;
    private GoogleMap mMap;
    private List<Record> recordsList = new ArrayList<>();

    public MyMapFragment() {
    }

    public static MyMapFragment newInstance(int sectionNumber) {
        MyMapFragment fragment = new MyMapFragment();
        Bundle args = new Bundle();
        args.putInt(RecordsActivity.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        //mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        return rootView;
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)
                        mMap.setMyLocationEnabled(true);
                }
            } else {
                mMap.setMyLocationEnabled(false);
                // Permission was denied. can't get user location.
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // For showing a move to my location button
        //mMap.setMyLocationEnabled(true);
        checkPermissions();

        LoadRecordsAsync loadRecordsAsync = new LoadRecordsAsync();
        loadRecordsAsync.execute();

        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build(); // see zoom comment
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        /*
        zoom:
            1: World
            5: Landmass/continent
            10: City
            15: Streets
            20: Buildings
        */
    }


    private void showRecords() {
        for (Record rec : recordsList) {
            mMap.addMarker(new MarkerOptions().position(rec.getLocation()).title(rec.getTheRecord___()).snippet("Record Description"));
        }
        LatLng lastRecord = recordsList.get(recordsList.size()-1).getLocation();
        CameraPosition cameraPosition = new CameraPosition.Builder().target(lastRecord).zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    /*
    retrieve
     */
    protected class LoadRecordsAsync extends AsyncTask<Void, Void, Void> {
        //final String Tag = LoadRecordsAsync.class.getSimpleName();

        public LoadRecordsAsync(){
        }

        @Override
        protected Void doInBackground(Void... params) {
            //recordsList = retrieve records from db

            // but for now...
            for(int i = 0; i < 50; i++)
                recordsList.add(new Record());
            return null;
        }

        // -- gets called just before thread begins
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // -- called as soon as doInBackground method completes
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            showRecords();
        }
    }
}