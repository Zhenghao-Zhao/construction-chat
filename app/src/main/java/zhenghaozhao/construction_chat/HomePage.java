package zhenghaozhao.construction_chat;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements Fragments.Fragment_all.Fragment_allListener
, Fragments.Fragment_manager.Fragment_managerListener, Fragments.Fragment_worker.Fragment_workerListener
        , Fragments.Fragment_site.Fragment_siteListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    //asynchronously retrieve all documents
    private AutoCompleteUserAdapter autoCompleteUserAdapter;
    private Fragments fragments;
    private ContactAdapter allRecycler;
    private ContactAdapter managerRecycler;
    private ContactAdapter workerRecycler;
    private ContactAdapter siteRecycler;
    private RecyclerView nav_recycler;
    private NavAdapter adapter;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        DataRepository dataRepository = new DataRepository();
        fragments = new Fragments();

        nav_recycler = findViewById(R.id.recycler_view_nav);
        adapter = new NavAdapter(this);
        nav_recycler.setAdapter(adapter);
        nav_recycler.setLayoutManager(new LinearLayoutManager(this));

        final List<P2PChat> container = new ArrayList<>();
        DataRepository.P2PDataRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String name = DataRepository.getMyData().getName();
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    if (snapshot.getData().get("receiver").equals(name) ||
                            snapshot.getData().get("sender").equals(name)){
                        P2PChat chatData = snapshot.toObject(P2PChat.class);
                        container.add(chatData);
                    }
                }
                adapter.setChats(container);
                Conversation p2PConversation = new Conversation(container);

            }
        });

        MyViewModel userViewModel = ViewModelProviders.of(this, new ViewModelFactory("UserData_Test"))
                .get(MyViewModel.class);

        LiveData<QuerySnapshot> userLiveData = userViewModel.getQuerySnapshotLiveData();

        userLiveData.observe(this, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot querySnapshot) {
                List<UserData> list = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : querySnapshot){
                    UserData data = documentSnapshot.toObject(UserData.class);
                    list.add(data);
                }
                DataRepository.addRepoData(list);
                autoCompleteUserAdapter.setAll_userData(list);
                if (allRecycler != null) allRecycler.setContacts(DataRepository.getUserData());
                if (managerRecycler != null) managerRecycler.setContacts(DataRepository.getManagerData());
                if (workerRecycler != null) workerRecycler.setContacts(DataRepository.getWorkerData());
                if (siteRecycler != null) siteRecycler.setContacts(DataRepository.getSiteData());
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        setUpViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        AutoCompleteTextView editText = findViewById(R.id.actv);
        autoCompleteUserAdapter = new AutoCompleteUserAdapter(this, new ArrayList<UserData>());
        editText.setAdapter(autoCompleteUserAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.END);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }else super.onBackPressed();
    }

    private void setUpViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(fragments.getFragment_all(), "ALL");
        adapter.addFragment(fragments.getFragment_manager(), "MANAGER");
        adapter.addFragment(fragments.getFragment_worker(), "WORKER");
        adapter.addFragment(fragments.getFragment_site(), "SITE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void allSend(ContactAdapter adapter) {
        allRecycler = adapter;
    }

    @Override
    public void managerSend(ContactAdapter adapter) {
        managerRecycler = adapter;
    }

    @Override
    public void workerSend(ContactAdapter adapter) {
        workerRecycler = adapter;
    }

    @Override
    public void siteSend(ContactAdapter adapter) {
        siteRecycler = adapter;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private void addFragment(Fragment fragment, String fragmentTitle){
            fragmentList.add(fragment);
            fragmentTitleList.add(fragmentTitle);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return fragmentList.size();
        }
    }

}
