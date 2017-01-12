package com.diresos.alihaider.logreg2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Ali Haider on 11/15/2016.
 */

public class MyRecycleAdapterMissingPerson extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public String[] missingPersonNames;
    public String[] genders;
    public String[] ages;
    public String[] lastLocations;
    public String[] contacts;
    public String[] images;
    public String[] descriptions;
    public String[] appearances;

    public TextView missingPersonName;
    public TextView gender;
    public TextView age;
    public TextView lastLocation;
    public TextView contact;
    public TextView description;
    public TextView appearance;


    public ImageView missingPersonPic;

    public Button bRespond;
    Context context;
    //  public TextView cod;

    public MyRecycleAdapterMissingPerson(Context context, String[] missingPersonNames, String[] genders, String[] ages, String[] lastLocations, String[] appearances, String[] contacts, String[] images, String[] descriptions) {

        this.context = context;

        this.missingPersonNames = missingPersonNames;
        this.genders = genders;
        this.ages = ages;
        this.lastLocations = lastLocations;
        this.appearances = appearances;
        this.contacts = contacts;
        this.images = images;
        this.descriptions = descriptions;
        //this.code = comapnyCode;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.missing_persons_item_view, parent, false);
        RecyclerView.ViewHolder vh = new MyViewHolder(view);
        return (RecyclerView.ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        missingPersonName.setText(missingPersonNames[position]);
        gender.setText(genders[position]);
        age.setText(ages[position]);
        lastLocation.setText(lastLocations[position]);
        appearance.setText(appearances[position]);
        contact.setText(contacts[position]);
        description.setText(descriptions[position]);
      //  missingPersonPic.setImageUrl(missingPersonNames[position],imageLoader);
        Picasso.with(context).load("http://www.dirsos.com/userdata/missingperson/" + images[position]).into(missingPersonPic);
        //cod.setText( code[position] );

//        bRespond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "message", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
       // return missingPersonNames.length;
        return (missingPersonNames == null) ? 0 : missingPersonNames.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View mpItemView) {
            super(mpItemView);

          //  bRespond = (Button) itemView.findViewById(R.id.bRespond);

            missingPersonName = (TextView) mpItemView.findViewById(R.id.tvName);
            gender = (TextView) mpItemView.findViewById(R.id.tvGender);
            age = (TextView) mpItemView.findViewById(R.id.tvAge);
            lastLocation = (TextView) mpItemView.findViewById(R.id.tvLastLocation);
            appearance = (TextView) mpItemView.findViewById(R.id.tvAppearance);
            contact = (TextView) mpItemView.findViewById(R.id.tvContact);
            description = (TextView) mpItemView.findViewById(R.id.tvDescription);
            missingPersonPic = (ImageView) mpItemView.findViewById(R.id.ivMissingPersonPic);
            // cod = (TextView) itemView.findViewById( R.id.code );
        }
    }
}