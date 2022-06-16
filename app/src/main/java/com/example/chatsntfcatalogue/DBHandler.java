package com.example.chatsntfcatalogue;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class DBHandler extends SQLiteOpenHelper {

    public static class Constants {
        public static final String DATABASE_NAME = "chaton_nfts.db";
        public static final int DATABASE_VERSION = 1;
        public static final String TABLE_NAME = "Chatons_NFT";
        public static final String KEY_COL_ID = "Id";
        public static final String KEY_COL_NAME = "Name";
        public static final String KEY_COL_BTC = "BTC";
        public static final String KEY_COL_ETH = "ETH";
        public static final String KEY_COL_PRICE = "Price";
        public static final String KEY_COL_USER_ID = "User_id";
        public static final String KEY_COL_IMAGE = "Image";
    }

    public DBHandler(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE = "CREATE TABLE " +
                Constants.TABLE_NAME + "(" +
                Constants.KEY_COL_ID + " integer primary key autoincrement, " +
                Constants.KEY_COL_NAME + " TEXT, " +
                Constants.KEY_COL_BTC + " TEXT, " +
                Constants.KEY_COL_ETH + " TEXT, " +
                Constants.KEY_COL_PRICE + " TEXT, " +
                Constants.KEY_COL_USER_ID + " TEXT, " +
                Constants.KEY_COL_IMAGE + " TEXT)";

        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }
    @SuppressLint("DefaultLocale")
    public ArrayList<ItemModal> readItems() throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor itemData = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);
        ArrayList<ItemModal> itemModalArrayList = new ArrayList<>();

        if(itemData.moveToFirst()) {
            do {
                JSONObject response = null;
                try {
                    response  = new JSONObject(getResponseText());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                assert response != null;
                Log.i("dtc", itemData.getString(2));
                JSONObject eurObj = response.getJSONObject("bitcoin");
                JSONObject ethObj = response.getJSONObject("bitcoin");
                double btcBalance = (1 / eurObj.getDouble("eur") * Double.parseDouble(itemData.getString(4)));
                double ethBalance = (1 / ((eurObj.getDouble("eur") / ethObj.getDouble("eth")))) * Double.parseDouble(itemData.getString(4));
                String btcBS = String.format("%.8f", btcBalance);
                String ethBS = String.format("%.8f", ethBalance);
//                String btcC = String.format("%.8f", itemData.getString(2));
//                String ethC = String.format("%.8f", itemData.getString(3));
//                BigDecimal eth =  BigDecimal.valueOf(itemData.getDouble(2));
//
//                double btcP =  ((Double.parseDouble(btcBS) - Double.parseDouble(itemData.getString(2))) / Double.parseDouble(btcC)) * 100;
//                double ethP =  ((Double.parseDouble(ethBS) - Double.parseDouble(itemData.getString(3))) / Double.parseDouble(ethC)) * 100;

                itemModalArrayList.add(new ItemModal(
                        itemData.getString(1),
                        btcBS,
                        itemData.getString(3),
                        "0",
                        ethBS,
                        itemData.getString(6),
                        "0",
                        itemData.getString(8),
                        itemData.getString(9),
                        itemData.getInt(0)));

            } while (itemData.moveToNext());
        }
        itemData.close();

        return itemModalArrayList;
    }
    public ArrayList<ItemModal> readCollectionItems(int id) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor itemData = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.KEY_COL_USER_ID + " = '" + id + "'", null);
        ArrayList<ItemModal> itemModalArrayList = new ArrayList<>();

        if(itemData.moveToFirst()) {
            do {
                JSONObject response = null;
                try {
                    response  = new JSONObject(getResponseText());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                assert response != null;
                Log.i("dtc", itemData.getString(2));
                JSONObject eurObj = response.getJSONObject("bitcoin");
                JSONObject ethObj = response.getJSONObject("bitcoin");
                double btcBalance = (1 / eurObj.getDouble("eur") * Double.parseDouble(itemData.getString(4)));
                double ethBalance = (1 / ((eurObj.getDouble("eur") / ethObj.getDouble("eth")))) * Double.parseDouble(itemData.getString(4));
                String btcBS = String.format("%.8f", btcBalance);
                String ethBS = String.format("%.8f", ethBalance);
//                String btcC = String.format("%.8f", itemData.getString(2));
//                String ethC = String.format("%.8f", itemData.getString(3));
//                BigDecimal eth =  BigDecimal.valueOf(itemData.getDouble(2));
//
//                double btcP =  ((Double.parseDouble(btcBS) - Double.parseDouble(itemData.getString(2))) / Double.parseDouble(btcC)) * 100;
//                double ethP =  ((Double.parseDouble(ethBS) - Double.parseDouble(itemData.getString(3))) / Double.parseDouble(ethC)) * 100;

                itemModalArrayList.add(new ItemModal(
                        itemData.getString(1),
                        btcBS,
                        itemData.getString(3),
                        "0",
                        ethBS,
                        itemData.getString(6),
                        "0",
                        itemData.getString(8),
                        itemData.getString(9),
                        itemData.getInt(0)));

            } while (itemData.moveToNext());
        }
        itemData.close();

        return itemModalArrayList;
    }
    @SuppressLint("DefaultLocale")
    public void insert(String name, int price, int image) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        JSONObject response = null;
        try {
            response  = new JSONObject(getResponseText());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        assert response != null;
        JSONObject eurObj = response.getJSONObject("bitcoin");
        JSONObject ethObj = response.getJSONObject("bitcoin");
        double btcBalance = (1 / eurObj.getDouble("eur") * price);
        double ethBalance = (1 / ((eurObj.getDouble("eur") / ethObj.getDouble("eth")))) * price;

        contentValues.put(DBHandler.Constants.KEY_COL_NAME, name);
        contentValues.put(DBHandler.Constants.KEY_COL_BTC, String.format("%.8f",btcBalance));
        contentValues.put(DBHandler.Constants.KEY_COL_ETH, String.format("%.8f", ethBalance));
        contentValues.put(DBHandler.Constants.KEY_COL_PRICE, String.valueOf(price));
        contentValues.put(DBHandler.Constants.KEY_COL_IMAGE, String.valueOf(image));

        db.insert(DBHandler.Constants.TABLE_NAME, null, contentValues);
        db.close();
    }

    public long update(long rowId, String name, Integer price) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        if (price != null) {
            JSONObject response = null;
            try {
                response = new JSONObject(getResponseText());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            assert response != null;
            JSONObject eurObj = response.getJSONObject("bitcoin");
            JSONObject ethObj = response.getJSONObject("bitcoin");
            double btcBalance = (1 / eurObj.getDouble("eur")) * price;
            double ethBalance = (1 / ((eurObj.getDouble("eur") / ethObj.getDouble("eth")))) * price;

            contentValues.put(DBHandler.Constants.KEY_COL_PRICE, price);
            contentValues.put(DBHandler.Constants.KEY_COL_BTC, String.valueOf(btcBalance));
            contentValues.put(DBHandler.Constants.KEY_COL_ETH, String.valueOf(ethBalance));
        }
        if (name != null) contentValues.put(DBHandler.Constants.KEY_COL_NAME, name);

        db.update(DBHandler.Constants.TABLE_NAME, contentValues, DBHandler.Constants.KEY_COL_ID + "=" + rowId, null);
        db.close();
        return rowId;
    }

    public long delete(long rowId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(DBHandler.Constants.TABLE_NAME, DBHandler.Constants.KEY_COL_ID + "=" + rowId, null);
        db.close();
        return rowId;
    }

    private String getResponseText() throws IOException
    {
        StringBuilder response  = new StringBuilder();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        URL url = new URL("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=eur%2Ceth");
        HttpsURLConnection https = (HttpsURLConnection)url.openConnection();

        if (https.getResponseCode() == HttpsURLConnection.HTTP_OK)
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(https.getInputStream()),8192);
            String strLine;
            while ((strLine = input.readLine()) != null)
            {
                response.append(strLine);
            }
            input.close();
        }
        return response.toString();
    }
}

