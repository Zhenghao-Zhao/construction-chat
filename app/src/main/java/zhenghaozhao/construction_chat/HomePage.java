package zhenghaozhao.construction_chat;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragments = new Fragments();
        DataRepository dataRepository = new DataRepository();


        viewModel viewModel = ViewModelProviders.of(this, new viewModelFactory("UserData_Test"))
                .get(zhenghaozhao.construction_chat.viewModel.class);

        LiveData<QuerySnapshot> liveData = viewModel.getQuerySnapshotLiveData();

        liveData.observe(this, new Observer<QuerySnapshot>() {
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
