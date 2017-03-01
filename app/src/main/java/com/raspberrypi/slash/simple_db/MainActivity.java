package com.raspberrypi.slash.simple_db;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "registration";
    public static final String REGISTERED = "";
    Context mainAct = MainActivity.this;
    Button register,view, insert, relay;
    TextView token;
    EditText email;
    SharedPreferences share;
    SharedPreferences.Editor editor;
    Register r = new Register();
    String refreshedToken,reg_text;
    Thread reg = new Thread(new Runnable() {
        @Override
        public void run() {
            r.sendRegistrationToServer(refreshedToken, email.getText().toString(),mainAct);
            editor.clear();
            editor.putString(REGISTERED, "Registered").apply();
            Log.d(TAG, reg_text);
            Log.d(TAG, "In reg thread");
            if(Thread.interrupted())
                Log.d(TAG,"Interrupted, now returning");
        }
    });
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        register = (Button) findViewById(R.id.button_reg);
        token = (TextView) findViewById(R.id.textView);
        email = (EditText) findViewById(R.id.editText3);
        view = (Button)findViewById(R.id.button3);
        insert = (Button) findViewById(R.id.button5);
        relay = (Button) findViewById(R.id.button6);
        share = mainAct.getSharedPreferences(REGISTERED, 0);
        editor = share.edit();
        editor.putString(REGISTERED,"Unregistered");
        editor.apply();
        register.setOnClickListener(tokenListener);
        view.setOnClickListener(displayFinder);
        insert.setOnClickListener(queryInserter);
        relay.setOnClickListener(relayFinder);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    View.OnClickListener tokenListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            token.setText(refreshedToken);
            reg_text = share.getString(REGISTERED, "defaultStringIfNothingFound");
            Log.d(TAG, reg_text);
            if (email.getText() != null && isRegistered()) {
                Log.d(TAG, "in gettext if");
                reg.start();
            } else if (!isRegistered()) {
                Toast.makeText(getApplicationContext(), "Already Registered", Toast.LENGTH_SHORT).show();
                if(reg.isAlive()) reg.interrupt();
            }
            Log.d(TAG, "In Onclick");
        }
    };
    View.OnClickListener queryInserter = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent in = new Intent(MainActivity.this, Insert.class);
            startActivity(in);
            finish();
        }
    };
    View.OnClickListener displayFinder = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, Display.class);
            startActivity(i);
            finish();
        }
    };
    View.OnClickListener relayFinder = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, Relay.class);
            startActivity(i);
            finish();
        }
    };

    public boolean isRegistered() {
        Log.d(TAG, "In isreg");
        if (reg_text.equalsIgnoreCase("Unregistered")) {
            Log.d(TAG, reg_text);
            return true;
        } else {
            Log.d(TAG, reg_text);
            return false;
        }
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

