package com.wineo.thewinediary.listeners;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class MainTabListener<T extends Fragment> implements TabListener {
	private Fragment mFragment;
	
	private final Activity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	
	public MainTabListener(Activity activity, String tag, Class<T> fragmentClass){
		mActivity = activity;
		mTag = tag;
		mClass = fragmentClass;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		Fragment existingFragment = this.mActivity.getFragmentManager().findFragmentByTag(this.mTag);
		
		if(existingFragment != null){
			this.mFragment = existingFragment;
			ft.attach(this.mFragment);
		}else{
			this.mFragment = Fragment.instantiate(this.mActivity, this.mClass.getName());
			ft.add(android.R.id.content, this.mFragment, this.mTag);	
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if(this.mFragment != null){
			ft.detach(this.mFragment);
		}
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
