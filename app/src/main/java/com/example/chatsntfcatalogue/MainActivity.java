package com.example.chatsntfcatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
                UserModal userModal;
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                if (!checkIfEmpty(dbUserHandler.getWritableDatabase())) {
                    dbUserHandler.insert(login.getText().toString(), password.getText().toString());
                    Toast.makeText(MainActivity.this, "Création du compte réussi", Toast.LENGTH_LONG).show();
                }
                else {
                    if (!checkIfLoginOk(dbUserHandler.getWritableDatabase(), login.getText().toString())) {
                        dbUserHandler.insert(login.getText().toString(), password.getText().toString());
                        Toast.makeText(MainActivity.this, "Création du compte réussi", Toast.LENGTH_LONG).show();
                    } else if (checkIfPasswordOk(dbUserHandler.getWritableDatabase(), login.getText().toString(), password.getText().toString())) {
                        userModal = dbUserHandler.readItems(login.getText().toString(), password.getText().toString());
                        Toast.makeText(MainActivity.this, "Connexion au compte réussi", Toast.LENGTH_LONG).show();
                        i.putExtra("usermodal", userModal);
                        startActivity(i);
                    } else Toast.makeText(MainActivity.this, "Password incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean checkIfEmpty(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT " + DBUserHandler.Constants.KEY_COL_ID + " FROM " + DBUserHandler.Constants.TABLE_NAME + " LIMIT 1", null);
        boolean checked = cursor.moveToFirst();
        cursor.close();
        return checked;
    }

    private boolean checkIfLoginOk(SQLiteDatabase db, String login) {

        Cursor cursor = db.rawQuery("SELECT " + DBUserHandler.Constants.KEY_COL_ID + " FROM " + DBUserHandler.Constants.TABLE_NAME + " WHERE " + DBUserHandler.Constants.KEY_COL_LOGIN + " = " + login, null);
        boolean checked = cursor.moveToFirst();
        cursor.close();
        return checked;
    }

    private boolean checkIfPasswordOk(SQLiteDatabase db, String login, String password) {
        Cursor cursor = db.rawQuery(
                "SELECT " + DBUserHandler.Constants.KEY_COL_ID + " FROM " + DBUserHandler.Constants.TABLE_NAME +
                        " WHERE " + DBUserHandler.Constants.KEY_COL_LOGIN + "=" + login + " AND " + DBUserHandler.Constants.KEY_COL_PASSWORD + " = " + password, null);
        boolean checked = cursor.moveToFirst();
        cursor.close();
        return checked;
    }
}