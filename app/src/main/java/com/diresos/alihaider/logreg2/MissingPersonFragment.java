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

import java.util.ArrayList;
import java.util.List;

import static android.R.id.content;
import static android.R.id.list;


/**
 * A simple {@link Fragment} subclass.
 */
public class MissingPersonFragment extends Fragment {

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

        String myDataSet[] = {"3rd Missing Person", "2nd Missing Person", "1st Missing Person"};
        String Designations[] = {
                "This a sample missing person's details. Original details will be fetched from webservice provided from the website.",
                "This a sample missing person's details. Original details will be fetched from webservice provided from the website.",
                "This a sample missing person's details. Original details will be fetched from webservice provided from the website."
        };
        //String companyCode[] = {"001","002","003"};

        adaptor = new MyRecycleAdapterMissingPerson(view.getContext(),myDataSet, Designations);
        rv.setAdapter(adaptor);

        addTextListener(myDataSet, Designations);

        return view;
        



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
                    adaptor = new MyRecycleAdapterMissingPerson(view.getContext(),filteredDataSet, filteredDesignations);
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
