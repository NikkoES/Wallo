package id.co.travels.wallo.activity.toko.produk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.travels.wallo.R;
import id.co.travels.wallo.adapter.ProdukAdapter;
import id.co.travels.wallo.model.data.Harga;
import id.co.travels.wallo.model.data.Produk;

public class ListProdukActivity extends AppCompatActivity {

    @BindView(R.id.rv_produk)
    RecyclerView rvProduk;

    private ProdukAdapter mAdapter;
    List<Produk> listProduk = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produk);
        ButterKnife.bind(this);

        initToolbar();
        initProdukData();
        initRecyclerView();
    }

    @OnClick(R.id.btn_add)
    public void buttonAddProduk(){
        startActivity(new Intent(this, ProdukActivity.class));
    }

    private void initProdukData() {
        Toast.makeText(this, "On Development !", Toast.LENGTH_LONG).show();
    }

    private void initRecyclerView() {
        rvProduk.setHasFixedSize(true);
        rvProduk.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProdukAdapter(this, listProduk);
        rvProduk.setAdapter(mAdapter);
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List Produk");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
