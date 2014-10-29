package de.hfu.mos;

import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebmailFragment extends Fragment {
	
	private WebView _WebView;
		
	//String to display when no internet available:
    private String noInternet = "<html><body>No internet available! Try again later.</body></html>";
	
	public WebmailFragment() {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_webmail, container, false);

		_WebView = (WebView) rootView.findViewById(R.id.webView_WebMail);
		
		webLogin();
	
		return rootView;
	}
	
    @Override
    public void onSaveInstanceState(Bundle outState )
    {
        super.onSaveInstanceState(outState);
        _WebView.saveState(outState);
    }
	
    //Handles webView:
	public void webLogin() {

		_WebView.getSettings().setJavaScriptEnabled(true);

		_WebView.getSettings().setUseWideViewPort(true);

		_WebView.getSettings().setLoadWithOverviewMode(true);

		_WebView.getSettings().setBuiltInZoomControls(true);

		_WebView.getSettings().setDisplayZoomControls(false);

		_WebView.setWebViewClient(new WebViewClient() {

			// forces page to open in webView instead of Browser
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);

				return true;
			}
		});

		_WebView.setWebChromeClient(new WebChromeClient());

		if (isOnline())
			_WebView.loadUrl("https://webmail.hs-furtwangen.de/ox.html");
		else
			_WebView.loadData(noInternet, "text/html", null);

	}
	

	//looks for onlinestate //Redundanz WebMail <-> FelixLogin <-> Website
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
