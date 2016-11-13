package com.diresos.alihaider.logreg2;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ali Haider on 11/8/2016.
 */

public class AnnouncementsFragment extends Fragment {

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

        String myDataSet[] = {"3rd Announcement", "2nd Announcement", "1st Announcement"};
        String Designations[] = {
                "This a sample announcement text. Original announcements will be fetched from webservice provided from the website.",
                "This a sample announcement text. Original announcements will be fetched from webservice provided from the website.",
                "This a sample announcement text. Original announcements will be fetched from webservice provided from the website."
        };
        //String companyCode[] = {"001","002","003"};

        RecyclerView.Adapter adaptor = new MyRecycleAdapter(view.getContext(),myDataSet, Designations);
        rv.setAdapter(adaptor);

        return view;
    }
}
