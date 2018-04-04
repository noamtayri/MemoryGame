package memorygame.com.memorygame;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    MyBtn firstChoiseBtn = new MyBtn(null);
    private int corrects = 0;
    TextView timer;
    TextView name;
    TextView winLose;

    MyBtn[] allBtn = new MyBtn[4];

    List<Integer> imageList = new ArrayList<>(2);

    CountDownTimer countDown;

    Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        for (int i = 0; i < allBtn.length; i++) {
            allBtn[i] = new MyBtn(null);
        }

        data = getIntent().getExtras();
        if (data.getBoolean(choose_level_Activity.TIMER)) {
            timer = (TextView) findViewById(R.id.timerTextView);
            timerLogic();
        }

        initImageList();

        bindUI();

        name.setText("" + data.get(HomeActivity.USER_NAME));

        for (final MyBtn btn : allBtn) {
            btn.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageBtnClicked(btn);
                }
            });
        }

    }

    private void imageBtnClicked(final MyBtn pressedBtn) {
        disableAllBtns();
        if (pressedBtn.isStar) {
            enableAllBtns();
            return;
        }

        pressedBtn.btn.setImageResource(Integer.parseInt(pressedBtn.btn.getTag().toString()));
        if (firstChoiseBtn.btn == null) {
            firstChoiseBtn = pressedBtn;
            enableAllBtns();
        } else {
            if ((!firstChoiseBtn.btn.equals(pressedBtn.btn)) && (firstChoiseBtn.btn.getTag().toString().equals(pressedBtn.btn.getTag().toString()))) {
                firstChoiseBtn.isStar = true;
                pressedBtn.isStar = true;
                firstChoiseBtn = new MyBtn(null);
                correct();
            } else {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        firstChoiseBtn.btn.setImageResource(R.mipmap.ic_launcher);
                        pressedBtn.btn.setImageResource(R.mipmap.ic_launcher);
                        firstChoiseBtn = new MyBtn(null);
                        enableAllBtns();
                    }
                }, 500);
            }
        }
    }

    private void bindUI() {
        name = (TextView) findViewById(R.id.nameTextView);
        winLose = (TextView) findViewById(R.id.winLoseTextView);

        allBtn[1].btn = (ImageButton) findViewById(R.id.button1);
        allBtn[2].btn = (ImageButton) findViewById(R.id.button2);
        allBtn[3].btn = (ImageButton) findViewById(R.id.button3);
        allBtn[0].btn = (ImageButton) findViewById(R.id.button4);

        dealNewCards();
    }

    private void correct() {
        disableAllBtns();
        corrects += 1;
        if (corrects == 2) {
            if (data.getBoolean(choose_level_Activity.TIMER))
                countDown.cancel();
            winLose.setText(R.string.win_msg);
            winLose.setVisibility(View.VISIBLE);
            for (MyBtn btn : allBtn) {
                btn.btn.setVisibility(View.INVISIBLE);
            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);

        }
        enableAllBtns();
    }

    private void initImageList() {
        imageList.add(R.drawable.israel_flag_icon);
        imageList.add(R.drawable.israel_flag_icon);
        imageList.add(R.drawable.united_states_flag_icon);
        imageList.add(R.drawable.united_states_flag_icon);

        shuffleImageList();
    }

    private void shuffleImageList() {
        Collections.shuffle(imageList);
    }

    private void dealNewCards() {
        for (int i = 0; i < allBtn.length; i++) {
            allBtn[i].btn.setTag(imageList.get(i));
        }
    }

    private void disableAllBtns() {
        for (MyBtn btn : allBtn) {
            btn.btn.setEnabled(false);
        }
    }

    private void enableAllBtns() {
        for (MyBtn btn : allBtn) {
            btn.btn.setEnabled(true);
        }
    }

    private void timerLogic() {
        countDown = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timer.setText("0");
                winLose.setText(R.string.lose_msg);
                winLose.setVisibility(View.VISIBLE);
                for (MyBtn btn : allBtn) {
                    btn.btn.setVisibility(View.INVISIBLE);
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        }.start();
    }
}


