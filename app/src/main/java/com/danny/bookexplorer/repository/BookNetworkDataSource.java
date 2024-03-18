package com.danny.bookexplorer.repository;



import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.danny.bookexplorer.api.ElasticAPI;
import com.danny.bookexplorer.model.BookSource;
import com.danny.bookexplorer.model.search_request_1.Knn;
import com.danny.bookexplorer.model.search_request_1.MatchPhrase;
import com.danny.bookexplorer.model.search_request_1.Query;
import com.danny.bookexplorer.model.search_request_1.Rank;
import com.danny.bookexplorer.model.search_request_1.Rrf;
import com.danny.bookexplorer.model.search_request_1.SearchRequest;
import com.danny.bookexplorer.model.SearchResult;
import com.danny.bookexplorer.model.search_request_2.AverageRating;
import com.danny.bookexplorer.model.search_request_2.Bool;
import com.danny.bookexplorer.model.search_request_2.Match;
import com.danny.bookexplorer.model.search_request_2.MatchPhrase2;
import com.danny.bookexplorer.model.search_request_2.Must;
import com.danny.bookexplorer.model.search_request_2.PageCount;
import com.danny.bookexplorer.model.search_request_2.Query2;
import com.danny.bookexplorer.model.search_request_2.Range;
import com.danny.bookexplorer.model.search_request_2.SearchRequest2;


//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch.core.SearchResponse;
//import co.elastic.clients.elasticsearch.core.search.Hit;
//import co.elastic.clients.elasticsearch.core.search.TotalHits;
//import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BookNetworkDataSource {
    private final ElasticAPI elasticAPI;

//    private ModelAPI modelAPI;
    private final CompositeDisposable compositeDisposable;

    private final MutableLiveData<BookSource> _downloadBookDetailsResponse = new MutableLiveData<BookSource>();
    private final MutableLiveData<NetworkState> _networkState = new MutableLiveData<NetworkState>();

    private final MutableLiveData<SearchResult> _searchResult = new MutableLiveData<SearchResult>();

    private MutableLiveData<double[]> _downloadVectorResponse = new MutableLiveData<double[]>();

    public MutableLiveData<double[]> downloadVectorResponse() {
        return _downloadVectorResponse;
    }

    public BookNetworkDataSource(ElasticAPI elasticAPI, CompositeDisposable compositeDisposable) {
        this.elasticAPI = elasticAPI;
        this.compositeDisposable = compositeDisposable;
    }

//    public BookNetworkDataSource(ModelAPI modelAPI, CompositeDisposable compositeDisposable){
//        this.modelAPI = modelAPI;
//        this.compositeDisposable= compositeDisposable;
//    }


    public LiveData<NetworkState> networkState(){
        return _networkState;
    }

    public MutableLiveData<BookSource> downloadBookDetailsResponse() {
        return _downloadBookDetailsResponse;
    }

    public MutableLiveData<SearchResult> searchResult() {
        return _searchResult;
    }




    public void hybridSearch(String querySearch, String knnField, double[] knnQueryVector){
        _networkState.postValue(NetworkState.LOADING);
        try{

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setSource(new String[]{"title"});

            // Thiết lập truy vấn cho title
            Query query = new Query();
            MatchPhrase matchPhrase = new MatchPhrase();
            matchPhrase.setDesc(querySearch);
            query.setMatchPhrase(matchPhrase);
            searchRequest.setQuery(query);

            // Thiết lập truy vấn KNN
            Knn knn = new Knn("desc_vector", knnQueryVector, 50, 100);
            searchRequest.setKnn(knn);

            // Thiết lập rank
            Rank rank = new Rank();
            Rrf rrf = new Rrf(50, 20);
            rank.setRrf(rrf);
            searchRequest.setRank(rank);


            compositeDisposable.add(
                    elasticAPI.hybridSearch(searchRequest)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    searchResult -> {
                                        _searchResult.postValue(searchResult);
                                        _networkState.postValue(NetworkState.LOADED);
                                    },
                                    throwable -> {
                                        Log.e("BookDetailsDataSource","Error: " + throwable.getMessage());
                                        _networkState.postValue(NetworkState.ERROR);
                                    }
                            )
            );
        }catch (Exception e){
            _networkState.postValue(new NetworkState(NetworkState.Status.FAILED, "Something went wrong!!!"));
            Log.e("BookDetailsDataSource Lỗi chỗ này nè", e.getMessage());
        }

    }

    public void multipleSearch(String queryTitle, String queryDesc, int pageCountGTE,
                               int pageCountLTE, double averageRatingGTE, double averageRatingLTE){
        _networkState.postValue(NetworkState.LOADING);
        try{
            SearchRequest2 searchRequest2 = new SearchRequest2();
            Query2 query = new Query2();
            Bool bool = new Bool();

            MatchPhrase2 matchPhrase = new MatchPhrase2();
            matchPhrase.setTitle(queryTitle);
            Match match = new Match();
            match.setDesc(queryDesc);
            Range range = new Range(new AverageRating(averageRatingGTE, averageRatingLTE));

            Must[] must = new Must[]{new Must(matchPhrase), new Must(match), new Must(range)};
            bool.setMust(must);
            query.setBool(bool);
            searchRequest2.setQuery(query);

            compositeDisposable.add(
                    elasticAPI.multipleSearch(searchRequest2)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    searchResult -> {
                                        _searchResult.postValue(searchResult);
                                        _networkState.postValue(NetworkState.LOADED);

                                    },
                                    throwable -> {
                                        Log.e("BookDetailsDataSource","Error: " + throwable.getMessage());
                                        _networkState.postValue(NetworkState.ERROR);
                                    }
                            )
            );

        }catch (Exception e){
            _networkState.postValue(new NetworkState(NetworkState.Status.FAILED, "Something went wrong!!!"));
            Log.e("BookDetailsDataSource Lỗi chỗ này nè", e.getMessage());
        }
    }


    public void searchBooks(String query){

        _networkState.postValue(NetworkState.LOADING);

        try {
            compositeDisposable.add(
                    elasticAPI.searchBooks(query)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    searchResult -> {
                                        _searchResult.postValue(searchResult);
                                        _networkState.postValue(NetworkState.LOADED);
                                    },
                                    throwable -> {
                                        Log.e("BookDetailsDataSource","Error: " + throwable.getMessage());
                                        _networkState.postValue(NetworkState.ERROR);
                                    }
                            )
            );
        }catch (Exception e){
            _networkState.postValue(new NetworkState(NetworkState.Status.FAILED, "Something went wrong"));
            Log.e("BookDetailsDataSource", "Loi: "+query +e.getMessage());
        }


    }



    public void fetchBookDetails(String bookID){
        _networkState.postValue(NetworkState.LOADING);

        try {
            compositeDisposable.add(
                elasticAPI.getBook(bookID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                bookResponse -> {
                                    BookSource book = bookResponse.getSource();
                                    _downloadBookDetailsResponse.postValue(book);
                                    _networkState.postValue(NetworkState.LOADED);
                                },
                                throwable -> {
                                    Log.e("BookDetailsDataSource", throwable.getMessage());
                                    _networkState.postValue(NetworkState.ERROR);
                                }
                        )
            );
        }catch (Exception e){
            _networkState.postValue(new NetworkState(NetworkState.Status.FAILED, "Something went wrong"));
            Log.e("BookDetailsDataSource", e.getMessage());
        }
    }



}
