
package com.example.mytestapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Links__1 {

    @SerializedName("self")
    @Expose
    private Self__1 self;
    @SerializedName("show")
    @Expose
    private Show show;

    public Self__1 getSelf() {
        return self;
    }

    public void setSelf(Self__1 self) {
        this.self = self;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

}
