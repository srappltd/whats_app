package com.sandhya.whatsapp.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sandhya.whatsapp.homefragement.CallsFragment;
import com.sandhya.whatsapp.homefragement.ChatsFragment;
import com.sandhya.whatsapp.homefragement.CommunityFragment;
import com.sandhya.whatsapp.homefragement.StatusFragment;

public class TabAdapter extends FragmentPagerAdapter {

    private Context context;
    int tabs;

    public TabAdapter(@NonNull FragmentManager fm, Context context, int tabs) {
        super(fm);
        this.context = context;
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        int id = position;
        switch (id) {
            case 0:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;
//                return new ChatsFragment();
            case 1:
                return new StatusFragment();
            case 2:
                return new CallsFragment();
            case 3:
                return new CommunityFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
