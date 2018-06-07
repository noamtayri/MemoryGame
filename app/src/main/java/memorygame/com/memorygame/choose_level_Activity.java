package memorygame.com.memorygame;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class choose_level_Activity extends AppCompatActivity {

    TextView welcomeTextView = null;
    TextView nameTextView = null;
    TextView difficultTextView = null;
    Spinner spinner = null;
    Button playBtn = null;
    CheckBox checkBox;
    String userName;
    String age;

    private boolean locPermission = false;
    private LatLng mLastLocation;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level_);

        bindUI();

        //init();

        Bundle data = getIntent().getExtras();
        userName = data.getString(FinalVariables.USER_NAME);
        age = data.getString(FinalVariables.USER_AGE);

        TextView name = (TextView)findViewById(R.id.nameTextView);
        name.setText(userName + ", " + age);

        String[] levels = {"easy","medium","hard"};

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, levels);
        spinner.setAdapter(adapter);

        checkPermissions();
        getCurrentLocation();

        Button play = (Button)findViewById(R.id.playButton);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                playBtn();
            }
        });
    }

    private void playBtn(){
        Intent intent = new Intent(this, GameActivity.class);
        switch (spinner.getSelectedItem().toString()){
            case "easy":
                intent.putExtra(FinalVariables.LEVEL,FinalVariables.EASY);
                break;
            case "medium":
                intent.putExtra(FinalVariables.LEVEL,FinalVariables.MEDIUM);
                break;
            case "hard":
                intent.putExtra(FinalVariables.LEVEL,FinalVariables.HARD);
                break;
            default:
                break;
        }
        intent.putExtra(FinalVariables.LOCATION_PERM, locPermission);
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
        if(requestCode == FinalVariables.REQUEST_CODE && resultCode == RESULT_OK){
            if(data.getBooleanExtra(FinalVariables.RESULT,true))
                welcomeTextView.setText(R.string.win_msg);
            else
                welcomeTextView.setText(R.string.lose_msg);
        }else
            welcomeTextView.setText(R.string.welcome);
    }

    // region Location and Permission
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locPermission = true;
            init();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
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
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        locPermission  = true;
                    }
                }
            } else {
                locPermission = false;
                // Permission was denied. can't get user location.
            }
            init();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(choose_level_Activity.this);
        if (!locPermission){
            return;
        }
        else{
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                Geocoder geocoder = new Geocoder(choose_level_Activity.this, Locale.getDefault());
                                try {
                                    List<Address> addressList = geocoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1);
                                    if (addressList.size() > 0) {
                                        String addressLine = addressList.get(0).getAddressLine(0);

                                        //Toast.makeText(choose_level_Activity.this, "got location: " + addressLine, Toast.LENGTH_SHORT).show();
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
