package com.example.alex.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

import static com.example.alex.shoppinglist.TypeSpinnerHandler.context;

public class EditItem extends AppCompatActivity {
    public static final String KEY_ITEM = "KEY_ITEM";
    @BindView(R.id.etNameEdit)
    EditText etName;
    @BindView(R.id.etPriceEdit)
    EditText etPrice;
    @BindView(R.id.etDescriptionEdit)
    EditText etDescription;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.typeSpinnerEdit)
    Spinner typeSpinnerEdit;

    private ItemData newItemData = null;


    private void populateSpinnerList (ArrayList<String> types) {
        types.add(getString(R.string.food_type));
        types.add(getString(R.string.drinks_type));
        types.add(getString(R.string.clothes_type));
        types.add(getString(R.string.travel_type));
        types.add(getString(R.string.electronic_type));
        types.add(getString(R.string.art_type));
        types.add(getString(R.string.other_type));
    }

    private void setUpAndroidSpinner(Spinner typeSpinner, ArrayList<String> types) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        populateSpinnerList(types);
    }

    public void initSpinner(Spinner typeSpinner) {
        ArrayList<String> types = new ArrayList<>();
        setUpAndroidSpinner(typeSpinner, types);

        HintSpinner<String> hintSpinner = new HintSpinner<>(
                typeSpinner,
                new HintAdapter<>(this, R.string.type_spinner, types),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {

                    }
                });
        hintSpinner.init();
    }

    private int getSpinnerIndex(Spinner spinner, String myString){
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        ButterKnife.bind(this);

        initSpinner(typeSpinnerEdit);

        if (getIntent().hasExtra(MainActivity.KEY_ITEM)) {
            String itemID = getIntent().getStringExtra(MainActivity.KEY_ITEM);
            newItemData = getRealm().where(ItemData.class)
                    .equalTo("itemID", itemID)
                    .findFirst();
        }

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItemChanges();
            }
        });

        if (newItemData != null) {
            etName.setText(newItemData.getName());
            typeSpinnerEdit.setSelection(getSpinnerIndex(typeSpinnerEdit, newItemData.getType()));
            etDescription.setText(newItemData.getDescription());
            etPrice.setText("" + newItemData.getPrice());
        }
    }

    public Realm getRealm() {
        return ((MainApplication) getApplication()).getRealmItem();
    }

    public boolean checkEmptyField() {
        boolean isValid = false;
        if ("".equals(etName.getText().toString())) {
            etName.setError(getString(R.string.empty_field_error));
        }
        else if ("".equals(etDescription.getText().toString())) {
            etDescription.setError(getString(R.string.empty_field_error));
        }
        else if ("".equals(etPrice.getText().toString())) {
            etPrice.setError(getString(R.string.empty_field_error));
        }
        else if (typeSpinnerEdit.getSelectedItem() == null) {
            Toast.makeText(this, getString(R.string.type_spinner_empty), Toast.LENGTH_LONG).show();
        } else {
            isValid = true;
        }

        return isValid;
    }

    public void saveItemChanges() {
        boolean isValid = checkEmptyField();

        if(isValid) {
            Intent results = new Intent();

            getRealm().beginTransaction();
            newItemData.setName(etName.getText().toString());
            newItemData.setType(typeSpinnerEdit.getSelectedItem().toString());
            newItemData.setDescription(etDescription.getText().toString());
            newItemData.setPrice(Double.parseDouble(etPrice.getText().toString()));
            getRealm().commitTransaction();

            results.putExtra(KEY_ITEM, newItemData.getItemID());
            setResult(RESULT_OK, results);
            finish();
        }
    }
}
