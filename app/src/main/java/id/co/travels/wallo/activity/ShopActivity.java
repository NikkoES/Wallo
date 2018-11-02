package id.co.travels.wallo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import id.co.travels.wallo.R;
import id.co.travels.wallo.activity.legalitas.LegalitasActivity;
import id.co.travels.wallo.activity.profil.ProfilTokoActivity;
import id.co.travels.wallo.activity.toko.AturTokoActivity;

public class ShopActivity extends AppCompatActivity {

    @BindView(R.id.sw_aktivasi)
    Switch swAktivasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);

        swAktivasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(ShopActivity.this, "Toko dibuka", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ShopActivity.this, "Toko ditutup", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick({R.id.btn_atur_toko, R.id.btn_profi_toko, R.id.btn_legalitas})
    public void actionButton(View v){
        switch (v.getId()){
            case R.id.btn_atur_toko :
                startActivity(new Intent(this, AturTokoActivity.class));
                break;
            case R.id.btn_profi_toko :
                startActivity(new Intent(this, ProfilTokoActivity.class));
                break;
            case R.id.btn_legalitas :
                startActivity(new Intent(this, LegalitasActivity.class));
                break;
        }
    }
}
