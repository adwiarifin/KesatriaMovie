package com.kesatriakeyboard.kesatriamovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "db_movie";

    private static final int DATABASE_VERSION = 1;

    private static String CREATE_TABLE_FAVOURITE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s INTEGER UNIQUE," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL" +
                    "%s TEXT NOT NULL" +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s REAL);",
            DatabaseContract.TABLE_FAVOURITE,
            DatabaseContract.FavouriteTable._ID,
            DatabaseContract.FavouriteTable.MOVIE_ID,
            DatabaseContract.FavouriteTable.TITLE,
            DatabaseContract.FavouriteTable.OVERVIEW,
            DatabaseContract.FavouriteTable.RELEASE_DATE,
            DatabaseContract.FavouriteTable.POSTER_PATH,
            DatabaseContract.FavouriteTable.BACKDROP_PATH,
            DatabaseContract.FavouriteTable.GENRE_IDS,
            DatabaseContract.FavouriteTable.VOTE_AVERAGE
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVOURITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVOURITE);
        onCreate(db);
    }
}
