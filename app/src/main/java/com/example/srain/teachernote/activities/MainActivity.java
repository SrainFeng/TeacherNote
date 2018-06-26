package com.example.srain.teachernote.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.srain.teachernote.fragments.ExperimentClassListFragment;
import com.example.srain.teachernote.R;
import com.example.srain.teachernote.fragments.TeachClassListFragment;

import org.litepal.tablemanager.Connector;


/**
 * Project: TeacherNote
 * Date: 2018/5/2
 *
 * @author srain
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationBar bottomBar;

    private TeachClassListFragment mTeachClassListFragment;

    private ExperimentClassListFragment mExperimentClassListFragment;

    BottomNavigationItem classButton;
    BottomNavigationItem labButton;
    BottomNavigationItem userButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Connector.getDatabase();

        bottomBar = findViewById(R.id.bottom_bar);
        classButton = new BottomNavigationItem(R.mipmap.ic_paste, "教学班");
        labButton = new BottomNavigationItem(R.mipmap.ic_folder, "实验");
        userButton = new BottomNavigationItem(R.mipmap.ic_user, "用户");
        TextBadgeItem numberBadgeItem = new TextBadgeItem();
        numberBadgeItem.setText("5")
                .setBackgroundColor("#ea3f3f")
                .setTextColor("#ecdcff")
                .setAnimationDuration(100)
                .setHideOnSelect(true);
        bottomBar
                .addItem(classButton)
                .addItem(labButton.setBadgeItem(numberBadgeItem))
                .addItem(userButton)
                .setFirstSelectedPosition(0)
                .initialise();
        showStudentListFragment();

        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        showStudentListFragment();
                        break;
                    case 1:
                        showExperimentListFragment();
                    default:
                        break;
                }

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

    }

    private void showStudentListFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mTeachClassListFragment == null) {
            mTeachClassListFragment = new TeachClassListFragment();
            transaction.add(R.id.fragment_layout, mTeachClassListFragment);
        }
        hideFragments(transaction);
        transaction.show(mTeachClassListFragment);
        transaction.commit();
    }

    private void showExperimentListFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mExperimentClassListFragment == null) {
            mExperimentClassListFragment = new ExperimentClassListFragment();
            transaction.add(R.id.fragment_layout, mExperimentClassListFragment);
        }
        hideFragments(transaction);
        transaction.show(mExperimentClassListFragment);
        transaction.commit();
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (mTeachClassListFragment != null) {
            transaction.hide(mTeachClassListFragment);
        }
        if (mExperimentClassListFragment != null) {
            transaction.hide(mExperimentClassListFragment);
        }
    }

}
