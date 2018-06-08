package memorygame.com.memorygame;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

import memorygame.com.memorygame.Dal.DBHandler;
import memorygame.com.memorygame.Fragments.*;
import memorygame.com.memorygame.Model.Record;

public class RecordsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public static DBHandler dbRecords;
    public static boolean locPermission;
    public SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    public static final String ARG_SECTION_NUMBER = "section_number";
    public List<Record> activityRecordsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        getData();
        dbRecords = new DBHandler(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        TableFragment tableFragment = TableFragment.newInstance(1);
        MyMapFragment mapFragment = MyMapFragment.newInstance(2);

        mSectionsPagerAdapter.addFragment(tableFragment, "RECORDS TABLE");
        mSectionsPagerAdapter.addFragment(mapFragment, "RECORDS MAP");

        LoadRecordsAsync loadRecordsAsync = new LoadRecordsAsync();
        loadRecordsAsync.execute();

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    private void getData() {
        Bundle data = getIntent().getExtras();
        locPermission = data.getBoolean(FinalVariables.LOCATION_PERM, true);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            //return 3;
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
            /*
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
            */
        }
    }

    protected class LoadRecordsAsync extends AsyncTask<Void, Void, Void> {
        //final String Tag = LoadRecordsAsync.class.getSimpleName();

        public LoadRecordsAsync(){
        }

        @Override
        protected Void doInBackground(Void... params) {
            //recordsList = retrieve records from db

            activityRecordsList = dbRecords.getAllRecords();

            // but for now...
            //for(int i = 0; i < 20; i++)
              //  activityRecordsList.add(new Record(RecordsActivity.this));
            return null;
        }

        // -- gets called just before thread begins
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // -- called as soon as doInBackground method completes
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        }
    }
}
