
package com.vr.example.sqlite;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        databaseHelper.addFruits();
//        databaseHelper.updateFruit();
//        databaseHelper.getFruit();
//        databaseHelper.deleteFruit();

        DataHandler dataHandler = new DataHandler(this);
        dataHandler.createDataBase();
        dataHandler.getFruit();
    }

}
