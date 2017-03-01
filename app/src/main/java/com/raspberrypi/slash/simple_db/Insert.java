package com.raspberrypi.slash.simple_db;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Insert extends AppCompatActivity implements View.OnClickListener {
    
    private Button buttonGet;
    private TextView textViewResult;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        
        buttonGet = (Button) findViewById(R.id.button4);
        textViewResult = (TextView) findViewById(R.id.textView2);

        buttonGet.setOnClickListener(this);
    }

    private void getData() {

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Constants.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Insert.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String pir="", soil="",raindrop="",temperature="",humidity="";
        String flame="";
        String reed = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constants.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            pir = collegeData.getString(Constants.KEY_PIR);
            flame = collegeData.getString(Constants.KEY_FLAME);
            soil = collegeData.getString(Constants.KEY_SOIL);
            raindrop = collegeData.getString(Constants.KEY_RAINDROP);
            temperature = collegeData.getString(Constants.KEY_TEMPERATURE);
            humidity = collegeData.getString(Constants.KEY_HUMIDITY);
            reed = collegeData.getString(Constants.KEY_REED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textViewResult.setText("Motion:\t"+pir+"\nFire:\t" +flame+ "\nsoil:\t"+ soil+ "\nraindrop:\t"+ raindrop+ "\ntemperature:\t"+ temperature+ "\nHumidity:\t"+ humidity+ "\nReed:\t"+ reed);
    }

    @Override
    public void onClick(View v) {
        getData();
    }
}