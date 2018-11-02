package id.co.travels.wallo.activity.legalitas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.travels.wallo.R;

public class LegalitasActivity extends AppCompatActivity {

    Intent intent;
    Uri fileUri;
    Bitmap bitmap, decoded;

    int bitmap_size = 100;
    int max_resolution_image = 800;

    public final int REQUEST_CAMERA = 0;
    public final int SELECT_FILE = 1;

    @BindView(R.id.image_legalitas)
    ImageView imageLegalitas;
    @BindView(R.id.image_correct)
    ImageView imageCorrect;
    @BindView(R.id.image_incorrect)
    ImageView imageIncorrect;
    @BindView(R.id.et_tipe_identitas)
    EditText etTipeIdentitas;
    @BindView(R.id.txt_keterangan)
    EditText txtKeterangan;
    @BindView(R.id.btn_ambil_foto)
    Button buttonAmbilFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legalitas);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_ambil_foto, R.id.et_tipe_identitas})
    public void actionButton(View v) {
        switch (v.getId()) {
            case R.id.btn_ambil_foto:
                selectImage();
                break;
            case R.id.et_tipe_identitas:
                tipeIdentitas();
                break;
        }
    }

    private void tipeIdentitas() {
        final CharSequence[] items = {"Kartu Tanda Penduduk (KTP)", "Nomor Pokok Wajib Pajak (NPWP)", "Passport"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Tipe Identitas");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        buttonAmbilFoto.setText("Ambil Foto KTP");
                        etTipeIdentitas.setText("Kartu Tanda Penduduk (KTP)");
                        txtKeterangan.setText("ini keterangan KTP");
                        imageCorrect.setImageResource(R.drawable.ic_launcher_background);
                        imageIncorrect.setImageResource(R.drawable.ic_launcher_background);
                        break;
                    case 1:
                        buttonAmbilFoto.setText("Ambil Foto NPWP");
                        etTipeIdentitas.setText("Nomor Pokok Wajib Pajak (NPWP)");
                        txtKeterangan.setText("ini keterangan NPWP");
                        imageCorrect.setImageResource(R.drawable.ic_launcher_background);
                        imageIncorrect.setImageResource(R.drawable.ic_launcher_background);
                        break;
                    case 2:
                        buttonAmbilFoto.setText("Ambil Foto Passport");
                        etTipeIdentitas.setText("Passport");
                        txtKeterangan.setText("ini keterangan Passport");
                        imageCorrect.setImageResource(R.drawable.ic_launcher_background);
                        imageIncorrect.setImageResource(R.drawable.ic_launcher_background);
                        break;
                }
            }
        });
        builder.show();
    }

    private void selectImage() {
        StrictMode.VmPolicy.Builder builderCamera = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builderCamera.build());
        final CharSequence[] items = {"Ambil Foto", "Pilih dari Galeri", "Batal"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ambil Foto");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Ambil Foto")) {
                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri();
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Pilih dari Galeri")) {
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                try {
                    bitmap = BitmapFactory.decodeFile(fileUri.getPath());
                    setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setToImageView(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        imageLegalitas.setVisibility(View.VISIBLE);
        imageLegalitas.setImageBitmap(decoded);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Wallo");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("Monitoring", "Oops! Failed create Monitoring directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_Wallo_" + timeStamp + ".jpg");

        return mediaFile;
    }
}