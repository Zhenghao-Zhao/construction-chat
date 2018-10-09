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
    private EditText manager;
    private EditText onSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        name = findViewById(R.id.name);
        manager = findViewById(R.id.isManager);
        onSite = findViewById(R.id.isOnSite);
    }

    public void logInClicked(View view) {
        boolean isManager = manager.getText().toString().equals("true");
        boolean isOnSite = onSite.getText().toString().equals("true");

        DataRepository.setMyData(new UserData(name.getText().toString(), isManager, isOnSite));
        Intent intent = new Intent(this, HomePage.class);
        this.startActivity(intent);
    }
}
