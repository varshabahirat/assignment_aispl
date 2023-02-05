
package com.example.mytestapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Rating__1 {

    @SerializedName("average")
    @Expose
    private Double average;

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

}
