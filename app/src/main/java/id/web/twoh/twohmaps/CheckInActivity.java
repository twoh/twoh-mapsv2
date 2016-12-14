package id.web.twoh.twohmaps;

import java.util.ArrayList;

import id.web.twoh.twohmaps.R;
import id.web.twoh.twohmaps.database.DBDataSource;
import id.web.twoh.twohmaps.model.DBLokasi;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckInActivity extends ListActivity implements OnItemLongClickListener{

	private TextView tvCheckin;
	private DBDataSource dataSource;
	private Button btCheck;
	private Button btShowAll;
	private Button btDelete;
	private double lat,lng;
	private ArrayList<DBLokasi> values;
	private ArrayAdapter<DBLokasi> adapter;
	ListView lv;
	public static final String TAG = CheckInActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkin);
		
		dataSource = new DBDataSource(this);
		dataSource.open();
		values = dataSource.getAllLokasi();
		
		btCheck = (Button)findViewById(R.id.bt_checkin);
		btDelete = (Button) findViewById(R.id.bt_delete);
		btShowAll = (Button)findViewById(R.id.bt_showmap);
		tvCheckin = (TextView) findViewById(R.id.textcheckin);
		
		btDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dataSource.deleteAllLokasi();
				adapter.clear();
				adapter.notifyDataSetChanged();
				Toast.makeText(CheckInActivity.this, "All data deleted", Toast.LENGTH_SHORT).show();
			}
		});
		
		btCheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(CheckInActivity.this, "checked in", Toast.LENGTH_LONG);
				final Dialog dialog = new Dialog(CheckInActivity.this);
				dialog.setTitle("Input Data Checkin");				 
				dialog.setContentView(R.layout.fragment_dialog_checkin);				 
				TextView text = (TextView)dialog.findViewById(R.id.tv_koordinat);
				Button btSave =  (Button) dialog.findViewById(R.id.bt_checkin_save);
				Button btCancel = (Button) dialog.findViewById(R.id.bt_checkin_cancel);
				final EditText etNama = (EditText) dialog.findViewById(R.id.et_nama);				
				text.setText("Masukkan data check-in di koordinat ("+lat+","+lng+") : ");
				
				btCancel.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						dialog.cancel();						
					}
				});
				btSave.setOnClickListener(new OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						DBLokasi lokasi = null;
						DBLokasi lokasiTemp = new DBLokasi();
						lokasiTemp.setLat(lat+"");
						lokasiTemp.setLng(lng+"");
						
						lokasiTemp.setNama(etNama.getText().toString());						
						
						lokasi = dataSource.createLokasi(lokasiTemp);
						if(lokasi!=null)
						{
							adapter.add(lokasi);
							adapter.notifyDataSetChanged();
						}						
						dialog.cancel();
					}
				});
				dialog.show();				
			}
		});
		
		btShowAll.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(values.size()>0)
				{
					startActivity(new Intent(CheckInActivity.this,MapsActivity.class).putExtra("arraylokasi", values));
				}
				
			}
		});
		
		Bundle b = this.getIntent().getExtras();
		lat = b.getDouble("latitude");
		lng = b.getDouble("longitude");
		tvCheckin.setText("Ingin check-in di koordinat berikut ? "+lat+","+lng);
	
		dataSource = new DBDataSource(this);
		dataSource.open();
		
		values = dataSource.getAllLokasi();
		
		adapter = new ArrayAdapter<DBLokasi>(this,R.layout.layout_checkin_item, values);
		setListAdapter(adapter);
		lv = getListView();		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
					DBLokasi lokTemp = adapter.getItem(position);
					dataSource.deleteLokasi(lokTemp);
					Toast.makeText(CheckInActivity.this, "location has been deleted", Toast.LENGTH_SHORT).show();
					adapter.remove(lokTemp);
					adapter.notifyDataSetChanged();
				
									
				return true;
			}
		});
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		DBLokasi lokasi = dataSource.getLokasi((int)adapter.getItem(position).getId());
		if(lokasi!=null)
			startActivity(new Intent(this, MapsActivity.class).putExtra("lokasi", lokasi));
		else
			Toast.makeText(this, "location is null", Toast.LENGTH_LONG).show();
	}

	
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		DBLokasi lokasi = dataSource.getLokasi(position+1);
		if(lokasi!=null)
		{
			dataSource.deleteLokasi(lokasi);
			Toast.makeText(CheckInActivity.this, "location has been deleted", Toast.LENGTH_SHORT).show();
			adapter.remove(lokasi);
			adapter.notifyDataSetChanged();
		}else
			Toast.makeText(CheckInActivity.this, "location is null", Toast.LENGTH_SHORT).show();
			
		return false;
	}
		
	
}
