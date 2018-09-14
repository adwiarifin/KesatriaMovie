package com.kesatriakeyboard.kesatriamovie.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.kesatriakeyboard.kesatriamovie.tool.Helper;
import com.kesatriakeyboard.kesatriamovie.pojo.MovieItem;
import com.kesatriakeyboard.kesatriamovie.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.CONTENT_URI;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.BACKDROP_PATH;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.GENRE_IDS;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.MOVIE_ID;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.OVERVIEW;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.POSTER_PATH;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.RELEASE_DATE;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.TITLE;
import static com.kesatriakeyboard.kesatriamovie.database.DatabaseContract.FavouriteTable.VOTE_AVERAGE;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imagePoster)
    ImageView imagePoster;
    @BindView(R.id.imageBackdrop)
    ImageView imageBackdrop;
    @BindView(R.id.textTitle)
    TextView textTitle;
    @BindView(R.id.textOverview)
    TextView textOverview;
    @BindView(R.id.textDate)
    TextView textReleaseDate;
    @BindView(R.id.textGenres)
    TextView textGenres;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.button_favourite)
    ToggleButton favButton;

    MovieItem movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        movie = getIntent().getParcelableExtra("MOVIE_ITEM");

        textTitle.setText(movie.getTitle());
        textOverview.setText(movie.getOverview());
        textReleaseDate.setText(movie.getReleaseDate());
        textGenres.setText(Helper.getInstance().getGenres(movie.getGenreIds()));
        ratingBar.setRating(movie.getVoteAverage());
        Picasso.get().load(movie.getPosterUrl()).into(imagePoster);
        Picasso.get().load(movie.getBackdropUrl()).into(imageBackdrop);

        favButton.setChecked(false);
        favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_off));
        favButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_on));
                } else {
                    favButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_off));
                }
            }
        });

        checkFavorited();
    }

    private void checkFavorited() {
        Uri uri = Uri.parse(CONTENT_URI+"/"+movie.getMovieId());
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                favButton.setChecked(true);
            } else {
                favButton.setChecked(false);
            }
            cursor.close();
        } else {
            Log.e(getClass().getSimpleName(), "cursor is null");
        }
    }

    @OnClick(R.id.button_favourite)
    public void favouriteClicked() {
        if (favButton.isChecked()) {
            // save data
            ContentValues values = new ContentValues();
            values.put(MOVIE_ID, movie.getMovieId());
            values.put(TITLE, movie.getTitle());
            values.put(OVERVIEW, movie.getOverview());
            values.put(RELEASE_DATE, movie.getOriginalReleaseDate());
            values.put(POSTER_PATH, movie.getPosterPath());
            values.put(BACKDROP_PATH, movie.getBackdropPath());
            values.put(GENRE_IDS, movie.getGenreIdsAsString());
            values.put(VOTE_AVERAGE, movie.getVoteAverage());

            getApplicationContext().getContentResolver().insert(CONTENT_URI, values);
        } else {
            // delete data
            Uri uri = Uri.parse(CONTENT_URI+"/"+movie.getMovieId());
            getApplicationContext().getContentResolver().delete(uri, null, null);
        }
    }
}
