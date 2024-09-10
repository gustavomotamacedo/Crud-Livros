package com.gustavomacedo.estudossqlite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    private EditText edtBookName;
    private EditText edtBookAuthor;
    private EditText edtBookPages;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtBookName = findViewById(R.id.edtBookName);
        edtBookAuthor = findViewById(R.id.edtBookAuthor);
        edtBookPages = findViewById(R.id.edtBookPages);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> {
            MyDbHelper dbHelper = new MyDbHelper(this);
            String strBookName = String.valueOf(edtBookName.getText()).trim();
            String strBookAuthor = String.valueOf(edtBookAuthor.getText()).trim();
            int intBookPages = Integer.parseInt(String.valueOf(edtBookPages.getText()).trim());
            dbHelper.addBook(strBookName, strBookAuthor, intBookPages);

            Intent in = new Intent(this, MainActivity.class);
            finish();
            startActivity(in);

        });

    }
}