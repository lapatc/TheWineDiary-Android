package com.wineo.thewinediary.winevault.database;

import com.wineo.thewinediary.winevault.model.Color;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WineDatabaseHelper extends SQLiteOpenHelper {
	// Database version
	private static final int DATABASE_VERSION = 1;
	
	// Database name
	private static final String DATABASE_NAME = "WINESDB";
	
	// Table Names
	private static final String TABLE_COLOR = "color";
	private static final String TABLE_WINERY = "winery";
	private static final String TABLE_WINERY_LOCATION = "winery_location";
	private static final String TABLE_WINE_GRAPE = "wine_grape";
	private static final String TABLE_WINE = "wine";
	
	// Common column names
	private static final String KEY_ID = "_id";
	private static final String KEY_NAME = "name";
	private static final String KEY_COLOR = "color";
	
	// Winery column names
	private static final String KEY_LOCATION_ID = "location_id";
	
	// WineryLocation column names
	private static final String KEY_COUNTRY = "country";
	private static final String KEY_STATE_PROV = "state_prov";
	private static final String KEY_TOWN = "town";
	
	// WineGrape column names
	private static final String KEY_WINE_ID = "wine_id";
	
	// Wine column names
	private static final String KEY_YEAR = "year";
	private static final String KEY_SPARKLING = "sparkling";
	private static final String KEY_ALCOHOL = "alcohol";
	private static final String KEY_DATE_TASTED = "date_tasted";
	private static final String KEY_WINERY_ID = "winery_id";
	private static final String KEY_LABEL_FRONT = "label_front";
	private static final String KEY_LABEL_BACK = "label_back";
	private static final String KEY_TASTING_NOTE = "tasting_note";
	private static final String KEY_PERSONAL_NOTE = "personal_note";
	
	// table create statements
	// Color table create statement
	private static final String CREATE_TABLE_COLOR = "CREATE TABLE "
			+ TABLE_COLOR + "(" + KEY_COLOR + " TEXT PRIMARY KEY)";
	
	// WineryLocation table create statement
	private static final String CREATE_TABLE_WINERY_LOCATION = "CREATE TABLE "
			+ TABLE_WINERY_LOCATION + "(" + KEY_ID + " INTEGER PRIMARY KEY, "
			+ KEY_COUNTRY + " TEXT," + KEY_STATE_PROV + " TEXT," + KEY_TOWN + " TEXT)";
	
	// Winery table create statement
	private static final String CREATE_TABLE_WINERY = "CREATE TABLE "
			+ TABLE_WINERY + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_NAME + " TEXT NOT NULL," + KEY_LOCATION_ID + " INTEGER,"
			+ "FOREIGN KEY(" + KEY_LOCATION_ID + ") REFERENCES "
			+ TABLE_WINERY_LOCATION + "(" + KEY_ID + ")" + ")";
	
	// WineGrape table create statement
	private static final String CREATE_TABLE_WINE_GRAPE = "CREATE TABLE "
			+ TABLE_WINE_GRAPE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_NAME + " TEXT NOT NULL," + KEY_WINE_ID + " INTEGER NOT NULL,"
			+ "FOREIGN KEY (" + KEY_WINE_ID + ") REFERENCES "
			+ TABLE_WINE + "(" + KEY_ID + ")" + ")";
	
	// Wine table create statement
	private static final String CREATE_TABLE_WINE = "CREATE TABLE "
			+ TABLE_WINE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_YEAR + " TEXT," + KEY_SPARKLING + " INTEGER,"
			+ KEY_ALCOHOL + " INTEGER," + KEY_NAME + " TEXT NOT NULL,"
			+ KEY_DATE_TASTED + " NUMERIC," + KEY_WINERY_ID + " INTEGER,"
			+ KEY_COLOR + " TEXT NOT NULL," + KEY_LABEL_FRONT + " BLOB,"
			+ KEY_LABEL_BACK + " BLOB," + KEY_TASTING_NOTE + " TEXT,"
			+ KEY_PERSONAL_NOTE + " TEXT,"
			+ "FOREIGN KEY (" + KEY_WINERY_ID + ") REFERENCES "
			+ TABLE_WINERY + "(" + KEY_ID + "),"
			+ "FOREIGN KEY (" + KEY_COLOR + ") REFERENCES "
			+ TABLE_COLOR + "(" + KEY_COLOR + ")"
			+ ")";
	
	public WineDatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create tables in necessary order
		db.execSQL(CREATE_TABLE_COLOR);
		db.execSQL(CREATE_TABLE_WINERY_LOCATION);
		db.execSQL(CREATE_TABLE_WINERY);
		db.execSQL(CREATE_TABLE_WINE);
		db.execSQL(CREATE_TABLE_WINE_GRAPE);

		this.initializeColorTable(db);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		this.dropTable(db, TABLE_COLOR);
		this.dropTable(db, TABLE_WINE);
		this.dropTable(db,TABLE_WINE_GRAPE);
		this.dropTable(db, TABLE_WINERY);
		this.dropTable(db, TABLE_WINERY_LOCATION);
		
		this.onCreate(db);
		this.addMockData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop old tables
		this.dropTable(db, TABLE_COLOR);
		this.dropTable(db, TABLE_WINE);
		this.dropTable(db,TABLE_WINE_GRAPE);
		this.dropTable(db, TABLE_WINERY);
		this.dropTable(db, TABLE_WINERY_LOCATION);
		
		// Create new tables
		this.onCreate(db);
	}
	
	// convenience method to drop a table
	private void dropTable(SQLiteDatabase db, String tableName){
		db.execSQL("DROP TABLE IF EXISTS " + tableName);
	}
	

	
	public void initializeColorTable(SQLiteDatabase db){
		ContentValues colorValue = new ContentValues();
		
		colorValue.put(KEY_COLOR, Color.RED.name());
		db.insert(TABLE_COLOR, null, colorValue);
		
		colorValue.put(KEY_COLOR, Color.ROSE.name());
		db.insert(TABLE_COLOR, null, colorValue);
		
		colorValue.put(KEY_COLOR, Color.WHITE.name());
		db.insert(TABLE_COLOR, null, colorValue);
	}
	
	/*
	 * Returns a Cursor object containing data on the wine id, wine name, sparkling, color, year,
	 * the winery name, and location (country, state, and town)
	 */
	public Cursor getBasicWineData(){
		SQLiteDatabase db = this.getReadableDatabase();
		
		String selectQuery = "SELECT wine." + KEY_ID + ", wine." + KEY_NAME + " as wine_name, wine." + KEY_YEAR + ", wine." + KEY_COLOR
				+ ", wine." + KEY_SPARKLING + ", winery." + KEY_NAME + " as winery_name"
				+ ", winery_location." + KEY_COUNTRY + ", winery_location." + KEY_STATE_PROV + ", winery_location." + KEY_TOWN				
				+ " FROM " + TABLE_WINE
				+ " LEFT OUTER JOIN " + TABLE_WINERY + " ON ("
				+ TABLE_WINE + "." + KEY_WINERY_ID + "="
				+ TABLE_WINERY + "." + KEY_ID + ")"
				+ " LEFT OUTER JOIN " + TABLE_WINERY_LOCATION + " ON ("
				+ TABLE_WINERY + "." + KEY_LOCATION_ID + "="
				+ TABLE_WINERY_LOCATION + "." + KEY_ID + ")";
		
		
		Cursor c = db.rawQuery(selectQuery, null);		
		
		return c;
	}
	
	
	public void addMockData(SQLiteDatabase db){
		
		ContentValues values = new ContentValues();
		
		/* wineryLocation */
		values.put(KEY_ID, 1);
		values.put(KEY_COUNTRY, "ITALY");//
		values.put(KEY_STATE_PROV, "VENETO");
		db.insert(TABLE_WINERY_LOCATION, null, values);
		values.clear();
		
		values.put(KEY_ID, 2);
		values.put(KEY_COUNTRY, "USA");
		values.put(KEY_STATE_PROV, "CALIFORNIA");
		values.put(KEY_TOWN, "TEMECULA");
		db.insert(TABLE_WINERY_LOCATION, null, values);
		values.clear();
		
		values.put(KEY_ID, 3);
		values.put(KEY_COUNTRY, "USA");
		values.put(KEY_STATE_PROV, "CALIFORNIA");
		values.put(KEY_TOWN, "NAPA VALLEY");
		db.insert(TABLE_WINERY_LOCATION, null, values);
		values.clear();
		
		
		/* winery */
		values.put(KEY_ID, 1);
		values.put(KEY_NAME, "WINERY1");
		values.put(KEY_LOCATION_ID, 2);
		db.insert(TABLE_WINERY, null, values);
		values.clear();
		
		values.put(KEY_ID, 2);
		values.put(KEY_NAME, "WINERY2");
		values.put(KEY_LOCATION_ID, 1);
		db.insert(TABLE_WINERY, null, values);
		values.clear();
		
		
		/* wine */
		values.put(KEY_NAME, "WINE1");
		values.put(KEY_COLOR, "RED");
		values.put(KEY_WINERY_ID, 1);
		db.insert(TABLE_WINE, null, values);
		values.clear();
		
		values.put(KEY_NAME, "WINE2");
		values.put(KEY_COLOR, "WHITE");
		values.put(KEY_WINERY_ID, 2);
		db.insert(TABLE_WINE, null, values);
		values.clear();
		
		values.put(KEY_NAME, "WINE3");
		values.put(KEY_COLOR, "ROSE");
		db.insert(TABLE_WINE, null, values);
		values.clear();
	}

}
