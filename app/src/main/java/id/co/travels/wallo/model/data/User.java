package id.co.travels.wallo.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("id_wallet")
    @Expose
    private String idWallet;
    @SerializedName("id_shop")
    @Expose
    private String idShop;
    @SerializedName("name_user")
    @Expose
    private String nameUser;
    @SerializedName("code_country")
    @Expose
    private String codeCountry;
    @SerializedName("email_user")
    @Expose
    private String emailUser;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("profile_picture_user")
    @Expose
    private String profilePictureUser;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdWallet() {
        return idWallet;
    }

    public String getIdShop() {
        return idShop;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getCodeCountry() {
        return codeCountry;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getImei() {
        return imei;
    }

    public String getProfilePictureUser() {
        return profilePictureUser;
    }
}
