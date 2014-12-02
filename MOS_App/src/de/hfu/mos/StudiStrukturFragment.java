package de.hfu.mos;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class StudiStrukturFragment extends Fragment {
	
	private Button buttonS3M1_5, buttonS2M1, buttonS2M2, buttonS2M3, buttonS2M4, buttonS2M5,
					buttonS1M1, buttonS1M2, buttonS1M3, buttonS1M4, buttonS1M5;
	
	public StudiStrukturFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_studistruktur, container, false);
		
		OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				doOnClick(v);
				
			}
		};
			
		buttonS1M5 = (Button) rootView.findViewById(R.id.ButtonS1M5);
		buttonS1M5.setOnClickListener(clickListener);
		return rootView;
	}
	
	
	

	
	private void doOnClick(View v){
		
		Toast.makeText(getActivity(), "Funtzt!", Toast.LENGTH_SHORT).show();
	}
}
