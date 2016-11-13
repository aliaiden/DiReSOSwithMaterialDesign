package com.diresos.alihaider.logreg2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static java.security.AccessController.getContext;

/**
 * Created by Ali Haider on 11/8/2016.
 */
public class MyRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public String myDataSet[];
    public String designations[];
    public String code[];
    public TextView title;
    public TextView des;
    public Button bDetails;
    Context context;
    //  public TextView cod;


    public MyRecycleAdapter(Context context, String MyDataSet[], String Designations[]) {
        this.myDataSet = MyDataSet;
        this.designations = Designations;
        this.context = context;
        //this.code = comapnyCode;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_item_view, parent, false);
        RecyclerView.ViewHolder vh = new MyViewHolder(view);
        return (RecyclerView.ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        title.setText(myDataSet[position]);
        des.setText(designations[position]);
        //cod.setText( code[position] );

        bDetails.setOnClickListener(new View.OnClickListener() {
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

        public MyViewHolder(View annItemView) {
            super(annItemView);

            title = (TextView) annItemView.findViewById(R.id.info_text);
            des = (TextView) itemView.findViewById(R.id.des);
            bDetails = (Button) itemView.findViewById(R.id.bDetails);


            // cod = (TextView) itemView.findViewById( R.id.code );
        }
    }
}
