package com.example.spoon;

import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final String TAG = "mark";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ProfilesDB.db";
    public static final String TABLE_PROFILES = "profiles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_AGE = "_age";
    public static final String COLUMN_GENDER = "_gender";
    public static final String TABLE_DATA = "DATA";
    public static final String COLUMN_DAY = "_day";
    public static final String COLUMN_MONTH = "_month";
    public static final String COLUMN_YEAR = "_year";
    public static final String COLUMN_ENERGY = "energy";
    public static final String COLUMN_PROTEIN = "protein";
    public static final String COLUMN_CARBS = "carbs";
    public static final String COLUMN_FAT = "fat";
    public static final String COLUMN_FIBRE = "fibre";
    public static final String COLUMN_SODIUM = "sodium";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_PROFILES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_GENDER + " TEXT " +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_DATA + "(" +
                COLUMN_ID + " INTEGER , " +
                COLUMN_DAY + " INTEGER , " +
                COLUMN_MONTH + " INTEGER , " +
                COLUMN_YEAR + " INTEGER , " +
                COLUMN_ENERGY + " INTEGER, " +
                COLUMN_PROTEIN + " INTEGER, " +
                COLUMN_CARBS + " INTEGER, " +
                COLUMN_FAT + " INTEGER, " +
                COLUMN_FIBRE + " INTEGER, " +
                COLUMN_SODIUM + " INTEGER, " +
                " PRIMARY KEY ("+COLUMN_ID+","+COLUMN_DAY+","+ COLUMN_MONTH+","+ COLUMN_YEAR+")"+
                //" FOREIGN KEY (" + COLUMN_ID + ") REFERENCES " + TABLE_PROFILES + "(" + COLUMN_ID + ")" +
                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        //onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

    public long addProfile(Profile profile) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, profile.get_name());
        values.put(COLUMN_AGE, profile.get_age());
        values.put(COLUMN_GENDER, profile.get_gender());
        SQLiteDatabase db = getWritableDatabase();
        long n=db.insert(TABLE_PROFILES, null, values);
        return n;
    }

    public long addData(Data data) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, data.getId());
        values.put(COLUMN_DAY, data.getDay());
        values.put(COLUMN_MONTH, data.getMonth());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_ENERGY, data.getEnergy());
        values.put(COLUMN_PROTEIN, data.getProtein());
        values.put(COLUMN_CARBS, data.getCarbs());
        values.put(COLUMN_FAT, data.getFat());
        values.put(COLUMN_FIBRE, data.getFibre());
        values.put(COLUMN_SODIUM, data.getSodium());
        SQLiteDatabase db = getWritableDatabase();
        long n =db.insert(TABLE_DATA, null, values);
        return n;
    }

    public void updateData(Data data, int energy, int protein, int carbs, int fat, int fibre, int sodium) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENERGY, energy);
        values.put(COLUMN_PROTEIN, protein);
        values.put(COLUMN_CARBS, carbs);
        values.put(COLUMN_FAT, fat);
        values.put(COLUMN_FIBRE, fibre);
        values.put(COLUMN_SODIUM, sodium);
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_DATA, values, "_id=" + data.getId() +
                " AND _day=" + Integer.toString(data.getDay()) +
                " AND _month=" + Integer.toString(data.getMonth()) +
                " AND _year=" + Integer.toString(data.getYear()), null);
    }

    public ArrayList<Profile> getProfiles()
    {
        ArrayList<Profile> profiles = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PROFILES+";";
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            Profile p = new Profile();
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("_id")) != null) {
                p.set_id(recordSet.getInt(recordSet.getColumnIndex("_id")));
            }
            if (recordSet.getString(recordSet.getColumnIndex("_name")) != null) {
                p.set_name(recordSet.getString(recordSet.getColumnIndex("_name")));
            }
            if (recordSet.getString(recordSet.getColumnIndex("_age")) != null) {
                p.set_age(recordSet.getInt(recordSet.getColumnIndex("_age")));
            }
            if (recordSet.getString(recordSet.getColumnIndex("_gender")) != null) {
                p.set_gender(recordSet.getString(recordSet.getColumnIndex("_gender")));
            }
            profiles.add(p);
            recordSet.moveToNext();
        }
        return profiles;
    }

    public ArrayList<Data> getData()
    {
        ArrayList<Data> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_DATA;// + " WHERE " +COLUMN_ID+ "= "+ Integer.toString(id) ;
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            Data d = new Data();
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("_id")) != null) {
                d.setId(recordSet.getInt(recordSet.getColumnIndex("_id")));
            }
            if (recordSet.getString(recordSet.getColumnIndex("_day")) != null) {
                d.setDay(recordSet.getInt(recordSet.getColumnIndex("_day")));
            }
            if (recordSet.getString(recordSet.getColumnIndex("_month")) != null) {
                d.setMonth(recordSet.getInt(recordSet.getColumnIndex("_month")));
            }
            if (recordSet.getString(recordSet.getColumnIndex("_year")) != null) {
                d.setYear(recordSet.getInt(recordSet.getColumnIndex("_year")));
            }
            if (recordSet.getString(recordSet.getColumnIndex("energy")) != null) {
               d.setEnergy(recordSet.getInt(recordSet.getColumnIndex("energy")));
            }
            if (recordSet.getString(recordSet.getColumnIndex("protein")) != null) {
                d.setProtein(recordSet.getInt(recordSet.getColumnIndex("protein")));
            }
            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_CARBS)) != null) {
                d.setCarbs(recordSet.getInt(recordSet.getColumnIndex(COLUMN_CARBS)));
            }
            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_FIBRE)) != null) {
                d.setFibre(recordSet.getInt(recordSet.getColumnIndex(COLUMN_FIBRE)));
            }
            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_FAT)) != null) {
                d.setFat(recordSet.getInt(recordSet.getColumnIndex(COLUMN_FAT)));
            }
            if (recordSet.getString(recordSet.getColumnIndex(COLUMN_SODIUM)) != null) {
                d.setSodium(recordSet.getInt(recordSet.getColumnIndex(COLUMN_SODIUM)));
            }
            data.add(d);
            recordSet.moveToNext();
        }
        return data;
    }
}
