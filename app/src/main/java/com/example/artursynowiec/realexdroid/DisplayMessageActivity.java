package com.example.artursynowiec.realexdroid;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.realexpayments.hpp.HPPError;
import com.realexpayments.hpp.HPPManager;
import com.realexpayments.hpp.HPPManagerListener;
import com.realexpayments.hpp.HPPResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class DisplayMessageActivity extends AppCompatActivity implements HPPManagerListener {

    public static final String HPP_RESPONSE_RES = "com.example.artursynowiec.realexdroid.getResponse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String amount = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(amount);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        layout.addView(textView);

        Fragment hppFragement = initHppManager(amount).newInstance();
        getFragmentManager().beginTransaction().add(R.id.content, hppFragement).commit();


    }

    private HPPManager initHppManager(String amount){
        HPPManager manager = new HPPManager();

        manager.setHppRequestProducerURL("http://10.0.2.2:8080/hppRequestProcedure");
        manager.setHppURL("https://hpp.test.realexpayments.com/pay");
        manager.setHppResponseConsumerURL("http://10.0.2.2:8080/hppResponseConsumer");



        manager.setAmount(amount);




        return manager;
    }


    @Override
    public void hppManagerCompletedWithResult(Object response) {
        Gson gson = new GsonBuilder().create();
        HPPResponse res = new HPPResponse();
        String jsonString = gson.toJson(response);
        res = gson.fromJson(jsonString, HPPResponse.class);

        Intent intent = new Intent(this, PaymentResActivity.class);
        //intent.putExtra(HPP_RESPONSE_DATA, (Parcelable)res);
        intent.putExtra(HPP_RESPONSE_RES, jsonString);

        startActivity(intent);



    }

    @Override
    public void hppManagerFailedWithError(HPPError error) {
        String res = error.toString();
        res = res;
    }

    @Override
    public void hppManagerCancelled() {

    }

}
