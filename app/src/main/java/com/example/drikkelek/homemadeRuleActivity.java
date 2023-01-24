package com.example.drikkelek;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class homemadeRuleActivity extends AppCompatActivity {
    boolean newRuleView = false;
    boolean alreadyChosen = false;
    String selectedRule;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("rules");

    ArrayList<String> ruleList = new ArrayList<String>(){};
    ArrayList<String> usedRules = new ArrayList<String>();

    EditText newRuleText;
    Button makeNewRuleButton;
    FloatingActionButton floatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemade_rule_coordinatorlayout);
        Intent intent = getIntent();
        usedRules = intent.getStringArrayListExtra(CardsActivity.usedRuleSend);

        newRuleText = findViewById(R.id.makeNewRuleConfirm);
        makeNewRuleButton = findViewById(R.id.makeNewRuleButton);
        floatButton = findViewById(R.id.confirm);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ruleList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ruleList.add(ds.getValue().toString());
                }
                updateList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNewRule();
            }
        });
    }

    public void showMakeNewRule(View view){
        if(!newRuleView) {
            floatButton.setImageResource(R.drawable.plus_sign);
            newRuleText.setVisibility(View.VISIBLE);
            newRuleView = true;
        } else {
            floatButton.setImageResource(R.drawable.checkmark_sign);
            newRuleText.setVisibility(View.GONE);
            newRuleView = false;
        }
    }

    public void makeNewRule(){
        if(!newRuleView) {
            finishRule();
        } else {
            String newRule = newRuleText.getText().toString();
            if(newRule.equals("")){
                floatButton.setImageResource(R.drawable.checkmark_sign);
                newRuleText.setVisibility(View.GONE);
                newRuleView = false;
                Toast.makeText(getBaseContext(), R.string.tomt_felt, Toast.LENGTH_LONG).show();
            } else {
                addRule(newRule);
            }
        }
    }

    public void updateList(){
        final ListView simpleList = findViewById(R.id.ruleList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_rule_list, R.id.textView, ruleList);
        simpleList.setAdapter(arrayAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
                selectedRule =(simpleList.getItemAtPosition(pos).toString());
                alreadyChosen = usedRules.contains(selectedRule);
            }
        });
    }

    public void addRule(String newRule){
            floatButton.setImageResource(R.drawable.checkmark_sign);
            newRuleText.setVisibility(View.GONE);
            newRuleView = false;
            myRef.push().setValue(newRule);
            newRuleText.setText("");
            newRuleText.setVisibility(View.GONE);
    }

    public void finishRule(){
        if(selectedRule == null){
            Toast.makeText(getBaseContext(), R.string.dont_pick_empty_rule, Toast.LENGTH_LONG).show();
        } else if (alreadyChosen){
            Toast.makeText(getBaseContext(), R.string.already_chosen_this_rule, Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, CardsActivity.class);
            intent.putExtra(CardsActivity.ruleRequest, selectedRule);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
