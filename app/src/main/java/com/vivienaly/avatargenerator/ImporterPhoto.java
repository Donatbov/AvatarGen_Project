package com.vivienaly.avatargenerator;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

public class ImporterPhoto extends AppCompatActivity {
    private static final int SELECTED_PIC = 1;

    Button importer;
    Button btnProcess;
    ImageView import_view;
    String ImageDecode;
    Canvas canvas;
    Bitmap myBitmap;
    Bitmap tempBitmap;
    Landmark_struct my_ls;
    Boolean isImported;
    private int STORAGE_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_photo);
        my_ls = new Landmark_struct();

        importer = findViewById(R.id.importer_photo);
        btnProcess = findViewById(R.id.btnProcess);
        import_view = findViewById(R.id.import_view);
        myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.face_example);
        import_view.setImageBitmap(myBitmap);
        isImported = false;


    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ImporterPhoto.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void btnClick(View v) {
        if (ContextCompat.checkSelfPermission(ImporterPhoto.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECTED_PIC);
        } else {
            requestStoragePermission();
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECTED_PIC);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == SELECTED_PIC && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };


                assert URI != null;
                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();

                import_view.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));

                isImported = true;

            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again"+ImageDecode, Toast.LENGTH_LONG)
                    .show();
        }

    }


    public void BtnProcessClick(View v){
        if (isImported){
            import_view.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
            myBitmap = BitmapFactory.decodeFile(ImageDecode);
        }else{
            myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.face_example);
        }
        myBitmap = BitmapFactory.decodeFile(ImageDecode);
        import_view.setImageBitmap(myBitmap);

        tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
        canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(myBitmap,0,0,null);


        FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();
        if (!faceDetector.isOperational()){
            Toast.makeText(this, "Face Detector could not be set up on your device", Toast.LENGTH_SHORT).show();
        }else{
            Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
            SparseArray<Face> sparseArray = faceDetector.detect(frame);
            for (int i = 0; i<sparseArray.size(); i++){
                Face face = sparseArray.valueAt(i);
                my_ls = detectLandMarks(face);
                drawLandmarks(my_ls);
                drawElementsOnFace(my_ls);
            }

            import_view.setImageBitmap(tempBitmap);
        }


    }

    /** Sauvegarde les différents éléments du visage dététés par la classe Landmark **/
    private Landmark_struct detectLandMarks(Face face) {

        Landmark_struct ls = new Landmark_struct();
        for (Landmark xcellent
                :face.getLandmarks()) {
            int type = xcellent.getType();

            switch (type) {
                case Landmark.NOSE_BASE:
                    ls.setNoseBase_x((int) xcellent.getPosition().x);
                    ls.setNoseBase_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.BOTTOM_MOUTH:
                    ls.setBottomMouth_x((int) xcellent.getPosition().x);
                    ls.setBottomMouth_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.LEFT_CHEEK:
                    ls.setLeftCheek_x((int) xcellent.getPosition().x);
                    ls.setLeftCheek_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.RIGHT_CHEEK:
                    ls.setRightCheek_x((int) xcellent.getPosition().x);
                    ls.setRightCheek_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.LEFT_EAR:
                    ls.setRightCheek_x((int) xcellent.getPosition().x);
                    ls.setRightCheek_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.RIGHT_EAR:
                    ls.setRigthEar_x((int) xcellent.getPosition().x);
                    ls.setRigthEar_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.LEFT_EAR_TIP:
                    ls.setLeftEarTip_x((int) xcellent.getPosition().x);
                    ls.setLeftEarTip_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.RIGHT_EAR_TIP:
                    ls.setRigthEar_x((int) xcellent.getPosition().x);
                    ls.setLeftEar_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.LEFT_EYE:
                    ls.setLeftEye_x((int) xcellent.getPosition().x);
                    ls.setLeftEye_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.RIGHT_EYE:
                    ls.setRigthEye_x((int) xcellent.getPosition().x);
                    ls.setRigthEye_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.LEFT_MOUTH:
                    ls.setLeftMouth_x((int) xcellent.getPosition().x);
                    ls.setLeftMouth_y((int) xcellent.getPosition().y);
                    break;
                case Landmark.RIGHT_MOUTH:
                    ls.setRightMouth_x((int) xcellent.getPosition().x);
                    ls.setRightMouth_y((int) xcellent.getPosition().y);
                    break;
            }
        }
        return ls;
    }


    private void drawLandmarks(Landmark_struct ls) {
        /*Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(5, , 5, paint);
        imageView.setImageBitmap(bitmap);*/
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if(ls.noseBase_isSet()) {
            paint.setColor(Color.WHITE);
            canvas.drawCircle(ls.getNoseBase_x(), ls.getNoseBase_y(), 10, paint);
        }else {
            Toast.makeText(this,"NOSE_BASE property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.bottomMouth_isSet()) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(ls.getBottomMouth_x(), ls.getBottomMouth_y(), 10, paint);
        }else {
            Toast.makeText(this,"BOTTOM_MOUTH property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.leftMouth_isSet()) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(ls.getLeftMouth_x(), ls.getLeftMouth_y(), 10, paint);
        }else {
            Toast.makeText(this,"LEFT_MOUTH property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.rightMouth_isSet()) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(ls.getRightMouth_x(), ls.getRightMouth_y(), 10, paint);
        }else {
            Toast.makeText(this,"RIGHT_MOUTH property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.leftCheek_isSet()) {
            paint.setColor(Color.CYAN);
            canvas.drawCircle(ls.getLeftCheek_x(), ls.getLeftCheek_y(), 10, paint);
        }else {
            Toast.makeText(this,"LEFT_CHEEK property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.rightCheek_isSet()) {
            paint.setColor(Color.CYAN);
            canvas.drawCircle(ls.getRightCheek_x(), ls.getRightCheek_y(), 10, paint);
        }else {
            Toast.makeText(this,"RIGHT_CHEEK property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.leftEye_isSet()) {
            paint.setColor(Color.RED);
            canvas.drawCircle(ls.getLeftEye_x(), ls.getLeftEye_y(), 10, paint);
        }else {
            Toast.makeText(this,"LEFT_EYE property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.rigthEye_isSet()) {
            paint.setColor(Color.RED);
            canvas.drawCircle(ls.getRigthEye_x(), ls.getRigthEye_y(), 10, paint);
        }else {
            Toast.makeText(this,"RIGHT_EYE property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.leftEar_isSet()) {
            paint.setColor(Color.GREEN);
            canvas.drawCircle(ls.getLeftEar_x(), ls.getLeftEar_y(), 10, paint);
        }else {
            Toast.makeText(this,"LEFT_EAR property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.rigthEar_isSet()) {
            paint.setColor(Color.GREEN);
            canvas.drawCircle(ls.getRigthEar_x(), ls.getRigthEar_y(), 10, paint);
        }else {
            Toast.makeText(this,"RIGHT_EAR property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.leftEarTip_isSet()) {
            paint.setColor(Color.MAGENTA);
            canvas.drawCircle(ls.getLeftEarTip_x(), ls.getLeftEarTip_y(), 10, paint);
        }else {
            Toast.makeText(this,"LEFT_EAR_TIP property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.rigthEarTip_isSet()) {
            paint.setColor(Color.MAGENTA);
            canvas.drawCircle(ls.getRigthEarTip_x(), ls.getRigthEarTip_y(), 10, paint);
        }else {
            Toast.makeText(this,"RIGHT_EAR_TIP property is empty", Toast.LENGTH_SHORT).show();
        }

    }


    private void drawElementsOnFace(Landmark_struct ls) {
        Bitmap faceBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.faceform_0);
        int faceScaledWidth = faceBitmap.getScaledWidth(canvas);
        int faceScaledHeight = faceBitmap.getScaledHeight(canvas);
        Bitmap eyedBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.eye_d);
        Bitmap eyegBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.eye_g);
        int eyeScaledWidth = eyedBitmap.getScaledWidth(canvas);
        int eyesScaledHeight = eyegBitmap.getScaledHeight(canvas);
        Bitmap mouthBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.mouth_1);
        int mouthScaledWidth = mouthBitmap.getScaledWidth(canvas);
        int mouthScaledHeight = mouthBitmap.getScaledHeight(canvas);
        Bitmap noseBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.nose_4);
        int noseScaledWidth = noseBitmap.getScaledWidth(canvas);
        int noseScaledHeight = noseBitmap.getScaledHeight(canvas);

        canvas.drawBitmap(faceBitmap, ls.getNoseBase_x() - (faceScaledWidth/2), ls.getNoseBase_y()- (faceScaledHeight/2), null);
        canvas.drawBitmap(eyedBitmap, ls.getNoseBase_x() - 15 /*- (eyeScaledWidth/2)*/, ls.getLeftEye_y() - (eyesScaledHeight/2), null);
        canvas.drawBitmap(eyegBitmap, ls.getNoseBase_x() - (eyeScaledWidth), ls.getLeftEye_y() - (eyesScaledHeight/2), null);
        canvas.drawBitmap(mouthBitmap, ls.getBottomMouth_x() - (mouthScaledWidth/2), ls.getLeftMouth_y() - mouthScaledHeight/2, null);
        canvas.drawBitmap(noseBitmap, ls.getNoseBase_x() - (noseScaledWidth/2), ls.getNoseBase_y() - (noseScaledHeight/2), null);

    }
}



