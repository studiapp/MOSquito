package de.hfu.mos;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Fragment;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.hfu.mos.util.QuickSortCalendar;

public class VorlesungsplanFragment extends Fragment {
	
	private CalendarBuilder _CalendarBuilder;
	private DownloadManager _DownloadManager;
	private Button _buttonUpdate;
	private LinearLayout _Layout, emptyLayout;
	
	private java.util.Calendar today;
	
	private final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd'T'hhmmss", Locale.GERMANY);
		
	//we need the Downloadmanager to download ics File from the HFU website.
	//Downloadmanager has to be initialized in the mainActivity. Otherwise the application will crash
	public VorlesungsplanFragment(DownloadManager dm) {
		
		_DownloadManager = dm;
	}

	//For this function we use the .ics File provided by the HFU website/schedule.
	//to be able to read and work with the entries of the ics File, we use the opensource libary iCal4j
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_vorlesungsplan, container, false);

		today = java.util.Calendar.getInstance();
		
		_CalendarBuilder = new CalendarBuilder();
		
		_Layout = (LinearLayout) rootView.findViewById(R.id.linearLayout_Vorlesung);
				
		_buttonUpdate = (Button) rootView.findViewById(R.id.button_Vorlesungsplan);
		
		OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				updatePlan(v);
			}
		};
		_buttonUpdate.setOnClickListener(clickListener);
			
		updatePlan(null);
		
		return rootView;
	}
	
	public void updatePlan(View v){
		
		new Vorlesung().execute(v);

	}
    
	
	//The entries in the ics file are sorted by the Event and not by date
	//here we sort the content by date
	private Vector<Component> sortData(Calendar cal) throws ParseException{
		
		Vector<Component> testList = new Vector<Component>();
		
		testList.addAll(cal.getComponents(Component.VEVENT));
		
		QuickSortCalendar.sortiere(testList);
		
		return testList;
		
	}
	
	private String getDay(int day){

		switch (day) {
		case 1:
			return "Montag";
		case 2:
			return "Dienstag";
		case 3:
			return "Mittwoch";
		case 4:
			return "Donnerstag";
		case 5:
			return "Freitag";
		case 6:
			return "Samstag";
		case 7:
			return "Sonntag";
		default:
			return "Wrong value";
		}
	}
	
	private class Vorlesung extends AsyncTask<View, Void, LinearLayout>{
		

		@Override
		protected void onPostExecute(LinearLayout result) {
			try {
				_Layout.removeAllViews();
				_Layout.addView(this.get());
				Toast.makeText(getActivity(), "fertig", Toast.LENGTH_SHORT).show();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected void onPreExecute() {
			
			Toast.makeText(getActivity(), "loading...", Toast.LENGTH_SHORT).show();
			
		}
		@Override
		protected LinearLayout doInBackground(View... params) {
			
			//the ics File from the HFU website
			File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/vorlesungsplan.ics");
			
			//if v is null, this method was called by the onCreate method
			//otherwise a button was clicked
			if(params[0] != null)
				switch(params[0].getId()){
				
				//if the button is pressed, we delete the file and go on with the code -> redownload file and display entries
				case R.id.button_Vorlesungsplan:
					if(file.exists()) 
						file.delete();
					break;
				}
			
			LayoutInflater inflater = getActivity().getLayoutInflater();
			
			View view, view2;
			
			//it's possible to have more events at the same date
			//actuallDate is used to look for this cases
			java.util.Calendar actuallDate = java.util.Calendar.getInstance();
			
			//holds the StartDate and time of the event we are looking at 
			java.util.Calendar tempDateStart = java.util.Calendar.getInstance();
			//holds the EndDate and time of the event we are looking at
			java.util.Calendar tempDateEnd = java.util.Calendar.getInstance();
			
	        //_Layout.removeAllViews();
	        
			view2 = inflater.inflate(R.layout.empty_layout,_Layout, false);
			emptyLayout = (LinearLayout) view2.findViewById(R.id.empty_Layout);
			
			emptyLayout.removeAllViews();
			try {
				
				//needs to be set somewhere in the (far) past to make the following code work (see: switch/case 0)
				actuallDate.setTime(SDF.parse("20000101T123456"));
				
				FileInputStream fin;
				Uri path;
				if(!file.exists()){

					String url = "https://stundenplan.hs-furtwangen.de/splan/ical?type=pg&puid=8&pgid=2505&lan=de";
					Request request = new Request(Uri.parse(url));
					request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
					request.setDestinationInExternalPublicDir(
							Environment.DIRECTORY_DOWNLOADS, "vorlesungsplan.ics");
					long id = _DownloadManager.enqueue(request);

					while ((path = _DownloadManager.getUriForDownloadedFile(id)) == null) {

					}
					fin = new FileInputStream(path.getPath());

				}
				else 
					fin = new FileInputStream(file);
				
				Calendar calendar = _CalendarBuilder.build(fin);
				
				Vector<Component> daten = sortData(calendar);

				for(int i = 0; i < daten.size(); i++){

					//parses the entry of the time into somthing we can work with
					tempDateStart.setTime(SDF.parse(daten.get(i).getProperty(Property.DTSTART).getValue()));
					tempDateEnd.setTime(SDF.parse(daten.get(i).getProperty(Property.DTEND).getValue()));
					
					//tests if date is already in the past. If so the entry is not used and we jump to the next entry
					if(tempDateStart.before(today)){
						continue;
					}
					
					for(int j = 0; j<5; j++){
						
						//to dynamically add Views to the application, we created a simple textLayout with a LinearLayout and two child views
						//this textLayout is created for every iteration and inserted into our _Layout of this Fragment (see end of switch/case)
						view = inflater.inflate(R.layout.vorlesung_textlayout,emptyLayout, false);

						LinearLayout layoutToDisplay = (LinearLayout) view.findViewById(R.id.vorlesung_Textlayout);

						switch(j){
						//Note: TextSize should always be set in SP, to scale with different screen sizes
						
						//prints the first line of the schedule -> Montag: 12.12.1900
							case 0:
								//tests if the date is already shown. If not this date is used for the header.
								//also saves date in actualDate for next iteration to check
								//actualDate is first set to somewhere in the past (see above)
								if (tempDateStart.get(java.util.Calendar.DAY_OF_YEAR) > actuallDate.get(java.util.Calendar.DAY_OF_YEAR)
										|| tempDateStart.get(java.util.Calendar.YEAR) > actuallDate.get(java.util.Calendar.YEAR)) {
									actuallDate.setTime(tempDateStart.getTime());
									((TextView) (layoutToDisplay.getChildAt(0))).setTypeface(Typeface.DEFAULT_BOLD);
									((TextView) (layoutToDisplay.getChildAt(0))).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
									((TextView) (layoutToDisplay.getChildAt(0))).setText(getDay(tempDateStart.get(java.util.Calendar.DAY_OF_WEEK) - 1)
												+ ": "
												+ tempDateStart.get(java.util.Calendar.DATE)
												+ "."
												+ (tempDateStart.get(java.util.Calendar.MONTH) + 1)
												+ "."
												+ tempDateStart.get(java.util.Calendar.YEAR));
									//childAt(1) is View to show a line under the TextView. We set it HFULight = green to underline the header.
									((View) (layoutToDisplay.getChildAt(1))).setBackgroundResource(R.color.HFULight);
								}
								else{
									//if date was already printed before, we end up here
									//we set Visibility of the ChildAt(0) (the TextView) to GONE, because it has no Text set
									//and would otherwise take some space that would result in a empty row
					        		layoutToDisplay.getChildAt(0).setVisibility(View.GONE);
					        		//CildAt(1) is a View to display a line
					        		//we set it GrayLight because ifwe end up here there is a second event at the same date
					        		//the gray line is displayed between the Events to make it more appealing
					        		((View) (layoutToDisplay.getChildAt(1))).setBackgroundResource(R.color.HFUGrayLight);
								}
								break;
							case 1:
								//prints the second line, shows the course name
								((TextView) (layoutToDisplay.getChildAt(0))).setTypeface(Typeface.DEFAULT_BOLD);
								//the description is not only the course name, but also, in some cases some additional info, the name of the professor
								//but the course name is always in the first part and its parted by an '\n'
								((TextView) (layoutToDisplay.getChildAt(0))).setText(daten.get(i).getProperty(Property.DESCRIPTION).getValue().split("\n")[0]);
								((TextView) (layoutToDisplay.getChildAt(0))).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
								//we set ChildAt(1) ( View to show line under textView) to White so that we don't see it. So we also get a little space between entries
								((View) (layoutToDisplay.getChildAt(1))).setBackgroundResource(R.color.White);						
								break;
							case 2:	
								//prints the third line, shows the remaining description after the course name
								
								String tempString = "";
								((TextView) (layoutToDisplay.getChildAt(0))).setTypeface(Typeface.DEFAULT);
								((TextView) (layoutToDisplay.getChildAt(0))).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
								//we iterate through all remaining description entries and save them in one string to be able to print it in one line
								for(int part = 1; part < daten.get(i).getProperty(Property.DESCRIPTION).getValue().split("\n").length; part++)
									tempString += daten.get(i).getProperty(Property.DESCRIPTION).getValue().split("\n")[part] + " ";
								((TextView) (layoutToDisplay.getChildAt(0))).setText(tempString);
								((View) (layoutToDisplay.getChildAt(1))).setBackgroundResource(R.color.White);
								break;
								
							case 3:
								//prints the fourth line, shows the start and end time of this event
								((TextView) (layoutToDisplay.getChildAt(0))).setTypeface(Typeface.DEFAULT);
								((TextView) (layoutToDisplay.getChildAt(0))).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
								//libary was bugged here, had to do some workaround, casts are needed to make it work
								((TextView) (layoutToDisplay.getChildAt(0))).setText(
										//hour_of_day showed "0" when hour was actually "12". so we look if it's 0 and then print 12, otherwise we print the hour
										((CharSequence) (tempDateStart.get(java.util.Calendar.HOUR_OF_DAY) == 0 ? "12" : Integer.toString(tempDateStart.get(java.util.Calendar.HOUR_OF_DAY))))+
										":" +
										//Minute showed "0" when minute was "00". we simply print 00 or otherwise the actual minute
										((CharSequence) (tempDateStart.get(java.util.Calendar.MINUTE) == 0 ? "00" : Integer.toString(tempDateStart.get(java.util.Calendar.MINUTE)))) +
										" - " +
										((CharSequence) (tempDateEnd.get(java.util.Calendar.HOUR_OF_DAY) == 0 ? "12" : Integer.toString(tempDateEnd.get(java.util.Calendar.HOUR_OF_DAY)))) +
										":" +
										((CharSequence) (tempDateEnd.get(java.util.Calendar.MINUTE) == 0 ? "00" : Integer.toString(tempDateEnd.get(java.util.Calendar.MINUTE)))) +
										" Uhr"
										);
								((View) (layoutToDisplay.getChildAt(1))).setBackgroundResource(R.color.White);
								break;
								
							case 4:
								//prints the fifth line, shows the location
								((TextView) (layoutToDisplay.getChildAt(0))).setTypeface(Typeface.DEFAULT);
								((TextView) (layoutToDisplay.getChildAt(0))).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
								((TextView) (layoutToDisplay.getChildAt(0))).setText("Raum: "+daten.get(i).getProperty(Property.LOCATION).getValue());
								((View) (layoutToDisplay.getChildAt(1))).setBackgroundResource(R.color.White);
								break;
								
						}
						emptyLayout.addView(layoutToDisplay);
					}
				}
									
				} catch (Exception e) {
					Toast.makeText(getActivity(), "ERROR: "+ e.toString(), Toast.LENGTH_LONG).show();
				}		

			return emptyLayout;
		}
		
	}

}
