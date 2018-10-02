package zhenghaozhao.construction_chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteUserAdapter extends ArrayAdapter<UserData>{
    private List<UserData> all_userData;
    private Context this_context;

    public AutoCompleteUserAdapter(@NonNull Context context, @NonNull List<UserData> dataList) {
        super(context, 0, dataList);
        all_userData = new ArrayList<>(dataList);
        this_context = context;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_search, parent, false
            );
        }
        TextView textViewName = convertView.findViewById(R.id.userName);
        ImageView imageView = convertView.findViewById(R.id.avatar);
        View view = convertView.findViewById(R.id.onSiteStatus);

        UserData data = getItem(position);
        if (data != null){
            textViewName.setText(data.getName());
            if (data.isOnSite()) {
                view.setBackgroundColor(Color.YELLOW);
            } else {
                view.setBackgroundColor(Color.GRAY);
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            UserData data = getItem(position);
            @Override
            public void onClick(View view) {
                P2PChatPage.addReceiver(data);
                Intent intent = new Intent(this_context, P2PChatPage.class);
                this_context.startActivity(intent);

            }
        });

        return convertView;
    }

    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<UserData> suggestions = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                suggestions.addAll(all_userData);
            }else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (UserData data : all_userData){
                    if (data.getName().toLowerCase().contains(filterPattern)){
                        suggestions.add(data);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((UserData) resultValue).getName();
        }
    };

    public void setAll_userData(List<UserData> all_userData){
        this.all_userData = all_userData;
        notifyDataSetChanged();
    }


}
