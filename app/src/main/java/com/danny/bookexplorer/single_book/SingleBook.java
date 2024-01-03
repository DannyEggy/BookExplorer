package com.danny.bookexplorer.single_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.danny.bookexplorer.R;
import com.danny.bookexplorer.api.ElasticAPI;
import com.danny.bookexplorer.api.ElasticClient;
import com.danny.bookexplorer.databinding.ActivitySingleBookBinding;
import com.danny.bookexplorer.model.Book;
import com.danny.bookexplorer.model.BookSource;
import com.danny.bookexplorer.repository.NetworkState;
import com.squareup.picasso.Picasso;

public class SingleBook extends AppCompatActivity {

    private ActivitySingleBookBinding binding;
    private BookDetailsRepository bookDetailsRepository;
    private String bookID;
    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        bookID = intent.getStringExtra("id");

        ElasticAPI elasticAPI = ElasticClient.getElasticAPIClient();
        bookDetailsRepository = new BookDetailsRepository(elasticAPI);

        bookViewModel = getBookViewModel(bookID);
        bookViewModel.getBookDetails().observe(this, book -> {
            bindUI(book);
        });

        bookViewModel.getNetworkState().observe(this, networkState -> {
            int visibility = (networkState == NetworkState.LOADING) ? View.VISIBLE : View.GONE;
            binding.progressBar.setVisibility(visibility);
            binding.txtError.setVisibility((networkState == NetworkState.ERROR) ? View.VISIBLE : View.GONE);
        });

    }

    private void bindUI(BookSource book) {

        String imageUrl = "https://books.google.com/books/content?id=" + bookID + "&printsec=frontcover&img=1&zoom=1&source=gbs_api";
        Picasso.get().load(imageUrl).into(binding.thumbnail);



        binding.bookTitle.setText(book.getTitle());
        binding.bookMaturityRating.setText(book.getMaturityRating());
        binding.bookPublishedDate.setText(book.getPublishedDate());
        binding.bookPageCount.setText(String.valueOf(book.getPageCount()));

//        StringBuilder stringBuilder = new StringBuilder();
//        for (String author : book.getAuthors()) {
//            stringBuilder.append(author).append(", ");
//        }
//
        binding.bookAuthor.setText(book.getAuthors());
        binding.bookAverageRating.setText(String.valueOf(book.getAverageRating()));
//        StringBuilder stringBuilder2 = new StringBuilder();
//        for (String category : book.getCategories()) {
//            stringBuilder2.append(category).append(", ");
//        }
        binding.bookCategories.setText(book.getCategories());
        binding.bookDesc.setText(book.getDescription());
    }

    private BookViewModel getBookViewModel(String bookID){
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @NonNull
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new BookViewModel(bookDetailsRepository, bookID);
            }
        }).get(BookViewModel.class);
    }
}