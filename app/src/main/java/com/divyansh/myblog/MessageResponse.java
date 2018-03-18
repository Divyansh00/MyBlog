package com.divyansh.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Divyansh on 18-03-2018.
 */

public class MessageResponse {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message.toString();
    }
}
