package com.example.alex.expensesincomes;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etPrice)
    EditText etPrice;

    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.btnDelete)
    Button btnDelete;

    @BindView(R.id.incomeCheckBox)
    CheckBox incomeCb;

    @BindView(R.id.tvBalance)
    TextView tvBalance;

    @BindView(R.id.layoutContent)
    LinearLayout layoutContent;

    private int income = 0;
    private int expenses = 0;
    private int balance = 0;
    private int myPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(
                R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.summary) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Summary.class);

            Bundle bundle = new Bundle();

            bundle.putString("income", income + "");
            bundle.putString("expenses", expenses + "");
            bundle.putString("balance", balance + "");

            intent.putExtras(bundle);
            startActivity(intent);
        }

        return true;
    }


    @OnClick(R.id.btnSave)
    public void savePressed(Button btn) {

        final View viewItem =
                getLayoutInflater().inflate(R.layout.row_item, null);

        TextView tvName = (TextView) viewItem.findViewById(R.id.tvName);
        TextView tvPrice = (TextView) viewItem.findViewById(R.id.tvPrice);
        ImageView icon = (ImageView) viewItem.findViewById(R.id.icon);

        String nameString = etName.getText().toString();
        String priceString = etPrice.getText().toString();


        boolean addItem = true;

        tvPrice.setText(priceString);
        if (!nameString.equals("")) {
            tvName.setText(nameString);
        } else {
            addItem = false;
            etName.setError("Name field must not be empty!");
        }

        if (!priceString.equals("")) {
            try {
                myPrice = Integer.parseInt(priceString);
                tvPrice.setText(priceString);
            } catch (NumberFormatException e) {
                etPrice.setError("This must be an integer number!");
                addItem = false;
            }
        } else {
            addItem = false;
            etPrice.setError("Price field must not be empty!");
        }

        if (incomeCb.isChecked()) {
            icon.setImageResource(R.drawable.add);
            income += myPrice;
            balance += myPrice;
        } else {
            icon.setImageResource(R.drawable.minus);
            expenses -= myPrice;
            balance -= myPrice;
        }

        if (addItem) {
            layoutContent.addView(viewItem, 0);
            tvBalance.setText("Balance: " + balance + "$");
            tvBalance.setTextColor(Color.BLACK);
        }
    }

    @OnClick(R.id.btnDelete)
    public void deletePressed(Button btn) {
        Button btnDel = (Button) findViewById(R.id.btnDelete);
        btnDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                layoutContent.removeAllViews();
                balance = 0;
                tvBalance.setText("Balane: 0$");
            }
        });
    }

    public int getIncome() {
        return income;
    }

    public int getExpenses() {
        return expenses;
    }

    public int getBalance() {
        return balance;
    }

}
