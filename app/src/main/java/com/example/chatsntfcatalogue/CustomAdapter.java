package com.example.chatsntfcatalogue;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private final Activity activity;
    private final ArrayList<ItemModal> itemModalArrayList;
    private static DBHandler dbHandler;
    private static UserModal userModal;

    public CustomAdapter(@NonNull Activity activity, ArrayList<ItemModal> itemModalArrayList, DBHandler dbHandler, UserModal userModal) {
        this.activity = activity;
        this.itemModalArrayList = itemModalArrayList;
        CustomAdapter.dbHandler = dbHandler;
        CustomAdapter.userModal = userModal;
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
        viewH.id.setText("NFT Id: " + itemModal.getId());
        viewH.name.setText("Nom: " + itemModal.getName());
        viewH.price.setText(itemModal.getPrice() + "€");
        viewH.btc.setText(itemModal.getBtc() + " BTC");
        viewH.btcP.setText(itemModal.getBtcP() + " %");
        viewH.eth.setText(itemModal.getEth() + " ETH");
        viewH.ethP.setText(itemModal.getEthP() + " %");
        viewH.imageNFT.setImageResource(Integer.parseInt(itemModal.getImage()));

    }

    @Override
    public int getItemCount() {
        return itemModalArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, btc,btcP, eth, ethP, price;
        ImageView imageNFT;
        Button buyBtn;

        ViewHolder(View v) {
            super(v);
            id = (TextView) v.findViewById(R.id.idNFTb);
            name = (TextView) v.findViewById(R.id.nameNFTb);
            btc = (TextView) v.findViewById(R.id.btcPrixb);
            btcP = (TextView) v.findViewById(R.id.btcPourcentb);
            eth = (TextView) v.findViewById(R.id.ethPrixb);
            ethP = (TextView) v.findViewById(R.id.ethPourcentb);
            price = (TextView) v.findViewById(R.id.eurPrixb);
            imageNFT = (ImageView) v.findViewById(R.id.imageNFTb);
            buyBtn = (Button) v.findViewById(R.id.buyBtn);

            buyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] arrId = id.getText().toString().split(": ");
                    String[] arrName = name.getText().toString().split(": ");
                    String[] arrPrice = price.getText().toString().split("€");

                    try {
                        dbHandler.update(Integer.parseInt(arrId[1]), arrName[1], Float.parseFloat(arrPrice[0]), userModal.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
