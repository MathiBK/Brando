package com.example.drikkelek;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String USER_PASS = "com.drikkelek.extra.PASS";
    public static final String USER_LIST = "com.drikkelek.extra.USERS";
    public static final int REQUEST_HASH = 1;
    public ArrayList<UserObject> mUserArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserArray = new ArrayList<>();
        Log.d(LOG_TAG, "------");
        Log.d(LOG_TAG, "onCreate");
    }

    public void menu2(View view){
        Intent intent = new Intent(this, Menu2Activity.class);
        intent.putExtra(USER_LIST, mUserArray);

        startActivity(intent);
    }

    public void goLogin(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.putExtra(USER_PASS, mUserArray);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);


        startActivityForResult(loginIntent, REQUEST_HASH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_HASH) {
            if(resultCode == RESULT_OK) {

                mUserArray = (ArrayList<UserObject>) data.getExtras().getSerializable(LoginActivity.EXTRA_USER);
                Log.d(LOG_TAG, "Kom hit");
            }
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(LOG_TAG, "onStart");
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