package zhenghaozhao.construction_chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


public class P2PChatPage extends AppCompatActivity{

    private EditText editText;
    private ListView messageView;
    private MessageAdapter messageAdapter;
    private boolean isMyMessage;
    private static UserData receiverData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.editText);
        messageView = (ListView) findViewById(R.id.messages_view);
        messageAdapter = new MessageAdapter(this);
        messageView.setAdapter(messageAdapter);
        isMyMessage = false;

    }

    public static void addReceiver(UserData newReceiver){
        receiverData = newReceiver;
    }

    public void sendMessageClicked(View view) {
        if (receiverData != null) {
            final String text = editText.getText().toString();
            if (text.length() > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Message message = new Message(text, isMyMessage, receiverData);
                        messageAdapter.add(message);
                        // scroll the ListView to the last added element
                        messageView.setSelection(messageView.getCount() - 1);
                    }
                });
                editText.setText(""); //reset text field
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        int action = keyEvent.getAction();
        switch (code){
            case KeyEvent.KEYCODE_K:
                if (action == KeyEvent.ACTION_DOWN){
                    isMyMessage = !isMyMessage;
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_B:
                if (action == KeyEvent.ACTION_DOWN){
                    Intent intent = new Intent(this, HomePage.class);
                    this.startActivity(intent);

                }
                break;
        }
        return false;
    }


    public void homeButtonClicked(View view) {
        Intent intent = new Intent(this, HomePage.class);
        this.startActivity(intent);
    }
}
