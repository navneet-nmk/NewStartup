package com.teenvan.newstartup.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teenvan.newstartup.Fragments.FirstFragment;
import com.teenvan.newstartup.Fragments.ZoomOutPageTransformer;
import com.teenvan.newstartup.R;

public class SplashActivity extends AppCompatActivity {

    // Declaration of member variables

    /* Number of pages in the view pager */
    private static final int NUM_PAGES = 3;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Referencing the UI elements
        mPager = (ViewPager)findViewById(R.id.pager);
        mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(false, new ZoomOutPageTransformer());
    }

    private class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new FirstFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
