package id.co.travels.wallo.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.co.travels.wallo.model.data.Menu;
import id.co.travels.wallo.model.data.Token;

public class ResponseToken {

    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    Token token;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Token getToken() {
        return token;
    }
}
