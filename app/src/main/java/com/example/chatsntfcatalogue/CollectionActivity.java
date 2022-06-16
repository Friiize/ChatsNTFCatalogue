package com.example.chatsntfcatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        ArrayList<ItemModal> itemModalArrayList;
        DBHandler dbHandler = new DBHandler(CollectionActivity.this);
        RecyclerView itemList = findViewById(R.id.collectionRV);
        UserModal userModal = (UserModal) getIntent().getSerializableExtra("usermodal");

        try {
            itemModalArrayList = dbHandler.readItems(userModal.getId());
            CustomCollectionAdapter customAdapter = new CustomCollectionAdapter(this, itemModalArrayList, dbHandler, userModal);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            itemList.setLayoutManager(linearLayoutManager);
            itemList.setAdapter(customAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dbHandler.close();
    }
}