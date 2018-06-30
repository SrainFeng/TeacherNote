package com.example.srain.teachernote.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Project: TeacherNote
 * Date: 2018/6/28
 *
 * @author srain
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> mFragmentList;

    public FragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList){
        super(fragmentManager);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
