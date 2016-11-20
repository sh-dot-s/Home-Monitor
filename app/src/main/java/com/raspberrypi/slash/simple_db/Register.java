package com.raspberrypi.slash.simple_db;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Register {
    public static final String TAG = "registrationPOST";
    public StringRequest req;
    public void sendRegistrationToServer(String refreshedToken, String email,Context cont){
        final Context c = cont;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.0.110/Tutorial/Register.php");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("firebaseid", refreshedToken));
            nameValuePairs.add(new BasicNameValuePair("email",email));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            Log.d(TAG,"Reg_Success");
            req = new StringRequest(Request.Method.POST, Constants.REGISTER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.trim().equalsIgnoreCase("success"))
                        Toast.makeText(c, "Registration Successful", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(c, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG,"Failed Error : ",e);
        }
    }
}
