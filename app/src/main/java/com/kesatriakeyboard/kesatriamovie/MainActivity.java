package com.kesatriakeyboard.kesatriamovie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private RequestQueue queue;
    private MovieAdapter adapter;

    @BindView(R.id.listView) ListView listView;
    @BindView(R.id.text_query) EditText textQuery;
    @BindView(R.id.button_search) Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;
        queue = Volley.newRequestQueue(context);
        adapter = new MovieAdapter(context);
        listView.setAdapter(adapter);
    }

    private void getMovieList(String query) {
        String apiKey = BuildConfig.API_KEY;
        try {
            query = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&language=en-US&query=" + query;
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.wtf(response.toString(), "utf-8");

                try {
                    JSONArray results = response.getJSONArray("results");

                    if (results.length() > 0) {
                        ArrayList<MovieItem> movieItems = new ArrayList<>();
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject item = results.getJSONObject(i);
                            MovieItem movieItem = new MovieItem(item);
                            movieItems.add(movieItem);
                        }

                        adapter.setData(movieItems);
                    } else {
                        Toast.makeText(context, "Film tak ditemukan, coba cari dengan kata kunci lain...", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VolleyError", "Error: " + error.getMessage());
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    @OnClick(R.id.button_search)
    public void searchClicked() {
        String query = textQuery.getText().toString();
        if (query.length() < 4) {
            Toast.makeText(context, "Please input minimum 4 characters...", Toast.LENGTH_SHORT).show();
        } else {
            getMovieList(query);
        }
    }

    @OnItemClick(R.id.listView)
    public void listItemClicked(AdapterView<?> parent, View view, int position, long id) {
        MovieItem item = adapter.getItem(position);
        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        detailIntent.putExtra("title", item.getTitle());
        detailIntent.putExtra("overview", item.getOverview());
        detailIntent.putExtra("releaseDate", item.getReleaseDate());
        detailIntent.putExtra("posterUrl", item.getPosterUrl());
        detailIntent.putExtra("backdropUrl", item.getBackdropUrl());
        detailIntent.putExtra("genres", Helper.getInstance().getGenres(item.getGenreIds()));
        detailIntent.putExtra("voteAverage", item.getVoteAverage());
        startActivity(detailIntent);
    }
}
