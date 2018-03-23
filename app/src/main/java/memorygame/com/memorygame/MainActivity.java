package memorygame.com.memorygame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton firstChoiseBtn = null;
    private int corrects = 0;

    ImageButton isrBtn1 = null;
    ImageButton isrBtn2 = null;

    ImageButton usaBtn1 = null;
    ImageButton usaBtn2 = null;

    ImageButton argBtn1 = null;
    ImageButton argBtn2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindUI();

        isrBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageBtnClicked(isrBtn1, isrBtn2);
            }
        });

        isrBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageBtnClicked(isrBtn2, isrBtn1);
            }
        });

        usaBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageBtnClicked(usaBtn1, usaBtn2);
            }
        });

        usaBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageBtnClicked(usaBtn2, usaBtn1);
            }
        });

        argBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageBtnClicked(argBtn1, argBtn2);
            }
        });

        argBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imageBtnClicked(argBtn2, argBtn1);
            }
        });
    }

    private void imageBtnClicked(ImageButton pressedBtn, ImageButton matchBtn){
        if(!pressedBtn.isEnabled())
            return;
        if(firstChoiseBtn == null){
            firstChoiseBtn = pressedBtn;
            pressedBtn.setBackgroundColor(Color.YELLOW);
        }
        else{
            if(firstChoiseBtn == matchBtn){
                matchBtn.setImageResource(R.drawable.star);
                matchBtn.setEnabled(false);
                pressedBtn.setImageResource(R.drawable.star);
                pressedBtn.setEnabled(false);
                correct();
            }
            else{
                firstChoiseBtn.setBackgroundColor(0xFAFAFA);
                firstChoiseBtn = null;
            }
        }
    }

    private void bindUI(){
        isrBtn1 = (ImageButton)findViewById(R.id.isrImageButton1);
        isrBtn2 = (ImageButton)findViewById(R.id.isrImageButton2);

        usaBtn1 = (ImageButton)findViewById(R.id.usaImageButton1);
        usaBtn2 = (ImageButton)findViewById(R.id.usaImageButton2);

        argBtn1 = (ImageButton)findViewById(R.id.argImageButton1);
        argBtn2 = (ImageButton)findViewById(R.id.argImageButton2);
    }

    private void correct(){
        firstChoiseBtn = null;
        corrects += 1;
        isrBtn1.setBackgroundColor(0xFAFAFA);
        isrBtn2.setBackgroundColor(0xFAFAFA);
        usaBtn1.setBackgroundColor(0xFAFAFA);
        usaBtn2.setBackgroundColor(0xFAFAFA);
        argBtn1.setBackgroundColor(0xFAFAFA);
        argBtn2.setBackgroundColor(0xFAFAFA);
        if(corrects == 3){
            isrBtn1.setImageResource(R.drawable.israel_flag_icon);
            isrBtn1.setEnabled(true);
            isrBtn2.setImageResource(R.drawable.israel_flag_icon);
            isrBtn2.setEnabled(true);

            usaBtn1.setImageResource(R.drawable.united_states_flag_icon);
            usaBtn1.setEnabled(true);
            usaBtn2.setImageResource(R.drawable.united_states_flag_icon);
            usaBtn2.setEnabled(true);

            argBtn1.setImageResource(R.drawable.argentina_flag_icon);
            argBtn1.setEnabled(true);
            argBtn2.setImageResource(R.drawable.argentina_flag_icon);
            argBtn2.setEnabled(true);
            corrects = 0;
        }
    }
}
