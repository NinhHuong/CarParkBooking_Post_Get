package com.quocngay.carparkbooking.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.quocngay.carparkbooking.R;
import com.quocngay.carparkbooking.dbcontext.DbContext;
import com.quocngay.carparkbooking.fragment.BookedListFragment;
import com.quocngay.carparkbooking.model.BookedTicketModel;
import com.quocngay.carparkbooking.model.GaraModel;
import com.quocngay.carparkbooking.other.BookedTicketAdapter;
import com.quocngay.carparkbooking.other.Constant;
import com.quocngay.carparkbooking.other.MyPagerAdapter;
import com.quocngay.carparkbooking.other.ServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private SharedPreferences pref;
    private String TAG = BookedListFragment.class.getSimpleName();
    private DbContext dbContext;
    private View loadProgress;
    private BookedTicketAdapter ticketAdapter;

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
            new UpdateData().execute();
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

    public class UpdateData extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... arg0) {
            try{
                String token = pref.getString(Constant.SERVER_TOKEN, "");
                JSONObject param = new JSONObject();
                param.put(Constant.SERVER_TOKEN, token);
                ServerRequest sr = new ServerRequest();
                JSONObject jsonObj = sr.getResponse("http://54.255.178.120:5000/ticket/getopenticket", param);
                Log.e(TAG, "Response from url: " + jsonObj);

                // Getting JSON Array node
                if(jsonObj == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.fail_internet_connection), Toast.LENGTH_LONG).show();
                        }
                    });

                    return false;
                } else if(jsonObj.getBoolean(Constant.SERVER_RESPONSE)) {
                    dbContext = DbContext.getInst();
                    JSONArray garaList = jsonObj.getJSONObject(Constant.SERVER_RESPONSE_DATA).getJSONArray(Constant.SERVER_RESPONSE_GARA);
                    JSONArray ticketList = jsonObj.getJSONObject(Constant.SERVER_RESPONSE_DATA).getJSONArray(Constant.SERVER_RESPONSE_TICKTET);
                    for(int i=0; i< garaList.length(); i++) {
                        GaraModel gara = GaraModel.createByJson(garaList.getJSONObject(i));
                        if(gara != null) {
                            dbContext.addGaraModel(gara);
                        }
                    }

                    for(int i=0; i<ticketList.length(); i++) {
                        BookedTicketModel ticket = BookedTicketModel.createByJson(ticketList.getJSONObject(i));
                        if(ticket != null) {
                            dbContext.addBookedTicketModel(ticket);
                        }
                    }
                }

                return true;
            } catch(JSONException ex) {
                Log.e(TAG, "Json parsing error: " + ex.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            /*if(result) {
                dbContext = DbContext.getInst();
                List<BookedTicketModel> ticketList = dbContext.getAllBookedTicketModel();
                ticketAdapter = new BookedTicketAdapter(ticketList, );
                listTicket.setAdapter(ticketAdapter);
            }*/
//            Common.showProgress(false, loadProgress, listTicket, getContext());
        }
    }


}
