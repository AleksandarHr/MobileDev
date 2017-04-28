package com.example.alex.shoppinglist;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

// Last minute decided to add spinner and then to extract the common code but had some troubles
//   doing so. This class is currently not used anywhere

public class TypeSpinnerHandler {

    static Context context;
    static ArrayList<String> types;

    public TypeSpinnerHandler(Context context) {
        this.context = context;
        types = new ArrayList<>();
    }

    public static void populateSpinnerList () {
        types.add("Food");
        types.add("Drinks");
        types.add("Clothes");
        types.add("Travel");
        types.add("Electronic");
        types.add("Art Supplies");
        types.add("Other");
    }

    public static void setUpAndroidSpinner(Spinner typeSpinner, ArrayList<String> types) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        populateSpinnerList();
    }

    public static void initSpinner(Spinner typeSpinner) {
        ArrayList<String> types = new ArrayList<>();
        setUpAndroidSpinner(typeSpinner, types);

        HintSpinner<String> hintSpinner = new HintSpinner<>(
                typeSpinner,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                new HintAdapter<>(context, R.string.type_spinner, types),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {

                    }
                });
        hintSpinner.init();
    }

}
