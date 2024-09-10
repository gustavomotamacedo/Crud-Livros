package com.gustavomacedo.estudossqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcvList;
    FloatingActionButton fbtAdd;
    ImageView imgEmpty;

    MyDbHelper myDB;
    ArrayList<String> bookId;
    ArrayList<String> bookName;
    ArrayList<String> bookAuthor;
    ArrayList<String> bookPages;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rcvList = findViewById(R.id.rcvList);
        fbtAdd = findViewById(R.id.fbtAdd);
        imgEmpty = findViewById(R.id.imgEmpty);

        myDB = new MyDbHelper(this);

        bookId = new ArrayList<>();
        bookName = new ArrayList<>();
        bookAuthor = new ArrayList<>();
        bookPages = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(this, bookId, bookName, bookAuthor, bookPages);
        rcvList.setAdapter(customAdapter);
        rcvList.setLayoutManager(new LinearLayoutManager(this));

        fbtAdd.setOnClickListener((v) -> {
            Intent in = new Intent(this, AddActivity.class);
            startActivity(in);
        });
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            imgEmpty.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Não há dados", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if (imgEmpty.getVisibility() == View.VISIBLE)
                    imgEmpty.setVisibility(View.INVISIBLE);
                bookId.add(cursor.getString(0));
                bookName.add(cursor.getString(1));
                bookAuthor.add(cursor.getString(2));
                bookPages.add(cursor.getString(3));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteAll) {
            confirmDialog();

            Toast.makeText(this, "Deletados", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deletar todos");
        builder.setMessage("Tem certeza que deseja deletar todos os títulos?");
        builder.setPositiveButton("Sim", (dialog, which) -> {
            MyDbHelper dbHelper = new MyDbHelper(MainActivity.this);
            dbHelper.deleteAllData();
            Intent in = new Intent(MainActivity.this, MainActivity.class);
            startActivity(in);
            finish();
        });
        builder.setNegativeButton("Nao", (dialog, which) -> {
            Intent in = new Intent(MainActivity.this, MainActivity.class);
            startActivity(in);
            finish();
        });
        builder.create().show();
    }
}