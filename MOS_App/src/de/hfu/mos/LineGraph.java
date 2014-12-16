package de.hfu.mos;

import java.util.ArrayList;
import java.util.Arrays;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;


public class LineGraph extends Fragment{
	
public int counter = 0;	
public ArrayList<Integer> coordinates;
	
@SuppressWarnings("deprecation")
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		
		if(counter == 0){
		//BACHELOR
		int[] x = {25,25,27,27,30,30,35,35,40,40,45,45,50,50,55,55,60,60,65,65};	
		TimeSeries series = new TimeSeries("Bachelor");
		for(int i = 0; i < x.length; i = i+2){
			series.add(x[i],coordinates.get(i));
			//Linienst�rke
			renderer.setLineWidth(3f);
			renderer.setPointStyle(PointStyle.SQUARE);
			renderer.setFillPoints(true);
		}				
		dataset.addSeries(series);		
		renderer.setColor(Color.GREEN);		
		//MASTER		
		int[] a = {25,25,27,27,30,30,35,35,40,40,45,45,50,50,55,55,60,60,65,65};
		//int[] b = {28,30,35,50,90,110};		
		TimeSeries series2 = new TimeSeries("Master");
		for(int c = 1; c < a.length; c = c+2){
			series2.add(a[c],coordinates.get(c));
			//Linienst�rke
			renderer2.setLineWidth(3f);
			renderer.setChartValuesTextSize(50);
		}	
		dataset.addSeries(series2);		
		renderer2.setColor(Color.RED);		
		} else if(counter == 0){			
			//ALL BACHELOR || MASTER
			int[] x = {1,2,3,4,5,6};
			int[]y =  {30,30,30,30,30,30};		
			TimeSeries series = new TimeSeries("Bachelor");
			for(int i = 0; i < x.length; i++){
				series.add(x[i],y[i]);
			}				
			dataset.addSeries(series);			
			renderer.setColor(Color.GREEN);			
			//MASTER		
			int[] a = {1,2,3,4,5,6};
			int[] b = {50,50,50,50,50,50};		
			TimeSeries series2 = new TimeSeries("Master");
			for(int c = 0; c < a.length; c++){
				series2.add(a[c],b[c]);
			}	
			dataset.addSeries(series2);			
			renderer2.setColor(Color.RED);			
		} else {	
			System.out.println("ERROR, wrong Paramater");
		}
	
		
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.addSeriesRenderer(renderer2);
		mRenderer.setChartTitle("");
		
		//overall graph settings
		//mRenderer.setChartTitle("Test");
		mRenderer.setLabelsTextSize(20);
		mRenderer.setAxisTitleTextSize(20);
		mRenderer.setXTitle("Alter in Jahren");
		mRenderer.setYTitle("Gehalt in �");
		mRenderer.setLegendHeight(50);
		mRenderer.setLegendTextSize(20);
		
		View view = inflater.inflate(R.layout.dashboard_chart, container, false);		
		// Getting a reference to LinearLayout of the MainActivity Layout
        LinearLayout chartContainer = (LinearLayout)view.findViewById(R.id.chart_container);
        // Creating a Line Chart
        View mChart = ChartFactory.getLineChartView(getActivity().getBaseContext(), dataset, mRenderer);
        // Adding the Line Chart to the LinearLayout
        chartContainer.addView(mChart);	
		return view;
}


	public LineGraph(int number, Integer...ints) {
		this.counter = number;
		this.coordinates = new ArrayList<Integer>(Arrays.asList(ints));
		
	}


}