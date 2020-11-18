package com.openclassrooms.realestatemanager.oldactivity;

import android.os.Bundle;
//Change due to migration to androidx and permit the compilation
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;


import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.Utils;

public class OldMainActivity extends AppCompatActivity {

    private TextView textViewMain;
    private TextView textViewQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FIX BUG : The textview id wasn't the right one
        //this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main);
        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main);
        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);

        this.configureTextViewMain();
        this.configureTextViewQuantity();

        boolean lIsInternetAvailable = Utils.isInternetAvailable(this);
    }

    private void configureTextViewMain(){
        this.textViewMain.setTextSize(15);
        this.textViewMain.setText("Le premier bien immobilier enregistré vaut ");
    }

    private void configureTextViewQuantity(){
        int quantity = Utils.convertDollarToEuro(100);
        this.textViewQuantity.setTextSize(20);
        //FIX BUG : The quantity is converted into a string (with Integer.toString()) for the display on the screen
        // setText(int) is also used to get string resource by id. And In Android all resource id has int value.
        this.textViewQuantity.setText(Integer.toString(quantity));
    }
}
