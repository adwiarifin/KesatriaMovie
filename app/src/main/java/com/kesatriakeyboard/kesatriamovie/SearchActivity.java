package com.kesatriakeyboard.kesatriamovie;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    private static final String EXTRAS_QUERY = "EXTRAS_QUERY";
    private Context context;

    private MovieCardAdapter adapter;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.text_query)
    EditText textQuery;
    @BindView(R.id.button_search)
    Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        context = this;
        adapter = new MovieCardAdapter(context);
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        rvMovie.setAdapter(adapter);

        String query = textQuery.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_QUERY, query);
        getLoaderManager().initLoader(0, bundle, this);
    }

    @OnClick(R.id.button_search)
    public void searchClicked() {
        String query = textQuery.getText().toString();
        if (query.length() < 4) {
            Toast.makeText(context, "Please input minimum 4 characters...", Toast.LENGTH_SHORT).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_QUERY, query);
            getLoaderManager().restartLoader(0, bundle, SearchActivity.this);
        }
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        String query = "";
        if (args != null) {
            query = args.getString(EXTRAS_QUERY);
        }

        return new MovieSearchLoader(context, query);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        adapter.setData(null);
    }
}
