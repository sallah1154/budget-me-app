package com.example.budgetme;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

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
         */
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
        addTopPlace("Home Depot", "$34.56");
        addTopPlace("Walmart", "$23.06");
        addTopPlace("Boba Heaven", "$50.95");
        addTopPlace("Target", "$23.06");
        addTopPlace("Zelle John Doe", "$50.95");
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
}
