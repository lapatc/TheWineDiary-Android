package com.wineo.thewinediary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
import android.content.res.AssetManager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Copy over everything from the assets folder (databases and images)
		this.copyPackagedAssets();
		
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
	
	/* 
	 * Method to copy resources from the assets folder to the application.
	 * Currently used for development data but may be useful in the future to
	 * pre-load a database. 
	 */
	private void copyPackagedAssets(){
		final String ASSETS_DATA_PATH = "data";
		final String ASSETS_IMAGES_PATH = "app_images";
		
		AssetManager am = this.getAssets();
		
		InputStream is = null;
		OutputStream os = null;
		
		try {
			String[] dbList = am.list(ASSETS_DATA_PATH);
			String[] imagesList = am.list(ASSETS_IMAGES_PATH);
			
			for(String dbName : dbList){
				File dbFile = this.getDatabasePath(dbName);
				if(!dbFile.exists()){
					is = am.open(ASSETS_DATA_PATH + "/" + dbName);
					
					// Create the file and the directory hierarchy if necessary
					dbFile.getParentFile().mkdirs();
					dbFile.createNewFile();
					
					os = new FileOutputStream(dbFile);
					
					byte[] data = new byte[1024];
					while(is.read(data) > 0){
						os.write(data);
					}
					
					is.close();
					os.close();
				}
			}
			
			File appFilesDir = this.getExternalFilesDir(null);
			for(String imageName : imagesList){
				File imageFile = new File(appFilesDir, imageName);
				
				if(!imageFile.exists()){
					
					is = am.open(ASSETS_IMAGES_PATH + "/" + imageName);
					os = new FileOutputStream(imageFile);
					
					
					byte[] data = new byte[1024];
					while(is.read(data) > 0){
						os.write(data);
					}
					
					is.close();
					os.close();
				}
			}
			
		} catch (IOException e1) {
			System.err.println("There was an error copying data to the application: " + e1.getMessage());
			System.exit(0);
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					System.err.println("There was an error closing the input stream: " + e.getMessage());
					System.exit(0);
				}
			}
			
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					System.err.println("There was an error closing the output stream: " + e.getMessage());
					System.exit(0);
				}
			}
		}
	}
}
