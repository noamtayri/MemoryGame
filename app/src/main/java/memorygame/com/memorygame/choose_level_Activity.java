package memorygame.com.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class choose_level_Activity extends AppCompatActivity {

    public final String USER_NAME = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_level_);

        Bundle data = getIntent().getExtras();
        String userName = data.getString(USER_NAME);

        TextView name = (TextView)findViewById(R.id.nameTextView);
        name.setText(userName);

        Integer[] levels = {4,6,8};

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, levels);
        spinner.setAdapter(adapter);
    }
}
