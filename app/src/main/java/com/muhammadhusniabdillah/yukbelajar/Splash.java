package com.muhammadhusniabdillah.yukbelajar;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent splash = new Intent(this, MainActivity.class);
        startActivity(splash);
        finish();
    }
}
