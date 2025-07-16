package com.example.budgetme;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


public class AddTransactionActivity extends AppCompatActivity {
    private Category selectedCategory;

    private void loadCategoryRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Categorymanager categoryManager = new Categorymanager();

        CategoryAdapter adapter = new CategoryAdapter(
                this,
                categoryManager.getCategories(),
                new OnCategoryClickListener() {
                    @Override
                    public void onCategoryClick(Category category) {
                        selectedCategory = category;
                        Log.d("AddTransactionActivity", "Selected category: " + category.getName());
                    }
                }
        );
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_transaction_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(getSupportActionBar() !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadCategoryRecycler();


    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
