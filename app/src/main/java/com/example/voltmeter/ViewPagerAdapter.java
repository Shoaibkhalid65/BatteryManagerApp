package com.example.voltmeter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new StatsFragment();
            case 1: return new ChargingFragment();
            case 2: return new PropertiesFragment();
            default:return new SettingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
