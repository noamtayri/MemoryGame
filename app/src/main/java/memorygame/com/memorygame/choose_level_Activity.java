package memorygame.com.memorygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import memorygame.com.memorygame.Dal.DBHandler;
import memorygame.com.memorygame.Model.Record;

public class choose_level_Activity extends AppCompatActivity {

    TextView welcomeTextView = null;
    TextView nameTextView = null;
    TextView difficultTextView = null;
    Spinner spinner = null;
    Button playBtn = null;
    CheckBox checkBox;
    String userName;
    String age, myAddress;

    private boolean locPermission = false;
    private LatLng mLastLocation;
    private LocationManager locationManager;
    private boolean gps_enabled = false, network_enabled = false;


    private FusedLocationProviderClient mFusedLocationClient;
    private DBHandler db;
    private LocationListener listener;
    private boolean firstGame = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level_);

        bindUI();

        Bundle data = getIntent().getExtras();
        userName = data.getString(FinalVariables.USER_NAME);
        age = data.getString(FinalVariables.USER_AGE);
        locPermission = data.getBoolean(FinalVariables.LOCATION_PERM, true);

        initLocation();
        init();

        TextView name = (TextView)findViewById(R.id.nameTextView);
        name.setText(userName + ", " + age);

        String[] levels = {"Easy","Medium","Hard"};

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, levels);
        spinner.setAdapter(adapter);

        Button play = (Button)findViewById(R.id.playButton);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                playBtn();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListenerGps);
        locationManager.removeUpdates(locationListenerNetwork);
    }

    private void playBtn(){
        Intent intent = new Intent(this, GameActivity.class);
        switch (spinner.getSelectedItem().toString()){
            case "Easy":
                intent.putExtra(FinalVariables.LEVEL,FinalVariables.EASY);
                break;
            case "Medium":
                intent.putExtra(FinalVariables.LEVEL,FinalVariables.MEDIUM);
                break;
            case "Hard":
                intent.putExtra(FinalVariables.LEVEL,FinalVariables.HARD);
                break;
            default:
                break;
        }
        if(firstGame)
            firstGame = false;
        else
            initLocation();
        intent.putExtra(FinalVariables.TIMER,checkBox.isChecked());
        intent.putExtra(FinalVariables.USER_NAME, userName);
        startActivityForResult(intent, FinalVariables.REQUEST_CODE);
    }

    private void init(){
        final Animation in1 = new AlphaAnimation(0.0f,1.0f);
        final Animation in2 = new AlphaAnimation(0.0f,1.0f);
        final Animation in3 = new AlphaAnimation(0.0f,1.0f);
        in1.setDuration(2000);
        in2.setDuration(4000);
        in3.setDuration(6000);

        welcomeTextView.startAnimation(in1);
        nameTextView.startAnimation(in2);
        difficultTextView.startAnimation(in3);
        spinner.startAnimation(in3);
        playBtn.startAnimation(in3);
        checkBox.startAnimation(in3);

        welcomeTextView.setVisibility(View.VISIBLE);
        nameTextView.setVisibility(View.VISIBLE);
        difficultTextView.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        playBtn.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.VISIBLE);
    }

    private void bindUI(){
        welcomeTextView = (TextView)findViewById(R.id.WelcomeTextView);
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        difficultTextView = (TextView)findViewById(R.id.difficultTextView);
        spinner = (Spinner)findViewById(R.id.spinner);
        playBtn = (Button)findViewById(R.id.playButton);
        checkBox = (CheckBox)findViewById(R.id.timerCheckBox);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FinalVariables.REQUEST_CODE){
            if(resultCode == RESULT_OK) {
                if (data.getBooleanExtra(FinalVariables.RESULT, false)) {
                    if (checkBox.isChecked()) {
                        welcomeTextView.setText(R.string.win_msg);
                        int points = data.getIntExtra(FinalVariables.RESULT_POINTS, 0);
                        getLastLocationAndSave(points);
                    }
                }
                else
                    welcomeTextView.setText(R.string.lose_msg);
            }
            else if (resultCode == RESULT_CANCELED){
                welcomeTextView.setText(R.string.cancel_msg);
            }
        }else
            welcomeTextView.setText(R.string.welcome);
    }


    //region Location and DB


    @SuppressLint("MissingPermission")
    private void initLocation() {
        if(!locPermission)
            return;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);



        if (!gps_enabled && !network_enabled) {
            Toast.makeText(getApplicationContext(), "can't find location", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        if (gps_enabled)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    locationListenerGps);
        if (!network_enabled)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                    locationListenerNetwork);
        //timer=new Timer();
        //timer.schedule(new GetLastLocation(), 20000);

    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            //timer.cancel();
            mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);

            Toast.makeText(choose_level_Activity.this,
                    "gps location (" + location.getLatitude() + ", " + location.getLongitude() + ")",
                    Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            //timer.cancel();
            if(mLastLocation == null)
                mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);

            Toast.makeText(getApplicationContext(), "network location ("+ location.getLatitude() + ", " + location.getLongitude() + ")", Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };


    @SuppressLint("MissingPermission")
    private void getLastLocationAndSave(final int points) {
        if(locationManager == null) {
            new Runnable(){

                @Override
                public void run() {
                    mLastLocation = new LatLng(-1,-1);
                    getAddress();
                    saveRecordToDB(points);
                }
            }.run();
        }
        else {
            locationManager.removeUpdates(locationListenerGps);
            locationManager.removeUpdates(locationListenerNetwork);

            new Runnable() {
                @Override
                public void run() {
                    double x = -1, y = -1;

                    Location net_loc = null, gps_loc = null;
                    if (gps_enabled)
                        gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (network_enabled)
                        net_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    //if there are both values use the latest one
                    if (gps_loc != null && net_loc != null) {
                        if (gps_loc.getTime() > net_loc.getTime()) {
                            x = gps_loc.getLatitude();
                            y = gps_loc.getLongitude();
                            //Toast.makeText(getApplicationContext(), "gps last known " + x + "\n" + y, Toast.LENGTH_SHORT).show();
                        } else {
                            x = net_loc.getLatitude();
                            y = net_loc.getLongitude();
                            //Toast.makeText(getApplicationContext(), "network last known " + x + "\n" + y, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (net_loc != null) {
                            {
                                x = net_loc.getLatitude();
                                y = net_loc.getLongitude();
                                //Toast.makeText(getApplicationContext(), "network last known " + x + "\n" + y, Toast.LENGTH_SHORT).show();
                            }
                        } else if (gps_loc != null) {
                            {
                                x = gps_loc.getLatitude();
                                y = gps_loc.getLongitude();
                                //Toast.makeText(getApplicationContext(), "gps last known " + x + "\n" + y, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    mLastLocation = new LatLng(x, y);
                    getAddress();
                    saveRecordToDB(points);
                    //Toast.makeText(context, "no last know available", Toast.LENGTH_SHORT).show();
                }
            }.run();
        }
    }

    private void getAddress() {
        if(mLastLocation.latitude == -1)
            myAddress = "No Location";
        else {
            Geocoder geocoder = new Geocoder(choose_level_Activity.this, Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1);
                if (addressList.size() > 0) {
                    String addressLine = addressList.get(0).getAddressLine(0);

                    myAddress = addressLine;

                    //Toast.makeText(GameActivity.this, "got location: " + addressLine, Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveRecordToDB(final int points) {
        db = new DBHandler(this);
        final String levelStr = spinner.getSelectedItem().toString();

        db.addNewRecord(new Record(userName, mLastLocation.latitude, mLastLocation.longitude, points, myAddress, levelStr));
        db.close();
        Toast.makeText(choose_level_Activity.this, "saved to db. points = " + points + " address: " + myAddress, Toast.LENGTH_LONG).show();

    }

    //endregion
}
