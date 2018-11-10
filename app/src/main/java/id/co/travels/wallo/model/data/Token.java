
package id.co.travels.wallo.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Token implements Serializable{

    @SerializedName("id_user")
    String idUser;
    @SerializedName("token")
    String token;

    public String getIdUser() {
        return idUser;
    }

    public String getToken() {
        return token;
    }
}
