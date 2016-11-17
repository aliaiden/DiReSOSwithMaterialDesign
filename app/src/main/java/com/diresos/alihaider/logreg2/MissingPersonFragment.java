package com.diresos.alihaider.logreg2;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MissingPersonFragment extends Fragment {

    View view;
    RecyclerView rv;
    FloatingActionButton bAddMissingPerson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_missing_person, container, false);
        bAddMissingPerson = (FloatingActionButton) view.findViewById(R.id.bAddMissingPerson);


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

        RecyclerView.Adapter adaptor = new MyRecycleAdapterMissingPerson(view.getContext(),myDataSet, Designations);
        rv.setAdapter(adaptor);

        return view;
    }
}
