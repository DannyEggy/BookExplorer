package com.danny.bookexplorer.single_book;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.danny.bookexplorer.model.Book;
import com.danny.bookexplorer.model.BookSource;
import com.danny.bookexplorer.repository.NetworkState;

import java.io.Closeable;

import io.reactivex.disposables.CompositeDisposable;

public class BookViewModel extends ViewModel {
    private final BookDetailsRepository bookDetailsRepository;
    private final String bookID;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BookViewModel(BookDetailsRepository bookDetailsRepository, String bookID) {
        this.bookDetailsRepository = bookDetailsRepository;
        this.bookID = bookID;
    }

//    public BookViewModel(BookDetailsRepository bookDetailsRepository, String bookID, CompositeDisposable compositeDisposable, @NonNull Closeable... closeables) {
//        super(closeables);
//        this.bookDetailsRepository = bookDetailsRepository;
//        this.bookID = bookID;
//        this.compositeDisposable = compositeDisposable;
//    }

    public MutableLiveData<BookSource> getBookDetails(){
        return bookDetailsRepository.fetchSingleBookDetails(bookID, compositeDisposable);
    }

    public LiveData<NetworkState> getNetworkState(){
        return bookDetailsRepository.getBookDetailsNetworkState();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
