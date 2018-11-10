package id.co.travels.wallo.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.travels.wallo.R;
import id.co.travels.wallo.activity.toko.MenuActivity;
import id.co.travels.wallo.api.BaseApiService;
import id.co.travels.wallo.api.UtilsApi;
import id.co.travels.wallo.model.data.User;
import id.co.travels.wallo.model.response.ResponseToken;
import id.co.travels.wallo.model.response.ResponseUser;
import id.co.travels.wallo.utils.AuthUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.image_user)
    CircularImageView imageUser;
    @BindView(R.id.txt_nama_user)
    TextView txtNamaUser;
    @BindView(R.id.txt_id_shop)
    TextView txtIdShop;

    BaseApiService apiService;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);

        apiService = UtilsApi.getAPIService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserData();
    }

    private void initUserData() {
        apiService.getUserData(pref.getString("token", "")).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    User data = response.body().getUser();
                    txtNamaUser.setText(data.getNameUser());
                    txtIdShop.setText("ID Shop : " + data.getIdShop());
                } else {
                    Toast.makeText(SettingActivity.this, "Failed to fetch data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.btn_account, R.id.btn_wallet, R.id.btn_shop, R.id.btn_update_token})
    public void actionButton(View v) {
        switch (v.getId()) {
            case R.id.btn_account:
                Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_wallet:
                Toast.makeText(this, "Wallet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_shop:
                Intent i = new Intent(this, ShopActivity.class);
                startActivity(i);
                break;
            case R.id.btn_update_token:
                Map<String, Object> jsonParams = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    jsonParams = new ArrayMap<>();
                    jsonParams.put("ip_address", "");
                }

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

                apiService.updateToken(AuthUtils.token, body).enqueue(new Callback<ResponseToken>() {
                    @Override
                    public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                        if (response.isSuccessful()) {
                            String token = response.body().getToken().getToken();
                            SharedPreferences.Editor editor = getSharedPreferences("setting", MODE_PRIVATE).edit();
                            editor.putString("token", token);
                            editor.apply();

                            Toast.makeText(SettingActivity.this, "Token berhasil diupdate", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SettingActivity.this, "Token gagal diupdate !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseToken> call, Throwable t) {
                        Toast.makeText(SettingActivity.this, "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
