package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.CONTENT_URI;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.ITEM_COLUMN;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.PRICE_COLUMN;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.QUANTITY_COLUMN;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry.TABLE_NAME;
import static com.example.android.inventoryapp.InventoryContract.InventoryEntry._ID;


/**
 * Created by phartmann on 10/03/2018.
 */

public class InventoryCursorAdapter extends CursorAdapter {

    private static final String LOG_TAG = InventoryCursorAdapter.class.getSimpleName();

    public InventoryCursorAdapter( Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView( Context context, Cursor cursor, ViewGroup parent ) {
        /* Create new views from cursor to adapter */
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView( View view, final Context context, final Cursor cursor ) {

        /* Find views on Layout */
        TextView itemView = view.findViewById(R.id.item_tv);
        TextView priceView = view.findViewById(R.id.price_tv);
        TextView quantityView = view.findViewById(R.id.quatity_tv);
        Button sellButton = view.findViewById(R.id.sell_button);

        /* Fill data from cursor on each textview */
        itemView.setText(
                cursor.getString(cursor.getColumnIndexOrThrow(ITEM_COLUMN))
        );

        priceView.setText(
                cursor.getString(cursor.getColumnIndexOrThrow(PRICE_COLUMN))
        );

        quantityView.setText(
                cursor.getString(cursor.getColumnIndexOrThrow(QUANTITY_COLUMN))
        );

        Log.e(LOG_TAG, "ID: " + cursor.getString(cursor.getColumnIndexOrThrow(_ID)));

        /* Get values from db */
        final int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(QUANTITY_COLUMN));
        String currentId = cursor.getString(cursor.getColumnIndexOrThrow(_ID));
        final Uri currentUri = ContentUris.withAppendedId(CONTENT_URI, Long.parseLong(currentId));

        /* Set up sell button */
        sellButton.setText("Sold +1");
        sellButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick( View v ) {

                int attempt = 2;
                /* Switch to try to do this logic.
                 *
                 * Case 0 updates db directly.
                 * Case 1 updates using content resolver */
                switch (attempt) {
                    case 1: {

                        /* Try to update using content resolver */
                        int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(QUANTITY_COLUMN));
                        String currentId = cursor.getString(cursor.getColumnIndexOrThrow(_ID));
                        Uri currentUri = ContentUris.withAppendedId(CONTENT_URI, Long.parseLong(currentId));

                        Log.e(LOG_TAG, "Quantity: " + currentQuantity + " from ID: " + currentId);
                        currentQuantity -= 1;
                        Log.e(LOG_TAG, "Quantity Updated: " + currentQuantity + " from ID: " + currentId);
                        Log.e(LOG_TAG, String.valueOf(currentUri));

                        ContentValues values = new ContentValues();
                        values.put(QUANTITY_COLUMN, currentQuantity);

                        context.getContentResolver().update(currentUri,
                                values,
                                currentId,
                                null);

                        break;
                    }case 2: {

                        ContentValues values = new ContentValues();
                        values.put(QUANTITY_COLUMN, (currentQuantity - 1));

                        context.getContentResolver().update(currentUri,
                                values,
                                null,
                                null);
                        break;
                    }
                }

            }
        });
    }
}
