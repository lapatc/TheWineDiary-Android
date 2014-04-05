package com.wineo.thewinediary;

import com.wineo.thewinediary.winevault.WineVaultFragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.wineo.thewinediary.winevault.database.WineDatabaseHelper;

public class WineDetailsActivity extends Activity{
	
	private WineDatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = this.getIntent();
		int id = intent.getIntExtra(WineVaultFragment.WINE_ID, -1);
		
		this.db = new WineDatabaseHelper(this);
		
		Cursor c = db.getBasicWineData("WHERE wine._id = " + id);
		
		this.setContentView(R.layout.activity_wine_details);
		
		TextView name = (TextView)this.findViewById(R.id.wineDetailFloater_name);
		
		int wine_name_index = c.getColumnIndex("wine_name");
		c.moveToFirst();
		name.setText(c.getString(wine_name_index));
	}

}
