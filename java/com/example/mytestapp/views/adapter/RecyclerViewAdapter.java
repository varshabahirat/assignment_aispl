package com.example.mytestapp.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytestapp.R;
import com.example.mytestapp.models.Episode;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<Episode> episodesDataArrayList;
    private Context mContext;
    public RecyclerViewAdapter(ArrayList<Episode> recyclerDataArrayList, Context mContext) {
        this.episodesDataArrayList = recyclerDataArrayList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shows_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Episode episode = episodesDataArrayList.get(position);
        holder.episodeNameTV.setText(episode.getName());
        holder.episodeSeasonTV.setText(String.format("#%d", episode.getSeason()));
        holder.episodeTypeTV.setText(episode.getType());
        holder.tagTV.setText(String.format("Air Date : %s ", episode.getAirdate()));
    }

    @Override
    public int getItemCount() {
        return episodesDataArrayList.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView episodeNameTV, episodeSeasonTV, episodeTypeTV, tagTV;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeNameTV = itemView.findViewById(R.id.txt_episode_item_name);
            episodeSeasonTV = itemView.findViewById(R.id.txt_episode_item_season);
            episodeTypeTV = itemView.findViewById(R.id.txt_episode_item_type);
            tagTV = itemView.findViewById(R.id.txt_episode_item_tag);
        }
    }
}

