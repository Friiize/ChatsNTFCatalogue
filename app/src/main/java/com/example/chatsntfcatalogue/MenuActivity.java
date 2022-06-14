package com.example.chatsntfcatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button collectionBtn, boutiqueBtn;
        collectionBtn = findViewById(R.id.collectionBtn);
        boutiqueBtn = findViewById(R.id.boutiqueBtn);

        collectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, CollectionActivity.class);
                startActivity(i);
            }
        });
    }
}