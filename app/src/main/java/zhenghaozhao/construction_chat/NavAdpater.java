package zhenghaozhao.construction_chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavAdpater extends RecyclerView.Adapter <NavAdpater.ChatViewHolder> {
    List<P2PChat> Chats = new ArrayList<>();

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public View onSiteStatus;
        public CircleImageView userAvatar;
        public RelativeLayout parent_layout;

        public ChatViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            onSiteStatus = (View) itemView.findViewById(R.id.onSiteStatus);
            userAvatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            parent_layout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
