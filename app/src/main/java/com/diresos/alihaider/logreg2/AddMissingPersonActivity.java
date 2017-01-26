package com.diresos.alihaider.logreg2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class AddMissingPersonActivity extends AppCompatActivity {
    ImageView missingPersonPic;
    Button addPerson;
    Bitmap bitmap;
    EditText etName, etGender, etAge, etLastLocation, etAppearance, etContact, etDescription;
    String name, gender, age, lastLocation, appearance, contact, description, image;
    String selectedImagePath = "";
    RadioGroup rgGender;

    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing_person);

        initUI();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_missing_person);
        final ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        image = Base64.encodeToString(ba, Base64.DEFAULT);

        missingPersonPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });

        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etName.setError("Please fill this");

                if (checkFields()) {
//                    int selectedId = rgGender.getCheckedRadioButtonId();
//                    rbSelectedGender = (RadioButton) findViewById(selectedId);
//                    Toast.makeText(AddMissingPersonActivity.this, rbSelectedGender.getText(), Toast.LENGTH_SHORT).show();
//                    gender=rbSelectedGender.getText().toString();

                    sendDataToServer();
                }

            }
        });
    }

    private String setGender() {

//        int selectedId = rgGender.getCheckedRadioButtonId();
//        rbSelectedGender = (RadioButton) findViewById(selectedId);
////        gender = rbSelectedGender.getText().toString();
//        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                int id = rgGender.getCheckedRadioButtonId();
//                switch (id) {
//                    case R.id.rbMale:
//                        gender = "Male";
//                        Toast.makeText(AddMissingPersonActivity.this, gender, Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.rbFemale:
//                        gender = "Female";
//                        break;
//                }
//            }
//        });
//        Toast.makeText(AddMissingPersonActivity.this, gender, Toast.LENGTH_SHORT).show();
//
//        return gender;

        int checkedRadioButtonId = rgGender.getCheckedRadioButtonId();

        if (checkedRadioButtonId == R.id.rbMale) {
            gender = "Male";
            return gender;
        } else if (checkedRadioButtonId == R.id.rbFemale) {
            gender = "Female";
            return gender;
        } else
            return gender;

    }

    private void sendDataToServer() {

        String url = "http://app.dirsos.com/api/setMissingPerson";

        //Showing the progress dialog
        loading = ProgressDialog.show(AddMissingPersonActivity.this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(AddMissingPersonActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(AddMissingPersonActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample_missing_person);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] ba = bao.toByteArray();
                image = Base64.encodeToString(ba, Base64.DEFAULT);
                //Log.d("B64 IMAGE", image);
                //image = URLEncoder.encode(image, "UTF-8");

                //Log.d("ENCODED IMAGE", image);

                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("name", name);
                params.put("description", description);
                params.put("contact", contact);
                params.put("image", image);
                params.put("age", age);
                params.put("location", lastLocation);
                params.put("gender", gender);
                params.put("appearance", appearance);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//
//            Uri pickedImage = data.getData();
//            // Let's read picked image path using content resolver
//            String[] filePath = { MediaStore.Images.Media.DATA };
//            Cursor cursor = this.getContentResolver().query(pickedImage, filePath, null, null, null);
//            cursor.moveToFirst();
//            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
//
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            bitmap = BitmapFactory.decodeFile(imagePath, options);
//            missingPersonPic.setImageBitmap(bitmap);
//
//            // Do something with the bitmap
//            final ByteArrayOutputStream bao = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
//            byte[] ba = bao.toByteArray();
//            image = Base64.encodeToString(ba, Base64.DEFAULT);
//            Log.d("B64 IMAGE", image);
//
//
//            // At the end remember to close the cursor or you will end with the RuntimeException!
//            cursor.close();
//        }

//        if (resultCode != Activity.RESULT_CANCELED) {
//            if (requestCode == 1) {
//                selectedImagePath = getAbsolutePath(data.getData());
//                missingPersonPic.setImageBitmap(decodeFile(selectedImagePath));
//            } else {
//                super.onActivityResult(requestCode, resultCode, data);
//            }
//        }

        if (resultCode != Activity.RESULT_CANCELED) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                missingPersonPic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAbsolutePath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private void initUI() {
        missingPersonPic = (ImageView) findViewById(R.id.iv_missing_person_pic);
        addPerson = (Button) findViewById(R.id.b_submit_missing_person);
        etName = (EditText) findViewById(R.id.et_missing_person_name);
        etAge = (EditText) findViewById(R.id.et_age);
        etAppearance = (EditText) findViewById(R.id.et_appearence);
        etContact = (EditText) findViewById(R.id.et_contact);
        etDescription = (EditText) findViewById(R.id.et_description);
        etLastLocation = (EditText) findViewById(R.id.et_last_location);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);

        //rgGender = (RadioGroup) findViewById(R.id.rgGender);

    }

    private boolean checkFields() {
        gender = setGender();
        //gender="Male";
        name = etName.getText().toString();
        Log.d("NAMENAMEnAMEnAEM", name);
        age = etAge.getText().toString();
        lastLocation = etLastLocation.getText().toString();
        appearance = etAppearance.getText().toString();
        contact = etContact.getText().toString();
        description = etDescription.getText().toString();

        boolean returnValue = true;

        if (name == null | name == "") {
            etName.setError("Please fill this");
            returnValue = false;
        } else if (gender == null) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            returnValue = false;
        } else if (age == null | age == "") {
            etAge.setError("Please fill this");
            returnValue = false;
        } else if (lastLocation == null | lastLocation == "") {
            etLastLocation.setError("Please fill this");
            returnValue = false;
        } else if (appearance == null | appearance == "") {
            etAppearance.setError("Please fill this");
            returnValue = false;
        } else if (contact == null | contact == "") {
            etContact.setError("Please fill this");
            returnValue = false;
        } else if (description == null | description == "") {
            etDescription.setError("Please fill this");
            returnValue = false;
        }

        return returnValue;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (loading != null)
            loading.dismiss();
    }
}
