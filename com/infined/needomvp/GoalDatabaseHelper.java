package com.infined.needomvp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class  GoalDatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "GoalDatabaseHelper";
    public static final String TABLE_NAME_G = "goal_table";
    public static final String COL1 = "_id";
    public static final String COL2 = "name";
    public static final String COL3 = "priority";
    public static final String COL4 = "selected";

    public GoalDatabaseHelper(Context context) {
        super(context, TABLE_NAME_G, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME_G + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, '" +
                COL2 + "' TEXT ,'" + COL3 + "' INTEGER ,'" + COL4 + "' INTEGER DEFAULT 0)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_G);
        onCreate(db);
    }
    public boolean addData(String name, int pri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, pri);
        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME_G);
        long result = db.insert(TABLE_NAME_G, null, contentValues);

        //if data as inserted incorrectly it will return -1
        return result != -1;

    }
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME_G +
                " WHERE " + COL2 + " = ?";
        Cursor data = db.rawQuery(query, new String[]{name});
        return data;
    }
    public Cursor getSelect(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL4 + " FROM " + TABLE_NAME_G +
                " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME_G +
                " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void updatePriority(int newPriority, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_G + " SET " + COL3 +
                " = '" + newPriority + "' WHERE " + COL1 + " = '" + id + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newPriority);
        db.execSQL(query);
    }
    public void updateSelected(int choice, int id, int oldChoice){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_G + " SET " + COL4 +
                " = '" + choice + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL4 + " = '" + oldChoice + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + choice);
        db.execSQL(query);
    }
    public Cursor getGoalPriority(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME_G +
                " WHERE " + COL1 + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_G + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = ?";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query, new String[] {name});
    }
    public String[] databaseToStringArray() {
        String[] fromColumns = new String[]{COL2};
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_G + " WHERE 1 ", null);

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
