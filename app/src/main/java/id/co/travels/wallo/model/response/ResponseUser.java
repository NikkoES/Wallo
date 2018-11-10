package id.co.travels.wallo.model.response;

import com.google.gson.annotations.SerializedName;

import id.co.travels.wallo.model.data.User;

public class ResponseUser {

    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    User user;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
