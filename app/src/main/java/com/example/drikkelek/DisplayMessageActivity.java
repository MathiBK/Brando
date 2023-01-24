package com.example.drikkelek;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DisplayMessageActivity extends AppCompatActivity {
    public static final String RETURN_IMAGE = "com.example.drikkelek.extra.IMAGE";
    public static final String LOG_TAG = DisplayMessageActivity.class.getSimpleName();
    public static final int PERMISSIONS_REQUEST = 1;
    String currentPhotoPath;
    Bitmap bitmap;
    ImageView imageView;
    Boolean customImg = false;
    Random random;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    String[] randDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        randDrawable = new String[]{
                getUriToDrawable(this, R.drawable.pils).toString(),
                getUriToDrawable(this, R.drawable.ol).toString(),
                getUriToDrawable(this, R.drawable.kokos2).toString(),
                getUriToDrawable(this, R.drawable.paraply).toString(),
                getUriToDrawable(this, R.drawable.whisky).toString(),
                getUriToDrawable(this, R.drawable.punch).toString()
        };

        imageView = findViewById(R.id.valgtBilde);
        random = new Random();
        int choice = random.nextInt(randDrawable.length);
        imageView.setImageURI(Uri.parse(randDrawable[choice]));
        currentPhotoPath = randDrawable[choice];

    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent(View view) {

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSIONS_REQUEST);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);
                    Glide.with(this)
                            .load(photoURI)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(RequestOptions.circleCropTransform())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    customImg = true;

                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }



    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    public void returnImage(View view) {
        Intent returnIntent = new Intent(this, LoginActivity.class);

        if(customImg) {
            returnIntent.putExtra(RETURN_IMAGE, currentPhotoPath);
        } else {
            returnIntent.putExtra("drawable", currentPhotoPath);
        }
        setResult(RESULT_OK, returnIntent);

        finish();
    }


    public void kokosKlikk(View view) {
        Uri drawableUri = getUriToDrawable(this, R.drawable.kokos2);
        imageView.setImageURI(drawableUri);
        currentPhotoPath = drawableUri.toString();
        customImg = false;
    }

    public void whiskyKlikk(View view) {
        Uri drawableUri = getUriToDrawable(this, R.drawable.whisky);
        imageView.setImageURI(drawableUri);
        currentPhotoPath = drawableUri.toString();
        Log.d(LOG_TAG, drawableUri.toString());
        customImg = false;
    }

    public void pilsKlikk(View view) {
        Log.d(LOG_TAG, Integer.toString(view.getId()));
        Uri drawableUri = getUriToDrawable(this, R.drawable.pils);
        imageView.setImageURI(drawableUri);
        currentPhotoPath = drawableUri.toString();
        customImg = false;
    }

    public void punchKlikk(View view) {
        Uri drawableUri = getUriToDrawable(this, R.drawable.punch);
        imageView.setImageURI(drawableUri);
        currentPhotoPath = drawableUri.toString();
        customImg = false;
    }

    public void paraplyKlikk(View view) {
        Uri drawableUri = getUriToDrawable(this, R.drawable.paraply);
        imageView.setImageURI(drawableUri);
        currentPhotoPath = drawableUri.toString();

        customImg = false;
    }
    public void olKlikk(View view) {
        Uri drawableUri = getUriToDrawable(this, R.drawable.ol);
        imageView.setImageURI(drawableUri);
        currentPhotoPath = drawableUri.toString();
        customImg = false;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Uri getUriToDrawable(@NonNull Context context, @AnyRes int drawableId) {
        Resources resources = context.getResources();
        Uri imageUri = (new Uri.Builder())
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(drawableId))
                .appendPath(resources.getResourceTypeName(drawableId))
                .appendPath(resources.getResourceEntryName(drawableId))
                .build();
        return imageUri;
    }

}
