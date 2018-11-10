package id.co.travels.wallo.activity.profil;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.travels.wallo.R;
import id.co.travels.wallo.adapter.KurirAdapter;
import id.co.travels.wallo.model.data.Kurir;

public class KurirTokoActivity extends AppCompatActivity {

    @BindView(R.id.rv_kurir)
    RecyclerView rvKurir;

    private KurirAdapter mAdapter;
    List<Kurir> listKurir = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurir_toko);
        ButterKnife.bind(this);

        initToolbar();
        initKurirData();
        initRecyclerView();
    }

    @OnClick(R.id.btn_simpan)
    public void buttonSimpan(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", (Serializable) listKurir);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void initKurirData() {
        listKurir.add(new Kurir("JNE", "", true));
        listKurir.add(new Kurir("Gojek", "", false));
        listKurir.add(new Kurir("JNT", "", false));
        listKurir.add(new Kurir("Grab", "", true));
    }

    private void initRecyclerView() {
        rvKurir.setHasFixedSize(true);
        rvKurir.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new KurirAdapter(this, listKurir);
        rvKurir.setAdapter(mAdapter);
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kurir");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
