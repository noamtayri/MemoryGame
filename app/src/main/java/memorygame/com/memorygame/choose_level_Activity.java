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
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class choose_level_Activity extends AppCompatActivity {

    public static final String TIMER = "timer";
    public static final String LEVEL = "level";
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;
    public static final int REQUEST_CODE = 1;

    TextView welcomeTextView = null;
    TextView nameTextView = null;
    TextView difficultTextView = null;
    Spinner spinner = null;
    Button playBtn = null;
    CheckBox checkBox;
    String userName;
    String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level_);

        bindUI();

        init();

        Bundle data = getIntent().getExtras();
        userName = data.getString(HomeActivity.USER_NAME);
        age = data.getString(HomeActivity.USER_AGE);

        TextView name = (TextView)findViewById(R.id.nameTextView);
        name.setText(userName + ", " + age);

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
        Intent intent = new Intent(this, GameActivity.class);
        switch (spinner.getSelectedItem().toString()){
            case "easy":
                intent.putExtra(LEVEL,EASY);
                break;
            case "medium":
                intent.putExtra(LEVEL,MEDIUM);
                break;
            case "hard":
                intent.putExtra(LEVEL,HARD);
                break;
            default:
                break;
        }
        intent.putExtra(TIMER,checkBox.isChecked());
        intent.putExtra(HomeActivity.USER_NAME, userName);
        startActivityForResult(intent, REQUEST_CODE);
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
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data.getBooleanExtra(GameActivity.RESULT,true))
                welcomeTextView.setText(R.string.win_msg);
            else
                welcomeTextView.setText(R.string.lose_msg);
        }else
            welcomeTextView.setText(R.string.welcome);
    }
}
