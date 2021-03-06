package com.example.android.inventoryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.CONTACT_COLUMN;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.IMAGE_COLUMN;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.ITEM_COLUMN;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.PRICE_COLUMN;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.QUANTITY_COLUMN;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.TABLE_NAME;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry._ID;

/**
 * Created by phartmann on 09/03/2018.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "inventory.db";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ITEM_COLUMN + " TEXT NOT NULL, " +
                    PRICE_COLUMN + " INTEGER NOT NULL DEFAULT 0, " +
                    QUANTITY_COLUMN + " INTEGER NOT NULL DEFAULT 0, " +
                    CONTACT_COLUMN + " INTEGER, " +
                    IMAGE_COLUMN + " TEXT " + ")";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public InventoryDbHelper( Context context ) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
