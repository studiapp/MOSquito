package de.hfu.mos;

import java.util.ArrayList;
import java.util.Arrays;

import org.achartengine.ChartFactory;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;



public class LineGraph extends Fragment{
	
public int counter = 0;	
public ArrayList<Integer> coordinates;
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		
		if(counter == 0){
		//BACHELOR
		int[] x = {0,0,1,1,2,2,3,3,4,4};		
		TimeSeries series = new TimeSeries("Bachelor");
		for(int i = 0; i < x.length; i = i+2){
			series.add(x[i],coordinates.get(i));
		}				
		dataset.addSeries(series);		
		renderer.setColor(Color.GREEN);		
		//MASTER		
		int[] a = {0,0,1,1,2,2,3,3,4,4};
		//int[] b = {28,30,35,50,90,110};		
		TimeSeries series2 = new TimeSeries("Master");
		for(int c = 1; c < a.length; c = c+2){
			series2.add(a[c],coordinates.get(c));
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
		mRenderer.setChartTitle("Budgetrechner Studiengang Informatik XY");
		
		View rootView = inflater.inflate(R.layout.fragment_webmail, container, false);
		//rootView = ChartFactory.getLineChartIntent(budgetRechner, dataset, mRenderer, "Budgetrechner");
		

		return rootView;
}


	public LineGraph(int number, Integer...ints) {
		this.counter = number;
		this.coordinates = new ArrayList<Integer>(Arrays.asList(ints));
		
	}


}
