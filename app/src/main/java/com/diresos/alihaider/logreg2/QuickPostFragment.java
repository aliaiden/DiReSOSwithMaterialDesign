package com.diresos.alihaider.logreg2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class QuickPostFragment extends Fragment {

    EditText etSenderName, etSenderEmail, etSenderContact, etPostDetails;
    String sname, email, post, contact, image, post_cat, p_time, p_date, status;
    TextView chooseCategory;

    Button bSend, bGallery;
    Button bCamera;
    ImageView ivPic;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quick_post, container, false);

        status = "1";

        bCamera = (Button) view.findViewById(R.id.bCamera);
        bGallery = (Button) view.findViewById(R.id.bGallery);
        bSend = (Button) view.findViewById(R.id.b_send_post);
        ivPic = (ImageView) view.findViewById(R.id.ivPic);
        chooseCategory = (TextView) view.findViewById(R.id.tvChooseCategory);

        //convert image into b64 string
        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        image = Base64.encodeToString(ba, Base64.DEFAULT);

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

        chooseCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });


        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                try {
                    initialize();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sendDataToServer(sname, email, post, contact, image, post_cat, p_time, p_date, status);


            }
        });



        return view;
    }

    private void showAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.post_category_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText smsMessage = (EditText) dialogView.findViewById(R.id.etSMSmessage);
        //dialogBuilder.setTitle("Send us an SMS");
        dialogBuilder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    private void sendDataToServer(final String sname, final String email, final String post, final String contact, final String image, String post_cat, String p_time, String p_date, String status) {
        post_cat = "1";

        String url = "http://app.dirsos.com/api/setPost";
        /*String REGISTER_URL = "http://app.dirsos.com/api/setPost/" + sname + "/" + email + "/" + post + "/" + contact + "/" +
                image + "/" +   "post_cat" +
                "/" +  "p_time"  + "/" +  "p_date" + "/" +  status;
        String url = "http://app.dirsos.com/api/setPost/" + sname + "/" + email + "/" + post + "/" + contact + "/" +
                "image" + "/" +   "post_cat" +
                 "/" +  "p_time"  + "/" +  "p_date" + "/" +  status;
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String Response = response.trim().toString().replace("\"", "");
                        //CheckStatus(Response);
                        ///alert.showAlertDialog(Login.this, "Login failed..", response.trim().toString().replace("\"", "");, false);
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        Toast.makeText(((Activity) view.getContext()), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );


        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);*/



/*        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,username);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_EMAIL, email);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getActivity(), s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        //
                        Toast.makeText(getActivity(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String

                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("sname", sname);
                params.put("email", email);
                params.put("post", post);
                params.put("contact", contact);
                params.put("image", image);
                params.put("post_cat", "post_cat");
                params.put("p_time", "p_time");
                params.put("status", "1");


                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
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

    private void initialize() throws UnsupportedEncodingException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        //Log.d("Date Checking",dateFormat.format(date));
        p_date = dateFormat.format(date).toString().substring(0,9);

        p_time = dateFormat.format(date).toString().substring(10);

        //get strings from edit texts

        etSenderName = (EditText) view.findViewById(R.id.etSenderName);
        sname = etSenderName.getText().toString();

        etSenderEmail = (EditText) view.findViewById(R.id.etEmail);
        email = etSenderEmail.getText().toString();

        etPostDetails = (EditText) view.findViewById(R.id.etPostDetails);
        post = etPostDetails.getText().toString();

        etSenderContact = (EditText) view.findViewById(R.id.etContactNum);
        contact = etSenderContact.getText().toString();

        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        image = Base64.encodeToString(ba, Base64.DEFAULT);
        Log.d("B64 IMAGE", image);
        image = URLEncoder.encode(image, "UTF-8");

        Log.d("ENCODED IMAGE", image);
    }


}
