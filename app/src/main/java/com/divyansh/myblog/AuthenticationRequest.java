package com.divyansh.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Divyansh on 18-03-2018.
 */

public class AuthenticationRequest {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
