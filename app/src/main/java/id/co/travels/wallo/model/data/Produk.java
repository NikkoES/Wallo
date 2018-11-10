package id.co.travels.wallo.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Produk implements Serializable {

    @SerializedName("id_menu")
    @Expose
    private String idMenu;
    @SerializedName("menu_name")
    @Expose
    private String menuName;
    @SerializedName("item")
    @Expose
    private List<ItemProduk> item;

    public String getIdMenu() {
        return idMenu;
    }

    public String getMenuName() {
        return menuName;
    }

    public List<ItemProduk> getItem() {
        return item;
    }
}
