package com.example.chatsntfcatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText login = findViewById(R.id.loginEdit);
        EditText password = findViewById(R.id.passwordEdit);
        Button connexion = findViewById(R.id.connexionBtn);

        DBUserHandler dbUserHandler = new DBUserHandler(MainActivity.this);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModal userModal = new UserModal();
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                if (!checkIfEmpty(dbUserHandler.getWritableDatabase())) {
                    dbUserHandler.insert(login.getText().toString(), password.getText().toString());
                    Toast.makeText(MainActivity.this, "Création du compte réussi", Toast.LENGTH_LONG).show();
                }
                else {
                    if (dbUserHandler.readItems(login.getText().toString()) == null) {
                        dbUserHandler.insert(login.getText().toString(), password.getText().toString());
                        Toast.makeText(MainActivity.this, "Création du compte réussi", Toast.LENGTH_LONG).show();
                    }
                    else {
                        userModal = dbUserHandler.readItems(login.getText().toString());
                        Toast.makeText(MainActivity.this, "Connexion au compte réussi", Toast.LENGTH_LONG).show();
                    }
                }
                i.putExtra("usermodal", userModal);
                startActivity(i);
            }
        });
    }

    private boolean checkIfEmpty(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBUserHandler.Constants.TABLE_NAME + " LIMIT 1", null);
        boolean d = cursor.moveToFirst();
        cursor.close();
        return d;
    }
}