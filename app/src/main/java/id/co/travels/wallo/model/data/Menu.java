package id.co.travels.wallo.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Menu implements Serializable {

    @SerializedName("id_menu")
    @Expose
    private String idMenu;
    @SerializedName("menu_name")
    @Expose
    private String menuName;
    @SerializedName("parent_id")
    @Expose
    private Object parentId;
    @SerializedName("id_shop")
    @Expose
    private String idShop;
    @SerializedName("id_category")
    @Expose
    private String idCategory;

    boolean isSwitched;

    public void setSwitched(boolean switched) {
        isSwitched = switched;
    }

    public String getIdMenu() {
        return idMenu;
    }

    public String getMenuName() {
        return menuName;
    }

    public Object getParentId() {
        return parentId;
    }

    public String getIdShop() {
        return idShop;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public boolean isSwitched() {
        return isSwitched;
    }
}
