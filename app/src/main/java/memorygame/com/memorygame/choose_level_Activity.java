package memorygame.com.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class choose_level_Activity extends AppCompatActivity {

    public final String USER_NAME = "userName";

    Spinner spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level_);

        Bundle data = getIntent().getExtras();
        String userName = data.getString(USER_NAME);

        TextView name = (TextView)findViewById(R.id.nameTextView);
        name.setText(userName);

        String[] levels = {"easy","medium","hard"};

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, levels);
        spinner.setAdapter(adapter);

        Button play = (Button)findViewById(R.id.playButton);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                playBtn();
            }
        });
    }

    private void playBtn(){
        Intent intent;
        switch (spinner.getSelectedItem().toString()){
            case "easy":
                intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                break;
            case "medium:":
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case "hard":
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
