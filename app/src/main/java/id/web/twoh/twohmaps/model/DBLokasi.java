package id.web.twoh.twohmaps.model;

import java.io.Serializable;


public class DBLokasi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -151607691806704480L;
	private long id;
	private String lat;
	private String lng;
	private String nama;	
	private double latD;
	private double lngD;
	
	public DBLokasi()
	{
		
	}
	
	public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return "("+id+")"+" Nama : "+nama+" "+" ("+lat+" , "+lng+")";
	  }

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}
	
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public double getLatD() {
		latD = Double.parseDouble(lat);
		return latD;
	}

	public void setLatD(double latD) {
		this.latD = latD;
	}

	public double getLngD() {
		lngD = Double.parseDouble(lng);
		return lngD;
	}

	public void setLngD(double lngD) {
		this.lngD = lngD;
	}
}
