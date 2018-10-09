package zhenghaozhao.construction_chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NavAdapter extends RecyclerView.Adapter <NavAdapter.ChatViewHolder> {
    private List<P2PChat> chats = new ArrayList<>(); //a list that contains all chats by the current user
    private Context context;

    NavAdapter(Context context){
        this.context = context;
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public TextView userLetter;
        public View avatar;
        public View onSite;
        public RelativeLayout parentLayout;

        public ChatViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userLetter = (TextView) itemView.findViewById(R.id.userLetter);
            avatar = (View) itemView.findViewById(R.id.avatar);
            onSite = (View) itemView.findViewById(R.id.onSiteStatus);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_nav, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder holder, int position) {
        P2PChat chat = chats.get(position);
        UserData thisUser = DataRepository.fetchUserData(chat.getReceiver());
        if (chat.getSender().equals(DataRepository.getMyData().getName())) {
            holder.userName.setText(chat.getReceiver());
        }else {
            holder.userName.setText(chat.getSender());
        }
        if (thisUser.isManager()){
            holder.userLetter.setText("M");
            GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
            drawable.setColor(Color.GREEN);
        }else{
            holder.userLetter.setText("W");
            GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
            drawable.setColor(Color.RED);
        }
        if (thisUser.isOnSite()){
            holder.onSite.setBackgroundColor(Color.YELLOW);
        }else holder.onSite.setBackgroundColor(Color.GRAY);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                P2PChat chat = chats.get(holder.getAdapterPosition());
                UserData thisUser = DataRepository.fetchUserData(chat.getReceiver());
                P2PChatPage.addReceiver(thisUser);
                Intent intent = new Intent(context, P2PChatPage.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void setChats(List<P2PChat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }
}
