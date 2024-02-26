package com.danny.bookexplorer.autocomplete_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.widget.TextView;

import com.danny.bookexplorer.R;

import java.util.List;

public class AutoCompleteRecentSearch extends ArrayAdapter<String> {
    private List<String> recentSearchList;

    public AutoCompleteRecentSearch(@NonNull Context context, @NonNull List<String> recentSearchList ){
        super(context, 0, recentSearchList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view ==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.recent_search_item, parent, false );

        }


        String recent_search = getItem(position);
        TextView title_recent_search = view.findViewById(R.id.recent_search_title);
        title_recent_search.setText(recent_search);

        return view;
    }
}
