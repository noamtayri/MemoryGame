package memorygame.com.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {

    public final String USER_NAME = "userName";
    final int SEND_USER_NAME = 1;

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
        Intent intent = new Intent(this, choose_level_Activity.class);
        intent.putExtra(USER_NAME, name.getText().toString());
        startActivity(intent);
    }
}
