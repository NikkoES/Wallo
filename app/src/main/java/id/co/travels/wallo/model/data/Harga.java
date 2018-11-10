package id.co.travels.wallo.model.data;

import java.io.Serializable;

public class Harga implements Serializable {

    int minQtt;
    int maxQtt;
    int harga;

    public Harga(int minQtt, int maxQtt, int harga) {
        this.minQtt = minQtt;
        this.maxQtt = maxQtt;
        this.harga = harga;
    }

    public int getMinQtt() {
        return minQtt;
    }

    public int getMaxQtt() {
        return maxQtt;
    }

    public int getHarga() {
        return harga;
    }
}
