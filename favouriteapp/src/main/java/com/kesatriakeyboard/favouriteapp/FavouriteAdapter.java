package com.kesatriakeyboard.favouriteapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FavouriteAdapter extends CursorAdapter {

    public FavouriteAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_list, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            ImageView imagePoster = view.findViewById(R.id.imagePoster);
            TextView textTitle = view.findViewById(R.id.textTitle);
            TextView textOverview = view.findViewById(R.id.textOverview);
            TextView textReleaseDate = view.findViewById(R.id.textDate);

            MovieItem item = new MovieItem(cursor);

            String url = item.getPosterUrl();
            Picasso.get().load(url).into(imagePoster);

            textTitle.setText(item.getTitle());
            textOverview.setText(item.getOverviewStripped());
            textReleaseDate.setText(item.getReleaseDate());
        }
    }
}
