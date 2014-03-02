package com.wineo.thewinediary.winevault.adapter;

import com.wineo.thewinediary.R;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/* 
 * Class to act as the cursor adapter for the main wine list
 * Provides functionality to present thumbnail, wine name,
 * location, etc.
 */
public class WineListResourceAdapter extends ResourceCursorAdapter {
	
	private int wine_name_index, winery_name_index, country_index, state_prov_index, town_index; 
	
	public WineListResourceAdapter(Context context, int layout, Cursor cursor, int flags){
		super(context, layout, cursor, flags);
		
		this.wine_name_index = cursor.getColumnIndex("wine_name");
		this.winery_name_index = cursor.getColumnIndex("winery_name");
		this.country_index = cursor.getColumnIndex("country");
		this.state_prov_index = cursor.getColumnIndex("state_prov");
		this.town_index = cursor.getColumnIndex("town");
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView wineListName = (TextView) view.findViewById(R.id.wineList_name);
		TextView wineryListName = (TextView) view.findViewById(R.id.wineList_winery);
		TextView location = (TextView) view.findViewById(R.id.wineList_Location);
		
		StringBuilder locationString = new StringBuilder();
		
		if(this.wine_name_index >= 0 && !cursor.isNull(this.wine_name_index)){
			wineListName.setText(cursor.getString(this.wine_name_index));
		}
		
		if(this.winery_name_index >= 0 && !cursor.isNull(this.winery_name_index)){
			wineryListName.setText(cursor.getString(this.winery_name_index));
		}
		
		/*
		 * Builds the location string from the town, state, and country
		 * and formats it so that it is presented well on screen.
		 */
		if(this.town_index >= 0 && !cursor.isNull(this.town_index)){
			locationString.append(cursor.getString(this.town_index));
		}
		
		if(this.state_prov_index >= 0 && !cursor.isNull(this.state_prov_index)){
			if(locationString.length() > 0){
				locationString.append(", ");
			}
			
			locationString.append(cursor.getString(this.state_prov_index));
		}
		

		if(this.country_index >= 0 && !cursor.isNull(this.country_index)){
			if(locationString.length() > 0){
				locationString.append(", ");
			}
			
			locationString.append(cursor.getString(this.country_index));
		}
		
		location.setText(locationString.toString());
		
	}

}
