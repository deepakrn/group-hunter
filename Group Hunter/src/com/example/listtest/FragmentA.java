package com.example.listtest;

import java.util.ArrayList;



import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentA extends Fragment {
	
	private ArrayList<Course> classes=new ArrayList<Course>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		populateClassList();
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {		
		
		
		
		return inflater.inflate(R.layout.fragment_a, container,false);
	} 
	private void registerOnClickCallback() {
		// TODO Auto-generated method stub
		ListView view=(ListView) getActivity().findViewById(R.id.classListView);
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			
			//Notification code
			Intent viewIntent = new Intent(getActivity(), MainActivity.class);
			//viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
			PendingIntent viewPendingIntent =
			        PendingIntent.getActivity(getActivity(), 0, viewIntent, 0);

			NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity())
			.setTicker("Ticker title").setContentTitle("Content Title")
			.setContentText("Content text")
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(viewPendingIntent);
			//Important to set the flag to close the notification on click.
			notificationBuilder.setAutoCancel(true);
			NotificationManagerCompat notificationManager =
			        NotificationManagerCompat.from(getActivity());
			notificationManager.notify(0, notificationBuilder.build());
			
		}
		});
		
	}
	private void populateListView() {
		// TODO Auto-generated method stub
		ArrayAdapter<Course> adapter = new MyListAdapter();
		ListView list = (ListView) getActivity().findViewById(R.id.classListView);
		list.setAdapter(adapter);	
		
	}
	private void populateClassList() {
		// TODO Auto-generated method stub
		classes.add(new Course("CSC505", "Algorithms", "Description"));
		classes.add(new Course("CSC520", "ArtificialIntelligence", "Machines"));
		
	}
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        populateListView();
		registerOnClickCallback();
	}
private class MyListAdapter extends ArrayAdapter<Course>{
		
		public MyListAdapter(){
			super(getActivity(),R.layout.item_list,classes);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if(convertView==null){				
				itemView= getActivity().getLayoutInflater().inflate(R.layout.item_list,parent,false);
			}
			Course currentClass=classes.get(position);
			TextView imageView = (TextView)itemView.findViewById(R.id.item_course);
			imageView.setText(currentClass.getId());
//			return super.getView(position, convertView, parent);
			return itemView;
		}		
	}
	
}
