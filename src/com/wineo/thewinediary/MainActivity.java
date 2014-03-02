package com.wineo.thewinediary;

import com.wineo.thewinediary.dialogs.AboutDialogFragment;
import com.wineo.thewinediary.listeners.MainTabListener;
import com.wineo.thewinediary.recipes.RecipeDrawerFragment;
import com.wineo.thewinediary.winevault.WineVaultFragment;

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
		
		Tab tab = actionBar.newTab().setText("Wine Vault").setTabListener(new MainTabListener<WineVaultFragment>(this, "wine_vault", WineVaultFragment.class));
		actionBar.addTab(tab);
		
		tab = actionBar.newTab().setText("Recipe Drawer").setTabListener(new MainTabListener<RecipeDrawerFragment>(this, "recipe_drawer", RecipeDrawerFragment.class));
		actionBar.addTab(tab);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		ActionBar actionBar = getActionBar();
		int selectedTabIndex = actionBar.getSelectedNavigationIndex();
		outState.putInt("SelectedTab", selectedTabIndex);
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		ActionBar actionBar = getActionBar();
		actionBar.setSelectedNavigationItem(savedInstanceState.getInt("SelectedTab"));
		super.onRestoreInstanceState(savedInstanceState);
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
	}

}
