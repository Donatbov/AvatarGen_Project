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
        private String chemin_photo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prendrephoto);
         capture = (Button) findViewById(R.id.capture); // Récupération de l'instance voir les avatars
        imageAffiche = (ImageView) findViewById(R.id.image);
        //evenement click sur le bouton
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.capture) { // C'est notre bouton alors affichage d'un message
                    Toast.makeText(getApplicationContext(), "capture", Toast.LENGTH_SHORT).show();
                    //prendreUnePhoto();
                }
            }
        });



    }
    /**
     * accés a l'appareil photo

    private void prendreUnePhoto () {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //test si appareil photo marche
        if (intent.resolveActivity(getPackageManager()) != null) {
            //création d'un nom de fichier unique
            String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File photoFile = File.createTempFile("photo" + date, "jpg", photoDir);
                chemin_photo = photoFile.getAbsolutePath();
                //creation de l'accés a ce fichier
                Uri photouri = FileProvider.getUriForFile(PrendrephotoActivity.this,
                        PrendrephotoActivity.this.getApplicationContext().getPackageName() + ".provider",
                        photoFile);
                //transfert de la photo vers le fichier temporaire
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
                startActivityForResult(intent, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
    /**
     * retour de l'appel de l'appareil photo

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            Bitmap image;
            image = BitmapFactory.decodeFile(chemin_photo);
            imageAffiche.setImageBitmap(image);
        }

    }*/
}

