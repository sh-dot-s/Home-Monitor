package com.raspberrypi.slash.simple_db;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    Register r = new Register();
    public static final String TAG = "registrationPOST";
    Context mainAct = MainActivity.this;
    Button register;
    TextView token;
    EditText email;
    SharedPreferences share;
    SharedPreferences.Editor editor;

    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        register = (Button) findViewById(R.id.button_reg);
        token = (TextView) findViewById(R.id.textView);
        email = (EditText) findViewById(R.id.editText3);
        share = getSharedPreferences(Constants.SHARED_PREF,MODE_PRIVATE);
        editor= share.edit();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText() != null && isRegistered()) {
                    r.sendRegistrationToServer(refreshedToken, email.getText().toString());
                    editor.putString(Constants.REGISTERED, "True");
                    editor.apply();
                }
                r.checkRegistration(mainAct);
                token.setText(refreshedToken);
                if(isRegistered())
                    Toast.makeText(getApplicationContext(),"Already Registered", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isRegistered(){

        if(share.toString().equals("False"))
            return false;
        else
            return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}

