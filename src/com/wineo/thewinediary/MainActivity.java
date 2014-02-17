package com.wineo.thewinediary;

import come.wineo.thewinediary.dialogs.AboutDialogFragment;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
		
		Tab tab = actionBar.newTab().setText("Wine Vault").setTabListener(tabListener);
		actionBar.addTab(tab);
		
		tab = actionBar.newTab().setText("Recipe Drawer").setTabListener(tabListener);
		actionBar.addTab(tab);
		
		tab = actionBar.newTab().setText("Recipe Drawer").setTabListener(tabListener);
		actionBar.addTab(tab);
		
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_settings:
				this.openSettingsActivity();
				return true;
			case R.id.action_search:
				return true;
			case R.id.action_about:
				this.showAboutDialog();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		// TODO Auto-generated method stub
	}
	
	private void openSettingsActivity(){
		Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
	}
	
	private void showAboutDialog(){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		
		if(prev != null){
			ft.remove(prev);
		}
		
		ft.addToBackStack(null);
		
		new AboutDialogFragment().show(ft, "dialog");
//		new AboutDialogFragment().show
	}

}
