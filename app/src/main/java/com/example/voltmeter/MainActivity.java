package com.example.voltmeter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_bar);
        viewPager2=findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.menu_stats){
                    viewPager2.setCurrentItem(0);
                    return true;
                } else if (id==R.id.menu_charging) {
                    viewPager2.setCurrentItem(1);
                    return true;
                } else if (id==R.id.menu_property) {
                    viewPager2.setCurrentItem(2);
                    return true;
                } else if (id==R.id.menu_setting) {
                    viewPager2.setCurrentItem(3);
                    return true;
                }
                return false;
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.menu_stats);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.menu_charging);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.menu_property);
                        break;
                    default:
                        bottomNavigationView.setSelectedItemId(R.id.menu_setting);
                }
            }
        });


    }
}