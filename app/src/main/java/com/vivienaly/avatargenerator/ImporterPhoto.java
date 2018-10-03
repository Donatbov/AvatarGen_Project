package com.vivienaly.avatargenerator;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

public class ImporterPhoto extends AppCompatActivity {
    private static final int SELECTED_PIC = 1;

    Button importer;
    ImageView import_view;
    String ImageDecode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_photo);
        importer = (Button) findViewById(R.id.importer_photo);
        import_view =(ImageView) findViewById(R.id.import_view);
       /* importer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.importer_photo) { // C'est notre bouton alors affichage d'un message
                    Toast.makeText(getApplicationContext(), "bouton ok", Toast.LENGTH_SHORT).show();

                }
            }
        });*/
    }

    public void btnClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);

    }

    @Override
   /* protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PIC:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filepath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    Drawable drawable = new BitmapDrawable(bitmap);
                    import_view.setBackground(drawable);
                }
                break;
            default:
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECTED_PIC && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            import_view.setImageBitmap(BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/IMG_20180925_144106.jpg"));
            Toast.makeText(getApplicationContext(),imagePath , Toast.LENGTH_SHORT).show();
            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == SELECTED_PIC && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };


                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();

                import_view.setImageBitmap(BitmapFactory
                        .decodeFile(ImageDecode));
                Toast.makeText(this, "tres bien"+ImageDecode, Toast.LENGTH_LONG)
                        .show();

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again"+ImageDecode, Toast.LENGTH_LONG)
                    .show();
        }

    }
}



