package com.kesatriakeyboard.kesatriamovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imagePoster) ImageView imagePoster;
    @BindView(R.id.imageBackdrop) ImageView imageBackdrop;
    @BindView(R.id.textTitle) TextView textTitle;
    @BindView(R.id.textOverview) TextView textOverview;
    @BindView(R.id.textDate) TextView textReleaseDate;
    @BindView(R.id.textGenres) TextView textGenres;
    @BindView(R.id.ratingBar) RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        String overview = extras.getString("overview");
        String releaseDate = extras.getString("releaseDate");
        String posterUrl = extras.getString("posterUrl");
        String backdropUrl = extras.getString("backdropUrl");
        String genres = extras.getString("genres");
        float vote = extras.getFloat("voteAverage");

        textTitle.setText(title);
        textOverview.setText(overview);
        textReleaseDate.setText(releaseDate);
        textGenres.setText(genres);
        ratingBar.setRating(vote);
        Picasso.get().load(posterUrl).into(imagePoster);
        Picasso.get().load(backdropUrl).into(imageBackdrop);
    }
}
