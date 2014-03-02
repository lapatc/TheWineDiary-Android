package com.wineo.thewinediary.recipes;

import com.wineo.thewinediary.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeDrawerFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO FIND out more about the final boolean parameter in the inflate method
		return inflater.inflate(R.layout.recipedrawer_fragment, container, false);
	}
}
