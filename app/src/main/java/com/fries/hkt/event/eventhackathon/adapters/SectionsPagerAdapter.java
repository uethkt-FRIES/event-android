package com.fries.hkt.event.eventhackathon.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fries.hkt.event.eventhackathon.fragments.AgendaFragment;
import com.fries.hkt.event.eventhackathon.fragments.EventInfoFragment;
import com.fries.hkt.event.eventhackathon.fragments.MapFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position) {
            case 0:
                return AgendaFragment.newInstance();
            case 1:
                return EventInfoFragment.newInstance(1);
            default:
                return MapFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Lịch trình";
            case 1:
                return "Thông tin";
            case 2:
                return "Bản đồ";
        }
        return null;
    }
}
