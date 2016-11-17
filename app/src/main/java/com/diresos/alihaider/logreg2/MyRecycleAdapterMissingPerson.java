package com.diresos.alihaider.logreg2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ali Haider on 11/15/2016.
 */

public class MyRecycleAdapterMissingPerson extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public String myDataSet[];
    public String designations[];
    public TextView title;
    public TextView des;
    public Button bRespond;
    Context context;
    //  public TextView cod;


    public MyRecycleAdapterMissingPerson(Context context, String MyDataSet[], String Designations[]) {
        this.myDataSet = MyDataSet;
        this.designations = Designations;
        this.context = context;
        //this.code = comapnyCode;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.missing_persons_item_view, parent, false);
        RecyclerView.ViewHolder vh = new MyViewHolder(view);
        return (RecyclerView.ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        title.setText(myDataSet[position]);
        des.setText(designations[position]);
        //cod.setText( code[position] );

        bRespond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "message", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return myDataSet.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View mpItemView) {
            super(mpItemView);

            title = (TextView) mpItemView.findViewById(R.id.info_text);
            des = (TextView) itemView.findViewById(R.id.des);
            bRespond = (Button) itemView.findViewById(R.id.bRespond);


            // cod = (TextView) itemView.findViewById( R.id.code );
        }
    }
}
