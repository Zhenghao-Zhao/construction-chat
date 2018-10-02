package zhenghaozhao.construction_chat;

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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    //asynchronously retrieve all documents
    private FirebaseFirestore db;
    private CollectionReference userDataRef;
    private AutoCompleteUserAdapter autoCompleteUserAdapter;
    private DataRepository repository;
    private Fragments fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         db = FirebaseFirestore.getInstance();
         userDataRef = db.collection("UserData_Test");
         repository = new DataRepository();

         fragments = new Fragments();

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
    protected void onStart() {
        super.onStart();
        userDataRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                List<UserData> cloudUserDataList = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    System.out.println("Name is: " + documentSnapshot.getData().get("name"));
                    UserData data = documentSnapshot.toObject(UserData.class);
                    cloudUserDataList.add(data);
                }
                repository.addRepoData(cloudUserDataList);
                autoCompleteUserAdapter.setAll_userData(cloudUserDataList);
                populateData(repository);

            }
        });

    }

    private void setUpViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(fragments.getFragment_all(), "ALL");
        adapter.addFragment(fragments.getFragment_manager(), "MANAGER");
        adapter.addFragment(fragments.getFragment_worker(), "WORKER");
        adapter.addFragment(fragments.getFragment_site(), "SITE");
        viewPager.setAdapter(adapter);
    }


    private void populateData(DataRepository repo){
        fragments.addData(repo);
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String fragmentTitle){
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
