package com.raspberrypi.slash.simple_db;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Insert extends AppCompatActivity{

    EditText id, relay;
    Button insert;
    String idEdit,relayEdit;
    String POST_URL = "http://192.168.1.16/relay-send.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        id = (EditText) findViewById(R.id.editText2);
        relay = (EditText) findViewById(R.id.editText);
        insert = (Button) findViewById(R.id.button4);
        insert.setOnClickListener(inserter);
    }
    View.OnClickListener inserter = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try{
                insertIntoServer();
            }catch (Exception e){

            }
        }
    };
    public static final String TAG = "registrationPOST";
    public StringRequest req;
    public void insertIntoServer() throws IOException{
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.1.16/relay-send.php");
        idEdit = id.getText().toString();
        relayEdit = relay.getText().toString();
        String POST_PARAMS = "id="+idEdit+"&relayValue="+relayEdit;

        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        Log.d("POST Response Code :: " , "responseCode: "+responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }
    }
}
