package com.example.alex.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.shoppinglist.adapter.ShoppingRecyclerAdapter;
import com.example.alex.shoppinglist.touch.support.ItemTouchHelperCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recyclerShoppingList)
    RecyclerView recyclerShoppingList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnDeleteAll)
    Button btnDeleteAll;
    @BindView(R.id.tvExpenses)
    TextView tvExpenses;

    private double expenses = 0.0;

    ShoppingRecyclerAdapter shoppingRecyclerAdapter;

    public static final String KEY_ITEM = "KEY_ITEM";
    public static final int REQUEST_CODE_EDIT = 101;

    private int positionOfItemToEdit = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((MainApplication) getApplication()).openRealm();
        setUpUI();
        setExpenses(shoppingRecyclerAdapter.itemsCost());
    }

    private void setUpUI() {
        setUpRecyclerView();
        setUpItemAddUI();
        setUpToolbar();
        deleteAll();
    }

    private void setUpRecyclerView() {
        recyclerShoppingList.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerShoppingList.setLayoutManager(layoutManager);

        shoppingRecyclerAdapter = new ShoppingRecyclerAdapter(
                this,
                ((MainApplication) getApplication()).getRealmItem());

        recyclerShoppingList.setAdapter(shoppingRecyclerAdapter);
        setUpTouchHelper(shoppingRecyclerAdapter);
    }

    private void setUpTouchHelper (ShoppingRecyclerAdapter adapter) {
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerShoppingList);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setUpItemAddUI() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialogue();
            }
        });

    }

    private void deleteAll() {
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoppingRecyclerAdapter.deleteAllItems();
                expenses = 0.0;
                tvExpenses.setText(getString(R.string.expected_total) + expenses);
            }
        });
    }

    private boolean checkEmptyField(EditText etName,
                                    EditText etDescription, EditText etPrice, Spinner typeSpinner) {
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
        else {
            isValid = true;
        }

        return isValid;
    }

    private void populateSpinnerList(ArrayList<String> types) {
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

    private void showAddItemDialogue() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.new_item);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_new_item, null);
        builder.setView(dialogView);

        final EditText etItemName = (EditText) dialogView.findViewById(R.id.etNameNew);
        final EditText etItemPrice = (EditText) dialogView.findViewById(R.id.etPriceNew);
        final EditText etItemDescription = (EditText) dialogView.findViewById(R.id.etDescriptionNew);

        final Spinner typeSpinner = (Spinner) dialogView.findViewById(R.id.typeSpinnerNew);
        initSpinner(typeSpinner);

        builder.setPositiveButton(R.string.add_new, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinnerContent;
                if (typeSpinner.getSelectedItemPosition() >  typeSpinner.getCount()) {
                    spinnerContent = getString(R.string.other_type);
                }
                else {
                    spinnerContent = typeSpinner.getSelectedItem().toString();
                }

                if (checkEmptyField(etItemName, etItemDescription, etItemPrice, typeSpinner)) {
                    shoppingRecyclerAdapter.addItem(
                            etItemName.getText().toString(),
                            Double.parseDouble(etItemPrice.getText().toString()),
                            spinnerContent,
                            etItemDescription.getText().toString());
                    expenses += Double.parseDouble(etItemPrice.getText().toString());
                    tvExpenses.setText(getString(R.string.expected_total) + expenses);
                    recyclerShoppingList.scrollToPosition(0);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.fill_out_fields,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void openEditActivity(int index, String todoID) {
        positionOfItemToEdit = index;

        Intent startEdit = new Intent(this, EditItem.class);

        startEdit.putExtra(KEY_ITEM, todoID);

        startActivityForResult(startEdit, REQUEST_CODE_EDIT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_CODE_EDIT) {
                    String itemID = data.getStringExtra(
                            EditItem.KEY_ITEM);
                    shoppingRecyclerAdapter.updateItem(itemID, positionOfItemToEdit);
                }
                break;
            case RESULT_CANCELED:
                Toast.makeText(MainActivity.this, R.string.cancelled, Toast.LENGTH_SHORT).show();
                break;
        }
        setExpenses(shoppingRecyclerAdapter.itemsCost());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MainApplication) getApplication()).closeRealm();
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
        tvExpenses.setText(getString(R.string.expected_total) + expenses);
    }

}
