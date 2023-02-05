package com.example.mytestapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytestapp.databinding.FragmentTvShowsBinding;
import com.example.mytestapp.models.Episode;
import com.example.mytestapp.models.ShowsResponse;
import com.example.mytestapp.network.APIClient;
import com.example.mytestapp.repository.APIInterface;
import com.example.mytestapp.repository.LocalDBRepository;
import com.example.mytestapp.views.adapter.RecyclerViewAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsFragment extends Fragment {

    private FragmentTvShowsBinding binding;
    APIInterface apiInterface;
    private RecyclerView courseRV;
    private RecyclerViewAdapter recyclerViewAdapter;

    private LocalDBRepository localDBRepository;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentTvShowsBinding.inflate(inflater, container, false);
        initScreen();
        return binding.getRoot();
    }

    private void initScreen(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
        courseRV = binding.recyclerViewEpisodes;
        localDBRepository = new LocalDBRepository(getActivity().getApplication());
        getTVEpisodesData();
    }

    private void getTVEpisodesData(){
        Call<ShowsResponse> call = apiInterface.doGetShowsList();
        call.enqueue(new Callback<ShowsResponse>() {
            @Override
            public void onResponse(Call<ShowsResponse> call, Response<ShowsResponse> response) {

                binding.tvName.setText(response.body().getName());
                binding.tvSummary.setText(String.format("Summary : %s", response.body().getSummary()));
                binding.tvLanguage.setText(String.format("Language : %s", response.body().getLanguage()));

                storeDataToLocalDB(response);
            }

            @Override
            public void onFailure(Call<ShowsResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void storeDataToLocalDB(Response<ShowsResponse> response){
        for (Episode episode: response.body().getEmbedded().getEpisodes() ) {
            localDBRepository.insert(episode);
        }
        fetchDataFromLocalDB();
    }

    private void fetchDataFromLocalDB(){
        localDBRepository.getAllEpisodes().observe(getActivity(), new Observer<List<Episode>>() {
            @Override
            public void onChanged(List<Episode> models) {
                displayData((ArrayList<Episode>) models);
            }
        });
    }

    private void displayData(ArrayList<Episode> recyclerDataArrayLis){
        recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayLis, getContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        courseRV.setLayoutManager(manager);
        courseRV.setAdapter(recyclerViewAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Episode deletedCourse = recyclerDataArrayLis.get(viewHolder.getAdapterPosition());

                int position = viewHolder.getAdapterPosition();
                recyclerDataArrayLis.remove(viewHolder.getAdapterPosition());

                recyclerViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Snackbar.make(courseRV, deletedCourse.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerDataArrayLis.add(position, deletedCourse);
                        recyclerViewAdapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }).attachToRecyclerView(courseRV);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}