package zhenghaozhao.construction_chat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends BaseAdapter{
    class MessageViewHolder {
        private View avatar;
        private TextView userLetter;
        private TextView userName;
        private TextView messageBody;
    }

    List<Message> history = new ArrayList<>();
    private Context context;

    public MessageAdapter(Context context){
        this.context = context;
    }

    public void add(Message m){
        history.add(m);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return history.size();
    }

    @Override
    public Object getItem(int i) {
        return history.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = history.get(i);


        if (message.isMyMessage()) { // this message was sent by us so let's create a basic chat bubble on the right
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getText());
        } else { // this message was sent by someone else so let's create an advanced chat bubble on the left
            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            holder.avatar = (View) convertView.findViewById(R.id.avatar);
            holder.userName = (TextView) convertView.findViewById(R.id.name);
            holder.userLetter = (TextView) convertView.findViewById(R.id.userLetter);
            convertView.setTag(holder);

            holder.userName.setText(message.getName());
            holder.messageBody.setText(message.getText());
            if (message.isManager()){
                holder.userLetter.setText("M");
                GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
                drawable.setColor(Color.GREEN);
            }else{
                holder.userLetter.setText("W");
                GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
                drawable.setColor(Color.RED);
            }
        }

        return convertView;
    }

    public void setMessages(List<Message> messages){
        this.history = messages;
        notifyDataSetChanged();
    }
}
