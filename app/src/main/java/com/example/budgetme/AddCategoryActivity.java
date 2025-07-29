package com.example.budgetme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText editCategoryName;
    private EditText editIconName;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        editCategoryName = findViewById(R.id.edit_category_name);
        editIconName = findViewById(R.id.edit_emoji_input);
        saveButton = findViewById(R.id.button_save_category);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryName = editCategoryName.getText().toString().trim();
                String iconName = editIconName.getText().toString().trim();

                if (categoryName.isEmpty() || iconName.isEmpty()) {
                    Toast.makeText(AddCategoryActivity.this, "Please enter both name and icon", Toast.LENGTH_SHORT).show();
                    return;
                }

                Category newCategory = new Category(categoryName, iconName);

                Categorymanager.getInstance().addCategory(newCategory);

                Toast.makeText(AddCategoryActivity.this, "Category added", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
