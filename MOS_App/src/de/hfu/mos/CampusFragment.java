package de.hfu.mos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CampusFragment extends Fragment {
	
	private ImageView _ImageView;
	
	public CampusFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_campus, container, false);

		_ImageView = (ImageView) rootView.findViewById(R.id.imageView_Campus);
		
		return rootView;
	}

}
