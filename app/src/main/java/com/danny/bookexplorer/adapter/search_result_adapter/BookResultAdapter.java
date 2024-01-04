package com.danny.bookexplorer.adapter.search_result_adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danny.bookexplorer.databinding.ListItemBookBinding;
import com.danny.bookexplorer.model.BookSource;

import java.util.List;

public class BookResultAdapter extends RecyclerView.Adapter<BookResultAdapter.BookResultViewHolder>{

    private List<BookSource> bookList;

    public BookResultAdapter(List<BookSource> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBookBinding listItemBookBinding = ListItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);


        return new BookResultViewHolder(listItemBookBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookResultViewHolder holder, int position) {
        BookSource book = bookList.get(position);
        if(book == null){
            return;
        }
        holder.binding.bookTitleItem.setText(book.getTitle());

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookResultViewHolder extends RecyclerView.ViewHolder{
        private ListItemBookBinding binding;


        public BookResultViewHolder(@NonNull ListItemBookBinding listItemBookBinding) {
            super(listItemBookBinding.getRoot());
            this.binding = listItemBookBinding;



        }
    }
}
