package memorygame.com.memorygame;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    int level;
    String name;
    Boolean timer;
    int matrixSize;
    int btnAmount;
    TextView timeTextView;
    TextView nameTextView;
    TableLayout table;
    MyBtn[] allBtn;
    MyBtn firstChooseBtn = new MyBtn(null);
    int corrects = 0;
    List<Integer> allImagesList = new ArrayList<>();
    List<Integer> imageList = new ArrayList<>();
    CountDownTimer countDown;
    int timerLimit;
    Boolean winLose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BindUI();

        getData();

        nameTextView.setText(name);
        allBtn = new MyBtn[btnAmount];

        fillTable();

        initImageList();

        dealNewCards();

        if(timer)
            timerLogic();

    }

    private void fillTable() {
        for(int i = 0 ; i<matrixSize ; i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                                                    TableLayout.LayoutParams.MATCH_PARENT,1.0f));
            table.addView(tableRow);
            for(int j = 0 ; j<matrixSize ; j++){
                if(i == matrixSize-1 && j == matrixSize-1 && matrixSize % 2 == 1){
                    ImageButton garbage = new ImageButton(this);
                    garbage.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                    initialBtn(garbage);
                    garbage.setEnabled(false);
                    garbage.setVisibility(View.INVISIBLE);
                    tableRow.addView(garbage);
                    break;
                }
                final int current = i * matrixSize + j;
                allBtn[current] = new MyBtn(new ImageButton(this));
                allBtn[current].btn.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                                                                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                initialBtn(allBtn[current].btn);
                allBtn[current].btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageBtnClicked(allBtn[current]);
                    }
                });
                tableRow.addView(allBtn[current].btn);
            }
        }
    }

    private void initialBtn(ImageButton btn) {
        ViewGroup.LayoutParams params = btn.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

        btn.setBackgroundColor(getTitleColor());
        btn.setImageResource(R.mipmap.ic_launcher);
        btn.setScaleType(ImageView.ScaleType.FIT_XY);
        btn.setAdjustViewBounds(true);
        btn.setPadding(0,0,0,0);

        btn.setMinimumWidth(params.width);
        btn.setMaxWidth(params.width);
        btn.setMinimumHeight(params.height);
        btn.setMaxHeight(params.height);
    }

    private void BindUI() {
        timeTextView = (TextView)findViewById(R.id.timerTextView);
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        table = (TableLayout)findViewById(R.id.tableLayout);
    }

    private void getData(){
        Bundle data = getIntent().getExtras();
        name = data.getString(FinalVariables.USER_NAME);
        timer = data.getBoolean(FinalVariables.TIMER);
        level = data.getInt(FinalVariables.LEVEL);
        switch (level){
            case FinalVariables.EASY:
                matrixSize = 2;
                btnAmount = 4;
                timerLimit = 30000;
                break;
            case FinalVariables.MEDIUM:
                matrixSize = 4;
                btnAmount = 16;
                timerLimit = 45000;
                break;
            case FinalVariables.HARD:
                matrixSize = 5;
                btnAmount = 24;
                timerLimit = 60000;
                break;
            default:
                break;
        }
    }

    private void imageBtnClicked(final MyBtn pressedBtn) {
        disableAllBtns();
        if (pressedBtn.isStar) {
            enableAllBtns();
            return;
        }

        pressedBtn.btn.setImageResource(Integer.parseInt(pressedBtn.btn.getTag().toString()));
        if (firstChooseBtn.btn == null) {
            firstChooseBtn = pressedBtn;
            enableAllBtns();
        } else {
            if ((!firstChooseBtn.btn.equals(pressedBtn.btn)) && (firstChooseBtn.btn.getTag().toString().equals(pressedBtn.btn.getTag().toString()))) {
                firstChooseBtn.isStar = true;
                pressedBtn.isStar = true;
                firstChooseBtn = new MyBtn(null);
                correct();
            } else {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        firstChooseBtn.btn.setImageResource(R.mipmap.ic_launcher);
                        pressedBtn.btn.setImageResource(R.mipmap.ic_launcher);
                        firstChooseBtn = new MyBtn(null);
                        enableAllBtns();
                    }
                }, 500);
            }
        }
    }

    private void correct() {
        disableAllBtns();
        corrects += 1;
        if (corrects == btnAmount/2) {
            winLose = true;
            if (timer)
                countDown.cancel();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent resIntent = new Intent();
                    resIntent.putExtra(FinalVariables.RESULT, winLose);
                    setResult(RESULT_OK, resIntent);
                    finish();
                }
            }, 2000);

        }
        enableAllBtns();
    }

    private void timerLogic() {
        countDown = new CountDownTimer(timerLimit, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeTextView.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                winLose = false;
                timeTextView.setText("0");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent resIntent = new Intent();
                        resIntent.putExtra(FinalVariables.RESULT, winLose);
                        setResult(RESULT_OK, resIntent);
                        finish();
                    }
                }, 2000);
            }
        }.start();
    }

    private void initImageList() {

        allImagesList.add(R.drawable.israel_flag_icon);
        allImagesList.add(R.drawable.united_states_flag_icon);
        allImagesList.add(R.drawable.argentina_flag_icon);
        allImagesList.add(R.drawable.brazil_flag_icon);
        allImagesList.add(R.drawable.china_flag_icon);
        allImagesList.add(R.drawable.germany_flag_icon);
        allImagesList.add(R.drawable.spain_flag_icon);
        allImagesList.add(R.drawable.australia_flag_icon);
        allImagesList.add(R.drawable.belgium_flag_icon);
        allImagesList.add(R.drawable.canada_flag_icon);
        allImagesList.add(R.drawable.greece_flag_icon);
        allImagesList.add(R.drawable.kenya_flag_icon);
        allImagesList.add(R.drawable.croatian_flag_icon);
        allImagesList.add(R.drawable.czech_republic_flag_icon);
        allImagesList.add(R.drawable.england_flag_icon);
        allImagesList.add(R.drawable.france_flag_icon);
        allImagesList.add(R.drawable.italy_flag_icon);
        allImagesList.add(R.drawable.japan_flag_icon);

        Collections.shuffle(allImagesList);

        for(int i = 0 ; i<btnAmount/2 ; i++){
            imageList.add(allImagesList.get(i));
            imageList.add(allImagesList.get(i));
        }

        Collections.shuffle(imageList);
    }

    public void dealNewCards() {
        for (int i = 0; i < btnAmount; i++) {
            allBtn[i].btn.setTag(imageList.get(i));
        }
    }

    public void disableAllBtns() {
        for (MyBtn btn : allBtn) {
            btn.btn.setEnabled(false);
        }
    }

    public void enableAllBtns() {
        for (MyBtn btn : allBtn) {
            btn.btn.setEnabled(true);
        }
    }
}
