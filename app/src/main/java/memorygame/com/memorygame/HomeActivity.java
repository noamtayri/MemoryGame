package memorygame.com.memorygame;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private boolean locPermission = false;

    //first push
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkPermissions();

        final Button nextBtn = (Button)findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                nextBtn();
            }
        });
        final Button recordsBtn = (Button) findViewById(R.id.recordsButton);
        recordsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RecordsActivity.class));
            }
        });
    }

    private void nextBtn(){
        EditText name = (EditText)findViewById(R.id.nameEditText);
        EditText age = (EditText)findViewById(R.id.ageEditText);
        if((name.getText().toString().equals(""))){
            name.requestFocus();
            name.setError(getString(R.string.enter_name));
            return;
        }else if(age.getText().toString().equals("")){
            age.requestFocus();
            age.setError(getString(R.string.enter_age));
            return;
        }else if(Integer.parseInt(age.getText().toString()) < 1){
            age.requestFocus();
            age.setError(getString(R.string.positive_age));
            return;
        }else if(Integer.parseInt(age.getText().toString()) > 120){
            age.requestFocus();
            age.setError(getString(R.string.big_age));
            return;
        }
        Intent intent = new Intent(this, choose_level_Activity.class);
        intent.putExtra(FinalVariables.USER_NAME, name.getText().toString());
        intent.putExtra(FinalVariables.USER_AGE, age.getText().toString());
        intent.putExtra(FinalVariables.LOCATION_PERM, locPermission);
        startActivity(intent);
    }


    // region Permissions
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locPermission = true;

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            }, FinalVariables.MY_LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FinalVariables.MY_LOCATION_REQUEST_CODE) {
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    locPermission = true;
                }

            } else {
                locPermission = false;
                // Permission was denied. can't get user location.
            }
        }
    }

    //endregion


}
