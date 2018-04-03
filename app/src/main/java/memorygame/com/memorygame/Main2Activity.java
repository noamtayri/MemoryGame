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

    MyBtn[] allBtn = new MyBtn[4];

//    MyBtn btn1 = new MyBtn(null);
//    MyBtn btn2 = new MyBtn(null);
//    MyBtn btn3 = new MyBtn(null);
//    MyBtn btn4 = new MyBtn(null);

    List<Integer> imageList = new ArrayList<>(2);

    CountDownTimer countDown;

    Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        for(int i=0 ; i<allBtn.length ; i++){
            allBtn[i] = new MyBtn(null);
        }

        data = getIntent().getExtras();
        if(data.getBoolean(choose_level_Activity.TIMER))
        {
            timer = (TextView)findViewById(R.id.timerTextView);
            timerLogic();
        }

        initImageList();

        bindUI();

        name.setText("" + data.get(HomeActivity.USER_NAME));

        for(final MyBtn btn : allBtn){
            btn.btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    imageBtnClicked(btn);
                }
            });
        }

//        btn1.btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                imageBtnClicked(btn1);
//            }
//        });
//
//        btn2.btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                imageBtnClicked(btn2);
//            }
//        });
//
//        btn3.btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                imageBtnClicked(btn3);
//            }
//        });
//
//        btn4.btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                imageBtnClicked(btn4);
//            }
//        });

    }

    private void imageBtnClicked(final MyBtn pressedBtn){
        disableAllBtns();
        if(pressedBtn.isStar){
            enableAllBtns();
            return;
        }

        pressedBtn.btn.setImageResource(Integer.parseInt(pressedBtn.btn.getTag().toString()));
        if(firstChoiseBtn.btn == null){
            firstChoiseBtn = pressedBtn;
            enableAllBtns();
        }
        else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    if((!firstChoiseBtn.btn.equals(pressedBtn.btn)) && (firstChoiseBtn.btn.getTag().toString().equals(pressedBtn.btn.getTag().toString()))){
//                        firstChoiseBtn.btn.setImageResource(R.drawable.star);
//                        pressedBtn.btn.setImageResource(R.drawable.star);
                        firstChoiseBtn.isStar = true;
                        pressedBtn.isStar = true;
                        firstChoiseBtn = new MyBtn(null);
                        correct();
                    }else{
                        firstChoiseBtn.btn.setImageResource(R.mipmap.ic_launcher);
                        pressedBtn.btn.setImageResource(R.mipmap.ic_launcher);
                        firstChoiseBtn = new MyBtn(null);
                    }
                    enableAllBtns();
                }
            },1000);
        }
    }

    private void bindUI(){
        name = (TextView)findViewById(R.id.nameTextView);

        allBtn[1].btn = (ImageButton)findViewById(R.id.button1);
        allBtn[2].btn = (ImageButton)findViewById(R.id.button2);
        allBtn[3].btn = (ImageButton)findViewById(R.id.button3);
        allBtn[0].btn = (ImageButton)findViewById(R.id.button4);

        dealNewCards();
    }

    private void correct(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                disableAllBtns();
                corrects += 1;
                if(corrects == 2){
                    if(data.getBoolean(choose_level_Activity.TIMER))
                        countDown.cancel();
                    finish();
                }
                enableAllBtns();
            }
        },500);
    }

    private void initImageList(){
        imageList.add(R.drawable.israel_flag_icon);
        imageList.add(R.drawable.israel_flag_icon);
        imageList.add(R.drawable.united_states_flag_icon);
        imageList.add(R.drawable.united_states_flag_icon);

        shuffleImageList();
    }

    private void shuffleImageList(){
        Collections.shuffle(imageList);
    }

    private void dealNewCards(){
        for(int i = 0 ; i<allBtn.length ; i++){
            allBtn[i].btn.setTag(imageList.get(i));
        }

//        btn1.btn.setTag(imageList.get(0));
//        btn2.btn.setTag(imageList.get(1));
//        btn3.btn.setTag(imageList.get(2));
//        btn4.btn.setTag(imageList.get(3));
    }

    private void resetImages(){
//        btn1.btn.setImageResource(R.mipmap.ic_launcher);
//        btn2.btn.setImageResource(R.mipmap.ic_launcher);
//        btn3.btn.setImageResource(R.mipmap.ic_launcher);
//        btn4.btn.setImageResource(R.mipmap.ic_launcher);
//        btn1.isStar = false;
//        btn2.isStar = false;
//        btn3.isStar = false;
//        btn4.isStar = false;
    }

    private void disableAllBtns(){
        for(MyBtn btn : allBtn){
            btn.btn.setEnabled(false);
        }

//        btn1.btn.setEnabled(false);
//        btn2.btn.setEnabled(false);
//        btn3.btn.setEnabled(false);
//        btn4.btn.setEnabled(false);
    }

    private void enableAllBtns(){
        for(MyBtn btn : allBtn){
            btn.btn.setEnabled(true);
        }

//        btn1.btn.setEnabled(true);
//        btn2.btn.setEnabled(true);
//        btn3.btn.setEnabled(true);
//        btn4.btn.setEnabled(true);
    }

    private void timerLogic(){
        countDown = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timer.setText("time's up");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        finish();
                    }
                },1000);
            }
        }.start();
    }
}


