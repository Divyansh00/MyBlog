package com.divyansh.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Divyansh on 18-03-2018.
 */

public class ErrorResponse {

    @SerializedName("error")
    String error;

    public String getError() {
        return error.toString();
    }
}
