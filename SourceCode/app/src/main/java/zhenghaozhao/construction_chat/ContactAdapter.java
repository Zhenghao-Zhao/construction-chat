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

public class ContactAdapter extends RecyclerView.Adapter <ContactAdapter.ContactViewHolder> {
    private Context context;
    private List<UserData> contacts = new ArrayList<>(); // contains a list of contacts

    public ContactAdapter(Context context){
        this.context = context;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public TextView userLetter;
        public View onSiteStatus;
        public View userAvatar;
        public RelativeLayout parent_layout;

        public ContactViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.name);
            onSiteStatus = (View) itemView.findViewById(R.id.onSiteStatus);
            userAvatar = (View) itemView.findViewById(R.id.avatar);
            parent_layout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);
            userLetter = (TextView) itemView.findViewById(R.id.userLetter);
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

        if (thisUser.isManager()){
            holder.userLetter.setText("M");
            GradientDrawable drawable = (GradientDrawable) holder.userAvatar.getBackground();
            drawable.setColor(Color.GREEN);
        }else{
            holder.userLetter.setText("W");
            GradientDrawable drawable = (GradientDrawable) holder.userAvatar.getBackground();
            drawable.setColor(Color.RED);
        }

        holder.userName.setText(thisUser.getName());


        // match user to their avatar

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserData userData = contacts.get(holder.getAdapterPosition());
                ChatRecord record = DataRepository.getMyChatRecord();
                if (!record.getConversers().contains(userData.getName())) record.addConverser(userData.getName());
                DataRepository.uploadRecord(record);
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
