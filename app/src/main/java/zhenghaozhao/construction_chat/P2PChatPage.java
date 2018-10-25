package zhenghaozhao.construction_chat;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
                    if (data.getSender().equals(myData.getName()) && data.getReceiver().equals(receiverData.getName())) { //replace "Gregg" with current user name
                        Message message = new Message(data.getMessage(), true, data.getReceiver());
                        list.add(message);
                    }
                    else if (data.getReceiver().equals(myData.getName()) && data.getSender().equals(receiverData.getName())){
                        Message message = new Message(data.getMessage(), false, data.getSender(), receiverData.isManager());
                        list.add(message);
                    }
                }
                messageAdapter.setMessages(list);
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
                        P2PChat chat = new P2PChat(myData.getName(), receiverData.getName(), text, messageView.getCount()+1);
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
