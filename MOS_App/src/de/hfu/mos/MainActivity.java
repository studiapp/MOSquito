package de.hfu.mos;

import de.hfu.mos.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	// declare properties
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;

    // Button Kacheln
    private Button rssreader;
    
    //global Fragment for other methods, e.g. onBackPressed()
    private Fragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// for proper titles
		mTitle = mDrawerTitle = getTitle();
		
		// initialize properties
		mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        // Drawermenu Header und Foot (icons)
        
        LayoutInflater inflater = getLayoutInflater();
        // ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header, mDrawerList,
        //        false);
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.footer, mDrawerList,
                false);
         // mDrawerList.addHeaderView(header, null, false);
        mDrawerList.addFooterView(footer, null, false);
        
        // list the drawer items
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[7];
        
        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_nav_home, "Home");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_nav_studiengang, "Studiengang");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_nav_kontakte, "Kontakt");
        drawerItem[3] = new ObjectDrawerItem(R.drawable.ic_nav_campus, "Campus");
        drawerItem[4] = new ObjectDrawerItem(R.drawable.ic_nav_webmail, "Webmail");
        drawerItem[5] = new ObjectDrawerItem(R.drawable.ic_nav_vorlesungsplan, "Vorlesungsplan");
        drawerItem[6] = new ObjectDrawerItem(R.drawable.ic_nav_website, "Webseite");
        
        // Pass the folderData to our ListView adapter
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        
        // Set the adapter for the list view
        mDrawerList.setAdapter(adapter);
        
        // set the item click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        
        

        
        // for app icon control for nav drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {
        	
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        
        
        if (savedInstanceState == null) {
            // on first time display view for first nav item
        	selectItem(0);
        }


        //RSSREADER
        rssreader = (Button) findViewById(R.id.rssreader);
        /*rssreader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RSSReaderFragment.class);
                startActivity(intent);
            }
        });*/





	}


    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, RSSReaderFragment.class);
        startActivity(intent);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
       if (mDrawerToggle.onOptionsItemSelected(item)) {
           return true;
       }
       
       return super.onOptionsItemSelected(item);
	}
	
	// to change up caret
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
	
	
	// navigation drawer click listener
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        selectItem(position);
	    }
	    
	}

    private void selectItem(int position) {
    	
        // update the main content by replacing fragments
    	
        fragment = null;
        
        switch (position) {
        case 0:
            fragment = new HomeFragment();
            break;
        case 1:
            fragment = new StudiengangFragment();
            break;
        case 2:
            fragment = new KontaktFragment();
            break;
        case 3:
        	fragment = new CampusFragment();
        	break;
        case 4:
        	fragment = new WebmailFragment();
        	break;
        case 5:
        	fragment = new VorlesungsplanFragment();
        	break;
        case 6:
        	fragment = new WebsiteFragment();
        	break;
 
        default:
            break;
        }
         
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
 
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    } 
    
    @Override
    public void onBackPressed() {
        //enables WebView to go page back in website
    	if(fragment instanceof WebsiteFragment){
    		if( !((WebsiteFragment)fragment).webGoBack() ){
    			super.onBackPressed();
    			}
    	}
    	else super.onBackPressed();
    }
    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    

}
