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

import com.kesatriakeyboard.kesatriamovie.R;
import com.kesatriakeyboard.kesatriamovie.adapter.MovieCardAdapter;
import com.kesatriakeyboard.kesatriamovie.loader.MovieUpcomingLoader;
import com.kesatriakeyboard.kesatriamovie.pojo.MovieItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    private Context context;
    private MovieCardAdapter adapter;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        ButterKnife.bind(this, view);

        context = getActivity();
        adapter = new MovieCardAdapter(context);
        rvMovie.setLayoutManager(new LinearLayoutManager(context));
        rvMovie.setAdapter(adapter);

        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(MainActivity.LOADER_ID_UPCOMING, bundle, this);

        return view;
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, Bundle bundle) {
        System.out.println("Upcoming Loader invoked");
        return new MovieUpcomingLoader(context);
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
