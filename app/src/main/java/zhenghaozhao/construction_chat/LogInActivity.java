package zhenghaozhao.construction_chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/*
    Set up initial user data
 */

public class LogInActivity extends AppCompatActivity {
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        name = findViewById(R.id.name);

    }

    public void logInClicked(View view) {

        DataRepository.setMyData(new UserData(name.getText().toString(), true, false));
        Intent intent = new Intent(this, HomePage.class);
        this.startActivity(intent);
    }
}
