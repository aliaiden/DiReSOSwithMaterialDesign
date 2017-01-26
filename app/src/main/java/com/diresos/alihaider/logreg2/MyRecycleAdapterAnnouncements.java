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

import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

/**
 * Created by Ali Haider on 11/8/2016.
 */
public class MyRecycleAdapterAnnouncements extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    public String[] titles;
    public String[] messages;

    public TextView tvTitle;
    public TextView tvMessage;


    Context context;
    //  public TextView cod;

    public MyRecycleAdapterAnnouncements(Context context, String[] titles, String[] messages) {

        this.context = context;

        this.titles = titles;
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_item_view, parent, false);
        RecyclerView.ViewHolder vh = new MyRecycleAdapterAnnouncements.MyViewHolder(view);
        return (RecyclerView.ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        tvTitle.setText(titles[position]);
        tvMessage.setText(messages[position]);
    }

    @Override
    public int getItemCount() {
        // return missingPersonNames.length;
        return (titles == null) ? 0 : messages.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View mpItemView) {
            super(mpItemView);

            tvTitle = (TextView) mpItemView.findViewById(R.id.tvTitle);
            tvMessage = (TextView) mpItemView.findViewById(R.id.tvMessage);
        }
    }
}