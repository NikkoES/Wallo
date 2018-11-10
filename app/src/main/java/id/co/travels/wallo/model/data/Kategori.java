package id.co.travels.wallo.model.data;

import com.google.gson.annotations.SerializedName;

public class Kategori {

    @SerializedName("id_category")
    String idCategory;
    @SerializedName("category_name")
    String categoryName;

    public String getIdCategory() {
        return idCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
