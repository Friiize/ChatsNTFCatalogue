package com.example.chatsntfcatalogue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class DBUserHandler extends SQLiteOpenHelper {

    public static class Constants {
        public static final String DATABASE_NAME = "user_chaton_nfts.db";
        public static final int DATABASE_VERSION = 1;
        public static final String TABLE_NAME = "User";
        public static final String KEY_COL_ID = "Id";
        public static final String KEY_COL_LOGIN = "Login";
        public static final String KEY_COL_PASSWORD = "Password";
    }

    public DBUserHandler(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE = "CREATE TABLE " +
                Constants.TABLE_NAME + "(" +
                Constants.KEY_COL_ID + " integer primary key autoincrement, " +
                Constants.KEY_COL_LOGIN + " TEXT, " +
                Constants.KEY_COL_PASSWORD + " TEXT)";

        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public UserModal readItems(String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor itemData = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE Constants.TABLE_NAME.login=login", null);
        UserModal userModal = new UserModal();

        if (Objects.equals(itemData.getString(1), login)) {
            if (Objects.equals(itemData.getString(2), password)) {
                userModal.setLogin(itemData.getString(1));
                userModal.setId(itemData.getInt(0));
                userModal.setPassword(itemData.getString(2));
            }
            else {
               userModal = null;
            }
        }
        else {
            userModal = null;
        }
        itemData.close();

        return userModal;
    }

    public void insert(String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUserHandler.Constants.KEY_COL_LOGIN, login);
        contentValues.put(DBUserHandler.Constants.KEY_COL_PASSWORD, password);

        db.insert(DBUserHandler.Constants.TABLE_NAME, null, contentValues);
        db.close();
    }

    public long update(long rowId, String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        if (login != null) contentValues.put(DBUserHandler.Constants.KEY_COL_LOGIN, login);
        if (password != null) contentValues.put(DBUserHandler.Constants.KEY_COL_PASSWORD, password);

        db.update(DBUserHandler.Constants.TABLE_NAME, contentValues, DBUserHandler.Constants.KEY_COL_ID + "=" + rowId, null);
        db.close();
        return rowId;
    }

    public long delete(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(DBUserHandler.Constants.TABLE_NAME, DBUserHandler.Constants.KEY_COL_ID + "=" + rowId, null);
        db.close();
        return rowId;
    }
}

