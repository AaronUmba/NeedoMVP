package com.infined.needomvp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class  DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseHelper";
    public static final String TABLE_NAME = "task_table";
    public static final String COL1 = "_id";
    public static final String COL2 = "name";
    public static final String COL3 = "priority";
    public static final String COL4 = "desc";
    public static final String COL5 = "date";
    public static final String COL6 = "strat";
    public static final String COL7 = "person";
    public static final String COL8 = "urgen";
    public static final String COL9 = "goal";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, " + COL3 + " INTEGER, '" + COL4 + "' TEXT, '" + COL5 + "'INTEGER, '" + COL6 + "'INTEGER, '" + COL7 + "'INTEGER, '" + COL8 + "'INTEGER, '" + COL9 + "'INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean addData(String item, String desc, int pri, int strat, int person, int urgen, int goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL4, desc);
        contentValues.put(COL3, pri);
        contentValues.put(COL6, strat);
        contentValues.put(COL7, person);
        contentValues.put(COL8, urgen);
        contentValues.put(COL9, goal);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data as inserted incorrectly it will return -1
        return result != -1;
    }
    public Cursor getBar1(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL6 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getBar2(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL7 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getBar3(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL8 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getGoal(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL9 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL3 + " DESC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = ?";
        Cursor data = db.rawQuery(query, new String[] {name});
        return data;
    }
    public Cursor getItemPriority(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = ?";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query, new String[] {oldName} );
    }
    public void updateBar1(int newBar1, int id, int oldBar1){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL6 +
                " = '" + newBar1 + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL6 + " = '" + oldBar1 + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting bar1 to " + newBar1);
        db.execSQL(query);
    }
    public void updateBar2(int newBar2, int id, int oldBar2){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL7 +
                " = '" + newBar2 + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL7 + " = '" + oldBar2 + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting bar2 to " + newBar2);
        db.execSQL(query);
    }
    public void updateBar3(int newBar3, int id, int oldBar3){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL8 +
                " = '" + newBar3 + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL8 + " = '" + oldBar3 + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting bar3 to " + newBar3);
        db.execSQL(query);
    }
    public void updatePriority(int newPriority, int id, int oldPriority){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + newPriority + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + oldPriority + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newPriority);
        db.execSQL(query);
    }
    public void updateDesc(String newDesc, int id, String oldDesc){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newDesc + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL4 + " = ?";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newDesc);
        db.execSQL(query, new String[] {oldDesc});
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = ?";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query, new String[] {name});
    }
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( TABLE_NAME , null, null);
    }
    public String[] databaseToStringArray() {
        String[] fromColumns = new String[]{COL2, COL4};
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE 1 ", null);

        if (cursor != null && cursor.getCount()>0) {
            Log.d("Event", "Records do exist");
        }
        else {
            Log.d("Event", "Records do not exist");
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cursor.moveToNext();
        }
        db.close();
        return fromColumns;
    }
}
