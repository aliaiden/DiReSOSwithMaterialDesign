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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class QuickPostFragment extends Fragment {

    EditText etSenderName, etSenderEmail, etSenderContact, etPostDetails;
    String sname;
    String email;
    String post;
    String contact;
    String image;
    int post_cat;
    String p_time;
    String p_date;
    String status;
    TextView chooseCategory;
    Spinner chooseCategorySpinner;

    Button bSend, bGallery;
    Button bCamera;
    ImageView ivPic;
    View view;
    Bitmap bitmap;

    String[] categoryId;
    String[] categoryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quick_post, container, false);

        status = "1";

        etSenderName = (EditText) view.findViewById(R.id.etSenderName);
        etSenderEmail = (EditText) view.findViewById(R.id.etEmail);
        etPostDetails = (EditText) view.findViewById(R.id.etPostDetails);
        etSenderContact = (EditText) view.findViewById(R.id.etContactNum);
        bCamera = (Button) view.findViewById(R.id.bCamera);
        bGallery = (Button) view.findViewById(R.id.bGallery);
        bSend = (Button) view.findViewById(R.id.b_send_post);
        ivPic = (ImageView) view.findViewById(R.id.ivPic);
        chooseCategory = (TextView) view.findViewById(R.id.tvChooseCategory);
        chooseCategorySpinner = (Spinner) view.findViewById(R.id.spinnerCategory);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Hospital");
        categories.add("N.G.O");
        categories.add("Other");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        // attaching data adapter to spinner
        chooseCategorySpinner.setAdapter(dataAdapter);

        //convert image into b64 string

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        final ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
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

                if (checkFields()) {

                    try {
                        initialize();

                        sendDataToServer(sname, email, post, contact, image, "" + post_cat, p_time, p_date, status);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }


            }
        });


        return view;
    }

    private boolean checkFields() {
        boolean returnValue=true;
        if (sname == null | sname == "") {
            etSenderName.setError("Please fill this");
            returnValue = false;
        }
        else if (email == null | email == "") {
            etSenderEmail.setError("Please fill this");
            returnValue = false;
        }
        else if (contact == null | contact == "") {
            etSenderContact.setError("Please fill this");
            returnValue = false;
        }
        else if (post == null | post == "") {
            etPostDetails.setError("Please fill this");
            returnValue = false;
        }

        return returnValue;
    }

    private void showAlertDialog() {
//        ArrayList a = new ArrayList();
//
//        a.add("Hospital");
//        a.add("N.G.O");
//        a.add("Other");
//
//
//        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
//
//        ListView v = (ListView) getActivity().getLayoutInflater().inflate(R.layout.quick_post_choose_category_item,null);
//
//        b.setView(v);
//
//        final AlertDialog alert = b.create();
//
//        alert.show();
//
//        ArrayAdapter ad;
//        ad = new ArrayAdapter(this,android.R.layout.quick_post_choose_category_item,a);
//
//        v.setAdapter(ad);
//
//        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//
//        });
// Dialog d = new Dialog(this);
//­ d.setContentView(R.layo­ut.x);
//
//
//­ ListView l = (ListView) d.findViewById(R.id.lis­t);
// d.show();
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.post_category_dialog, null);
//        dialogBuilder.setView(dialogView);
//
//        final EditText smsMessage = (EditText) dialogView.findViewById(R.id.etSMSmessage);
//        //dialogBuilder.setTitle("Send us an SMS");
//        dialogBuilder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//
//
//            }
//        });
//        AlertDialog b = dialogBuilder.create();
//        b.show();
    }

    private void getPostCategories() {

        String categoryURL = "http://app.dirsos.com/api/getCategory";


    }


    private void sendDataToServer(final String sname, final String email, final String post, final String contact, final String image, final String post_cat, String p_time, String p_date, String status) {


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
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        //final String finalPost_cat = this.post_cat;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        if (volleyError.getMessage().toString() == null) {
                            Toast.makeText(getActivity(), "Failed to upload. Try again", Toast.LENGTH_LONG).show();
                        }

                        //Showing toast
                        Toast.makeText(getActivity(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] ba = bao.toByteArray();
                //  image = Base64.encodeToString(ba, Base64.DEFAULT);
                //Log.d("B64 IMAGE", image);
                //image = URLEncoder.encode(image, "UTF-8");

                //Log.d("ENCODED IMAGE", image);

                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("sname", sname);
                params.put("email", email);
                params.put("post", post);
                params.put("Contact", contact);
                params.put("image", image);
                params.put("post_cat", post_cat);

                //params.put("p_time", "p_time");
                //params.put("p_date", "p_date");
                //params.put("status", "1");

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
            bitmap = (Bitmap) extras.get("data");
            ivPic.setImageBitmap(bitmap);
            final ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte[] ba = bao.toByteArray();
            image = Base64.encodeToString(ba, Base64.DEFAULT);
            Log.d("B64 IMAGE", image);

        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = view.getContext().getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(imagePath, options);
            ivPic.setImageBitmap(bitmap);

            // Do something with the bitmap
            final ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte[] ba = bao.toByteArray();
            image = Base64.encodeToString(ba, Base64.DEFAULT);
            Log.d("B64 IMAGE", image);


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
//        else if(resultCode != RESULT_CANCELED){
//                if (requestCode == 1) {
//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
//                    ivPic.setImageBitmap(photo);
//                }
//            }
    }


    private void initialize() throws UnsupportedEncodingException {

//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//        //Log.d("Date Checking",dateFormat.format(date));
//        p_date = dateFormat.format(date).toString().substring(0,9);
//
//        p_time = dateFormat.format(date).toString().substring(10);

        post_cat = chooseCategorySpinner.getSelectedItemPosition();


        //get strings from edit texts
        sname = etSenderName.getText().toString();
        email = etSenderEmail.getText().toString();
        post = etPostDetails.getText().toString();
        contact = etSenderContact.getText().toString();

    }

}
