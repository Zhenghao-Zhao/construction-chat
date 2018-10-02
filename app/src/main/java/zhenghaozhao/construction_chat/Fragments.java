package zhenghaozhao.construction_chat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Fragments {
    private Fragment_all fragment_all;
    private Fragment_manager fragment_manager;
    private Fragment_worker fragment_worker;
    private Fragment_site fragment_site;

    public Fragments(){
        fragment_all = new Fragment_all();
        fragment_manager = new Fragment_manager();
        fragment_worker = new Fragment_worker();
        fragment_site = new Fragment_site();
    }

    public void addData(DataRepository repo) {
        fragment_all.setRecyclerViewData(repo.getUserData());
        fragment_manager.setRecyclerViewData(repo.getManagerData());
        fragment_worker.setRecyclerViewData(repo.getWorkerData());
        fragment_site.setRecyclerViewData(repo.getSiteData());
    }

    public Fragment_all getFragment_all() {
        return fragment_all;
    }

    public Fragment_manager getFragment_manager() {
        return fragment_manager;
    }

    public Fragment_worker getFragment_worker() {
        return fragment_worker;
    }

    public Fragment_site getFragment_site() {
        return fragment_site;
    }

    public static class Fragment_all extends Fragment {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext());

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_all_contacts, container, false);

            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_all);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        public void setRecyclerViewData(List<UserData> userData){
            recyclerViewAdapter.setContacts(userData);
        }
    }

    public static class Fragment_manager extends Fragment {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext());
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_manager_contacts, container, false);
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_manager);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        public void setRecyclerViewData(List<UserData> managerData){
            recyclerViewAdapter.setContacts(managerData);
        }
    }

    public static class Fragment_worker extends Fragment {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext());

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_worker_contacts, container, false);

            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_worker);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        public void setRecyclerViewData(List<UserData> workerData){
            recyclerViewAdapter.setContacts(workerData);
        }

    }

    public static class Fragment_site extends Fragment {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext());
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_site_contacts, container, false);
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_site);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        }

        public void setRecyclerViewData(List<UserData> siteData){
            recyclerViewAdapter.setContacts(siteData);
        }
    }

}
