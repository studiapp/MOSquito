package de.hfu.mos;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;


//implements OnItemClickListener 
public class BudgetRechner extends Fragment implements View.OnClickListener, OnItemClickListener  {

    private static final int LENGTH_SHORT = 0;


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View rootView = inflater.inflate(R.layout.fragment_graphauswahl, container, false);
			    
        
        Button ai = (Button) rootView.findViewById(R.id.ai);
        Button cn = (Button) rootView.findViewById(R.id.cn);
        Button spb = (Button) rootView.findViewById(R.id.spb);
        Button verglbachelor = (Button) rootView.findViewById(R.id.verglbachelor);
        Button in = (Button) rootView.findViewById(R.id.in);
        Button mos  = (Button) rootView.findViewById(R.id.mos);
        Button verglmaster  = (Button) rootView.findViewById(R.id.verglmaster);
        
        ai.setOnClickListener(this);
        cn.setOnClickListener(this);
        spb.setOnClickListener(this);
        verglbachelor.setOnClickListener(this);
        in.setOnClickListener(this);
        mos.setOnClickListener(this);
        verglmaster.setOnClickListener(this);

        
        
        return rootView;

    }
	
	
	
	
	
		public void onClick(View v){
			Fragment fragment = null;
	    	switch(v.getId()){   	

	    	case R.id.cn:
	    		Toast.makeText(getActivity(), "läuft", LENGTH_SHORT).show();
	    		fragment = new LineGraph(0,40452,0,42086,44000,44662,46693,49311,51553,54443,56919,60110,62843,66366,69384,73273,76605,80900,84578,89320,93381);
	    		break;
	    	case R.id.ai:
	    		fragment = new LineGraph(0,0,0,42086,44000,44662,46693,49311,51553,54443,56919,60110,62843,66366,69384,73273,76605,80900,84578,89320,93381);
	    		break;    				
	    	case R.id.spb:
	    		fragment = new LineGraph(0,40452,0,42086,44000,44662,46693,49311,51553,54443,56919,60110,62843,66366,69384,73273,76605,80900,84578,89320,93381);
	    		break;
	    	case R.id.verglbachelor:
	    		fragment = new LineGraph(0,40452,0,42086,44000,44662,46693,49311,51553,54443,56919,60110,62843,66366,69384,73273,76605,80900,84578,89320,93381);
	    		break;
	    	case R.id.mos:
	    		fragment = new LineGraph(0,40452,0,42086,44000,44662,46693,49311,51553,54443,56919,60110,62843,66366,69384,73273,76605,80900,84578,89320,93381);
	    		break;
	    	case R.id.in:
	    		fragment = new LineGraph(0,40452,0,42086,44000,44662,46693,49311,51553,54443,56919,60110,62843,66366,69384,73273,76605,80900,84578,89320,93381);
	    		break;
	    	case R.id.verglmaster:
	    		fragment = new LineGraph(0,40452,0,42086,44000,44662,46693,49311,51553,54443,56919,60110,62843,66366,69384,73273,76605,80900,84578,89320,93381);;
	    		break;
	    	default:
	    		break;
	    		}
	    	//openFragment(fragment, position);
	        if (fragment != null) {
	            FragmentManager fragmentManager = getFragmentManager();
	            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();}
       }

	
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
		Fragment fragment = null;
    	switch(view.getId()){   	

    	case R.id.cn:
    		//Toast.makeText(getActivity(), "läuft", LENGTH_SHORT).show();
    		fragment = new LineGraph(0,0,0,2,3,3,4,5,6,7,8);
    		break;
    	case R.id.ai:
    		fragment = new LineGraph(0,0,0,2,3,3,4,5,6,7,8);
    		break;    				
    	case R.id.spb:
    		fragment = new LineGraph(0,0,0,2,3,3,4,5,6,7,8);
    		break;
    	case R.id.verglbachelor:
    		fragment = new LineGraph(1,30,34,45,57,77,89);
    		break;
    	case R.id.mos:
    		fragment = new LineGraph(0,0,0,2,3,3,4,5,6,7,8);
    		break;
    	case R.id.in:
    		fragment = new LineGraph(0,0,0,2,3,3,4,5,6,7,8);
    		break;
    	case R.id.verglmaster:
    		fragment = new LineGraph(1,30,34,45,57,77,89);
    		break;
    	default:
    		break;
    		}
    	//openFragment(fragment, position);
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();}
		}
		
		
	    //renders the fragment in activity_main
	    protected void openFragment(Fragment fragment, int position) {

	        if (fragment != null) {
	            FragmentManager fragmentManager = getFragmentManager();
	            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

	            //change the title to actuall fragment depended on position
	            //position is handled normaly by navigation drawer, but manually if button click
	            switch (position) {

	                //position of RSS:
	                case -2:
	                	
	                    break;
	                //position of Felix:
	                case -1:
	                    
	                    break;
	                //if position is declared by navigation drawer::
	                default:
	                    // update selected item and title, then close the drawer
	                	break;
	            }
	        } else {
	            // error in creating fragment
	            Log.e("MainActivity", "Error in creating fragment");
	        }
	    }
    }