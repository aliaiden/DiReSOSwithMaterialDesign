package com.diresos.alihaider.logreg2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ali Haider on 11/8/2016.
 */

public class AnnouncementsFragment extends Fragment {

    String titles[];
    //={"Volunteers Gathering"};
    String messages[];
    //={"People who are willing to participate in the relief providing process mus make sure to be present at Edhi meeting center at 5pm"};

    View view;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_announcements, container, false);

        //Initialize recycler view

        rv = (RecyclerView) view.findViewById(R.id.rv_announcement);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(lm);

        fetchDataFromServer();

        //String companyCode[] = {"001","002","003"};

        RecyclerView.Adapter adaptor = new MyRecycleAdapterAnnouncements(view.getContext(),titles, messages);
        rv.setAdapter(adaptor);

        return view;
    }

    private void fetchDataFromServer() {
        String url = "http://app.dirsos.com/api/getAnnouncement";
        JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(DetailClass.this, response.toString(), Toast.LENGTH_SHORT).show();
                        titles = new String[response.length()];
                        messages = new String[response.length()];

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                titles[i] = obj.getString("title");
                                messages[i] = obj.getString("message");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //prog.dismiss();
                            RecyclerView.Adapter rAdaptor = new MyRecycleAdapterAnnouncements(view.getContext(), titles, messages);
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
