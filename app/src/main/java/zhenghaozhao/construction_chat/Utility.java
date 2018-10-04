package zhenghaozhao.construction_chat;

public class Utility {
    private RecyclerViewAdapter recyclerViewAdapter;

    public void addRecycler(RecyclerViewAdapter adapter){
        recyclerViewAdapter = adapter;
    }

    public RecyclerViewAdapter getRecyclerViewAdapter() {
        return recyclerViewAdapter;
    }
}
