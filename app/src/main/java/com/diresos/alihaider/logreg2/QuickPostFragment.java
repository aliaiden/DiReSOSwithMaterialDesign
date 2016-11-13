package com.diresos.alihaider.logreg2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


public class QuickPostFragment extends Fragment {

    EditText etSenderName, etSenderEmail, etSenderContact, etPostDetails;
    String senderName, senderEmail, senderContact, pic, postDetails;



    Button bSend, bGallery;
    Button bCamera;
    ImageView ivPic;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quick_post, container, false);

        bCamera = (Button) view.findViewById(R.id.bCamera);
        bGallery = (Button) view.findViewById(R.id.bGallery);
        bSend = (Button) view.findViewById(R.id.b_send_post);
        ivPic = (ImageView) view.findViewById(R.id.ivPic);




        //convert image into b64 string
        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        pic = Base64.encodeToString(ba, Base64.DEFAULT);

        bCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 1);
            }
        });

        bGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 2);
            }
        });


        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                initialize();
                sendDataToServer(senderName, senderEmail, senderContact, pic, postDetails);


            }
        });


        sendDataToServer(senderName, senderEmail, senderContact, pic, postDetails);

        return view;
    }


    private void sendDataToServer(String senderName, String senderEmail, String senderContact, String pic, String postDetails) {

        String url = "http://dirsos.com/insert_post.php/" + senderName + "/" + senderEmail + "/" + senderContact + "/" + pic + "/" + postDetails;
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String Response = response.trim().toString().replace("\"", "");
                        //CheckStatus(Response);
                        ///alert.showAlertDialog(Login.this, "Login failed..", response.trim().toString().replace("\"", "");, false);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        Toast.makeText(((Activity) view.getContext()), "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );


        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void CheckStatus(String response) {


        if (response.trim().equals("error")) {

            Toast.makeText(((Activity) getContext()), response, Toast.LENGTH_SHORT).show();
            //alert.showAlertDialog(((Activity) getContext()), response,"Something went wrong.",  false);

        } else {
            Toast.makeText(((Activity) getContext()), response, Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap pic = (Bitmap) extras.get("data");
            ivPic.setImageBitmap(pic);
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {



            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = view.getContext().getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap galleryPic = BitmapFactory.decodeFile(imagePath, options);
            ivPic.setImageBitmap(galleryPic);

            // Do something with the bitmap


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    private void initialize() {
        //get strings from edit texts
        etSenderName = (EditText) view.findViewById(R.id.etSenderName);
        senderName = etSenderName.getText().toString();

        etSenderEmail = (EditText) view.findViewById(R.id.etEmail);
        senderEmail = etSenderEmail.getText().toString();

        etSenderContact = (EditText) view.findViewById(R.id.etContactNum);
        senderContact = etSenderContact.getText().toString();

        etPostDetails = (EditText) view.findViewById(R.id.etPostDetails);
        postDetails = etPostDetails.getText().toString();
    }


}
