package com.example.chatsntfcatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import org.json.JSONException;
import java.util.ArrayList;

public class BoutiqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        ArrayList<ItemModal> itemModalArrayList = new ArrayList<>();
        DBHandler dbHandler = new DBHandler(BoutiqueActivity.this);
        RecyclerView itemList = findViewById(R.id.boutiqueRV);

        if(!checkIfEmpty(dbHandler.getWritableDatabase())) {
            try {
                dbHandler.insert("Green", 13, R.drawable.chaton_nft_g);
                dbHandler.insert("Green Legendary", 20, R.drawable.chaton_nft_g_l);
                dbHandler.insert("Red", 13, R.drawable.chaton_nft_r);
                dbHandler.insert("Purple Rose Legendary", 25, R.drawable.chaton_nft_bp_l);
                dbHandler.insert("Original", 10, R.drawable.chaton_nft_d);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            itemModalArrayList = dbHandler.readItems();
            CustomAdapter customAdapter = new CustomAdapter(this, itemModalArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            itemList.setLayoutManager(linearLayoutManager);
            itemList.setAdapter(customAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dbHandler.close();
    }

    private boolean checkIfEmpty(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT " + DBHandler.Constants.KEY_COL_ID + " FROM " + DBHandler.Constants.TABLE_NAME + " LIMIT 1", null);
        boolean checked = cursor.moveToFirst();
        cursor.close();
        return checked;
    }
}