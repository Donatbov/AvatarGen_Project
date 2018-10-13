package com.vivienaly.avatargenerator;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.FaceDetector;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;
/*import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.Frame;*/

public class ImporterPhoto extends AppCompatActivity {
    private static final int SELECTED_PIC = 1;

    Button importer;
    Button btnProcess;
    ImageView import_view;
    String ImageDecode;
    Canvas canvas;
    Bitmap eyeBitmap;
    Bitmap myBitmap;

    Paint rectPaint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_photo);
        importer = findViewById(R.id.importer_photo);
        btnProcess = findViewById(R.id.btnProcess);
        import_view = findViewById(R.id.import_view);

        btnProcess.setActivated(false);
    }

    public void btnClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);

    }

    public void BtnProcessClick(View v){
        myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.faceform_0);
        eyeBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.faceform_0);
        import_view.setImageBitmap(myBitmap);

        Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
        canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(myBitmap,0,0,null);

        FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

    }


    @Override
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
                Toast.makeText(this, ImageDecode, Toast.LENGTH_LONG)
                        .show();
                btnProcess.setActivated(true);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again"+ImageDecode, Toast.LENGTH_LONG)
                    .show();
        }

    }
}



