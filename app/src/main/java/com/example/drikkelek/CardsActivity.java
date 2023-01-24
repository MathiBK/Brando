package com.example.drikkelek;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Random;

public class CardsActivity extends AppCompatActivity {
    public static final String ruleRequest = "com.example.drikkelek.extra.ruleRequest";
    public static final String usedRuleSend = "com.example.drikkelek.extra.usedRuleSend";

    //Liste med navn på hel kortstokk
    ArrayList<String> cardList = new ArrayList<String>(){
        {
            add("s1");
            add("s2");
            add("s3");
            add("s4");
            add("s5");
            add("s6");
            add("s7");
            add("s8");
            add("s9");
            add("s10");
            add("s11");
            add("s12");
            add("s13");
            add("r1");
            add("r2");
            add("r3");
            add("r4");
            add("r5");
            add("r6");
            add("r7");
            add("r8");
            add("r9");
            add("r10");
            add("r11");
            add("r12");
            add("r13");
            add("h1");
            add("h2");
            add("h3");
            add("h4");
            add("h5");
            add("h6");
            add("h7");
            add("h8");
            add("h9");
            add("h10");
            add("h11");
            add("h12");
            add("h13");
            add("k1");
            add("k2");
            add("k3");
            add("k4");
            add("k5");
            add("k6");
            add("k7");
            add("k8");
            add("k9");
            add("k10");
            add("k11");
            add("k12");
            add("k13");
        }
    };
    //Liste med alle regler i bruk
    ArrayList<String> usedRules = new ArrayList<String>();

    ArrayList<UserObject> mUserArray;

    boolean alreadyChosen = false;
    int turn = 0;

    public static final int RULE_REQUEST = 1;
    public static final int NEW_USER_REQUEST = 2;
    Button seeRuleButton;
    ImageView cardImage;
    TextView cardRuleText;
    Button makeRuleButton;
    TextView showUsedRules;
    TextView userName;
    ImageView userPic;
    LinearLayout tripMenu;
    TextView newGameButton;
    TextView newPlayer;
    TextView backFromCurrentRuleList;
    ListView currentRuleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        Intent intent = getIntent();

        mUserArray = (ArrayList<UserObject>) intent.getExtras().getSerializable(Menu2Activity.USER_LIST);

        seeRuleButton = findViewById(R.id.seeRuleButton);
        cardImage = findViewById(R.id.card);
        cardRuleText = findViewById(R.id.cardRule);
        makeRuleButton = findViewById(R.id.makeRuleButton);
        showUsedRules = findViewById(R.id.showCurrentRules);
        tripMenu = findViewById(R.id.triDotMenu);
        newGameButton = findViewById(R.id.newGame);
        newPlayer = findViewById(R.id.addPlayer);
        userName = findViewById(R.id.userName);
        userPic = findViewById(R.id.userPic);
        backFromCurrentRuleList = findViewById(R.id.backFromCurrentRules);
        currentRuleList = findViewById(R.id.currentRuleList);

        ConstraintLayout mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout tripMenu = findViewById(R.id.triDotMenu);
                ImageView imageView = findViewById(R.id.card);
                tripMenu.setVisibility(View.GONE);
                imageView.setClickable(true);
            }
        });
    }

    public void newCard(View view){
        makeRuleButton.setVisibility(View.GONE);
        if(cardList.size() == 0){
            userName.setVisibility(View.INVISIBLE);
            userPic.setVisibility(View.INVISIBLE);
            cardImage.setImageResource(R.drawable.card_back);
            seeRuleButton.setVisibility(View.GONE);
            cardRuleText.setVisibility(View.GONE);
        } else {
            changeUser(mUserArray.size());
            String[] ruleSet = getResources().getStringArray(R.array.ruleSet);
            int random = new Random().nextInt(cardList.size());
            String newCardName = cardList.get(random);
            cardList.remove(random);
            int newCard = this.getResources().getIdentifier("drawable/" + newCardName, null, this.getPackageName());
            int ruleId = Integer.parseInt(newCardName.substring(1))-1;

            if(ruleId == 0 || ruleId == 12){
                alreadyChosen = false;
                makeRuleButton.setVisibility(View.VISIBLE);
            }
            cardImage.setImageResource(newCard);
            cardRuleText.setText(ruleSet[ruleId]);
            seeRuleButton.setVisibility(View.VISIBLE);
        }
    }

    public void changeUser(int size){
        if(size == 0){
            userName.setVisibility(View.VISIBLE);
            userName.setText(R.string.ring_of_fylla);
            userPic.setVisibility(View.GONE);
        } else {
            if (turn > mUserArray.size() - 1) {
                turn = 0;
            }
            String userNameText = mUserArray.get(turn).getName();
            String userNamePic = mUserArray.get(turn).getImgUri();
            userName.setText(userNameText);
            userPic.setImageURI(Uri.parse(userNamePic));

            if(userNamePic.contains("drawable")) {
                userPic.setImageURI(Uri.parse(userNamePic));
            } else {
                Glide.with(this)
                        .load(Uri.parse(userNamePic).getPath())
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .apply(RequestOptions.circleCropTransform())
                        .into(userPic);
            }

            turn++;
        }
    }

    public void addPlayer(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(MainActivity.USER_PASS, mUserArray);

        startActivityForResult(intent, NEW_USER_REQUEST);
    }

    public void showRule(View view){
        int visibility = cardRuleText.getVisibility();
        if(visibility == View.INVISIBLE){
            seeRuleButton.setText(R.string.hide_rule);
            visibility = View.VISIBLE;
        } else {
            seeRuleButton.setText(R.string.show_rule);
            visibility = View.INVISIBLE;
        }
        cardRuleText.setVisibility(visibility);
    }

    public void makeRule(View view){
        if(alreadyChosen){
            Toast.makeText(getBaseContext(), R.string.already_chosen_rule, Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, homemadeRuleActivity.class);
            intent.putExtra(usedRuleSend, usedRules);
            startActivityForResult(intent, RULE_REQUEST);
        }
    }

    public void tripleDotMenu(View view){
        if(tripMenu.getVisibility() == View.GONE) {
            tripMenu.setVisibility(View.VISIBLE);
            cardImage.setClickable(false);
            updateCurrentRules();
        } else {
            tripMenu.setVisibility(View.GONE);
            cardImage.setClickable(true);
        }
    }

    public void changeNamePic(View view){
        if(userName.getVisibility() == View.VISIBLE){
            userName.setVisibility(View.GONE);
            userPic.setVisibility(View.VISIBLE);
        } else {
            userName.setVisibility(View.VISIBLE);
            userPic.setVisibility(View.GONE);
        }
    }

    public void updateCurrentRules(){
        ListView currentRuleList = findViewById(R.id.currentRuleList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_rule_list_tridot, R.id.triDotTextView, usedRules);
        currentRuleList.setAdapter(arrayAdapter);
    }

    public void showCurrentRules(View view){
        ViewGroup.LayoutParams tripMenuLayoutParams = tripMenu.getLayoutParams();

        showUsedRules.setVisibility(View.GONE);
        newGameButton.setVisibility(View.GONE);
        newPlayer.setVisibility(View.GONE);
        currentRuleList.setVisibility(View.VISIBLE);
        backFromCurrentRuleList.setVisibility(View.VISIBLE);

        tripMenuLayoutParams.height = tripMenuLayoutParams.height + 300;
        tripMenu.setLayoutParams(tripMenuLayoutParams);

        updateCurrentRules();
    }

    public void hideCurrentRules(View view){
        ViewGroup.LayoutParams tripMenuLayoutParams = tripMenu.getLayoutParams();

        showUsedRules.setVisibility(View.VISIBLE);
        newPlayer.setVisibility(View.VISIBLE);
        newGameButton.setVisibility(View.VISIBLE);
        backFromCurrentRuleList.setVisibility(View.GONE);
        currentRuleList.setVisibility(View.GONE);

        tripMenuLayoutParams.height = tripMenuLayoutParams.height - 300;
        tripMenu.setLayoutParams(tripMenuLayoutParams);
    }

    public void newGame(View view){
        Intent intent = getIntent();
        intent.putExtra(Menu2Activity.USER_LIST, mUserArray);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RULE_REQUEST) {
            if(resultCode == RESULT_OK) {
                usedRules.add(data.getStringExtra(ruleRequest));
                alreadyChosen = true;
            }
        } else if(requestCode == NEW_USER_REQUEST) {
            tripMenu.setVisibility(View.GONE);
            if(resultCode == RESULT_OK) {
                mUserArray = (ArrayList<UserObject>) data.getExtras().getSerializable(LoginActivity.EXTRA_USER);
            }
        }

    }
}