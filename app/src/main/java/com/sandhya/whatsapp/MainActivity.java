package com.sandhya.whatsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.sandhya.whatsapp.adapter.TabAdapter;
import com.sandhya.whatsapp.setting.SettingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        initTabLayout();
        initMenu(toolbar);
//        initToolbar();
    }

    private void initMenu(Toolbar toolbar) {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.menuCamera:
                        Toast.makeText(MainActivity.this, "Camera", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuSearch:
                        Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuNewGroup:
                        Toast.makeText(MainActivity.this, "New Group", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuNewBroadcast:
                        Toast.makeText(MainActivity.this, "Broadcast", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuLinkDevices:
                        Toast.makeText(MainActivity.this, "Link Devices", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuStarredMessage:
                        Toast.makeText(MainActivity.this, "Starred Message", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuPayment:
                        Toast.makeText(MainActivity.this, "Payment", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menuSetting:
                        Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                }
                return false;
            }
        });
    }


    private void initTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Status"));
        tabLayout.addTab(tabLayout.newTab().setText("Calls"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_community));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);

//        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}