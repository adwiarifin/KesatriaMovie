package com.kesatriakeyboard.kesatriamovie.database;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_FAVOURITE = "tb_favourite";

    static final class FavouriteTable implements BaseColumns {
        static String MOVIE_ID = "movie_id";
        static String TITLE = "title";
        static String OVERVIEW = "overview";
        static String RELEASE_DATE = "date";
        static String POSTER_PATH = "poster_path";
        static String BACKDROP_PATH = "backdrop_path";
        static String GENRE_IDS = "genre_ids";
        static String VOTE_AVERAGE = "vote_average";
    }
}
