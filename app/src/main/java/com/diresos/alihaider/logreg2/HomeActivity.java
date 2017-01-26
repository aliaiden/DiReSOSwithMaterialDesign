package com.diresos.alihaider.logreg2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final boolean TODO = true;
    NavigationView navigationView;
    Toolbar toolbar;
    Button dashboard, sendNot;
    //String app_server_url = "http://dirsos.com/fcm_test/fcm_insert.php";
    String app_server_url = "http://app.dirsos.com/api/insertToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final android.app.FragmentManager myFrag = getFragmentManager();

        setContentView(R.layout.activity_home);


        //send token to server at start of activity
        sendTokenValue();

        //set fragment at start of activity
        myFrag.beginTransaction().replace(R.id.fLayout, new HomeFragment()).commit();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setItemIconTintList(null);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);


        //Initializing the navigation drawer
        final NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                //close the drawer again
                NavigationDrawerFragment.mDrawerLayout.closeDrawer(Gravity.LEFT);




                switch (item.getItemId()) {
                    case R.id.nav_home:
                        myFrag.beginTransaction().replace(R.id.fLayout, new HomeFragment()).commit();
                        // Intent i = new Intent(HomeActivity.this, PostActivity.class);
                        //startActivity(i);

                        break;
                    case R.id.nav_quick_post:
                        myFrag.beginTransaction().replace(R.id.fLayout, new QuickPostFragment()).commit();
                        // Intent i = new Intent(HomeActivity.this, PostActivity.class);
                        //startActivity(i);

                        break;

                    case R.id.nav_missing_person:
                        myFrag.beginTransaction().replace(R.id.fLayout, new MissingPersonFragment()).commit();
                        break;

                    case R.id.nav_donate:
                        Uri uri = Uri.parse("http://www.dirsos.com/donation.php"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);                        break;

                    case R.id.nav_announcments:
                        myFrag.beginTransaction().replace(R.id.fLayout, new AnnouncementsFragment()).commit();
                        break;

                    case R.id.nav_about:
                        myFrag.beginTransaction().replace(R.id.fLayout, new AboutFragment()).commit();
                        break;


                    case R.id.nav_website:
                        Uri uriWeb = Uri.parse("http://www.dirsos.com"); // missing 'http://' will cause crashed
                        Intent intentWeb = new Intent(Intent.ACTION_VIEW, uriWeb);
                        startActivity(intentWeb);
                        break;
                    case R.id.nav_email:
                        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "info@dirsos.com", null));
                        startActivity(Intent.createChooser(i, "Choose an Email client :"));
                        break;
                    case R.id.nav_sms:

                        showSMSDialog();

                        break;
                    case R.id.nav_call:
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:03009203159"));
                        if (ActivityCompat.checkSelfPermission(getApplication(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return TODO;
                        }
                        startActivity(callIntent);

                        break;
                }
                return true;
            }
        });


        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");
        Log.d("TOKENTOKTOKENTOKENTOKE", token);

        //Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dashboard = (Button) findViewById(R.id.bDashboard);
        sendNot = (Button) findViewById(R.id.bSendNot);


        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        sendNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");
                Log.d("TOKENTOKTOKENTOKENTOKE", token);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("fcm_token", token);

                        return params;
                    }
                };
                MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }

    private void sendTokenValue() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");
        Log.d("TOKENTOKTOKENTOKENTOKE", token);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fcm_token", token);

                return params;
            }
        };
        MySingleton.getInstance(HomeActivity.this).addToRequestQueue(stringRequest);
    }

    private void showSMSDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.sms_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText smsMessage = (EditText) dialogView.findViewById(R.id.etSMSmessage);
        dialogBuilder.setTitle("Send us an SMS");
        dialogBuilder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                sendSMS(smsMessage.getText().toString());
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void sendSMS(String s) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+923009203159", null, s, null, null);
            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Failed, please try again", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}
