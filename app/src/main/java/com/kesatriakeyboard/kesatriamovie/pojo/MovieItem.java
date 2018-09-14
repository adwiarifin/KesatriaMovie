package com.kesatriakeyboard.kesatriamovie.pojo;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.kesatriakeyboard.kesatriamovie.database.DatabaseContract;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.getColumnFloat;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.getColumnInt;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.getColumnString;

public class MovieItem implements Parcelable {

    private int id;
    private String title;
    private String overview;
    private String posterPath;
    private String backdropPath;
    private float voteAverage;
    private int[] genreIds;
    private Date release_date;

    public MovieItem(JSONObject object) {
        try {
            this.id = object.getInt("id");
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

    public MovieItem(int id, String title, String overview, String releaseDate, String posterPath, String backdropPath, String genreIds, float voteAverage) {
        try {
            this.id = id;
            this.title = title;
            this.overview = overview;
            this.posterPath = posterPath;
            this.backdropPath = backdropPath;
            this.voteAverage = voteAverage;

            String[] split = genreIds.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
            this.genreIds = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                this.genreIds[i] = Integer.parseInt(split[i]);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            this.release_date = sdf.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getMovieId() {
        return this.id;
    }

    public String getMovieUrl() {
        String MOVIE_PATH = "http:/https://www.themoviedb.org/movie/";
        return MOVIE_PATH + this.id;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public String getPosterUrl() {
        if (this.posterPath != null) {
            String POSTER_PATH = "http://image.tmdb.org/t/p/w185";
            return POSTER_PATH + this.posterPath;
        }
        return "https://via.placeholder.com/185x278?text=Poster+not+available";
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public String getBackdropUrl() {
        if (this.backdropPath != null) {
            String BACKDROP_PATH = "http://image.tmdb.org/t/p/w780";
            return BACKDROP_PATH + this.backdropPath;
        }
        return "https://via.placeholder.com/500x281?text=Backdrop+not+available";
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

    public String getOriginalReleaseDate() {
        if (this.release_date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(this.release_date);
    }

    public int[] getGenreIds() {
        return this.genreIds;
    }

    public String getGenreIdsAsString() {
        return Arrays.toString(genreIds);
    }

    public float getVoteAverage() {
        return this.voteAverage / 2;
    }


    public MovieItem(Cursor cursor) {
        try {
            this.id = getColumnInt(cursor, DatabaseContract.FavouriteTable.MOVIE_ID);
            this.title = getColumnString(cursor, DatabaseContract.FavouriteTable.TITLE);
            this.overview = getColumnString(cursor, DatabaseContract.FavouriteTable.OVERVIEW);
            this.posterPath = getColumnString(cursor, DatabaseContract.FavouriteTable.POSTER_PATH);
            this.backdropPath = getColumnString(cursor, DatabaseContract.FavouriteTable.BACKDROP_PATH);
            this.voteAverage = getColumnFloat(cursor, DatabaseContract.FavouriteTable.VOTE_AVERAGE);

            String sGenreIds = getColumnString(cursor, DatabaseContract.FavouriteTable.GENRE_IDS);
            String[] split = sGenreIds.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
            this.genreIds = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                this.genreIds[i] = Integer.parseInt(split[i]);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String sReleaseDate = getColumnString(cursor, DatabaseContract.FavouriteTable.RELEASE_DATE);
            this.release_date = sdf.parse(sReleaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private MovieItem(Parcel parcel) {
        this.id = parcel.readInt();
        this.title = parcel.readString();
        this.overview = parcel.readString();
        this.release_date = new Date(parcel.readLong());
        this.posterPath = parcel.readString();
        this.backdropPath = parcel.readString();
        this.genreIds = parcel.createIntArray();
        this.voteAverage = parcel.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.overview);
        parcel.writeLong(this.release_date.getTime());
        parcel.writeString(this.posterPath);
        parcel.writeString(this.backdropPath);
        parcel.writeIntArray(genreIds);
        parcel.writeFloat(this.voteAverage);
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel parcel) {
            return new MovieItem(parcel);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
