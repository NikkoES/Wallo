package id.co.travels.wallo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.travels.wallo.R;
import id.co.travels.wallo.model.data.Produk;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {

    private Context context;
    private List<Produk> listProduk;

    public ProdukAdapter(Context context, List<Produk> listProduk) {
        this.context = context;
        this.listProduk = listProduk;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_produk, null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Produk produk = listProduk.get(position);
//        Glide.with(context).load(produk.getItem()).into(holder.imageProduk);
//        holder.txtNamaProduk.setText(produk.getNamaProduk());
//        holder.txtHargaProduk.setText(String.valueOf(produk.getHarga().getHarga()));
//        holder.txtMenuProduk.setText(String.valueOf("Menu : "+produk.getMenuProduk()));
//        holder.btnProdukContext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final CharSequence[] items = {"Edit", "Duplicate", "Delete"};
//
//                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle(produk.getNamaProduk());
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        switch (item) {
//                            case 0:
//                                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 1:
//                                Toast.makeText(context, "Duplicate", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 2:
//                                Toast.makeText(context, "Hapus", Toast.LENGTH_SHORT).show();
//                                break;
//                        }
//                    }
//                });
//                builder.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listProduk.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_produk)
        ImageView imageProduk;
        @BindView(R.id.txt_nama_produk)
        TextView txtNamaProduk;
        @BindView(R.id.txt_harga_produk)
        TextView txtHargaProduk;
        @BindView(R.id.txt_menu_produk)
        TextView txtMenuProduk;
        @BindView(R.id.btn_context)
        ImageView btnProdukContext;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
