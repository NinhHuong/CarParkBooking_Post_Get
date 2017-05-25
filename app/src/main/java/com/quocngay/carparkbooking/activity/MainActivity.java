package com.quocngay.carparkbooking.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.quocngay.carparkbooking.R;
import com.quocngay.carparkbooking.dbcontext.DbContext;
import com.quocngay.carparkbooking.fragment.BookedListFragment;
import com.quocngay.carparkbooking.other.Constant;
import com.quocngay.carparkbooking.other.MyPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private SharedPreferences pref;
    private String TAG = BookedListFragment.class.getSimpleName();
    private DbContext dbContext;
    private View loadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadProgress = (View) findViewById(R.id.load_data_progress);
        dbContext = DbContext.getInst();
        //check login
        pref = getSharedPreferences(Constant.APP_PREF, MODE_PRIVATE);
        if(pref.getString(Constant.SERVER_TOKEN, null) == null) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.booked_list));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.map));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.profile));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
//            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//            mViewPager.setAdapter(mSectionsPagerAdapter);

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            final MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            mViewPager.setAdapter(adapter);
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mViewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

//            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//            tabLayout.setupWithViewPager(mViewPager);

           /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });*/
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
