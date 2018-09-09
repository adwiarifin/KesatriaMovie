package com.kesatriakeyboard.kesatriamovie.tool;

import android.util.SparseArray;

public class Helper {

    private SparseArray<String> genres;
    private static Helper helper;
    private String query;

    private Helper() {
        genres = new SparseArray<String>();
        genres.put(28, "action");
        genres.put(16, "animated");
        genres.put(99, "documentary");
        genres.put(18, "drama");
        genres.put(10751, "family");
        genres.put(14, "fantasy");
        genres.put(36, "history");
        genres.put(35, "comedy");
        genres.put(10752, "war");
        genres.put(80, "crime");
        genres.put(10402, "music");
        genres.put(9648, "mystery");
        genres.put(10749, "romance");
        genres.put(878, "sci fi");
        genres.put(27, "horror");
        genres.put(10770, "TV movie");
        genres.put(53, "thriller");
        genres.put(37, "western");
        genres.put(12, "adventure");

        query = "";
    }

    public static Helper getInstance() {
        if (helper == null) {
            helper = new Helper();
        }
        return helper;
    }

    public String getGenres(int[] genreIds) {
        String result = "";
        for (int i = 0; i < genreIds.length; i++) {
            result = result + getGenre(genreIds[i]);
            if (i < genreIds.length - 1) {
                result = result + ", ";
            }
        }

        return result;
    }

    private String getGenre(int genreId) {
        return genres.get(genreId);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
