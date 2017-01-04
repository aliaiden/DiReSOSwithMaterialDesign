package com.diresos.alihaider.logreg2;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ali on 1/1/17.
 */
public class HomeFragment extends Fragment {

    int delay = 0; // delay for 0 sec.
    int period = 2000; // repeat every 25 seconds.
    Timer timer = new Timer();


    int gallery_grid_Images[]={R.drawable.home_flipper_image1,R.drawable.home_flipper_image2,R.drawable.home_flipper_image3,
            R.drawable.home_flipper_image4,R.drawable.home_flipper_image5};

    View view;
    ViewFlipper viewFlipper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.flipper);
        for(int i=0;i<gallery_grid_Images.length;i++)
        {
            //  This will create dynamic image view and add them to ViewFlipper
            setFlipperImage(gallery_grid_Images[i]);
        }
        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.showNext();

            }
        });
        return view;
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void setFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        ImageView image = new ImageView(getActivity());
        image.setBackgroundResource(res);
        viewFlipper.addView(image);
    }

    public class SampleTimerTask extends TimerTask {
        @Override
        public void run() {
            //MAKE YOUR LOGIC TO SET IMAGE TO IMAGEVIEW
        }
    }
}
