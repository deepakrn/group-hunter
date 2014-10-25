package com.example.listtest;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class ActionActivity extends FragmentActivity implements TabListener{
	ViewPager viewPager=null;
	ActionBar actionBar;
	private Vibrator vibrator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab tab1=actionBar.newTab();
		tab1.setText("Find groups");
		tab1.setTabListener(this);
		actionBar.addTab(tab1);
		ActionBar.Tab tab2=actionBar.newTab();
		tab2.setIcon(R.drawable.search);
		tab2.setText("My Groups");
		tab2.setTabListener(this);
		actionBar.addTab(tab2);
		ActionBar.Tab tab3=actionBar.newTab();
		tab3.setText("Messages");
		tab3.setTabListener(this);
		actionBar.addTab(tab3);
		ActionBar.Tab tab4=actionBar.newTab();
		tab4.setText("Profile");
		tab4.setTabListener(this);
		
		actionBar.addTab(tab4);		
		vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
		
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}	
	
}
class MyAdapter extends FragmentPagerAdapter{

	public MyAdapter(FragmentManager f){
		super(f);
	}
	@Override
	public Fragment getItem(int i) {
		// TODO Auto-generated method stub
		if(i==0)
			return new FragmentA();
		else if(i==1)
			return new FragmentB();
		else
			return new FragmentC();
		
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}	
	
}
