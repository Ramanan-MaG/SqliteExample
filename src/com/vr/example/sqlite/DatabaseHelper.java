
package com.vr.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Fruits.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Fruits";
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    interface Column {
        String NAME = "name";
        String PRICE = "price";
    }

    private String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + Column.NAME + ", "
            + Column.PRICE + " )";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*
     * CURD operations
     * 
     */
    
    // Create
    public void addFruits() {
        SQLiteDatabase db = this.getWritableDatabase();

        // sample date 
        Log.v(LOG_TAG, "Name = Apple");
        Log.v(LOG_TAG, "Name = Banana");
        Log.v(LOG_TAG, "Price = 100");
        Log.v(LOG_TAG, "Price = 40");
        ContentValues contentValues = new ContentValues();
        // row 1
        contentValues.put(Column.NAME, "Apple");
        contentValues.put(Column.PRICE, 100);
        db.insert(TABLE_NAME, null, contentValues);

        // row 2
        contentValues.put(Column.NAME, "Banana");
        contentValues.put(Column.PRICE, 40);
        db.insert(TABLE_NAME, null, contentValues);

        db.close();
    }

    // Update
    public void updateFruit() {
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Column.PRICE, 120);

        db.update(TABLE_NAME, contentValues, Column.NAME + " = ?", new String[] {"Apple"});
        db.close();
    }

    // Read
    public void getFruit() {
        SQLiteDatabase db= this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
//        db.query(true, TABLE_NAME, null, null, null, null, null, null, null, null);
        Cursor cursor = db.rawQuery(query, null);
        if ( cursor.moveToFirst() ) {
            Log.v(LOG_TAG, "Name = " + cursor.getString(cursor.getColumnIndex(Column.NAME)));
            Log.v(LOG_TAG, "Price = " + cursor.getString(cursor.getColumnIndex(Column.PRICE)));
        }
        db.close();
    }
    
 // Read
    public void deleteFruit() {
        SQLiteDatabase db= this.getWritableDatabase();

        db.delete(TABLE_NAME, Column.NAME + " = ?", new String[] {"Apple"});
        db.close();
    }
}
