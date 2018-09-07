package com.kesatriakeyboard.kesatriamovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends BaseAdapter {

    private ArrayList<MovieItem> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItem> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final MovieItem item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public MovieItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.movie_item_list, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String url = mData.get(position).getPosterUrl();
        Picasso.get().load(url).into(holder.imagePoster);

        holder.textTitle.setText(mData.get(position).getTitle());
        holder.textOverview.setText(mData.get(position).getOverviewStripped());
        holder.textReleaseDate.setText(mData.get(position).getReleaseDate());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.imagePoster)
        ImageView imagePoster;
        @BindView(R.id.textTitle)
        TextView textTitle;
        @BindView(R.id.textOverview)
        TextView textOverview;
        @BindView(R.id.textDate)
        TextView textReleaseDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
