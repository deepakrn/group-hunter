package com.example.listtest;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private ArrayList<Course> classes=new ArrayList<Course>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		populateClassList();
		populateListView();
		registerOnClickCallback();		
	}

	private void registerOnClickCallback() {
		
		ListView view=(ListView) findViewById(R.id.classListView);
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			
			//Notification code
			Intent viewIntent = new Intent(MainActivity.this, MainActivity.class);
			//viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
			PendingIntent viewPendingIntent =
			        PendingIntent.getActivity(MainActivity.this, 0, viewIntent, 0);

			NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MainActivity.this)
			.setTicker("Ticker title").setContentTitle("Content Title")
			.setContentText("Content text")
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(viewPendingIntent);
			//Important to set the flag to close the notification on click.
			notificationBuilder.setAutoCancel(true);
			NotificationManagerCompat notificationManager =
			        NotificationManagerCompat.from(MainActivity.this);
			notificationManager.notify(0, notificationBuilder.build());
			
		}
		});
	}

	private void populateListView() {		
		ArrayAdapter<Course> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.classListView);
		list.setAdapter(adapter);		
	}
	
	private void populateClassList() {
		// TODO Auto-generated method stub
		classes.add(new Course("CSC505", "Algorithms", "Description"));
		classes.add(new Course("CSC520", "ArtificialIntelligence", "Machines"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private class MyListAdapter extends ArrayAdapter<Course>{
		
		public MyListAdapter(){
			super(MainActivity.this,R.layout.item_list,classes);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if(convertView==null){				
				itemView= getLayoutInflater().inflate(R.layout.item_list,parent,false);
			}
			Course currentClass=classes.get(position);
			TextView imageView = (TextView)itemView.findViewById(R.id.item_course);
			imageView.setText(currentClass.getName());
//			return super.getView(position, convertView, parent);
			return itemView;
		}		
	}
}

