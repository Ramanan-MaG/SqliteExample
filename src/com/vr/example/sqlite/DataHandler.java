package com.vr.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vr.example.sqlite.DatabaseHelper.Column;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Fruits.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Fruits";
    private static final String LOG_TAG = DataHandler.class.getSimpleName();

    private String DB_PATH;
    private Context mContext;

    public DataHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void createDataBase() {
        if (!isDbFileExist()) {
            this.getReadableDatabase();
            copyDbFile();
        }
    }

    private boolean isDbFileExist() {
        SQLiteDatabase database = null;
        String dbPath = DB_PATH + "/"+ DATABASE_NAME;
        Log.v(LOG_TAG, "dbPath = " + dbPath);
        try {
            database = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (database != null) database.close();
        return database != null ? true : false;
    }

    private void copyDbFile() {
        InputStream is;
        OutputStream os;
        File file = new File(DB_PATH);
        if (!file.exists()) file.mkdir(); 
        Log.v(LOG_TAG, "file.getAbsolutePath() =" + file.getAbsolutePath());
        try {
            is = mContext.getAssets().open(DATABASE_NAME);
            os = new FileOutputStream(new File(file.getAbsolutePath(), DATABASE_NAME));

            byte[] buffer = new byte[1024];
            int length;
            while((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
            }
            os.flush();
            os.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 // Read
    public void getFruit() {
        String dbPath = DB_PATH + "/"+ DATABASE_NAME;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);

        String query = "SELECT * FROM " + TABLE_NAME;
//        db.query(true, TABLE_NAME, null, null, null, null, null, null, null, null);
        Cursor cursor = db.rawQuery(query, null);
        if ( cursor.moveToFirst() ) {
            Log.v(LOG_TAG, "Name = " + cursor.getString(cursor.getColumnIndex(Column.NAME)));
            Log.v(LOG_TAG, "Price = " + cursor.getString(cursor.getColumnIndex(Column.PRICE)));
        }
        db.close();
    }
}
