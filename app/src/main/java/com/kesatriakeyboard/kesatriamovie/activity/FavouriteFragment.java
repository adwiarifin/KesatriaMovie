package com.kesatriakeyboard.kesatriamovie.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.kesatriakeyboard.kesatriamovie.R;
import com.kesatriakeyboard.kesatriamovie.adapter.MovieCardAdapter;
import com.kesatriakeyboard.kesatriamovie.loader.MovieFavouriteLoader;
import com.kesatriakeyboard.kesatriamovie.pojo.MovieItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    private Context context;
    private MovieCardAdapter adapter;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, view);

        context = getActivity();
        adapter = new MovieCardAdapter(context);
        rvMovie.setLayoutManager(new LinearLayoutManager(context));
        rvMovie.setAdapter(adapter);

        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(MainActivity.LOADER_ID_FAVOURITE, bundle, this);

        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MovieFavouriteLoader(context);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItem>> loader) {
        adapter.setData(null);
    }
}
