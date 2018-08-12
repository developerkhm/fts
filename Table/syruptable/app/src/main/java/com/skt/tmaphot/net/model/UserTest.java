package com.skt.tmaphot.net.model;

import com.google.gson.annotations.SerializedName;

public class UserTest {

    @SerializedName("name")
    public String name;
    @SerializedName("job")
    public String job;
    @SerializedName("id")
    public String id;
    @SerializedName("createdAt")
    public String createdAt;

    public UserTest(String name, String job) {
        this.name = name;
        this.job = job;
    }
}