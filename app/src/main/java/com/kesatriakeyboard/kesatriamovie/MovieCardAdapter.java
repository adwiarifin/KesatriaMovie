package com.kesatriakeyboard.kesatriamovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.CardViewHolder> {

    private ArrayList<MovieItem> mData = new ArrayList<>();
    private Context context;

    public MovieCardAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieItem> items) {
        mData = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String url = mData.get(position).getPosterUrl();
        Picasso.get().load(url).into(holder.imagePoster);

        holder.textTitle.setText(mData.get(position).getTitle());
        holder.textOverview.setText(mData.get(position).getOverviewStripped());
        holder.textReleaseDate.setText(mData.get(position).getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imagePoster)
        ImageView imagePoster;
        @BindView(R.id.textTitle)
        TextView textTitle;
        @BindView(R.id.textOverview)
        TextView textOverview;
        @BindView(R.id.textDate)
        TextView textReleaseDate;
        @BindView(R.id.buttonDetail)
        Button buttonDetail;
        @BindView(R.id.buttonShare)
        Button buttonShare;

        CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
