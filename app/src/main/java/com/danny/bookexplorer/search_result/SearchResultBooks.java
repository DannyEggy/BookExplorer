package com.danny.bookexplorer.search_result;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.danny.bookexplorer.adapter.search_result_adapter.BookResultAdapter;
import com.danny.bookexplorer.api.ElasticAPI;
import com.danny.bookexplorer.api.ElasticClient;
import com.danny.bookexplorer.databinding.ActivitySearchResultBooksBinding;
import com.danny.bookexplorer.model.Book;
import com.danny.bookexplorer.model.BookSource;
import com.danny.bookexplorer.model.Hit;
import com.danny.bookexplorer.model.SearchResult;
import com.danny.bookexplorer.repository.NetworkState;

import java.util.List;

public class SearchResultBooks extends AppCompatActivity {

    private ActivitySearchResultBooksBinding binding;
    private ResultDetailsRepository detailsRepository;
    private String query;
    private SearchResultViewModel viewModel;
    private List<Hit> hitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBooksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ElasticAPI elasticAPI = ElasticClient.getElasticAPIClient();
        detailsRepository = new ResultDetailsRepository(elasticAPI);

        Intent intent = getIntent();
        query = intent.getStringExtra("query");

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        viewModel = getSearchResultViewModel(query);




        viewModel.getSearchResult().observe(this, this::bindResultUI);
        viewModel.getNetworkState().observe(this, networkState -> {
            int visibility = (networkState == NetworkState.LOADING) ? View.VISIBLE : View.GONE;
            binding.progressBarSearchResult.setVisibility(visibility);
            binding.txtErrorSearchResult.setVisibility((networkState == NetworkState.ERROR) ? View.VISIBLE : View.GONE);
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void bindResultUI(SearchResult searchResult) {
        hitList = searchResult.getHits().getHits();

        BookResultAdapter adapter = new BookResultAdapter(hitList);
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