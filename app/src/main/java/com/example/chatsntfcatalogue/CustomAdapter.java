package com.example.chatsntfcatalogue;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private final Activity activity;
    private ArrayList<ItemModal> itemModalArrayList;

    public CustomAdapter(@NonNull Activity activity, ArrayList<ItemModal> itemModalArrayList) {
        this.activity = activity;
        this.itemModalArrayList = itemModalArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View converted = activity.getLayoutInflater().inflate(R.layout.buy_item_layout, null, true);
        return new ViewHolder(converted);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewH, int position) {

        ItemModal itemModal = itemModalArrayList.get(position);
        viewH.id.setText(String.valueOf(itemModal.getId()));
        viewH.name.setText(itemModal.getName());
        viewH.price.setText(itemModal.getPrice() + '€');
        viewH.btc.setText(itemModal.getBtc() + "BTC");
        viewH.btcP.setText(itemModal.getBtcP() + '%');
        viewH.eth.setText(itemModal.getEth() + "ETH");
        viewH.ethP.setText(itemModal.getEthP() + '%');

    }

    @Override
    public int getItemCount() {
        return itemModalArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, btc,btcP, eth, ethP, price;

        ViewHolder(View v) {
            super(v);
            id = (TextView) v.findViewById(R.id.idNFT);
            name = (TextView) v.findViewById(R.id.nameNFT);
            btc = (TextView) v.findViewById(R.id.btcPrix);
            btcP = (TextView) v.findViewById(R.id.btcPourcent);
            eth = (TextView) v.findViewById(R.id.ethPrix);
            ethP = (TextView) v.findViewById(R.id.ethPourcent);
            price = (TextView) v.findViewById(R.id.eurPrix);
        }
    }
}
