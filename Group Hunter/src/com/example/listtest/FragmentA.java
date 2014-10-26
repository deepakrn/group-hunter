package com.example.listtest;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentA extends Fragment implements OnItemSelectedListener,OnQueryTextListener {
	
	private ArrayList<Course> classes=new ArrayList<Course>();
	private ArrayList<Course> dup;
	SearchView s;
	MyListAdapter adapter;
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
		adapter = new MyListAdapter();
		ListView list = (ListView) getActivity().findViewById(R.id.classListView);
		list.setAdapter(adapter);	
		
	}
	private void populateClassList() {
		// TODO Auto-generated method stub
		classes.add(new Course("CSC-505", "Algorithms", "deepak"));
		classes.add(new Course("CSC-520", "ArtificialIntelligence", "neha"));
		classes.add(new Course("CSC-501", "Operating Systems", "joe"));
		classes.add(new Course("CSC-502", "HCI", "harry"));
		classes.add(new Course("CSC-505", "Game AI", "alice"));
	
		dup=(ArrayList<Course>)classes.clone();
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
        spinner.setOnItemSelectedListener(this);
         s = (SearchView) view.findViewById(R.id.searchView);
         s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				 Filter f=FragmentA.this.adapter.getFilter();
				f.filter(query);	
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				Filter f=FragmentA.this.adapter.getFilter();
				f.filter(newText);
				return false;
			}
		});
  
        populateListView();
		registerOnClickCallback();
	}
	
private class MyListAdapter extends ArrayAdapter<Course>{
		private Filter filter;
		private ArrayList<Course> filteredModelItemsArray=new ArrayList<Course>();
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
			TextView textView = (TextView)itemView.findViewById(R.id.course_name);
			textView.setText(currentClass.getId());
			TextView topic = (TextView)itemView.findViewById(R.id.course_description);
			topic.setText(currentClass.getDescription());
			TextView  desc= (TextView)itemView.findViewById(R.id.course_topic);
			desc.setText(currentClass.getName());			
			return itemView;
		}
		@Override
		public Filter getFilter() {
		// TODO Auto-generated method stub
			if (filter == null){
		          filter = new Filter(){
		        	@Override
		        	protected FilterResults performFiltering(
		        			CharSequence constraint) {
		        		constraint = constraint.toString().toLowerCase();
		        		if(constraint.equals("select class"))
		        			constraint=null;
		                FilterResults result = new FilterResults();
		                if(constraint != null && constraint.toString().length() > 0)
		                {
		                    ArrayList<Course> filteredItems = new ArrayList<Course>();
		                   
		                    for(int i = 0, l = dup.size(); i < l; i++)
		                    {
		                        Course m = dup.get(i);
		                        if(m.getId().toLowerCase().contains(constraint)||m.getName().toLowerCase().contains(constraint)||m.getDescription().toLowerCase().contains(constraint))
		                            filteredItems.add(m);
		                    }
		                    result.count = filteredItems.size();
		                    result.values = filteredItems;
		                }
		                else
		                {
		                    synchronized(this)
		                    {
		                        result.values = dup;
		                        result.count = dup.size();
		                    }
		                }
		                return result;
		        	}
					@Override
					protected void publishResults(CharSequence constraint,
							FilterResults results) {
						filteredModelItemsArray = (ArrayList<Course>)results.values;					       
				        clear();
				        for(int i = 0, l = filteredModelItemsArray.size(); i < l; i++)
				            add(filteredModelItemsArray.get(i));
				        notifyDataSetInvalidated();						
					}					
				};
		  }
		  return filter;
		        
		}
	}

@Override
public void onItemSelected(AdapterView<?> parent, View view, int position,
		long id) {
	// TODO Auto-generated method stub
	
	 Filter f = adapter.getFilter();
	 f.filter(parent.getItemAtPosition(position).toString().toLowerCase());
	 
}

@Override
public void onNothingSelected(AdapterView<?> parent) {
	// TODO Auto-generated method stub
	
}

@Override
public boolean onQueryTextChange(String arg0) {
		Filter f=adapter.getFilter();
		f.filter(arg0);	
	return false;
}
@Override
public boolean onQueryTextSubmit(String arg0) {
	Filter f=adapter.getFilter();
	f.filter(arg0);	
	return false;
}
}
	




