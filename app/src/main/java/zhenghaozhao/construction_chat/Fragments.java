package zhenghaozhao.construction_chat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        ContactAdapter contactAdapter;
        private Fragment_allListener listener;

        public interface Fragment_allListener{
            void allSend(ContactAdapter adapter);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_all_contacts, container, false);
            contactAdapter = new ContactAdapter(getContext());
            listener.allSend(contactAdapter);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_all);
            recyclerView.setAdapter(contactAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            contactAdapter.setContacts(DataRepository.getUserData());
            return view;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof Fragment_allListener){
                listener = (Fragment_allListener) context;
            }else{
                throw new RuntimeException(context.toString()
                + " must implement Listener");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            listener = null;
        }
    }

    public static class Fragment_manager extends Fragment {
        ContactAdapter contactAdapter;
        private Fragment_managerListener listener;

        public interface Fragment_managerListener{
            void managerSend(ContactAdapter adapter);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_manager_contacts, container, false);
            contactAdapter = new ContactAdapter(getContext());
            listener.managerSend(contactAdapter);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_manager);
            recyclerView.setAdapter(contactAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            contactAdapter.setContacts(DataRepository.getManagerData());
            return view;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof Fragment_managerListener){
                listener = (Fragment_managerListener) context;
            }else{
                throw new RuntimeException(context.toString()
                        + " must implement Listener");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            listener = null;
        }

    }

    public static class Fragment_worker extends Fragment {
        ContactAdapter contactAdapter;
        private Fragment_workerListener listener;

        public interface Fragment_workerListener{
            void workerSend(ContactAdapter adapter);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_worker_contacts, container, false);
            contactAdapter = new ContactAdapter(getContext());
            listener.workerSend(contactAdapter);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_worker);
            recyclerView.setAdapter(contactAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            contactAdapter.setContacts(DataRepository.getWorkerData());
            return view;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof Fragment_workerListener){
                listener = (Fragment_workerListener) context;
            }else{
                throw new RuntimeException(context.toString()
                        + " must implement Listener");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            listener = null;
        }

    }

    public static class Fragment_site extends Fragment {
        ContactAdapter contactAdapter;
        private Fragment_siteListener listener;

        public interface Fragment_siteListener{
            void siteSend(ContactAdapter adapter);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_site_contacts, container, false);
            contactAdapter = new ContactAdapter(getContext());
            listener.siteSend(contactAdapter);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_site);
            recyclerView.setAdapter(contactAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            contactAdapter.setContacts(DataRepository.getSiteData());
            return view;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof Fragment_siteListener){
                listener = (Fragment_siteListener) context;
            }else{
                throw new RuntimeException(context.toString()
                        + " must implement Listener");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            listener = null;
        }

    }

}
