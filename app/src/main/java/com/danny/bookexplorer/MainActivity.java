package com.danny.bookexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.danny.bookexplorer.databinding.ActivityMainBinding;
import com.danny.bookexplorer.model.SearchResult;
import com.danny.bookexplorer.search_result.SearchResultBooks;
import com.danny.bookexplorer.single_book.SingleBook;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

//        activityMainBinding.btn.setOnClickListener((View view)->{
//            Intent intent = new Intent(this, SingleBook.class);
//            intent.putExtra("id", "YlMOvgAACAAJ");
//            startActivity(intent);
//        });

        activityMainBinding.btn.setOnClickListener((View view)->{
            Intent intent = new Intent(this, SearchResultBooks.class);
            intent.putExtra("query", "Harry Potter");
            startActivity(intent);
        });
    }
}