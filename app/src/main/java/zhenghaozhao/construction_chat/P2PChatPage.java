package zhenghaozhao.construction_chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/*
    Chatting page activity
 */

public class P2PChatPage extends AppCompatActivity{

    private EditText editText;
    private ListView messageView;
    private MessageAdapter messageAdapter;
    private static UserData receiverData;
    private static UserData myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.receiver);
        textView.setText(receiverData.getName());

        myData = DataRepository.getMyData();

        editText = (EditText) findViewById(R.id.editText);
        messageView = (ListView) findViewById(R.id.messages_view);
        messageAdapter = new MessageAdapter(this);
        messageView.setAdapter(messageAdapter);

        MyViewModel viewModel = ViewModelProviders.of(this, new ViewModelFactory("P2PData_Test"))
                .get(MyViewModel.class);

        LiveData<QuerySnapshot> liveData = viewModel.getQuerySnapshotLiveData();

        liveData.observe(this, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot querySnapshot) {
                List<Message> list = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    P2PChat data = documentSnapshot.toObject(P2PChat.class);
                    if (data.getSenderId().equals(myData.getID()) && data.getReceiverId().equals(receiverData.getID())) { //replace "Gregg" with current user name
                        Message message = new Message(data.getMessage(), true, data.getReceiverId());
                        list.add(message);
                    }
                    else if (data.getReceiverId().equals(myData.getID()) && data.getSenderId().equals(receiverData.getID())){
                        Message message = new Message(data.getMessage(), false, data.getSenderId(), receiverData.isManager());
                        list.add(message);
                    }
                }
                messageAdapter.setMessages(list);
                messageView.smoothScrollToPosition(list.size()-1);

            }
        });

    }

    public static void addReceiver(UserData receiver){
        receiverData = receiver;
    }

    public void sendMessageClicked(View view) {
        if (receiverData != null) {
            final String text = editText.getText().toString();
            if (text.length() > 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Message message = new Message(text, true, receiverData.getName());
                        P2PChat chat = new P2PChat(myData.getID(), receiverData.getID(), text, messageView.getCount()+1);
                        DataRepository.uploadP2PChatData(chat);
                        messageAdapter.add(message);
                        // scroll the ListView to the last added element
                        messageView.setSelection(messageView.getCount() - 1);
                    }
                });
                editText.setText(""); //reset text field
            }
        }
    }

    public void homeButtonClicked(View view) {
        Intent intent = new Intent(this, HomePage.class);
        this.startActivity(intent);
    }
}
