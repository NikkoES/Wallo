package id.co.travels.wallo.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemProduk implements Serializable {

    @SerializedName("id_item")
    @Expose
    private String idItem;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_image_0")
    @Expose
    private String itemImage0;
    @SerializedName("item_image_1")
    @Expose
    private String itemImage1;
    @SerializedName("item_image_2")
    @Expose
    private String itemImage2;
    @SerializedName("item_image_3")
    @Expose
    private String itemImage3;
    @SerializedName("desc_short")
    @Expose
    private String descShort;
    @SerializedName("desc_long")
    @Expose
    private String descLong;
    @SerializedName("price_standart")
    @Expose
    private String priceStandart;
    @SerializedName("status_condition")
    @Expose
    private String statusCondition;
    @SerializedName("status_published")
    @Expose
    private String statusPublished;
    @SerializedName("user_seen_count")
    @Expose
    private String userSeenCount;
    @SerializedName("user_wishlist_count")
    @Expose
    private String userWishlistCount;

    public String getIdItem() {
        return idItem;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemImage0() {
        return itemImage0;
    }

    public String getItemImage1() {
        return itemImage1;
    }

    public String getItemImage2() {
        return itemImage2;
    }

    public String getItemImage3() {
        return itemImage3;
    }

    public String getDescShort() {
        return descShort;
    }

    public String getDescLong() {
        return descLong;
    }

    public String getPriceStandart() {
        return priceStandart;
    }

    public String getStatusCondition() {
        return statusCondition;
    }

    public String getStatusPublished() {
        return statusPublished;
    }

    public String getUserSeenCount() {
        return userSeenCount;
    }

    public String getUserWishlistCount() {
        return userWishlistCount;
    }
}
