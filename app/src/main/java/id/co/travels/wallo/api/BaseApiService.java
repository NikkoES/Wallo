package id.co.travels.wallo.api;

import id.co.travels.wallo.model.response.ResponseKategori;
import id.co.travels.wallo.model.response.ResponseMenu;
import id.co.travels.wallo.model.response.ResponseProduk;
import id.co.travels.wallo.model.response.ResponseShop;
import id.co.travels.wallo.model.response.ResponseToken;
import id.co.travels.wallo.model.response.ResponseUser;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BaseApiService {

    //update token
    @PUT("user/token")
    Call<ResponseToken> updateToken(@Header("Authorization") String auth,
                                    @Body RequestBody param);

    //blok kategori
    @GET("category/detail")
    Call<ResponseKategori> getAllKategori(@Header("Authorization") String auth);

    //blok user
    @GET("user/detail")
    Call<ResponseUser> getUserData(@Header("Authorization") String auth);

    //blok menu
    @GET("shop/menu/detail")
    Call<ResponseMenu> getMenuData(@Header("Authorization") String auth);

    @POST("shop/menu/create")
        //{ "menu_name":"xxx", "id_categories":"3" }
    Call<ResponseBody> createMenuData(@Header("Authorization") String auth,
                                      @Body RequestBody param);

    @PUT("shop/menu/update")
        //{ "menu_name":"xxx", "id_categories":"3", "id_menu":"2" }
    Call<ResponseBody> updateMenuData(@Header("Authorization") String auth,
                                      @Body RequestBody param);

    @DELETE("shop/menu/{id_menu}/{id_shop}/delete")
    Call<ResponseBody> deleteMenu(@Header("Authorization") String auth,
                                  @Path("id_menu") String idMenu,
                                  @Path("id_shop") String idShop);

    //blok shop
    @GET("shop/detail")
    Call<ResponseShop> getDetailShop(@Header("Authorization") String auth);

    @Multipart
    @POST("shop/create")
    Call<ResponseBody> createShop(@Header("Authorization") String auth,
                                  @Part("name_shop") RequestBody namaToko,
                                  @Part("description_shop") RequestBody deskripsiToko,
                                  @Part("latitude_shop") RequestBody latitudeToko,
                                  @Part("longtitude_shop") RequestBody longitudeToko,
                                  @Part("real_photo_shop\"; filename=\"pp.png\" ") RequestBody imageToko,
                                  @Part("hari_buka_shop") RequestBody hariBuka,
                                  @Part("open_hour_shop") RequestBody jamBuka,
                                  @Part("close_hour_shop") RequestBody jamTutup,
                                  @Part("office_phone") RequestBody nomorTelepon,
                                  @Part("published_shop") RequestBody publishedShop);

    @Multipart
    @POST("shop/update")
    Call<ResponseBody> updateShop(@Header("Authorization") String auth,
                                  @Part("name_shop") RequestBody namaToko,
                                  @Part("description_shop") RequestBody deskripsiToko,
                                  @Part("latitude_shop") RequestBody latitudeToko,
                                  @Part("longtitude_shop") RequestBody longitudeToko,
                                  @Part("real_photo_shop\"; filename=\"pp.png\" ") RequestBody imageToko,
                                  @Part("hari_buka_shop") RequestBody hariBuka,
                                  @Part("open_hour_shop") RequestBody jamBuka,
                                  @Part("close_hour_shop") RequestBody jamTutup,
                                  @Part("office_phone") RequestBody nomorTelepon,
                                  @Part("published_shop") RequestBody publishedShop);


    //blok produk
    @GET("shop/produk/view")
    Call<ResponseProduk> getProdukData(@Header("Authorization") String auth);
}