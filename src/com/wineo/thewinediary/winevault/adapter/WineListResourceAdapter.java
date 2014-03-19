package com.wineo.thewinediary.winevault.adapter;

import com.wineo.thewinediary.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/* 
 * Class to act as the cursor adapter for the main wine list
 * Provides functionality to present thumbnail, wine name,
 * location, etc.
 */
public class WineListResourceAdapter extends ResourceCursorAdapter {
	
	private int wine_name_index, wine_label_front_index, winery_name_index, country_index, state_prov_index, town_index; 
	
	public WineListResourceAdapter(Context context, int layout, Cursor cursor, int flags){
		super(context, layout, cursor, flags);

		this.wine_label_front_index = cursor.getColumnIndex("label_front");
		this.wine_name_index = cursor.getColumnIndex("wine_name");
		this.winery_name_index = cursor.getColumnIndex("winery_name");
		this.country_index = cursor.getColumnIndex("country");
		this.state_prov_index = cursor.getColumnIndex("state_prov");
		this.town_index = cursor.getColumnIndex("town");
	}
	
	// helper method to convert between dp and px
	public int dpToPx(Context context, int dp) {
	    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
	

	/*
	 * Calculates the scaling factor. Taken from the Android documentation:
	 * http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
	 */
	public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ImageView thumbnail = (ImageView) view.findViewById(R.id.wineList_thumbnail);
		TextView wineListName = (TextView) view.findViewById(R.id.wineList_name);
		TextView wineryListName = (TextView) view.findViewById(R.id.wineList_winery);
		TextView location = (TextView) view.findViewById(R.id.wineList_Location);
		
		StringBuilder locationString = new StringBuilder();
		
		if(this.wine_label_front_index >= 0 && !cursor.isNull(this.wine_label_front_index)){
			String imagePath = cursor.getString(this.wine_label_front_index);
			imagePath = context.getExternalFilesDir(null).getAbsolutePath() + "/" + imagePath;
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			
			BitmapFactory.decodeFile(imagePath, options);
			
			int px = this.dpToPx(context, 48);
			
			options.inSampleSize = this.calculateInSampleSize(options, px, px);
			
			options.inJustDecodeBounds = false;
		
			thumbnail.setImageBitmap(BitmapFactory.decodeFile(imagePath, options));
		}
		
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
