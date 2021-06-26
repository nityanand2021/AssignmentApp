package com.nn.nitya.pacewisdomtestapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

enum ImageViewSelected { imageView1, imageView2, imageView3, imageView4, imageView5};

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int GALLERY_REQUEST_CODE = 2;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    ImageViewSelected imageViewClicked = null;
    ImageView imageView1 = null;
    ImageView imageView2 = null;
    ImageView imageView3 = null;
    ImageView imageView4 = null;
    ImageView imageView5 = null;
    ImageView imageView6 = null;
    TextView txtView3 = null;
    TextView txtView4 = null;
    TextView txtView5 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] documentToBeUploaded = { "Driving License", "PAN Card"};

        txtView3 = findViewById(R.id.editText3);
        txtView4 = findViewById(R.id.editText4);
        txtView5 = findViewById(R.id.editText5);

        //Initiate the two imageviews to display the uploaded images
        imageView1 = findViewById(R.id.imageView1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewClicked = ImageViewSelected.imageView1;
                showGalleryCameraOptionsDialog();
            }
        });

        imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewClicked = ImageViewSelected.imageView2;
                showGalleryCameraOptionsDialog();
            }
        });

        imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewClicked = ImageViewSelected.imageView3;
                // Now display date picker dialog
                createDialog();
            }
        });
        imageView4 = findViewById(R.id.imageView4);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewClicked = ImageViewSelected.imageView4;
                // Now display date picker dialog
                createDialog();
            }
        });
        imageView5 = findViewById(R.id.imageView5);
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewClicked = ImageViewSelected.imageView5;
                // Now display date picker dialog
                createDialog();
            }
        });

        imageView6 = findViewById(R.id.imageView6);
        imageView6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Fill the spinner with values
        Spinner documentSpinner = findViewById(R.id.spinner2);

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                documentToBeUploaded);

        documentSpinner.setAdapter(ad);

        documentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Nothing to do here as of now.
                // Can be used to clear the data previously entered in the rest  of the form.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void pickFromGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (imageViewClicked == ImageViewSelected.imageView1 ) {
                imageView1.setImageBitmap(imageBitmap);
            } else if (imageViewClicked == ImageViewSelected.imageView2) {
                imageView2.setImageBitmap(imageBitmap);
            }

        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            if (imageViewClicked == ImageViewSelected.imageView1 ) {
                imageView1.setImageURI(selectedImage);
            } else if (imageViewClicked == ImageViewSelected.imageView2) {
                imageView2.setImageURI(selectedImage);
            }
        }
    }

    private void checkCameraPermission(){
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showGalleryCameraOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_camera_gallery)
                .setIcon(R.drawable.ic_image_black_24dp)
                .setItems(R.array.camera_gallery_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                        case 0: checkCameraPermission();
                            break;
                        case 1: pickFromGallery();
                            break;
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Date Picker related Code Below

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Increase the month by one
        int correctedMonth = month +1;
        String date = String.valueOf(day)+"/"+String.valueOf(correctedMonth)+"/"+String.valueOf(year);
        Log.v("Nitya","Inside onDateSet, date = "+date);
        if (imageViewClicked == ImageViewSelected.imageView3 ) {
            txtView3.clearComposingText();
            txtView3.setText(date);
        } else if (imageViewClicked == ImageViewSelected.imageView4) {
            txtView4.clearComposingText();
            txtView4.setText(date);
        } else if (imageViewClicked == ImageViewSelected.imageView5) {
            txtView5.clearComposingText();
            txtView5.setText(date);
        }
    }


    private void createDialog() {
        DatePickerDialog datePickerDlg;
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        datePickerDlg = new DatePickerDialog(this, this, year, month, day);
        datePickerDlg.show();
    }

}
