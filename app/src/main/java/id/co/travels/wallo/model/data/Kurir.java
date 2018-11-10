package id.co.travels.wallo.model.data;

import java.io.Serializable;

public class Kurir implements Serializable{

    String jenisKurir;
    String imageKurir;
    boolean isSwitched;

    public Kurir(String jenisKurir, String imageKurir, boolean isSwitched) {
        this.jenisKurir = jenisKurir;
        this.imageKurir = imageKurir;
        this.isSwitched = isSwitched;
    }

    public String getJenisKurir() {
        return jenisKurir;
    }

    public void setJenisKurir(String jenisKurir) {
        this.jenisKurir = jenisKurir;
    }

    public String getImageKurir() {
        return imageKurir;
    }

    public void setImageKurir(String imageKurir) {
        this.imageKurir = imageKurir;
    }

    public boolean isSwitched() {
        return isSwitched;
    }

    public void setSwitched(boolean switched) {
        isSwitched = switched;
    }
}
