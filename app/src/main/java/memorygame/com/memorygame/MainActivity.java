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

public class MainActivity extends AppCompatActivity {

    MyBtn firstChoiseBtn = new MyBtn(null);
    private int corrects = 0;
    TextView timer;
    TextView name;

    MyBtn[] allBtn = new MyBtn[16];

//    MyBtn btn1 = new MyBtn(null);
//    MyBtn btn2 = new MyBtn(null);
//    MyBtn btn3 = new MyBtn(null);
//    MyBtn btn4 = new MyBtn(null);
//    MyBtn btn5 = new MyBtn(null);
//    MyBtn btn6 = new MyBtn(null);

    List<Integer> imageList = new ArrayList<>(3);

    CountDownTimer countDown;

    Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//
//        btn5.btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                imageBtnClicked(btn5);
//            }
//        });
//
//        btn6.btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                imageBtnClicked(btn6);
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

        allBtn[0].btn = (ImageButton)findViewById(R.id.button1);
        allBtn[1].btn = (ImageButton)findViewById(R.id.button2);
        allBtn[2].btn = (ImageButton)findViewById(R.id.button3);
        allBtn[3].btn = (ImageButton)findViewById(R.id.button4);
        allBtn[4].btn = (ImageButton)findViewById(R.id.button5);
        allBtn[5].btn = (ImageButton)findViewById(R.id.button6);
        allBtn[6].btn = (ImageButton)findViewById(R.id.button7);
        allBtn[7].btn = (ImageButton)findViewById(R.id.button8);
        allBtn[8].btn = (ImageButton)findViewById(R.id.button9);
        allBtn[9].btn = (ImageButton)findViewById(R.id.button10);
        allBtn[10].btn = (ImageButton)findViewById(R.id.button11);
        allBtn[11].btn = (ImageButton)findViewById(R.id.button12);
        allBtn[12].btn = (ImageButton)findViewById(R.id.button13);
        allBtn[13].btn = (ImageButton)findViewById(R.id.button14);
        allBtn[14].btn = (ImageButton)findViewById(R.id.button15);
        allBtn[15].btn = (ImageButton)findViewById(R.id.button16);

//        btn1.btn = (ImageButton)findViewById(R.id.button1);
//        btn2.btn = (ImageButton)findViewById(R.id.button2);
//        btn3.btn = (ImageButton)findViewById(R.id.button3);
//        btn4.btn = (ImageButton)findViewById(R.id.button4);
//        btn5.btn = (ImageButton)findViewById(R.id.button5);
//        btn6.btn = (ImageButton)findViewById(R.id.button6);

        dealNewCards();
    }

    private void correct(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                disableAllBtns();
                corrects += 1;
                if(corrects == 8){
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
        imageList.add(R.drawable.argentina_flag_icon);
        imageList.add(R.drawable.argentina_flag_icon);
        imageList.add(R.drawable.brazil_flag_icon);
        imageList.add(R.drawable.brazil_flag_icon);
        imageList.add(R.drawable.china_flag_icon);
        imageList.add(R.drawable.china_flag_icon);
        imageList.add(R.drawable.germany_flag_icon);
        imageList.add(R.drawable.germany_flag_icon);
        imageList.add(R.drawable.spain_flag_icon);
        imageList.add(R.drawable.spain_flag_icon);
        imageList.add(R.drawable.australia_flag_icon);
        imageList.add(R.drawable.australia_flag_icon);

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
//        btn5.btn.setTag(imageList.get(4));
//        btn6.btn.setTag(imageList.get(5));
    }

    private void resetImages(){
//        btn1.btn.setImageResource(R.mipmap.ic_launcher);
//        btn2.btn.setImageResource(R.mipmap.ic_launcher);
//        btn3.btn.setImageResource(R.mipmap.ic_launcher);
//        btn4.btn.setImageResource(R.mipmap.ic_launcher);
//        btn5.btn.setImageResource(R.mipmap.ic_launcher);
//        btn6.btn.setImageResource(R.mipmap.ic_launcher);
//        btn1.isStar = false;
//        btn2.isStar = false;
//        btn3.isStar = false;
//        btn4.isStar = false;
//        btn5.isStar = false;
//        btn6.isStar = false;
    }

    private void disableAllBtns(){
        for(MyBtn btn : allBtn){
            btn.btn.setEnabled(false);
        }

//        btn1.btn.setEnabled(false);
//        btn2.btn.setEnabled(false);
//        btn3.btn.setEnabled(false);
//        btn4.btn.setEnabled(false);
//        btn5.btn.setEnabled(false);
//        btn6.btn.setEnabled(false);
    }

    private void enableAllBtns(){
        for(MyBtn btn : allBtn){
            btn.btn.setEnabled(true);
        }

//        btn1.btn.setEnabled(true);
//        btn2.btn.setEnabled(true);
//        btn3.btn.setEnabled(true);
//        btn4.btn.setEnabled(true);
//        btn5.btn.setEnabled(true);
//        btn6.btn.setEnabled(true);
    }

    private void timerLogic(){
        countDown = new CountDownTimer(45000, 1000) {
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