package com.example.drikkelek;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();

    public static final int IMAGE_REQUEST = 1;
    public static final String EXTRA_USER = "com.example.drikkelek.extra.USER";

    ImageButton imgBtn;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<UserObject> userArray;
    String URI;
    Intent getOldList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        URI = DisplayMessageActivity.getUriToDrawable(this, R.drawable.pils).toString();
        getOldList = getIntent();


        Log.d(LOG_TAG, "onCreate");

        if(savedInstanceState != null) {
            userArray = (ArrayList<UserObject>)savedInstanceState.getSerializable("user_list");
        } else if (getOldList.getSerializableExtra(MainActivity.USER_PASS) != null){
            userArray = (ArrayList<UserObject>)getOldList.getSerializableExtra(MainActivity.USER_PASS);
        } else {
            userArray = new ArrayList<>();
        }


        recyclerView = findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new drikkeAdapter(this, userArray);
        recyclerView.setAdapter(mAdapter);

        imgBtn = findViewById(R.id.chosenImage);


    }

    public void chooseImage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText =  findViewById(R.id.nameField);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_USER, message);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    public void updateList(View view) {
        TextView t = findViewById(R.id.nameField);
        String name = t.getText().toString();
        Log.d(LOG_TAG, name);
        UserObject userObj = new UserObject();

        String imgString = URI;
        if(name.equals("")) {
            Toast.makeText(this, "Du har vel et navn?", Toast.LENGTH_SHORT).show();

        } else {
            userObj.setName(name);
            userObj.setImgUri(imgString);
            userArray.add(userObj);

            mAdapter.notifyDataSetChanged();
            t.setText("");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == IMAGE_REQUEST) {
            if(resultCode == RESULT_OK) {

                Log.d(LOG_TAG, URI);
                if(data.getExtras().containsKey(DisplayMessageActivity.RETURN_IMAGE)) {
                    URI = data.getStringExtra(DisplayMessageActivity.RETURN_IMAGE);
                    Glide.with(this)
                            .load(Uri.parse(URI).getPath())
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .apply(RequestOptions.circleCropTransform())
                            .into(imgBtn);
                } else if (data.getExtras().containsKey("drawable")) {
                    URI = data.getStringExtra("drawable");
                    imgBtn.setImageURI(Uri.parse(URI));
                }
            }
        }
    }

    public void returnUserArray(View view) {
        Log.d(LOG_TAG, "returnUserArray");
        Intent returnHashIntent;
        if(getCallingActivity().getClassName().equals(MainActivity.class.getName())) {
            returnHashIntent = new Intent(this, MainActivity.class);
            setResult(RESULT_OK, returnHashIntent);
            returnHashIntent.putExtra(EXTRA_USER, userArray);
            finish();
        } else if (getCallingActivity().getClassName().equals(CardsActivity.class.getName())) {
            returnHashIntent = new Intent(this, CardsActivity.class);
            setResult(RESULT_OK, returnHashIntent);
            returnHashIntent.putExtra(EXTRA_USER, userArray);
            finish();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstance");
        outState.putSerializable("user_list", userArray);


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}

