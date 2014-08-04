package id.web.twoh.twohmaps.database;

import id.web.twoh.twohmaps.model.DBLokasi;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBDataSource {

  // Inisialisasi database fields
  private SQLiteDatabase database;
  private DBMapsHelper dbHelper;
  
  // Ambil konstanta
  private String[] allColumns = { DBMapsHelper.COLUMN_ID,
      DBMapsHelper.COLUMN_LAT, DBMapsHelper.COLUMN_LONG, DBMapsHelper.COLUMN_NAMA,DBMapsHelper.COLUMN_SEX, DBMapsHelper.COLUMN_DOB,DBMapsHelper.COLUMN_GROUP,DBMapsHelper.COLUMN_INFO_1,DBMapsHelper.COLUMN_INFO_2 };
  
  // Menggunakan DBMapsHelper yang diiinisialisasi pada konstruktor 
  public DBDataSource(Context context) {
    dbHelper = new DBMapsHelper(context);
  }

  // Mengambil sebuah database yang bisa digunakan
  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  // Method yang berfungsi untuk membuat lokasi baru dan memasukkannya ke dalam database
  public DBLokasi createLokasi(DBLokasi lokasi) {
    ContentValues values = new ContentValues();    
    values.put(DBMapsHelper.COLUMN_LAT, lokasi.getLat());
    values.put(DBMapsHelper.COLUMN_LONG, lokasi.getLng());
    values.put(DBMapsHelper.COLUMN_GROUP, lokasi.getGroup());
    values.put(DBMapsHelper.COLUMN_DOB, lokasi.getDob());
    values.put(DBMapsHelper.COLUMN_NAMA, lokasi.getNama());
    values.put(DBMapsHelper.COLUMN_SEX, lokasi.getSex());
    values.put(DBMapsHelper.COLUMN_INFO_1, lokasi.getInfo1());
    values.put(DBMapsHelper.COLUMN_INFO_2, lokasi.getInfo2());
    long insertId = database.insert(DBMapsHelper.TABLE_NAME, null,
        values);
    Cursor cursor = database.query(DBMapsHelper.TABLE_NAME,
        allColumns, DBMapsHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    DBLokasi newLokasi = cursorToLokasi(cursor);
    cursor.close();

    return newLokasi;
  }
  
  // Method yang berfungsi untuk menghapus lokasi berdasarkan ID
  public void deleteLokasi(DBLokasi lokasi) {
    long id = lokasi.getId();
    System.out.println("Lokasi deleted with id: " + id);
    database.delete(DBMapsHelper.TABLE_NAME, DBMapsHelper.COLUMN_ID
        + " = " + id, null);
  }
  
  public void deleteAllLokasi()
  {
	  database.execSQL("DELETE FROM "+ DBMapsHelper.TABLE_NAME);
  }

  // Method yang berfungsi untuk mengambil semua lokasi
  public ArrayList<DBLokasi> getAllLokasi() {
    ArrayList<DBLokasi> daftarLokasi = new ArrayList<DBLokasi>();

    Cursor cursor = database.query(DBMapsHelper.TABLE_NAME,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      DBLokasi lokasi = cursorToLokasi(cursor);
      daftarLokasi.add(lokasi);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return daftarLokasi;
  }

  public DBLokasi getLokasi(int id)
  {
	  DBLokasi lokasi = new DBLokasi();
	  
	  Cursor cursor = database.query(DBMapsHelper.TABLE_NAME, allColumns, "_id ="+id, null, null, null, null);
	  cursor.moveToFirst();
	  lokasi = cursorToLokasi(cursor);
	  cursor.close();
	  return lokasi;
  }
  
  // Method yang berfungsi untuk membuat sebuah objek lokasi baru yang nantinya akan dimasukkan ke dalam database
  private DBLokasi cursorToLokasi(Cursor cursor) {
	  
	  DBLokasi lokasi = new DBLokasi();
	  Log.v("info", "The getLONG "+cursor.getLong(0));
      Log.v("info", "The setLatLng "+cursor.getString(1)+","+cursor.getString(2)+cursor.getString(3)+cursor.getString(4)+cursor.getString(5)+cursor.getString(6)+cursor.getString(7));
	  lokasi.setId(cursor.getLong(0));
	  lokasi.setLat(cursor.getString(1));
	  lokasi.setLng(cursor.getString(2));
	  lokasi.setNama(cursor.getString(3));
	  lokasi.setSex(cursor.getString(4));
	  lokasi.setDob(cursor.getString(5));
	  lokasi.setGroup(cursor.getString(6));
	  lokasi.setInfo1(cursor.getString(7));
	  lokasi.setInfo2(cursor.getString(8));
	  return lokasi;
  }
} 