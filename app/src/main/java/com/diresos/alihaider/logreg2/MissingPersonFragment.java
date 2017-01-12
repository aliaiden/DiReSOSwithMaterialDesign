package com.diresos.alihaider.logreg2;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.content;
import static android.R.id.list;


/**
 * A simple {@link Fragment} subclass.
 */
public class MissingPersonFragment extends Fragment {

    String missingPersonNames[];
    String genders[];
    String ages[];
    String lastLocations[];
    String appearances[];
    String contacts[];
    String images[];
    String descriptions[];

    View view;
    EditText search;
    RecyclerView rv;
    FloatingActionButton bAddMissingPerson;
    RecyclerView.Adapter adaptor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_missing_person, container, false);
        bAddMissingPerson = (FloatingActionButton) view.findViewById(R.id.bAddMissingPerson);
        search = (EditText) view.findViewById( R.id.search);

        ((AppCompatActivity) getActivity()).getSupportActionBar();


        bAddMissingPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), AddMissingPersonActivity.class);
                startActivity(i);

            }
        });


        //Initialize recycler view

        rv = (RecyclerView) view.findViewById(R.id.rv_missing_persons);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(lm);




        fetchDataFromServer();

//        String myDataSet[] = {"3rd Missing Person", "2nd Missing Person", "1st Missing Person"};
//        String Designations[] = {
//                "This a sample missing person's details. Original details will be fetched from webservice provided from the website.",
//                "This a sample missing person's details. Original details will be fetched from webservice provided from the website.",
//                "This a sample missing person's details. Original details will be fetched from webservice provided from the website."
//        };
        //String companyCode[] = {"001","002","003"};

        adaptor = new MyRecycleAdapterMissingPerson(view.getContext(),missingPersonNames,genders,ages,lastLocations,appearances,contacts,images,descriptions);
        rv.setAdapter(adaptor);

        addTextListener(missingPersonNames, genders);

        return view;
        



    }









    private void fetchDataFromServer() {
        String url = "http://app.dirsos.com/api/getMissingPerson";
        JsonArrayRequest stringRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(DetailClass.this, response.toString(), Toast.LENGTH_SHORT).show();
                        missingPersonNames = new String[response.length()];
                        genders = new String[response.length()];
                        ages = new String[response.length()];
                        lastLocations = new String[response.length()];
                        appearances = new String[response.length()];
                        contacts = new String[response.length()];
                        images = new String[response.length()];
                        descriptions = new String[response.length()];

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                missingPersonNames[i] = obj.getString("Name");
                                genders[i] = obj.getString("gender");
                                ages[i] = obj.getString("age");
                                lastLocations[i] = obj.getString("last_loc");
                                appearances[i] = obj.getString("appearence");
                                contacts[i] = obj.getString("Contact");
                                images[i] = obj.getString("Picture");
                                descriptions[i] = obj.getString("detail");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //prog.dismiss();
                            RecyclerView.Adapter rAdaptor  = new MyRecycleAdapterMissingPerson(view.getContext(),missingPersonNames,genders,ages,lastLocations,appearances,contacts,images,descriptions);
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

    private void addTextListener(final String[] myDataSet, final String[] designations) {

        final String[] filteredDataSet = null;
        final String[] filteredDesignations = null;

        if (myDataSet != null || designations != null) {


            search.addTextChangedListener(new TextWatcher() {


                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence query, int start, int before, int count) {

                    query = query.toString().toLowerCase();

                    final List<String> filteredList = new ArrayList<>();


                    for (int i = 0; i <myDataSet.length; i++) {

                        final String text1 = myDataSet[i].toLowerCase();
                        if (text1.contains(query)) {

                            filteredList.add(myDataSet[i]);

                        }
                    }
                    try{
                        filteredDataSet[filteredList.size() - 1] =  filteredList.toString();
                    }catch (Exception e){
                        Toast.makeText(view.getContext(), "Error Error", Toast.LENGTH_LONG);
                    }


                    for (int i = 0; i < designations.length; i++) {

                        final String text1 = designations[i].toLowerCase();
                        if (text1.contains(query)) {

                            filteredList.add(designations[i]);
                        }
                    }

                    filteredDesignations[filteredList.size()] = filteredList.toString();

                    rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    //adaptor = new MyRecycleAdapterMissingPerson(view.getContext(),filteredDataSet, filteredDesignations);
                    //adaptor = new MyRecycleAdapterMissingPerson(filteredList, view.getContext());
                    rv.setAdapter(adaptor);
                    adaptor.notifyDataSetChanged();  // data set changed
                }
            });
        }


    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }
}
