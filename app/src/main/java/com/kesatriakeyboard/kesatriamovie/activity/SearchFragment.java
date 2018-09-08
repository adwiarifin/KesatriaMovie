package com.kesatriakeyboard.kesatriamovie.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kesatriakeyboard.kesatriamovie.R;
import com.kesatriakeyboard.kesatriamovie.adapter.MovieCardAdapter;
import com.kesatriakeyboard.kesatriamovie.loader.MovieSearchLoader;
import com.kesatriakeyboard.kesatriamovie.pojo.MovieItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    public static final String EXTRAS_QUERY = "EXTRAS_QUERY";
    private Context context;

    private MovieCardAdapter adapter;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.text_query)
    EditText textQuery;
    @BindView(R.id.button_search)
    Button buttonSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        context = getActivity();
        adapter = new MovieCardAdapter(context);
        rvMovie.setLayoutManager(new LinearLayoutManager(context));
        rvMovie.setAdapter(adapter);

        String query = textQuery.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_QUERY, query);
        getLoaderManager().initLoader(0, bundle, this);

        return view;
    }

    @OnClick(R.id.button_search)
    public void searchClicked() {
        String query = textQuery.getText().toString();
        if (query.length() < 4) {
            Toast.makeText(context, "Please input minimum 4 characters...", Toast.LENGTH_SHORT).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_QUERY, query);
            getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
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
