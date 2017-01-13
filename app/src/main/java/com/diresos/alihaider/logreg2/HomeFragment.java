package com.diresos.alihaider.logreg2;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ali on 1/1/17.
 */
public class HomeFragment extends Fragment {


    String senderNames[];
    String times[];
    String dates[];
    String descriptions[];
    String images[];

    RecyclerView rv;
    RecyclerView.Adapter adaptor;


    int delay = 0; // delay for 0 sec.
    int period = 2000; // repeat every 25 seconds.
    Timer timer = new Timer();


    int gallery_grid_Images[] = {R.drawable.home_flipper_image1, R.drawable.home_flipper_image2, R.drawable.home_flipper_image3,
            R.drawable.home_flipper_image4, R.drawable.home_flipper_image5};

    View view;
    ViewFlipper viewFlipper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);



        //Initialize recycler view

        rv = (RecyclerView) view.findViewById(R.id.rv_posts);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(lm);

        fetchDataFromServer();

        adaptor = new MyRecycleAdapterQuickPost(view.getContext(), senderNames, times, dates, descriptions, images);
        rv.setAdapter(adaptor);


        // Inflate the layout for this fragment
        viewFlipper = (ViewFlipper) view.findViewById(R.id.flipper);
        for (int i = 0; i < gallery_grid_Images.length; i++) {
            //  This will create dynamic image view and add them to ViewFlipper
            setFlipperImage(gallery_grid_Images[i]);
        }
        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.showNext();

            }
        });
        return view;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void setFlipperImage(int res) {
        Log.i("Set Filpper Called", res + "");
        ImageView image = new ImageView(getActivity());
        image.setBackgroundResource(res);
        viewFlipper.addView(image);
    }

    public class SampleTimerTask extends TimerTask {
        @Override
        public void run() {
            //MAKE YOUR LOGIC TO SET IMAGE TO IMAGEVIEW
        }
    }


    private void fetchDataFromServer()
    {
        String url = "http://app.dirsos.com/api/getDashBoard";
        JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(DetailClass.this, response.toString(), Toast.LENGTH_SHORT).show();
                        senderNames = new String[response.length()];
                        times = new String[response.length()];
                        dates = new String[response.length()];
                        descriptions = new String[response.length()];
                        images = new String[response.length()];

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                senderNames[i] = obj.getString("Name");
                                times[i] = obj.getString("gender");
                                dates[i] = obj.getString("age");
                                descriptions[i] = obj.getString("last_loc");
                                images[i] = obj.getString("appearence");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //prog.dismiss();
                            RecyclerView.Adapter rAdaptor = new MyRecycleAdapterQuickPost(view.getContext(), senderNames, times, dates, descriptions, images);
                            rAdaptor.notifyDataSetChanged();
                            rv.setAdapter(rAdaptor);

                        }
                    }
                },

                new Response.ErrorListener()

                {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);

    }
}
