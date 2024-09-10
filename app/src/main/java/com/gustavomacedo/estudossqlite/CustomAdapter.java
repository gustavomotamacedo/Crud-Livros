package com.gustavomacedo.estudossqlite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> bookId, bookName, bookAuthor, bookPages;

    public CustomAdapter(Context context, ArrayList<String> bookId, ArrayList<String> bookName, ArrayList<String> bookAuthor, ArrayList<String> bookPages) {
        this.context = context;
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPages = bookPages;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.bookId.setText(String.valueOf(bookId.get(position)));
        holder.bookName.setText(String.valueOf(bookName.get(position)));
        holder.bookAuthor.setText(String.valueOf(bookAuthor.get(position)));
        holder.bookPages.setText(String.valueOf(bookPages.get(position)));
        holder.mainRowLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(bookId.get(position)));
            intent.putExtra("title", String.valueOf(bookName.get(position)));
            intent.putExtra("author", String.valueOf(bookAuthor.get(position)));
            intent.putExtra("pages", String.valueOf(bookPages.get(position)));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView bookId, bookName, bookAuthor, bookPages;
        private ConstraintLayout mainRowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookId = itemView.findViewById(R.id.txtBookId);
            bookName = itemView.findViewById(R.id.txtBookName);
            bookAuthor = itemView.findViewById(R.id.txtBookAuthor);
            bookPages = itemView.findViewById(R.id.txtBookPages);
            mainRowLayout = itemView.findViewById(R.id.mainRowLayout);
        }

    }

}
