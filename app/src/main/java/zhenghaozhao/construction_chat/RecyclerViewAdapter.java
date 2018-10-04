package zhenghaozhao.construction_chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ContactViewHolder> {
    private Context context;
    private List<UserData> contacts = new ArrayList<>(); // contains a list of contacts

    public RecyclerViewAdapter(Context context){
        this.context = context;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public View onSiteStatus;
        public CircleImageView userAvatar;
        public RelativeLayout parent_layout;

        public ContactViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            onSiteStatus = (View) itemView.findViewById(R.id.onSiteStatus);
            userAvatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            parent_layout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
    

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder holder, int position) {
        UserData thisUser = contacts.get(position);
        if (thisUser.isOnSite()) {
            holder.onSiteStatus.setBackgroundColor(Color.YELLOW);
        } else {
            holder.onSiteStatus.setBackgroundColor(Color.GRAY);
        }

        holder.userName.setText(thisUser.getName());

        // match user to their avatar

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                P2PChatPage.addReceiver(contacts.get(holder.getAdapterPosition()));
                Intent intent = new Intent(context, P2PChatPage.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<UserData> userDataList){
        this.contacts = userDataList;
        notifyDataSetChanged();
    }


}
