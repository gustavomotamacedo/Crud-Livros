package com.gustavomacedo.estudossqlite;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {

    private EditText edtBookName2;
    private EditText edtBookAuthor2;
    private EditText edtBookPages2;
    private Button btnUpd;
    private Button btnDel;

    private String id;
    private String title;
    private String author;
    private String pages;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtBookName2 = findViewById(R.id.edtBookName2);
        edtBookAuthor2 = findViewById(R.id.edtBookAuthor2);
        edtBookPages2 = findViewById(R.id.edtBookPages2);

        btnUpd = findViewById(R.id.btnUpd);
        btnDel = findViewById(R.id.btnDel);

        getAndSetIntentData();

        btnUpd.setOnClickListener(v -> {
            MyDbHelper dbHelper = new MyDbHelper(this);
            title = String.valueOf(edtBookName2.getText());
            author = String.valueOf(edtBookAuthor2.getText());
            pages = String.valueOf(edtBookPages2.getText());

            dbHelper.updateDataById(id, title, author, pages);

            Intent in = new Intent(this, MainActivity.class);
            finish();
            startActivity(in);
        });

        btnDel.setOnClickListener(v -> {
            confirmDialog();


        });

    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deletar " + title);
        builder.setMessage("Tem certeza que deseja deletar " + title + "?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDbHelper dbHelper = new MyDbHelper(UpdateActivity.this);
                dbHelper.deleteOneRow(id);
                Intent in = new Intent(UpdateActivity.this, MainActivity.class);
                finish();
                startActivity(in);
            }
        });
        builder.setNegativeButton("Nao", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent in = new Intent(UpdateActivity.this, MainActivity.class);
                finish();
                startActivity(in);
            }
        });
        builder.create().show();
    }

    private void getAndSetIntentData() {
        if (getIntent().getExtras() == null) {
            Toast.makeText(this, "Error getIntentData()", Toast.LENGTH_SHORT).show();
        } else {
            this.id = getIntent().getStringExtra("id");
            this.title = getIntent().getStringExtra("title");
            this.author = getIntent().getStringExtra("author");
            this.pages = getIntent().getStringExtra("pages");

            setIntentData(title, author, pages);
        }
    }

    private void setIntentData(String title, String author, String pages) {
        edtBookName2.setText(title);
        edtBookAuthor2.setText(author);
        edtBookPages2.setText(pages);
    }
}