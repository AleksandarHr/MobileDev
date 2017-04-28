package com.example.alex.shoppinglist.adapter;


import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.shoppinglist.ItemData;
import com.example.alex.shoppinglist.MainActivity;
import com.example.alex.shoppinglist.MainApplication;
import com.example.alex.shoppinglist.R;
import com.example.alex.shoppinglist.touch.support.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

public class ShoppingRecyclerAdapter
        extends RecyclerView.Adapter<ShoppingRecyclerAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private List<ItemData> itemList;
    private Context context;

    private Realm realm;

    public ShoppingRecyclerAdapter(Context context, Realm realm) {
        this.context = context;
        this.realm = realm;

        RealmResults<ItemData> itemsResult =
                realm.where(ItemData.class)
                        .findAll()
                        .sort("name", Sort.ASCENDING);

        itemList = new ArrayList<ItemData>();
        for (int i = 0; i < itemsResult.size(); i++) {
            itemList.add(itemsResult.get(i));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item, parent, false);
        return new ViewHolder(itemView);
    }

    public void setLogo(final ViewHolder holder, int position) {
        String type = itemList.get(holder.getAdapterPosition()).getType();

        if (type.equals(context.getString(R.string.food_type))) {
            holder.imgLogo.setImageResource(R.drawable.food);
        }
        else if (type.equals(context.getString(R.string.art_type))) {
            holder.imgLogo.setImageResource(R.drawable.art);
        }
        else if (type.equals(context.getString(R.string.clothes_type))) {
            holder.imgLogo.setImageResource(R.drawable.clothes);
        }
        else if (type.equals(context.getString(R.string.drinks_type))) {
            holder.imgLogo.setImageResource(R.drawable.drinks);
        }
        else if (type.equals(context.getString(R.string.electronic_type))) {
            holder.imgLogo.setImageResource(R.drawable.electronic);
        }
        else if (type.equals(context.getString(R.string.travel_type))) {
            holder.imgLogo.setImageResource(R.drawable.travel);
        }
        else {
            holder.imgLogo.setImageResource(R.drawable.other);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.etName.setText(itemList.get(position).getName());
        holder.etPrice.setText("" + itemList.get(position).getPrice());
        holder.cbPurchased.setChecked(itemList.get(position).isPurchased());
        setLogo(holder, position);

        holder.cbPurchased.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                itemList.get(holder.getAdapterPosition())
                        .setPurchased(holder.cbPurchased.isChecked());
                realm.commitTransaction();
            }
        });

        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context)
                        .openEditActivity
                                (holder.getAdapterPosition(),
                                        itemList.get(holder.getAdapterPosition()).getItemID()
                                );
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reduceExpenses(holder.getAdapterPosition());
                deleteItem(holder.getAdapterPosition());
            }
        });
    }

    public void reduceExpenses (int position) {
        ((MainActivity) context)
                .setExpenses(((MainActivity) context).getExpenses() -
                        itemList.get(position).getPrice());
    }

    public void deleteItem(int index) {
        realm.beginTransaction();
        itemList.get(index).deleteFromRealm();
        realm.commitTransaction();

        itemList.remove(index);

        notifyItemRemoved(index);
    }

    public void deleteAllItems() {
        realm.beginTransaction();
        for (int i = 0; i < getItemCount(); i++) {
            itemList.get(i).deleteFromRealm();
        }
        realm.commitTransaction();

        itemList.clear();

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(String name, double price, String type, String description) {
        realm.beginTransaction();
        if("".equals(name) || "".equals("" + price) || "".equals(type) || "".equals(description)) {
            Toast.makeText(context, context.getString(R.string.fill_out_fields),
                    Toast.LENGTH_SHORT).show();
        }
        else {
            ItemData newItem = realm.createObject(ItemData.class, UUID.randomUUID().toString());
            newItem.setName(name);
            newItem.setPrice(price);
            newItem.setType(type);
            newItem.setPurchased(false);
            newItem.setDescription(description);


            realm.commitTransaction();

            itemList.add(0, newItem);
            notifyItemInserted(0);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgLogo;
        private TextView etName;
        private TextView etPrice;
        private CheckBox cbPurchased;
        private Button btnDelete;
        private Button btnDetails;

        public ViewHolder(View itemView) {
            super(itemView);

            etName = (TextView) itemView.findViewById(R.id.item_name);
            etPrice = (TextView) itemView.findViewById(R.id.item_price);
            imgLogo = (ImageView) itemView.findViewById(R.id.item_logo);
            cbPurchased = (CheckBox) itemView.findViewById(R.id.item_cbPurchased);
            btnDelete = (Button) itemView.findViewById(R.id.item_btnDelete);
            btnDetails = (Button) itemView.findViewById(R.id.item_btnDetails);
        }
    }

    public void updateItem(String itemID, int positionToEdit) {
        ItemData item = realm.where(ItemData.class)
                .equalTo("itemID", itemID)
                .findFirst();

        itemList.set(positionToEdit, item);
        notifyItemChanged(positionToEdit);
    }

    public double itemsCost () {
        double result = 0.0;
        if (itemList != null) {
            for (ItemData item : itemList) {
                result += item.getPrice();
            }
        }
        return result;
    }

    @Override
    public void onItemDismiss(int position) {
        reduceExpenses(position);
        deleteItem(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }
}
