package com.vivienaly.avatargenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;


public class Menu_activity extends AppCompatActivity {

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
                                         if (v.getId() == R.id.photo) {
                                             Intent prendrephoto = new Intent(Menu_activity.this, Prendrephoto_activity.class);
                                             startActivity(prendrephoto);
                                         }
                                     }
                                 });

        import_photo.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        if (v.getId() == R.id.importer_photo) {
                                            Intent importerphoto = new Intent(Menu_activity.this, ImporterPhoto_activity.class);
                                            startActivity(importerphoto);
                                        }
                                    }
                                });
        creer_avatar.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        if (v.getId() == R.id.creer_avatar) {
                                            Toast.makeText(getApplicationContext(), "creer", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
        voir_avatar.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        if (v.getId() == R.id.voir_avatar) {
                                            Intent voirAvatars = new Intent(Menu_activity.this, getAvatars_activity.class);
                                            startActivity(voirAvatars);
                                        }
                                    }
                                });

    }

}
