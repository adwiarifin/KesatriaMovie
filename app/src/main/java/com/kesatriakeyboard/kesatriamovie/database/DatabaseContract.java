package com.kesatriakeyboard.kesatriamovie.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_FAVOURITE = "tb_favourite";

    public static final class FavouriteTable implements BaseColumns {
        public static String MOVIE_ID = "movie_id";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "date";
        public static String POSTER_PATH = "poster_path";
        public static String BACKDROP_PATH = "backdrop_path";
        public static String GENRE_IDS = "genre_ids";
        public static String VOTE_AVERAGE = "vote_average";
    }

    public static final String AUTHORITY = "com.kesatriakeyboard.kesatriamovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVOURITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static float getColumnFloat(Cursor cursor, String columnName) {
        return cursor.getFloat( cursor.getColumnIndex(columnName) );
    }
}
