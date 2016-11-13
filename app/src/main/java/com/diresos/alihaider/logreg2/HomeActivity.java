package com.diresos.alihaider.logreg2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    Button dashboard, sendNot;
    String app_server_url = "http://dirsos.com/fcm_test/fcm_insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


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

                android.app.FragmentManager myFrag = getFragmentManager();


                switch (item.getItemId()) {

                    case R.id.nav_quick_post:
                        myFrag.beginTransaction().replace(R.id.fLayout, new QuickPostFragment()).commit();
                       // Intent i = new Intent(HomeActivity.this, PostActivity.class);
                        //startActivity(i);

                        break;

                    case R.id.nav_missing_person:
                        myFrag.beginTransaction().replace(R.id.fLayout, new MissingPersonFragment()).commit();
                        break;

                    case R.id.nav_donate:
                        myFrag.beginTransaction().replace(R.id.fLayout, new DonateFragment()).commit();
                        break;

                    case R.id.nav_announcments:
                        myFrag.beginTransaction().replace(R.id.fLayout, new AnnouncementsFragment()).commit();
                        break;

                    case R.id.nav_about:
                        myFrag.beginTransaction().replace(R.id.fLayout, new AboutFragment()).commit();
                        break;

                    case R.id.nav_events:
                        myFrag.beginTransaction().replace(R.id.fLayout, new EventsFragment()).commit();
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




}
