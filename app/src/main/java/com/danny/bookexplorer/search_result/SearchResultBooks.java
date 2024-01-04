package com.danny.bookexplorer.search_result;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.danny.bookexplorer.adapter.search_result_adapter.BookResultAdapter;
import com.danny.bookexplorer.api.ElasticAPI;
import com.danny.bookexplorer.api.ElasticClient;
import com.danny.bookexplorer.databinding.ActivitySearchResultBooksBinding;
import com.danny.bookexplorer.model.Book;
import com.danny.bookexplorer.model.BookSource;
import com.danny.bookexplorer.model.SearchResult;

import java.util.List;

public class SearchResultBooks extends AppCompatActivity {

    private ActivitySearchResultBooksBinding binding;
    private ResultDetailsRepository detailsRepository;
    private String query;
    private SearchResultViewModel viewModel;
    private List<Book> bookList;
    private List<BookSource> bookSourceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBooksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ElasticAPI elasticAPI = ElasticClient.getElasticAPIClient();
        detailsRepository = new ResultDetailsRepository(elasticAPI);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");



        viewModel = getSearchResultViewModel(query);
        viewModel.getSearchResult().observe(this, searchResult -> {
            bindResultUI(searchResult);
        });




    }

    private void bindResultUI(SearchResult searchResult) {
        bookSourceList = searchResult.getHits().getHitList();

        BookResultAdapter adapter = new BookResultAdapter(bookSourceList);
        binding.rcvResult.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rcvResult.setAdapter(adapter);

    }

    public SearchResultViewModel getSearchResultViewModel(String query) {
        return new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new SearchResultViewModel(detailsRepository, query);
            }
        }).get(SearchResultViewModel.class);
    }
}