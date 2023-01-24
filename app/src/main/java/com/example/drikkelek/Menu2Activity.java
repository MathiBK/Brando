package com.example.drikkelek;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Menu2Activity extends AppCompatActivity {

    public static final String USER_LIST = "com.drikkelek.extra.USERS";
    ArrayList<UserObject> mUserArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        Intent intent = getIntent();
        mUserArray = (ArrayList<UserObject>) intent.getExtras().getSerializable(MainActivity.USER_LIST);
    }

    public void ringOfFylla(View view){
        Intent intent = new Intent(this, CardsActivity.class);
        intent.putExtra(USER_LIST, mUserArray);
        startActivity(intent);
    }
}
