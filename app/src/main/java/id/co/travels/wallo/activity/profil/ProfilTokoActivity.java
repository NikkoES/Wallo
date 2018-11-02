package id.co.travels.wallo.activity.profil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.travels.wallo.R;

public class ProfilTokoActivity extends AppCompatActivity {

    @BindView(R.id.et_nama_toko)
    EditText etNamaToko;
    @BindView(R.id.et_deskripsi_toko)
    EditText etDeskripsiToko;
    @BindView(R.id.et_hari_buka)
    EditText etHariBuka;
    @BindView(R.id.et_jam_buka)
    EditText etJamBuka;
    @BindView(R.id.et_alamat_toko)
    EditText etAlamatToko;
    @BindView(R.id.cb_jarak_tawaran)
    EditText cbJarakTawaran;
    @BindView(R.id.et_harga_jarak)
    EditText etHargaJarak;

    List<String> listKurir = new ArrayList<>();
    Uri uriToko;

    public static final int RESULT_KURIR = 11;
    public static final int RESULT_IMAGE = 12;

    String namaToko, deskripsiToko, hariBuka, jamBuka, alamat, hargaJarak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_toko);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.et_hari_buka, R.id.et_jam_buka, R.id.et_alamat_toko, R.id.et_kurir, R.id.et_foto_toko})
    public void buttonEditText(View v) {
        switch (v.getId()) {
            case R.id.et_hari_buka:
                setHariBuka();
                break;
            case R.id.et_jam_buka:
                setJamBuka();
                break;
            case R.id.et_alamat_toko:
                Toast.makeText(this, "Place Picker Android", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_KURIR) {
            if (resultCode == Activity.RESULT_OK) {
                listKurir = (List<String>) data.getSerializableExtra("result");
            }
        } else if (requestCode == RESULT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                uriToko = Uri.parse(data.getStringExtra("result"));
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
                        etHariBuka.setText("(" + finalHariBuka + " - " + hariTutup + ")");
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
                        String jamTutup = selectedHour + ":" + selectedMinute;
                        etJamBuka.setText("(" + jamBuka + " - " + jamTutup + ")");
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
        if (!TextUtils.isEmpty(etDeskripsiToko.getText().toString())) {
            deskripsiToko = etDeskripsiToko.getText().toString();
        } else {
            deskripsiToko = "";
        }
        if (!TextUtils.isEmpty(etHariBuka.getText().toString())) {
            hariBuka = etHariBuka.getText().toString();
        } else {
            hariBuka = "";
        }
        if (!TextUtils.isEmpty(etJamBuka.getText().toString())) {
            jamBuka = etJamBuka.getText().toString();
        } else {
            jamBuka = "";
        }
        if (!TextUtils.isEmpty(etAlamatToko.getText().toString())) {
            alamat = etAlamatToko.getText().toString();
        } else {
            alamat = "";
        }
        if (!TextUtils.isEmpty(etHargaJarak.getText().toString())) {
            hargaJarak = etHargaJarak.getText().toString();
        } else {
            hargaJarak = "";
        }
    }
}
