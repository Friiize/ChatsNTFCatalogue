package com.example.chatsntfcatalogue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
                Constants.KEY_COL_IMAGE + " TEXT)";

        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<ItemModal> readItems() throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor itemData = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);
        ArrayList<ItemModal> itemModalArrayList = new ArrayList<>();

        if(itemData.moveToFirst()) {
            do {
                JSONObject btcResponse = null;
                JSONObject ethResponse = null;
                try {
                    btcResponse  = new JSONObject(getResponseText("https://api.coingecko.com/api/v3/coins/bitcoin/market_chart?vs_currency=eur&days=1"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                try {
                    ethResponse  = new JSONObject(getResponseText("https://api.coingecko.com/api/v3/coins/ethereum-wormhole/market_chart?vs_currency=eur&days=1"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                assert ethResponse != null;
                assert btcResponse != null;
                String btcBalance = btcResponse.getJSONArray("prices").getJSONArray(0).getJSONArray(1).toString();
                String ethBalance = ethResponse.getJSONArray("prices").getJSONArray(0).getJSONArray(1).toString();
                Integer btcP, ethP;
                btcP = ( Integer.parseInt(itemData.getString(3)) - Integer.parseInt(btcBalance)) / Integer.parseInt(itemData.getString(3)) * 100;
                ethP = ( Integer.parseInt(itemData.getString(6)) - Integer.parseInt(ethBalance)) / Integer.parseInt(itemData.getString(6)) * 100;

                itemModalArrayList.add(new ItemModal(
                        itemData.getString(1),
                        btcBalance,
                        itemData.getString(3),
                        btcP.toString(),
                        ethBalance,
                        itemData.getString(6),
                        ethP.toString(),
                        itemData.getString(8),
                        itemData.getString(9),
                        itemData.getInt(0)));

            } while (itemData.moveToNext());
        }
        itemData.close();

        return itemModalArrayList;
    }

    public void insert(String name, String btc, String eth, String price, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHandler.Constants.KEY_COL_NAME, name);
        contentValues.put(DBHandler.Constants.KEY_COL_BTC, btc);
        contentValues.put(DBHandler.Constants.KEY_COL_ETH, eth);
        contentValues.put(DBHandler.Constants.KEY_COL_PRICE, price);
        contentValues.put(DBHandler.Constants.KEY_COL_IMAGE, image);

        db.insert(DBHandler.Constants.TABLE_NAME, null, contentValues);
        db.close();
    }

    public long update(long rowId, String name, String btc, String eth, String price, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        if (name != null) contentValues.put(DBHandler.Constants.KEY_COL_NAME, name);
        if (btc != null) contentValues.put(DBHandler.Constants.KEY_COL_BTC, btc);
        if (eth != null) contentValues.put(DBHandler.Constants.KEY_COL_ETH, eth);
        if (price != null) contentValues.put(DBHandler.Constants.KEY_COL_PRICE, price);
        if (image != null) contentValues.put(DBHandler.Constants.KEY_COL_IMAGE, image);

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

    private String getResponseText(String stringUrl) throws IOException
    {
        StringBuilder response  = new StringBuilder();

        URL url = new URL(stringUrl);
        HttpURLConnection httpconn = (HttpURLConnection)url.openConnection();
        if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()),8192);
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

