package com.kesatriakeyboard.kesatriamovie;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        MovieItem item = getIntent().getParcelableExtra("MOVIE_ITEM");

        textTitle.setText(item.getTitle());
        textOverview.setText(item.getOverview());
        textReleaseDate.setText(item.getReleaseDate());
        textGenres.setText(Helper.getInstance().getGenres(item.getGenreIds()));
        ratingBar.setRating(item.getVoteAverage());
        Picasso.get().load(item.getPosterUrl()).into(imagePoster);
        Picasso.get().load(item.getBackdropUrl()).into(imageBackdrop);
    }
}
