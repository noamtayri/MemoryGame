package memorygame.com.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class choose_level_Activity extends AppCompatActivity {

    TextView welcomeTextView = null;
    TextView nameTextView = null;
    TextView difficultTextView = null;
    Spinner spinner = null;
    Button playBtn = null;

    public final String USER_NAME = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level_);

        bindUI();

        init();

        Bundle data = getIntent().getExtras();
        String userName = data.getString(USER_NAME);

        TextView name = (TextView)findViewById(R.id.nameTextView);
        name.setText(userName);

        String[] levels = {"easy","medium","hard"};

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

    private void playBtn(){
        Intent intent;
        switch (spinner.getSelectedItem().toString()){
            case "easy":
                intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                break;
            case "medium":
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case "hard":
                intent = new Intent(this, Main3Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
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

        welcomeTextView.setVisibility(View.VISIBLE);
        nameTextView.setVisibility(View.VISIBLE);
        difficultTextView.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        playBtn.setVisibility(View.VISIBLE);
    }

    private void bindUI(){
        welcomeTextView = (TextView)findViewById(R.id.WelcomeTextView);
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        difficultTextView = (TextView)findViewById(R.id.difficultTextView);
        spinner = (Spinner)findViewById(R.id.spinner);
        playBtn = (Button)findViewById(R.id.playButton);
    }
}
