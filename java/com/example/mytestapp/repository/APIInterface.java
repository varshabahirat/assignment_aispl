package com.example.mytestapp.repository;

import com.example.mytestapp.models.ShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("/singlesearch/shows?q=girls&embed=episodes")
    Call<ShowsResponse> doGetShowsList();
}
