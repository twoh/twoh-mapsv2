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
	private String dob;
	private String sex;
	private String group;
	private String info1;
	private String info2;
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
	    return "Lokasi ke "+id+" ("+lat+" , "+lng+")";
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
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
