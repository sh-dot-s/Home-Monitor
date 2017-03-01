package com.raspberrypi.slash.simple_db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Relay extends AppCompatActivity{
    int i=1;
    Button toggle;
    String msg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relay);
        toggle = (Button) findViewById(R.id.button7);
        toggle.setOnClickListener(toggleListener);

    }
    View.OnClickListener toggleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            msg = toggle.getText().toString();
            if(msg.equalsIgnoreCase("OFF")){
                msg = "ON";
                toggle.setText(msg);
            }
            else if(msg.equalsIgnoreCase("ON")){
                msg = "OFF";
                toggle.setText(msg);
            }
            i++;
            Toast.makeText(Relay.this,msg, Toast.LENGTH_SHORT).show();
            reg.start();
            reg.interrupt();
        }
    };
    public static final String TAG = "relayPOST";
    Thread reg = new Thread(new Runnable() {
        @Override
        public void run() {
            sendRelayToServer();
        }
    });
    public void sendRelayToServer(){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.0.112/relay-send.php");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("relayValue", msg));
            nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(i)));
            i++;
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            Log.d(TAG,"Reg_Success");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG,"Failed Error : ",e);
        }
        return;
    }
}
