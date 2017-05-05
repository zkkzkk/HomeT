package com.example.z.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class AdapterTest extends FragmentPagerAdapter
{
    List <Fragment> mDatas;
    String [] titles = new String[]{"环境数据","家电控制","情景模式"};
    public AdapterTest(FragmentManager fm, List<Fragment> mDatas)
    {
        super(fm);
        this.mDatas = mDatas;
    }


    @Override
    public Fragment getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles[position];
    }

}
