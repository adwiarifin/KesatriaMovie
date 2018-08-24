package com.kesatriakeyboard.kesatriamovie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieItem {

    private String title;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private float voteAverage;
    private int[] genreIds;
    private Date release_date;

    private final String DEFAULT_POSTER_PATH = "https://via.placeholder.com/185x278?text=Poster+not+available";
    private final String DEFAULT_BACKDROP_PATH = "https://via.placeholder.com/500x281?text=Backdrop+not+available";
    private final String POSTER_PATH = "http://image.tmdb.org/t/p/w185";
    private final String BACKDROP_PATH = "http://image.tmdb.org/t/p/w780";

    public MovieItem(JSONObject object) {
        try {
            this.title = object.getString("title");
            this.overview = object.getString("overview");
            this.posterPath = object.getString("poster_path");
            this.backdropPath = object.getString("backdrop_path");
            this.voteAverage = (float) object.getDouble("vote_average");

            JSONArray arr = object.getJSONArray("genre_ids");
            genreIds = new int[arr.length()];
            for (int i = 0; i < arr.length(); i++) {
                genreIds[i] = arr.getInt(i);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            this.release_date = sdf.parse(object.getString("release_date"));
        } catch (ParseException e) {
            this.release_date = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPosterUrl() {
        if (this.posterPath != null) {
            return POSTER_PATH + this.posterPath;
        }
        return DEFAULT_POSTER_PATH;
    }

    public String getBackdropUrl() {
        if (this.backdropPath != null) {
            return BACKDROP_PATH + this.backdropPath;
        }
        return DEFAULT_BACKDROP_PATH;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOverview() {
        return this.overview;
    }

    public String getOverviewStripped() {
        if (this.overview.length() < 50) {
            return this.overview;
        }
        return this.overview.substring(0, 50) + "...";
    }

    public String getReleaseDate() {
        if (this.release_date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault());
        return sdf.format(this.release_date);
    }

    public int[] getGenreIds() {
        return this.genreIds;
    }

    public float getVoteAverage() {
        return this.voteAverage / 2;
    }
}
