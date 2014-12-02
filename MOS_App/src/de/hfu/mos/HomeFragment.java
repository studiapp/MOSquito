package de.hfu.mos;

import de.hfu.mos.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	
	Button buttonTest;
	
	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container, false);

		buttonTest = (Button) rootView.findViewById(R.id.buttonFelix);
		
		OnClickListener oni = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				test(v);
				
			}
		};
		
		//buttonTest.setOnClickListener(oni);
		
		return rootView;
	}
	
	
	

	
	public void test(View v){
		
		Toast.makeText(getActivity(), "Funtzt!", Toast.LENGTH_SHORT).show();
	}
}
