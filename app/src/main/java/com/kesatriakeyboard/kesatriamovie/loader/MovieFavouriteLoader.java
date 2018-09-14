package com.kesatriakeyboard.kesatriamovie.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.kesatriakeyboard.kesatriamovie.pojo.MovieItem;

import java.util.ArrayList;

import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.CONTENT_URI;

public class MovieFavouriteLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {

    private ArrayList<MovieItem> mData;
    private boolean mHasResult = false;

    public MovieFavouriteLoader(final Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        } else if (mHasResult) {
            deliverResult(mData);
        }
    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
        ArrayList<MovieItem> favoriteMovies = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MovieItem item = new MovieItem(cursor);
                favoriteMovies.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return favoriteMovies;
    }

    private void onReleaseResources(ArrayList<MovieItem> data) {
        //nothing to do.
    }
}
