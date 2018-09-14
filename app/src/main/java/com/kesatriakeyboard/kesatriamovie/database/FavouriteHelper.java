package com.kesatriakeyboard.kesatriamovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kesatriakeyboard.kesatriamovie.pojo.MovieItem;

import java.util.ArrayList;

import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.TABLE_FAVOURITE;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable._ID;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.MOVIE_ID;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.TITLE;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.OVERVIEW;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.RELEASE_DATE;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.POSTER_PATH;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.BACKDROP_PATH;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.GENRE_IDS;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.VOTE_AVERAGE;

public class FavouriteHelper {

    private Context context;
    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    public FavouriteHelper(Context context) {
        this.context = context;
    }

    public FavouriteHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    public ArrayList<MovieItem> getAllData() {
        Cursor cursor = db.query(TABLE_FAVOURITE, null, null, null, null, null, _ID + " DESC", null);
        cursor.moveToFirst();
        ArrayList<MovieItem> arrayList = new ArrayList<>();
        MovieItem movie;
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
                int movie_id = cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
                String overview = cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
                String release_date = cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE));
                String poster_path = cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH));
                String backdrop_path = cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_PATH));
                String genre_ids = cursor.getString(cursor.getColumnIndexOrThrow(GENRE_IDS));
                float vote_average = cursor.getFloat(cursor.getColumnIndexOrThrow(VOTE_AVERAGE));

                movie = new MovieItem(movie_id, title, overview, release_date, poster_path, backdrop_path, genre_ids, vote_average);
                arrayList.add(movie);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MovieItem movie) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MOVIE_ID, movie.getMovieId());
        initialValues.put(TITLE, movie.getTitle());
        initialValues.put(OVERVIEW, movie.getOverview());
        initialValues.put(RELEASE_DATE, movie.getOriginalReleaseDate());
        initialValues.put(POSTER_PATH, movie.getPosterPath());
        initialValues.put(BACKDROP_PATH, movie.getBackdropPath());
        initialValues.put(GENRE_IDS, movie.getGenreIdsAsString());
        initialValues.put(VOTE_AVERAGE, movie.getVoteAverage());
        return db.insert(TABLE_FAVOURITE, null, initialValues);
    }

    public int update(MovieItem movie) {
        ContentValues args = new ContentValues();
        args.put(MOVIE_ID, movie.getMovieId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(RELEASE_DATE, movie.getOriginalReleaseDate());
        args.put(POSTER_PATH, movie.getPosterPath());
        args.put(BACKDROP_PATH, movie.getBackdropPath());
        args.put(GENRE_IDS, movie.getGenreIdsAsString());
        args.put(VOTE_AVERAGE, movie.getVoteAverage());
        return db.update(TABLE_FAVOURITE, args, MOVIE_ID + " = '" + movie.getMovieId() + "'", null);
    }

    public int delete(int id) {
        return db.delete(TABLE_FAVOURITE, _ID + " = '" + id + "'", null);
    }


    public Cursor queryByIdProvider(String id) {
        return db.query(TABLE_FAVOURITE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return db.query(TABLE_FAVOURITE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return db.insert(TABLE_FAVOURITE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return db.update(TABLE_FAVOURITE, values, MOVIE_ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return db.delete(TABLE_FAVOURITE, _ID + " = ?", new String[]{id});
    }
}
