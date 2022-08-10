package com.indotek.mobileindotek.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.indotek.mobileindotek.R;
import com.indotek.mobileindotek.datamodel.ApiResponse;
import com.indotek.mobileindotek.rest.ApiClient;
import com.indotek.mobileindotek.rest.ApiInterfaces;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraImagePreview extends AppCompatActivity {

    String jenismesin, merek,updateid,request;
    TextView tvjenismesin, tvmerek,btnKirim,btnUlang;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ApiInterfaces mApiInterface;
    private Call<com.indotek.mobileindotek.datamodel.ApiResponse> mApiCall;
    private RequestBody sid,requestBodyFile;
    private Bitmap mImageBitmap;
    private Uri mCurrentPhotoPath;
    private ImageView mImageView;
    private Context context;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_image_preview);
        Bundle extras = getIntent().getExtras();

        jenismesin = extras.getString("jenismesin");
        merek = extras.getString("merek");
        request = extras.getString("requestcamerafor");

        updateid = extras.getString("id_mesin");
        mApiInterface = ApiClient.getClient(this).create(ApiInterfaces.class);
        sid = RequestBody.create(MediaType.parse("multipart/form-data"), updateid);

        tvjenismesin = findViewById(R.id.jenismesin);
        tvmerek = findViewById(R.id.merek);
        btnKirim = findViewById(R.id.btn_kirim);
        btnUlang = findViewById(R.id.btn_ulang);

        tvjenismesin.setText(jenismesin);
        tvmerek.setText(merek);

        context=this;

        mImageView = findViewById(R.id.previewiv);

        checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);

    }


    private void uploadFotoMesin() {
        btnKirim.setOnClickListener(v->{
            try{
                upload();
            }catch (IOException e){
                Log.i("error", "IOException" + e);
            }
        });

        btnUlang.setOnClickListener(v->{
            requestCamera();

        });

        try {
            mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mCurrentPhotoPath);
            mImageView.setImageBitmap(mImageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Uri createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);


        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );
        Uri imageUri = FileProvider.getUriForFile(
                this,
                "com.indotek.mobileindotek.provider", //(use your app signature + ".provider" )
                image);
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = imageUri;
        return mCurrentPhotoPath;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
            mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mCurrentPhotoPath);
            file = compressImageForUpload(mImageBitmap);

                if (request.equals("profil") || request.equals("workshop")){

                        requestBodyFile = RequestBody.create(MediaType.parse("image/*"), file);
                        mApiCall = mApiInterface.uploadFotoTeknisi(requestBodyFile,sid,request);
                        upload();
                        finish();

                }else{

                    requestBodyFile = RequestBody.create(MediaType.parse("image/*"), file);
                    mApiCall = mApiInterface.uploadImage(requestBodyFile,sid,"after");
                    uploadFotoMesin();

                }
            } catch (IOException e) {
                e.printStackTrace();

            }

        }else{
            finish();
        }
    }

    private void upload() throws IOException {




        Toast.makeText(context, "mengirim foto" , Toast.LENGTH_SHORT).show();


        Call<ApiResponse> lm = mApiCall;
        lm.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful()){
                    if (response.body().getCode().equals("200")){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "link salah ", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error" + t, Toast.LENGTH_LONG).show();

            }
        });
    }

    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(CameraImagePreview.this, new String[] { permission }, requestCode);
        }
        else {
            requestCamera();
        }
    }

    public void requestCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            Uri photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("error", "IOException req cam" + ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File compressImageForUpload(Bitmap bitmap){

        File uploadThis = new File(context.getCacheDir(),"cache");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,out);
        byte[] bitmapData = out.toByteArray();

        try {
            uploadThis.createNewFile();
            FileOutputStream fos = new FileOutputStream(uploadThis);
            fos.write(bitmapData);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return uploadThis;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            requestCamera();
        }
    }
}