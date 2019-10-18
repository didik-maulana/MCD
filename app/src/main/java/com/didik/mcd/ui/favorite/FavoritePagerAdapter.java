package com.didik.mcd.ui.favorite;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FavoritePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> pages = new ArrayList<>();
    private List<String> titlePages = new ArrayList<>();

    FavoritePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    void addFragment(Fragment fragment, String title) {
        pages.add(fragment);
        titlePages.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlePages.get(position);
    }
}
