package id.co.travels.wallo.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.co.travels.wallo.model.data.Menu;
import id.co.travels.wallo.model.data.User;

public class ResponseMenu {

    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<Menu> listMenu;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Menu> getListMenu() {
        return listMenu;
    }
}
