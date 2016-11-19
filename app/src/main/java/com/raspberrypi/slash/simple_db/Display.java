package com.raspberrypi.slash.simple_db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class Display extends AppCompatActivity{

    WebView reader;
    Button read;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        reader = (WebView) findViewById(R.id.tempRead);
        read = (Button)findViewById(R.id.button2);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reader.loadUrl("http://192.168.0.110/temp-readings.php");
            }
        });
    }

}
