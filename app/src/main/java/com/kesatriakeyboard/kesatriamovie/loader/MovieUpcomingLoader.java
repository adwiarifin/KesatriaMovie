package com.kesatriakeyboard.kesatriamovie.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.kesatriakeyboard.kesatriamovie.BuildConfig;
import com.kesatriakeyboard.kesatriamovie.pojo.MovieItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieUpcomingLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {

    private ArrayList<MovieItem> mData;
    private boolean mHasResult = false;

    public MovieUpcomingLoader(final Context context) {
        super(context);

        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        } else if (mHasResult) {
            deliverResult(mData);
        }
    }

    @Override
    public void deliverResult(final ArrayList<MovieItem> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        String apiKey = BuildConfig.API_KEY;
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + apiKey + "&language=en-US";

        final ArrayList<MovieItem> movieItems = new ArrayList<>();
        SyncHttpClient client = new SyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject resultObject = new JSONObject(result);
                    JSONArray list = resultObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        MovieItem movieItem = new MovieItem(item);
                        movieItems.add(movieItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // do nothing
            }
        });

        return movieItems;
    }

    private void onReleaseResources(ArrayList<MovieItem> data) {
        //nothing to do.
    }
}
