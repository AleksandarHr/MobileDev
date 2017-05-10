package com.example.alexander.weatherreport.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexander.weatherreport.MainActivity;
import com.example.alexander.weatherreport.R;
import com.example.alexander.weatherreport.data.CityData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder> {

    private List<CityData> citiesList;
    private Context context;

    private Realm realmTodo;


    public CityRecyclerAdapter(Context context, Realm realmTodo) {
        this.context = context;

        this.realmTodo = realmTodo;

        RealmResults<CityData> cityResult =
                realmTodo.where(CityData.class)
                        .findAll()
                        .sort("cityName",
                        Sort.ASCENDING);

        citiesList = new ArrayList<CityData>();

        for (int i = 0; i < cityResult.size(); i++) {
            citiesList.add(cityResult.get(i));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.city_item, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvName.setText(citiesList.get(position).getCityName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).openDetailsActivity(holder.getAdapterPosition(),
                        citiesList.get(holder.getAdapterPosition()).getCityID()
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public void addCity(String cityName) {
        realmTodo.beginTransaction();
        CityData newCity = realmTodo.createObject(CityData.class, UUID.randomUUID().toString());
        newCity.setCityName(cityName);

        realmTodo.commitTransaction();
        ((MainActivity)context).openDetailsActivity(0, newCity.getCityID());

        citiesList.add(0, newCity);
        notifyItemInserted(0);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }

    /*
    @Override
    public void onItemDismiss(int position) {
        realmTodo.beginTransaction();
        citiesList.get(position).deleteFromRealm();
        realmTodo.commitTransaction();


        citiesList.remove(position);

        // refreshes the whole list
        //notifyDataSetChanged();
        // refreshes just the relevant part that has been deleted
        notifyItemRemoved(position);
    }

    /*

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(citiesList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(citiesList, i, i - 1);
            }
        }


        notifyItemMoved(fromPosition, toPosition);
    }
*/



}
