package de.hfu.mos;

import de.hfu.mos.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WebsiteFragment extends Fragment {
	
	public WebsiteFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_website, container, false);

		return rootView;
	}

}
