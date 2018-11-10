package id.co.travels.wallo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.travels.wallo.R;
import id.co.travels.wallo.model.data.Kurir;

public class KurirAdapter extends RecyclerView.Adapter<KurirAdapter.ViewHolder> {

    private Context context;
    private List<Kurir> listKurir;
    private OnCheckedChangeListener onItemCheckedListener;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(int position , boolean isChecked);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        onItemCheckedListener = listener;
    }

    public KurirAdapter(Context context, List<Kurir> listKurir) {
        this.context = context;
        this.listKurir = listKurir;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_kurir, null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Kurir kurir = listKurir.get(position);
        Glide.with(context).load(kurir.getImageKurir()).into(holder.imageKurir);
        holder.txtNamaKurir.setText(kurir.getJenisKurir());
        holder.swKurir.setChecked(kurir.isSwitched());
        holder.swKurir.setTag(position);
        holder.swKurir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                kurir.setSwitched(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listKurir.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_kurir)
        ImageView imageKurir;
        @BindView(R.id.txt_nama_kurir)
        TextView txtNamaKurir;
        @BindView(R.id.sw_kurir)
        Switch swKurir;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = (int) buttonView.getTag();

            if (onItemCheckedListener != null) {
                onItemCheckedListener.onCheckedChanged(position, isChecked);
            }
        }
    };

}
