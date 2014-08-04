package id.web.twoh.twohmaps;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import id.web.twoh.twohmaps.R;

public class MainActivity extends ActionBarActivity {

	protected LocationManager locationManager;
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 10; // dalam Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 60000; // dalam Milliseconds
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        locationManager.requestLocationUpdates(
	                LocationManager.GPS_PROVIDER, 
	                MINIMUM_TIME_BETWEEN_UPDATES, 
	                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
	                new MyLocationListener()
	        );
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
	
	protected void showCurrentLocation() {
    	Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            String message = String.format(
                    "Lokasi saat ini \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(this, message,
                    Toast.LENGTH_LONG).show();
            }
        
    }   

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "Deteksi Lokasi Baru \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            //switchToMap();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(MainActivity.this, "Status provider berubah",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(MainActivity.this,
                    "Provider dinonaktifkan oleh user, GPS off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(MainActivity.this,
                    "Provider diaktifkan oleh user, GPS on",
                    Toast.LENGTH_LONG).show();
        }

    }

	public static class PlaceholderFragment extends Fragment {

		private Button btMaps;
		private Button btCheckIn;	
		private Button btGetLocation;
		Location location;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			btMaps = (Button)rootView.findViewById(R.id.btViewOnMap);
			btMaps.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {		
					switchToMap();
				}
			});
			btCheckIn = (Button)rootView.findViewById(R.id.btCheckIn);
			btCheckIn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					switchToCheckIn();					
				}
			});
			btGetLocation = (Button) rootView.findViewById(R.id.btGetLocation);
			btGetLocation.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showCurrentLocation();
					
				}
			});
			return rootView;
		}
				
		@Override
		public void onActivityCreated(Bundle savedInstanceState) { 
			super.onCreate(savedInstanceState);
			location = ((MainActivity)getActivity()).locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(location==null)
			{
				location = ((MainActivity)getActivity()).locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
		}
		
		private void switchToMap()
		{						
	    	Intent i = new Intent(getActivity(), MapsActivity.class);
	    	Bundle b = new Bundle();
	    	if(location!=null)
	    	{
	    	b.putDouble("longitude", location.getLongitude());
	    	b.putDouble("latitude", location.getLatitude());
	    	Log.v("info", "The lat "+location.getLatitude());
	        Log.v("info", "The lng "+location.getLongitude());
	        i.putExtras(b);
	    	startActivity(i);
	    	}else
	    	{
	    		Toast.makeText(getActivity(), "Nyalakan lokasimu", Toast.LENGTH_LONG).show();
	    	}
		}
		
		public void switchToCheckIn()
	    {
	    
	    	Intent i = new Intent(getActivity(), CheckInActivity.class);
	    	Bundle b = new Bundle();
	    	if(location!=null)
	    	{
	    	b.putDouble("longitude", location.getLongitude());
	    	b.putDouble("latitude", location.getLatitude());
	    	Log.v("info", "The lat "+location.getLatitude());
	        Log.v("info", "The lng "+location.getLongitude());
	        i.putExtras(b);
	    	startActivity(i);
	    	}else
	    	{
	    		Toast.makeText(getActivity(), "Nyalakan lokasimu", Toast.LENGTH_LONG).show();
	    	}
	    }
		
		protected void showCurrentLocation() {	    	
	        if (location != null) {
	            String message = String.format(
	                    "Lokasi saat ini \n Longitude: %1$s \n Latitude: %2$s",
	                    location.getLongitude(), location.getLatitude()
	            );
	            Toast.makeText(getActivity(), message,
	                    Toast.LENGTH_LONG).show();
	            }
	        
	    }  
	}

}
