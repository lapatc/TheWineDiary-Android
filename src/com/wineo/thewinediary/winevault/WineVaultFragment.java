package com.wineo.thewinediary.winevault;

import com.wineo.thewinediary.R;
import com.wineo.thewinediary.winevault.adapter.WineListResourceAdapter;
import com.wineo.thewinediary.winevault.database.WineDatabaseHelper;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ListAdapter;

public class WineVaultFragment extends ListFragment {
	WineDatabaseHelper db;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setRetainInstance(true);
		
		// initialise the database helper
		this.db = new WineDatabaseHelper(this.getActivity());
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		/*
		 * Adapter takes the custom list item layout and a cursor
		 * with the basic wine information to be displayed in the list.
		 */
		ListAdapter adapter = new WineListResourceAdapter(this.getActivity(), R.layout.winevault_listitem, this.db.getBasicWineData(), 0);
		
		this.setListAdapter(adapter);
	}

}
