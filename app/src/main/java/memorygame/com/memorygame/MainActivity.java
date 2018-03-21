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

        isrBtn1 = (ImageButton)findViewById(R.id.isrImageButton1);
        isrBtn1.setTag(false);
        isrBtn2 = (ImageButton)findViewById(R.id.isrImageButton2);
        isrBtn2.setTag(false);

        usaBtn1 = (ImageButton)findViewById(R.id.usaImageButton1);
        usaBtn1.setTag(false);
        usaBtn2 = (ImageButton)findViewById(R.id.usaImageButton2);
        usaBtn2.setTag(false);

        argBtn1 = (ImageButton)findViewById(R.id.argImageButton1);
        argBtn1.setTag(false);
        argBtn2 = (ImageButton)findViewById(R.id.argImageButton2);
        argBtn2.setTag(false);

        isrBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(isrBtn1.getTag().equals(true))
                    return;
                if(firstChoiseBtn == null){
                    firstChoiseBtn = isrBtn1;
                    isrBtn1.setBackgroundColor(Color.YELLOW);
                }
                else{
                    if(firstChoiseBtn == isrBtn2){
                        isrBtn1.setImageResource(R.drawable.star);
                        isrBtn1.setTag(true);
                        isrBtn2.setImageResource(R.drawable.star);
                        isrBtn2.setTag(true);
                        correct();
                    }
                    else{
                        firstChoiseBtn.setBackgroundColor(Color.WHITE);
                        firstChoiseBtn = null;
                    }
                }
            }
        });

        isrBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(isrBtn2.getTag().equals(true))
                    return;
                if(firstChoiseBtn == null){
                    firstChoiseBtn = isrBtn2;
                    isrBtn2.setBackgroundColor(Color.YELLOW);
                }
                else{
                    if(firstChoiseBtn == isrBtn1){
                        isrBtn1.setImageResource(R.drawable.star);
                        isrBtn1.setTag(true);
                        isrBtn2.setImageResource(R.drawable.star);
                        isrBtn2.setTag(true);
                        correct();
                    }
                    else{
                        firstChoiseBtn.setBackgroundColor(Color.WHITE);
                        firstChoiseBtn = null;
                    }
                }
            }
        });

        usaBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(usaBtn1.getTag().equals(true))
                    return;
                if(firstChoiseBtn == null){
                    firstChoiseBtn = usaBtn1;
                    usaBtn1.setBackgroundColor(Color.YELLOW);
                }
                else{
                    if(firstChoiseBtn == usaBtn2){
                        usaBtn1.setImageResource(R.drawable.star);
                        usaBtn1.setTag(true);
                        usaBtn2.setImageResource(R.drawable.star);
                        usaBtn2.setTag(true);
                        correct();
                    }
                    else{
                        firstChoiseBtn.setBackgroundColor(Color.WHITE);
                        firstChoiseBtn = null;
                    }
                }
            }
        });

        usaBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(usaBtn2.getTag().equals(true))
                    return;
                if(firstChoiseBtn == null){
                    firstChoiseBtn = usaBtn2;
                    usaBtn2.setBackgroundColor(Color.YELLOW);
                }
                else{
                    if(firstChoiseBtn == usaBtn1){
                        usaBtn1.setImageResource(R.drawable.star);
                        usaBtn1.setTag(true);
                        usaBtn2.setImageResource(R.drawable.star);
                        usaBtn2.setTag(true);
                        correct();
                    }
                    else{
                        firstChoiseBtn.setBackgroundColor(Color.WHITE);
                        firstChoiseBtn = null;
                    }
                }
            }
        });

        argBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(argBtn1.getTag().equals(true))
                    return;
                if(firstChoiseBtn == null){
                    firstChoiseBtn = argBtn1;
                    argBtn1.setBackgroundColor(Color.YELLOW);
                }
                else{
                    if(firstChoiseBtn == argBtn2){
                        argBtn1.setImageResource(R.drawable.star);
                        argBtn1.setTag(true);
                        argBtn2.setImageResource(R.drawable.star);
                        argBtn2.setTag(true);
                        correct();
                    }
                    else{
                        firstChoiseBtn.setBackgroundColor(Color.WHITE);
                        firstChoiseBtn = null;
                    }
                }
            }
        });

        argBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(argBtn2.getTag().equals(true))
                    return;
                if(firstChoiseBtn == null){
                    firstChoiseBtn = argBtn2;
                    argBtn2.setBackgroundColor(Color.YELLOW);
                }
                else{
                    if(firstChoiseBtn == argBtn1){
                        argBtn1.setImageResource(R.drawable.star);
                        argBtn1.setTag(true);
                        argBtn2.setImageResource(R.drawable.star);
                        argBtn2.setTag(true);
                        correct();
                    }
                    else{
                        firstChoiseBtn.setBackgroundColor(Color.WHITE);
                        firstChoiseBtn = null;
                    }
                }
            }
        });
    }

    private void correct(){
        firstChoiseBtn = null;
        corrects += 1;
        isrBtn1.setBackgroundColor(Color.WHITE);
        isrBtn2.setBackgroundColor(Color.WHITE);
        usaBtn1.setBackgroundColor(Color.WHITE);
        usaBtn2.setBackgroundColor(Color.WHITE);
        argBtn1.setBackgroundColor(Color.WHITE);
        argBtn2.setBackgroundColor(Color.WHITE);
        if(corrects == 3){
            isrBtn1.setImageResource(R.drawable.israel_flag_icon);
            isrBtn1.setTag(false);
            isrBtn2.setImageResource(R.drawable.israel_flag_icon);
            isrBtn2.setTag(false);

            usaBtn1.setImageResource(R.drawable.united_states_flag_icon);
            usaBtn1.setTag(false);
            usaBtn2.setImageResource(R.drawable.united_states_flag_icon);
            usaBtn2.setTag(false);

            argBtn1.setImageResource(R.drawable.argentina_flag_icon);
            argBtn1.setTag(false);
            argBtn2.setImageResource(R.drawable.argentina_flag_icon);
            argBtn2.setTag(false);
            corrects = 0;
        }
    }
}
