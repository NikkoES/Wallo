package id.co.travels.wallo.activity.profil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.travels.wallo.R;
import id.co.travels.wallo.activity.SettingActivity;
import id.co.travels.wallo.api.BaseApiService;
import id.co.travels.wallo.api.UtilsApi;
import id.co.travels.wallo.model.data.Shop;
import id.co.travels.wallo.model.data.User;
import id.co.travels.wallo.model.response.ResponseShop;
import id.co.travels.wallo.model.response.ResponseUser;
import id.co.travels.wallo.utils.AuthUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilTokoActivity extends AppCompatActivity {

    @BindView(R.id.et_nama_toko)
    EditText etNamaToko;
    @BindView(R.id.et_deskripsi_toko)
    EditText etDeskripsiToko;
    @BindView(R.id.et_hari_buka)
    EditText etHariBuka;
    @BindView(R.id.et_jam)
    EditText etJam;
    @BindView(R.id.et_jam_buka)
    EditText etJamBuka;
    @BindView(R.id.et_jam_tutup)
    EditText etJamTutup;
    @BindView(R.id.et_alamat_toko)
    EditText etAlamatToko;
    @BindView(R.id.et_nomor_toko)
    EditText etNomorToko;
    @BindView(R.id.cb_jarak_tawaran)
    CheckBox cbJarakTawaran;
    @BindView(R.id.et_harga_jarak)
    EditText etHargaJarak;
    @BindView(R.id.et_kurir)
    EditText etKurir;
    @BindView(R.id.et_foto_toko)
    EditText etFotoToko;

    List<String> listKurir = new ArrayList<>();
    Uri uriToko;

    String uri;

    public static final int RESULT_KURIR = 11;
    public static final int RESULT_IMAGE = 12;
    public static final int PLACE_PICKER_REQUEST = 13;

    String namaToko, deskripsiToko, hariBuka, jamBuka, jamTutup, alamat, nomorTelp, hargaJarak, latitude, longitude, image;
    RequestBody rNamaToko, rDeskripsiToko, rHariBuka, rJamBuka, rJamTutup, rNomorTelp, rAlamat, rHarga, rLat, rLong, rImage;

    BaseApiService apiService;
    SharedPreferences pref;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_toko);
        ButterKnife.bind(this);

        pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);

        apiService = UtilsApi.getAPIService();

        initToolbar();
        initShopData();

        loading = new ProgressDialog(this);
        loading.setTitle("Loading");
        loading.setMessage("Saving Data..");
        loading.setCancelable(false);
    }


    public interface MyCallback {
        void onCallback(Response<ResponseShop> response);
    }

    private void checkShop(final MyCallback myCallback) {
        apiService.getDetailShop(pref.getString("token", "")).enqueue(new Callback<ResponseShop>() {
            @Override
            public void onResponse(Call<ResponseShop> call, Response<ResponseShop> response) {
                if (response.isSuccessful()) {
                    myCallback.onCallback(response);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to fetch data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseShop> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initShopData() {
        apiService.getDetailShop(pref.getString("token", "")).enqueue(new Callback<ResponseShop>() {
            @Override
            public void onResponse(Call<ResponseShop> call, Response<ResponseShop> response) {
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        Shop data = response.body().getShop();
                        etNamaToko.setText(data.getNameShop());
                        etDeskripsiToko.setText(data.getDescriptionShop());
                        etHariBuka.setText(data.getHariBukaShop());
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        try {
                            Date newJamBuka = timeFormat.parse(data.getOpenHourShop());
                            Date newJamTutup = timeFormat.parse(data.getCloseHourShop());
                            timeFormat = new SimpleDateFormat("HH:mm");
                            etJam.setText(timeFormat.format(newJamBuka) + " - " + timeFormat.format(newJamTutup));
                            etJamBuka.setText(timeFormat.format(newJamBuka));
                            etJamTutup.setText(timeFormat.format(newJamTutup));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        etAlamatToko.setText(data.getLatitudeShop() + ", " + data.getLongtitudeShop());
                        etNomorToko.setText(data.getOfficePhone());
                    } else {
                        Toast.makeText(ProfilTokoActivity.this, "Anda belum memasukkan profil toko !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to fetch data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseShop> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.et_hari_buka, R.id.et_jam, R.id.et_alamat_toko, R.id.et_kurir, R.id.et_foto_toko})
    public void buttonEditText(View v) {
        switch (v.getId()) {
            case R.id.et_hari_buka:
                setHariBuka();
                break;
            case R.id.et_jam:
                setJamBuka();
                break;
            case R.id.et_alamat_toko:
                setPlace();
                break;
            case R.id.et_kurir:
                Intent i = new Intent(this, KurirTokoActivity.class);
                startActivityForResult(i, RESULT_KURIR);
                break;
            case R.id.et_foto_toko:
                Intent intent = new Intent(this, ImageTokoActivity.class);
                startActivityForResult(intent, RESULT_IMAGE);
                break;
        }
    }

    private void setPlace() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_KURIR) {
            if (resultCode == Activity.RESULT_OK) {
                listKurir = (List<String>) data.getSerializableExtra("result");
            }
        } else if (requestCode == RESULT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getStringExtra("result");
                etFotoToko.setText(uri);
            }
        } else if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                etAlamatToko.setText(place.getAddress());
                latitude = String.valueOf(place.getLatLng().latitude);
                longitude = String.valueOf(place.getLatLng().longitude);
            }
        }

    }

    private void setHariBuka() {
        final CharSequence[] items = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hari Buka");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                String hariBuka = "";
                switch (item) {
                    case 0:
                        hariBuka = (String) items[0];
                        break;
                    case 1:
                        hariBuka = (String) items[1];
                        break;
                    case 2:
                        hariBuka = (String) items[2];
                        break;
                    case 3:
                        hariBuka = (String) items[3];
                        break;
                    case 4:
                        hariBuka = (String) items[4];
                        break;
                    case 5:
                        hariBuka = (String) items[5];
                        break;
                    case 6:
                        hariBuka = (String) items[6];
                        break;
                }
                final String finalHariBuka = hariBuka;

                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfilTokoActivity.this);
                builder.setTitle("Hari Tutup");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        String hariTutup = "";
                        switch (item) {
                            case 0:
                                hariTutup = (String) items[0];
                                break;
                            case 1:
                                hariTutup = (String) items[1];
                                break;
                            case 2:
                                hariTutup = (String) items[2];
                                break;
                            case 3:
                                hariTutup = (String) items[3];
                                break;
                            case 4:
                                hariTutup = (String) items[4];
                                break;
                            case 5:
                                hariTutup = (String) items[5];
                                break;
                            case 6:
                                hariTutup = (String) items[6];
                                break;
                        }
                        etHariBuka.setText(finalHariBuka + " - " + hariTutup);
                    }
                });
                builder.show();
            }
        });
        builder.show();
    }

    private void setJamBuka() {
        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                final String jamBuka = selectedHour + ":" + selectedMinute;
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ProfilTokoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        jamTutup = selectedHour + ":" + selectedMinute;
                        SimpleDateFormat timeFormat = new SimpleDateFormat("H:m");
                        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
                        try {
                            Date newBuka = timeFormat.parse(jamBuka);
                            Date newTutup = timeFormat.parse(jamTutup);
                            timeFormat = new SimpleDateFormat("HH:mm");
                            timeFormat.setTimeZone(tz);

                            etJam.setText(timeFormat.format(newBuka) + " - " + timeFormat.format(newTutup));
                            etJamBuka.setText(timeFormat.format(newBuka));
                            etJamTutup.setText(timeFormat.format(newTutup));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Jam Tutup");
                mTimePicker.show();
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Jam Buka");
        mTimePicker.show();
    }

    @OnClick(R.id.btn_simpan)
    public void buttonSimpan() {
        if (!TextUtils.isEmpty(etNamaToko.getText().toString())) {
            namaToko = etNamaToko.getText().toString();
        } else {
            namaToko = "";
        }
        rNamaToko = RequestBody.create(MediaType.parse("text/plain"), namaToko);
        if (!TextUtils.isEmpty(etDeskripsiToko.getText().toString())) {
            deskripsiToko = etDeskripsiToko.getText().toString();
        } else {
            deskripsiToko = "";
        }
        rDeskripsiToko = RequestBody.create(MediaType.parse("text/plain"), deskripsiToko);
        if (!TextUtils.isEmpty(etHariBuka.getText().toString())) {
            hariBuka = etHariBuka.getText().toString();
        } else {
            hariBuka = "";
        }
        rHariBuka = RequestBody.create(MediaType.parse("text/plain"), hariBuka);
        if (!TextUtils.isEmpty(etJamBuka.getText().toString())) {
            jamBuka = etJamBuka.getText().toString();
        } else {
            jamBuka = "";
        }
        rJamBuka = RequestBody.create(MediaType.parse("text/plain"), jamBuka);
        if (!TextUtils.isEmpty(etJamTutup.getText().toString())) {
            jamTutup = etJamTutup.getText().toString();
        } else {
            jamTutup = "";
        }
        rJamTutup = RequestBody.create(MediaType.parse("text/plain"), jamTutup);
        if (!TextUtils.isEmpty(etAlamatToko.getText().toString())) {
            alamat = etAlamatToko.getText().toString();
        } else {
            alamat = "";
        }
        if (!TextUtils.isEmpty(etNomorToko.getText().toString())) {
            nomorTelp = etNomorToko.getText().toString();
        } else {
            nomorTelp = "";
        }
        rNomorTelp = RequestBody.create(MediaType.parse("text/plain"), nomorTelp);
        if (!TextUtils.isEmpty(etHargaJarak.getText().toString())) {
            hargaJarak = etHargaJarak.getText().toString();
        } else {
            hargaJarak = "";
        }

        File file = new File(Uri.parse(uri).getPath());
        rImage = RequestBody.create(MediaType.parse("image/*"), file);

        checkShop(new MyCallback() {
            @Override
            public void onCallback(Response<ResponseShop> response) {
                rLat = RequestBody.create(MediaType.parse("text/plain"), response.body().getShop().getLatitudeShop());
                rLong = RequestBody.create(MediaType.parse("text/plain"), response.body().getShop().getLongtitudeShop());
                if (response.body().isStatus()) {
                    actionUpdate(rLat, rLong);
                } else {
                    actionCreate(rLat, rLong);
                }
            }
        });
    }

    private void actionUpdate(RequestBody rLat, RequestBody rLong) {
        apiService.updateShop(pref.getString("token", ""), rNamaToko, rDeskripsiToko, rLat, rLong, null, rHariBuka, rJamBuka, rJamTutup, rNomorTelp, null)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            loading.dismiss();
                            Toast.makeText(getApplicationContext(), "Data gagal disimpan !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actionCreate(RequestBody rLat, RequestBody rLong) {
        apiService.createShop(pref.getString("token", ""), rNamaToko, rDeskripsiToko, rLat, rLong, null, rHariBuka, rJamBuka, rJamTutup, rNomorTelp, null)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            loading.dismiss();
                            Toast.makeText(getApplicationContext(), "Data gagal disimpan !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil Toko");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
