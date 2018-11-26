package com.vivienaly.avatargenerator;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;


public class MenuActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button photo=(Button)findViewById(R.id.photo); // Récupération de l'instance du bouton prendre une photo
        final Button import_photo=(Button)findViewById(R.id.importer_photo); // Récupération de l'instance  bouton importer une photo
        Button creer_avatar=(Button)findViewById(R.id.creer_avatar); // Récupération de l'instance creer un avatar
        Button voir_avatar=(Button)findViewById(R.id.voir_avatar); // Récupération de l'instance voir les avatars

        //evenement click sur le bouton
        photo.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         if (v.getId() == R.id.photo) { // C'est notre bouton ? oui, alors affichage d'un message
                                             Toast.makeText(getApplicationContext(), "photo", Toast.LENGTH_SHORT).show();
                                             Intent prendrephoto = new Intent(MenuActivity.this, PrendrephotoActivity.class);
                                             startActivity(prendrephoto);
                                         }
                                     }
                                 });

        import_photo.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        if (v.getId() == R.id.importer_photo) { // C'est notre bouton ? oui, alors affichage d'un message
                                            Toast.makeText(getApplicationContext(), "importer", Toast.LENGTH_SHORT).show();
                                            Intent importerphoto = new Intent(MenuActivity.this, ImporterPhoto.class);
                                            startActivity(importerphoto);
                                        }
                                    }
                                });
        creer_avatar.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        if (v.getId() == R.id.creer_avatar) { // C'est notre bouton ? oui, alors affichage d'un message
                                            Toast.makeText(getApplicationContext(), "creer", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
        voir_avatar.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        if (v.getId() == R.id.voir_avatar) { // C'est notre bouton ? oui, alors affichage d'un message
                                            Toast.makeText(getApplicationContext(), "voir", Toast.LENGTH_SHORT).show();
                                            Intent voirAvatars = new Intent(MenuActivity.this, getAvatarsActivity.class);
                                            startActivity(voirAvatars);
                                        }
                                    }
                                });

    }

}
