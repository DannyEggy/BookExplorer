package com.danny.bookexplorer.adapter.search_result_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danny.bookexplorer.databinding.ListItemBookBinding;
import com.danny.bookexplorer.model.BookSource;
import com.danny.bookexplorer.model.Hit;
import com.danny.bookexplorer.single_book.SingleBook;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookResultAdapter extends RecyclerView.Adapter<BookResultAdapter.BookResultViewHolder>{

    private List<Hit> hitList;
    private Context context;

    public BookResultAdapter(List<Hit> hitList) {
        this.hitList = hitList;
    }

    @NonNull
    @Override
    public BookResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ListItemBookBinding listItemBookBinding = ListItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);


        return new BookResultViewHolder(listItemBookBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookResultViewHolder holder, int position) {
        Hit hit = hitList.get(position);
        if(hit == null){
            return;
        }


        String bookID = hit.getId();
        holder.binding.bookTitleItem.setText(hit.getSource().getTitle());

        String imageUrl = "https://books.google.com/books/content?id=" + bookID + "&printsec=frontcover&img=1&zoom=1&source=gbs_api";
        Picasso.get().load(imageUrl).into(holder.binding.thumbnailItem);

        holder.binding.cardItem.setOnClickListener((View view)->{
            Intent intent = new Intent(context, SingleBook.class);
            intent.putExtra("id", bookID);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {

        return hitList.size();
    }

    public class BookResultViewHolder extends RecyclerView.ViewHolder{
        private ListItemBookBinding binding;


        public BookResultViewHolder(@NonNull ListItemBookBinding listItemBookBinding) {
            super(listItemBookBinding.getRoot());
            this.binding = listItemBookBinding;



        }
    }
}
