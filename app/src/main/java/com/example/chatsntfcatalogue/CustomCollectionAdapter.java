package com.example.chatsntfcatalogue;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class CustomCollectionAdapter extends RecyclerView.Adapter<CustomCollectionAdapter.ViewHolder> {

    private final Activity activity;
    private ArrayList<ItemModal> itemModalArrayList;
    private static DBHandler dbHandler;
    private static UserModal userModal;

    public CustomCollectionAdapter(@NonNull Activity activity, ArrayList<ItemModal> itemModalArrayList, DBHandler dbHandler, UserModal userModal) {
        this.activity = activity;
        this.itemModalArrayList = itemModalArrayList;
        CustomCollectionAdapter.dbHandler = dbHandler;
        CustomCollectionAdapter.userModal = userModal;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View converted = activity.getLayoutInflater().inflate(R.layout.item_layout, null, true);
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

     class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, btc,btcP, eth, ethP, price;
        ImageView imageNFT;
        Button sellBtn;

        ViewHolder(View v) {
            super(v);
            id = (TextView) v.findViewById(R.id.idNFT);
            name = (TextView) v.findViewById(R.id.nameNFT);
            btc = (TextView) v.findViewById(R.id.btcPrix);
            btcP = (TextView) v.findViewById(R.id.btcPourcent);
            eth = (TextView) v.findViewById(R.id.ethPrix);
            ethP = (TextView) v.findViewById(R.id.ethPourcent);
            price = (TextView) v.findViewById(R.id.eurPrix);
            imageNFT = (ImageView) v.findViewById(R.id.imageNFT);
            sellBtn = (Button) v.findViewById(R.id.sellBtn);

            sellBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] arrId = id.getText().toString().split(": ");
                    String[] arrName = name.getText().toString().split(": ");
                    String[] arrPrice = price.getText().toString().split("€");

                    try {
                        dbHandler.update(Integer.parseInt(arrId[1]), arrName[1], Float.parseFloat(arrPrice[0]), userModal.getId());
                        itemModalArrayList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
