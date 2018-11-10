package id.co.travels.wallo.activity.toko;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.travels.wallo.R;
import id.co.travels.wallo.adapter.MenuAdapter;
import id.co.travels.wallo.api.BaseApiService;
import id.co.travels.wallo.api.UtilsApi;
import id.co.travels.wallo.model.data.Kategori;
import id.co.travels.wallo.model.data.Menu;
import id.co.travels.wallo.model.response.ResponseKategori;
import id.co.travels.wallo.model.response.ResponseMenu;
import id.co.travels.wallo.utils.AuthUtils;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.et_nama_menu)
    EditText etNamaMenu;
    @BindView(R.id.sp_kategori)
    Spinner spKategori;
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;

    private MenuAdapter mAdapter;
    List<Menu> listMenu = new ArrayList<>();
    List<Kategori> listKategori = new ArrayList<>();

    String idKategori, idMenu, namaMenu;

    String mode;

    BaseApiService apiService;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);

        apiService = UtilsApi.getAPIService();

        initToolbar();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMenuData(new AdapterCallback() {
            @Override
            public void onCallback(List<Menu> value) {
                listMenu = value;
            }
        });
        initKategori(spKategori, new MyCallback() {
            @Override
            public void onCallback(String value) {
                idKategori = value;
            }
        }, new KategoriCallback() {
            @Override
            public void onCallback(List<Kategori> value) {
                listKategori = value;
            }
        }, new SpinnerCallback() {
            @Override
            public void onCallback(Spinner value) {
                spKategori = value;
            }
        });
    }

    @OnClick(R.id.btn_add_menu)
    public void buttonAddMenu() {
        if (!TextUtils.isEmpty(etNamaMenu.getText().toString())) {
            namaMenu = etNamaMenu.getText().toString();
        } else {
            Toast.makeText(this, "Nama menu belum diisi !", Toast.LENGTH_SHORT).show();
        }
        Map<String, Object> jsonParams = null;

        if(mode.equalsIgnoreCase("edit")){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                jsonParams = new ArrayMap<>();
                jsonParams.put("menu_name", namaMenu);
                jsonParams.put("id_categories", idKategori);
                jsonParams.put("id_menu", idMenu);
            }
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            apiService.updateMenuData(pref.getString("token", ""), body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(MenuActivity.this, "Menu berhasil diupdate", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MenuActivity.this, "Menu gagal diupdate !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MenuActivity.this, "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                jsonParams = new ArrayMap<>();
                jsonParams.put("menu_name", namaMenu);
                jsonParams.put("id_categories", idKategori);
            }
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
            apiService.createMenuData(pref.getString("token", ""), body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(MenuActivity.this, "Menu berhasil dibuat", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MenuActivity.this, "Menu gagal dibuat !", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MenuActivity.this, "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public interface MyCallback {
        void onCallback(String value);
    }

    public interface AdapterCallback {
        void onCallback(List<Menu> value);
    }

    public interface KategoriCallback {
        void onCallback(List<Kategori> value);
    }

    public interface SpinnerCallback {
        void onCallback(Spinner value);
    }

    private void initKategori(final Spinner spinner, final MyCallback myCallback, final KategoriCallback callback, final SpinnerCallback spCallback) {
        apiService.getAllKategori(pref.getString("token", "")).enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                if (response.isSuccessful()) {
                    listKategori = response.body().getListKategori();
                    callback.onCallback(listKategori);

                    final List<String> categories = new ArrayList<>();
                    for (int i = 0; i < listKategori.size(); i++) {
                        categories.add(listKategori.get(i).getCategoryName());
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_spinner_item, categories);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(dataAdapter);
                    dataAdapter.notifyDataSetChanged();

                    spCallback.onCallback(spinner);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String key = adapterView.getItemAtPosition(i).toString();
                            int index = categories.indexOf(key); //0, 1, 2
                            String id = listKategori.get(index).getIdCategory();
                            myCallback.onCallback(id);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initMenuData(final AdapterCallback callback) {
        apiService.getMenuData(pref.getString("token", "")).enqueue(new Callback<ResponseMenu>() {
            @Override
            public void onResponse(Call<ResponseMenu> call, Response<ResponseMenu> response) {
                if (response.isSuccessful()) {
                    listMenu = response.body().getListMenu();

                    rvMenu.setAdapter(new MenuAdapter(MenuActivity.this, listMenu));
                    mAdapter.notifyDataSetChanged();
                    callback.onCallback(listMenu);
                } else {
                    Toast.makeText(MenuActivity.this, "Data menu tidak ditemukan !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMenu> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        rvMenu.setHasFixedSize(true);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MenuAdapter(this, listMenu);
        rvMenu.setAdapter(mAdapter);
        registerForContextMenu(rvMenu);
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Menu");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        Log.e("position", "" + item.getGroupId());

        Menu menu = listMenu.get(item.getGroupId());
        switch (item.getItemId()) {
            case 1:
                mode = "edit";
                etNamaMenu.setText(menu.getMenuName());
                for (int i = 0; i < listKategori.size(); i++) {
                    if (menu.getIdCategory().equalsIgnoreCase(listKategori.get(i).getIdCategory())) {
                        spKategori.setSelection(i);
                    }
                }
                idMenu = menu.getIdMenu();
                break;
            case 2:
                apiService.deleteMenu(pref.getString("token", ""), idMenu, "uNIh6kX21R").enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MenuActivity.this, "Menu berhasil dibuat", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MenuActivity.this, "Menu gagal dibuat !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MenuActivity.this, "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }
}