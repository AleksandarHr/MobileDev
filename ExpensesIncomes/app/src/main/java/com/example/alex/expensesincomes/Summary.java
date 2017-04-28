package com.example.alex.expensesincomes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Summary extends AppCompatActivity {

    String income, expenses, balance;
    @BindView(R.id.income)
    TextView tvIncome;

    @BindView(R.id.expenses)
    TextView tvExpenses;

    @BindView(R.id.tvBalance)
    TextView tvBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        ButterKnife.bind(this);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        income = extras.getString("income");
        expenses = extras.getString("expenses");
        balance = extras.getString("balance");

        tvIncome.setText("Income: " + income + "$");
        tvExpenses.setText("Expenses: " + expenses + "$");
        tvBalance.setText("Balance: " + balance + "$");
    }
}
