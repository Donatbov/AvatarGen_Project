package com.vivienaly.avatargenerator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrendrephotoActivity extends AppCompatActivity {

        ImageView imageAffiche;
        Button capture;
        static final int CAM_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendrephoto);
         capture = (Button) findViewById(R.id.capture); // Récupération de l'instance voir les avatars
        imageAffiche = (ImageView) findViewById(R.id.image);
        //evenement click sur le bouton
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager())!=null) {
                    startActivityForResult(takePictureIntent, CAM_REQUEST);
                }
                if (v.getId() == R.id.capture) { // C'est notre bouton alors affichage d'un message
                    Toast.makeText(getApplicationContext(), "capture", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
    private File getFile(){

        File folder = new File("sdcard/camera_app");

        if(!folder.exists())
        {
            folder.mkdir();
        }

        File image_file = new File(folder,"cam_image.jpg");
        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageAffiche.setImageBitmap(imageBitmap);
        }
    }

}

