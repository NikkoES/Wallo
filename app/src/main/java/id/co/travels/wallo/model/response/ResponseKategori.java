package id.co.travels.wallo.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

import id.co.travels.wallo.model.data.Kategori;
import id.co.travels.wallo.model.data.User;

public class ResponseKategori {

    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<Kategori> listKategori;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Kategori> getListKategori() {
        return listKategori;
    }
}
