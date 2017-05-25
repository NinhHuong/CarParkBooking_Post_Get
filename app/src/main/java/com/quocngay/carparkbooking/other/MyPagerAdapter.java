package com.quocngay.carparkbooking.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.quocngay.carparkbooking.fragment.BookedListFragment;
import com.quocngay.carparkbooking.fragment.HistoryFragment;
import com.quocngay.carparkbooking.fragment.MapFragment;

/**
 * Created by ninhh on 5/22/2017.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MyPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BookedListFragment bookedListTab = new BookedListFragment();
                return bookedListTab;
            case 1:
                MapFragment mapFragment = new MapFragment();
                return mapFragment;
            case 2:
                HistoryFragment historyFragment = new HistoryFragment();
                return historyFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
