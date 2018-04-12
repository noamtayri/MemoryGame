package memorygame.com.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button nextBtn = (Button)findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                nextBtn();
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
        startActivity(intent);
    }
}
