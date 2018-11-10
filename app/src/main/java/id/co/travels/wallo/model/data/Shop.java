package id.co.travels.wallo.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop {

    @SerializedName("id_shop")
    @Expose
    private String idShop;
    @SerializedName("name_shop")
    @Expose
    private String nameShop;
    @SerializedName("description_shop")
    @Expose
    private String descriptionShop;
    @SerializedName("latitude_shop")
    @Expose
    private String latitudeShop;
    @SerializedName("longtitude_shop")
    @Expose
    private String longtitudeShop;
    @SerializedName("real_photo_shop")
    @Expose
    private String realPhotoShop;
    @SerializedName("hari_buka_shop")
    @Expose
    private String hariBukaShop;
    @SerializedName("open_hour_shop")
    @Expose
    private String openHourShop;
    @SerializedName("close_hour_shop")
    @Expose
    private String closeHourShop;
    @SerializedName("office_phone")
    @Expose
    private String officePhone;
    @SerializedName("approval_shop")
    @Expose
    private String approvalShop;
    @SerializedName("published_shop")
    @Expose
    private String publishedShop;
    @SerializedName("verified_shop")
    @Expose
    private String verifiedShop;

    public String getIdShop() {
        return idShop;
    }

    public String getNameShop() {
        return nameShop;
    }

    public String getDescriptionShop() {
        return descriptionShop;
    }

    public String getLatitudeShop() {
        return latitudeShop;
    }

    public String getLongtitudeShop() {
        return longtitudeShop;
    }

    public String getRealPhotoShop() {
        return realPhotoShop;
    }

    public String getHariBukaShop() {
        return hariBukaShop;
    }

    public String getOpenHourShop() {
        return openHourShop;
    }

    public String getCloseHourShop() {
        return closeHourShop;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public String getApprovalShop() {
        return approvalShop;
    }

    public String getPublishedShop() {
        return publishedShop;
    }

    public String getVerifiedShop() {
        return verifiedShop;
    }
}
