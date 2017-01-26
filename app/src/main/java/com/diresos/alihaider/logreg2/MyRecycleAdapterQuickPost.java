package com.diresos.alihaider.logreg2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ali on 1/12/17.
 */

public class MyRecycleAdapterQuickPost extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public String[] senderNames;
    public String[] times;
    public String[] dates;
    public String[] descriptions;
    public String[] images;


    public TextView tvSenderName;
    public TextView tvTime;
    public TextView tvDate;
    public TextView tvDescription;
    public ImageView ivPostPic;

    Context context;
    //  public TextView cod;

    public MyRecycleAdapterQuickPost(Context context, String[] senderNames, String[] times, String[] dates, String[] descriptions, String[] images)
    {

        this.context = context;

        this.senderNames = senderNames;
        this.times = times;
        this.dates = dates;
        this.descriptions = descriptions;
        this.images = images;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quick_post_item_view, parent, false);
        RecyclerView.ViewHolder vh = new MyViewHolder(view);
        return (RecyclerView.ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        tvSenderName.setText(senderNames[position]);
        tvTime.setText(times[position]);
        tvDate.setText(dates[position]);
        tvDescription.setText(descriptions[position]);
        //  missingPersonPic.setImageUrl(missingPersonNames[position],imageLoader);
        Picasso.with(context).load("http://dirsos.com/userdata/postsimages/" + images[position]).into(ivPostPic);
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
        return (senderNames == null) ? 0 : senderNames.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View mpItemView) {
            super(mpItemView);

            tvSenderName = (TextView) mpItemView.findViewById(R.id.tvSenderName);
            tvTime = (TextView) mpItemView.findViewById(R.id.tvTime);
            tvDate = (TextView) mpItemView.findViewById(R.id.tvDate);
            tvDescription = (TextView) mpItemView.findViewById(R.id.tvDescription);
            ivPostPic = (ImageView) mpItemView.findViewById(R.id.ivPost);
        }
    }
}