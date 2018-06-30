package com.example.srain.teachernote.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.srain.teachernote.ui.adapters.FragmentAdapter;
import com.example.srain.teachernote.ui.fragments.ExperimentClassListFragment;
import com.example.srain.teachernote.R;
import com.example.srain.teachernote.ui.fragments.TeachClassListFragment;
import com.example.srain.teachernote.ui.fragments.UserFragment;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;


/**
 * Project: TeacherNote
 * Date: 2018/5/2
 *
 * @author srain
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;

    private TeachClassListFragment mTeachClassListFragment;

    private ExperimentClassListFragment mExperimentClassListFragment;

    private BottomNavigationBar mBottomBar;

    private UserFragment mUserFragment;

    private FragmentAdapter mFragmentAdapter;

    private List<Fragment> mFragmentList = new ArrayList<>();

    private Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Connector.getDatabase();

        mBottomBar = findViewById(R.id.bottom_bar);
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.main_toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.home);
        }

        initFragmentList();

        BottomNavigationItem classButton = new BottomNavigationItem(R.mipmap.ic_paste, "教学班");
        BottomNavigationItem labButton = new BottomNavigationItem(R.mipmap.ic_folder, "实验班");
        BottomNavigationItem userButton = new BottomNavigationItem(R.mipmap.ic_user, "用户");

        mBottomBar
                .addItem(classButton)
                .addItem(labButton)
                .addItem(userButton)
                .setFirstSelectedPosition(0)
                .initialise();

        mBottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);

        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);

    }

    private void initFragmentList() {
        mTeachClassListFragment = new TeachClassListFragment();
        mExperimentClassListFragment = new ExperimentClassListFragment();
        mUserFragment = new UserFragment();
        mFragmentList.add(mTeachClassListFragment);
        mFragmentList.add(mExperimentClassListFragment);
        mFragmentList.add(mUserFragment);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mBottomBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
