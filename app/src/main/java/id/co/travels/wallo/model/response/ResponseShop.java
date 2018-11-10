package id.co.travels.wallo.model.response;

import com.google.gson.annotations.SerializedName;

import id.co.travels.wallo.model.data.Shop;
import id.co.travels.wallo.model.data.User;

public class ResponseShop {

    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data_shop")
    Shop shop;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Shop getShop() {
        return shop;
    }
}
