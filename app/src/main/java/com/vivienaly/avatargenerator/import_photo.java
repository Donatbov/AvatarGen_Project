package com.vivienaly.avatargenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

public class import_photo extends AppCompatActivity {

    Button importer;
    ImageView import_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_photo);
        importer = (Button) findViewById(R.id.importer_photo);

        importer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.importer_photo) { // C'est notre bouton alors affichage d'un message
                    Toast.makeText(getApplicationContext(), "bouton ok", Toast.LENGTH_SHORT).show();

                }
            }
        });
            }
    }


