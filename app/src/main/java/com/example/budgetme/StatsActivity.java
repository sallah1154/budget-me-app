package com.example.budgetme;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.example.budgetme.Transactions;
import com.example.budgetme.Budget;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private PieChart pieChart;
    private TextView centerText, totalSpentValue, safeToSpendValue, expenseAmount, incomeAmount;
    private LinearLayout topPlacesList;
    private TransactionViewModel vmodel;
    private BudgetViewModel budgetViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        pieChart = findViewById(R.id.pieChart);
        centerText = findViewById(R.id.centerText);
        totalSpentValue = findViewById(R.id.totalSpentValue);
        safeToSpendValue = findViewById(R.id.safeToSpendValue);
        topPlacesList = findViewById(R.id.topPlacesList);
        expenseAmount = findViewById(R.id.expenseAmount);
        incomeAmount = findViewById(R.id.incomeAmount);

        vmodel = new ViewModelProvider(this).get(TransactionViewModel.class);
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);

        vmodel.getAllTransactions().observe(this, transactions -> {
            if (transactions == null || transactions.isEmpty()) {
                // No transactions - set all to zero and clear UI
                incomeAmount.setText("$0.00");
                expenseAmount.setText("$0.00");
                totalSpentValue.setText("$0.00");
                centerText.setText("$0.00");
                safeToSpendValue.setText("$0.00");
                topPlacesList.removeAllViews();

                pieChart.clear();
                pieChart.invalidate();
                return;
            }

            double totalIncome = 0.0;
            double totalExpense = 0.0;

            //Clear items
            topPlacesList.removeAllViews();

            List<PieEntry> pieEntries = new ArrayList<>();

            //get transactions info
            for (Transactions t : transactions) {
                if ("expense".equalsIgnoreCase(t.getType())) {
                    totalExpense += t.getAmount();

                    //Add each expense as a slice on pie chart
                    //pieEntries.add(new PieEntry((float) t.getAmount(), t.getName()));
                    if (t.getCategory() != null) {
                        pieEntries.add(new PieEntry((float) t.getAmount(), t.getCategory().toString()));
                    }


                    // Add to top places list
                    addTopPlace(t.getName(), String.format("$%.2f", t.getAmount()));

                } else if ("income".equalsIgnoreCase(t.getType())) {
                    totalIncome += t.getAmount();
                }
            }

            expenseAmount.setText(String.format("$%.2f", totalExpense));
            incomeAmount.setText(String.format("$%.2f", totalIncome));
            totalSpentValue.setText(String.format("$%.2f", totalExpense));
            centerText.setText(String.format("$%.2f", totalExpense));

            /*Calculate safe to spend
            double safeToSpend = totalIncome - totalExpense;
            if (safeToSpend < 0) {
                safeToSpendValue.setText("Over Budget");
            } else {
                safeToSpendValue.setText(String.format("$%.2f", safeToSpend));
            }
             */
            final double finalTotalExpense = totalExpense;

            budgetViewModel.getRmonthlybudget().observe(this, budgetAmount -> {
                if (budgetAmount != null) {
                    double safeToSpend = budgetAmount - finalTotalExpense;
                    safeToSpendValue.setText(safeToSpend < 0 ? "Over Budget" : String.format("$%.2f", safeToSpend));
                } else {
                    safeToSpendValue.setText("No Budget Set");
                }
            });


            //Setup pie chart with data
            PieDataSet dataSet = new PieDataSet(pieEntries, "");
            dataSet.setColors(new int[] {
                    Color.parseColor("#473771"),
                    Color.parseColor("#745db0"),
                    Color.parseColor("#6750a4"),
                    Color.parseColor("#FF8F00"),
                    Color.parseColor("#D32F2F"),
                    Color.parseColor("#7B1FA2"),
                    Color.parseColor("#1976D2")
            });
            dataSet.setSliceSpace(4f);
            dataSet.setSelectionShift(6f);

            PieData data = new PieData(dataSet);
            data.setDrawValues(true);
            data.setValueTextSize(16f);
            data.setValueTextColor(Color.WHITE);

            pieChart.setData(data);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleRadius(70f);
            pieChart.setHoleColor(Color.TRANSPARENT);
            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setEntryLabelColor(Color.BLACK);
            pieChart.setEntryLabelTextSize(13f);
            pieChart.animateY(1000, Easing.EaseInOutQuad);
            pieChart.invalidate();

            Legend legend = pieChart.getLegend();
            legend.setEnabled(true);
            legend.setTextSize(15f);
            legend.setFormSize(14f);
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        });
    }

    private void addTopPlace(String name, String amount) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        row.setPadding(0, 8, 0, 8);

        TextView nameView = new TextView(this);
        nameView.setText(name);
        nameView.setTextSize(18f);
        nameView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        ));

        TextView amountView = new TextView(this);
        amountView.setText(amount);
        amountView.setTextSize(18f);
        amountView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        row.addView(nameView);
        row.addView(amountView);

        topPlacesList.addView(row);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}


/*
package com.example.budgetme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity{
    private PieChart pieChart;
    private TextView centerText, totalSpentValue, safeToSpendValue, expenseAmount, incomeAmount;
    private LinearLayout topPlacesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        //setContentView(R.layout.activity_budget);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        pieChart = findViewById(R.id.pieChart);
        centerText = findViewById(R.id.centerText);
        totalSpentValue = findViewById(R.id.totalSpentValue);
        safeToSpendValue = findViewById(R.id.safeToSpendValue);
        topPlacesList = findViewById(R.id.topPlacesList);
        expenseAmount = findViewById(R.id.expenseAmount);
        incomeAmount = findViewById(R.id.incomeAmount);

        //harcoded values
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(500f, "Rent"));
        entries.add(new PieEntry(200f, "Dining"));
        entries.add(new PieEntry(200f, "Entertainment"));

        /*
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[]{
            Color.rgb(244, 67, 54),
            Color.rgb(33, 150, 243),
            Color.rgb(76, 175, 80)
        });
         close here

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setColors(new int[] {
                Color.parseColor("#473771"),
                Color.parseColor("#745db0"),
                Color.parseColor("#6750a4")
        });

        dataSet.setSliceSpace(4f);
        dataSet.setSelectionShift(6f);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(70f);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        //pieChart.getLegend().setEnabled(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(13f);
        pieChart.invalidate();

        // Customize legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(16f);           // Make text bigger
        legend.setFormSize(14f);           // Make the color box bigger
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); // Optional alignment

        //hardcoded values for expense/income
        expenseAmount.setText("$800");
        incomeAmount.setText("$900");

        //hardcoded values for total spent/safe to spend

        //hardcoded values for total spent
        centerText.setText("$900");

        //hardcoded values for safe to spend
        //get weekly budget, subtract total expenses, equal safe to spend
        //if negative value set to "Over Budget Text"
        //get total spent from transactions
        totalSpentValue.setText("$900");
        safeToSpendValue.setText("$100");

        //hardcoded values for top places
        addTopPlace("Amazon", "$34.56");
        addTopPlace("Starbucks", "$23.06");
        addTopPlace("Chick-Fil-A", "$50.95");
    }

    private void addTopPlace(String name, String amount) {
        // Create a horizontal layout for the row
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        row.setPadding(0, 8, 0, 8); // vertical spacing between items

        // Left side: Place name
        TextView nameView = new TextView(this);
        nameView.setText(name);
        nameView.setTextSize(18f);
        nameView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f // take up remaining space
        ));

        // Right side: Amount
        TextView amountView = new TextView(this);
        amountView.setText(amount);
        amountView.setTextSize(18f);
        amountView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        //add to row
        row.addView(nameView);
        row.addView(amountView);

        topPlacesList.addView(row);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
*/
