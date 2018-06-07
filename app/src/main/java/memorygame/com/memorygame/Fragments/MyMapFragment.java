package memorygame.com.memorygame.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import memorygame.com.memorygame.Dal.DBHandler;
import memorygame.com.memorygame.FinalVariables;
import memorygame.com.memorygame.GameActivity;
import memorygame.com.memorygame.Model.Record;
import memorygame.com.memorygame.R;
import memorygame.com.memorygame.RecordsActivity;

public class MyMapFragment extends Fragment implements OnMapReadyCallback {


    MapView mMapView;
    private GoogleMap mMap;
    private List<Record> recordsList = new ArrayList<>();

    private LatLng mLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;

    // region Ctors And Inits
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

        recordsList = ((RecordsActivity)getActivity()).activityRecordsList;

        //mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        return rootView;
    }

    // endregion

    // region location Permission
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, FinalVariables.MY_LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == FinalVariables.MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                }
            } else {
                mMap.setMyLocationEnabled(false);
                // Permission was denied. can't get user location.
            }
        }
    }

    //endregion

    //region Override Fragment Methods
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

    //endregion

    // region map CallBacks
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // For showing a move to my location button
        //mMap.setMyLocationEnabled(true);
        checkPermissions();

        showRecords();
    }

    // endregion

    //region GUI methods

    private void showRecords() {
        for (Record rec : recordsList) {
            if(rec.getLocation().latitude != -1)
                mMap.addMarker(new MarkerOptions()
                        .position(rec.getLocation())
                        .title(String.valueOf(rec.getRecordPoints()) + "\t-\t" +rec.getName())
                        .snippet(rec.getAddress()));
        }

        getMyLocation();
        //LatLng lastRecord = recordsList.get(recordsList.size() - 1).getLocation();
        //CameraPosition cameraPosition = new CameraPosition.Builder().target(lastRecord).zoom(12).build();
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        /*
        zoom:
            1: World
            5: Landmass/continent
            10: City
            15: Streets
            20: Buildings
        */
    }

    // endregion

    //region My Location

    public void getMyLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        else{
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                try {
                                    List<Address> addressList = geocoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1);
                                    if (addressList.size() > 0) {
                                        String addressLine = addressList.get(0).getAddressLine(0);

                                        mMap.addMarker(new MarkerOptions()
                                                .position(mLastLocation)
                                                .title("My Location")
                                                .snippet(addressLine));
                                        CameraPosition cameraPosition = new CameraPosition.Builder().target(mLastLocation).zoom(12).build();
                                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //mLocationTextView.setText(R.string.no_location);
                            }
                        }
                    });
        }
    }
    //endregion
}