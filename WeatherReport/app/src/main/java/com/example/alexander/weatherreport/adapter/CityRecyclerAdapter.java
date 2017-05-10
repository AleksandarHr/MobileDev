package com.example.alexander.weatherreport.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexander.weatherreport.DetailsActivity;
import com.example.alexander.weatherreport.MainActivity;
import com.example.alexander.weatherreport.R;
import com.example.alexander.weatherreport.data.City;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class CityRecyclerAdapter  extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder> {

    private List<City> citiesList;
    private Context context;

    //private Realm realm;

    public CityRecyclerAdapter(Context context, Realm realm) {
        this.context = context;
        //this.realm = realm;

//        RealmResults<City> itemsResult =
//                realm.where(City.class)
 ///                       .findAll()
 //                       .sort("name", Sort.ASCENDING);

        citiesList = new ArrayList<City>();
//        for (int i = 0; i < itemsResult.size(); i++) {
//            citiesList.add(itemsResult.get(i));
 //       }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.city_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tvName.setText(citiesList.get(position).getName());

        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).openDetailsActivityt(holder.getAdapterPosition(),
                        citiesList.get(holder.getAdapterPosition()).getId());
            }
        });
    }

    public void addCity(String name) {
      //  realm.beginTransaction();
        if("".equals(name)) {
            Toast.makeText(context, "Add the name of the city",
                    Toast.LENGTH_SHORT).show();
        }
        else {
        //    City newCity = realm.createObject(City.class, UUID.randomUUID().toString());
        //    newCity.setName(name);
        //    realm.commitTransaction();
        City newCity = new City(name);
            citiesList.add(0, newCity);
            notifyItemInserted(0);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private Button btnDetails;

        public ViewHolder(View cityView) {
            super(cityView);

            tvName = (TextView) cityView.findViewById(R.id.tvName);
            btnDetails = (Button) cityView.findViewById(R.id.btnDetails);
        }
    }

    public void deleteItem(int index) {
        //realm.beginTransaction();
        //citiesList.get(index).deleteFromRealm();
        //realm.commitTransaction();

        citiesList.remove(index);

        notifyItemRemoved(index);
    }
}
