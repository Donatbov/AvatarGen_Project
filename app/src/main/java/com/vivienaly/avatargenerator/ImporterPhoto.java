package com.vivienaly.avatargenerator;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
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
    Bitmap eyeBitmap;
    Bitmap myBitmap;
    Bitmap tempBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_photo);

        importer = findViewById(R.id.importer_photo);
        btnProcess = findViewById(R.id.btnProcess);
        import_view = findViewById(R.id.import_view);

        myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.face_example);
        eyeBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.eye_0);
        import_view.setImageBitmap(myBitmap);

        tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
        canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(myBitmap,0,0,null);

        btnProcess.setActivated(false);
    }

    public void btnClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);

    }

    public void BtnProcessClick(View v){
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
                detectLandMarks(face);
            }

            import_view.setImageBitmap(tempBitmap);
        }


    }

    private void detectLandMarks(Face face) {
        Landmark_struct my_ls = new Landmark_struct();
        for (Landmark landmark:face.getLandmarks()){
            int type = landmark.getType();

            switch (type) {
                case Landmark.NOSE_BASE:
                    my_ls.setNoseBase_x((int)landmark.getPosition().x);
                    my_ls.setNoseBase_y((int)landmark.getPosition().y);
                    break;
                case Landmark.BOTTOM_MOUTH:
                    my_ls.setBottomMouth_x((int)landmark.getPosition().x);
                    my_ls.setBottomMouth_y((int)landmark.getPosition().y);
                    break;
                case Landmark.LEFT_CHEEK:
                    my_ls.setLeftCheek_x((int)landmark.getPosition().x);
                    my_ls.setLeftCheek_y((int)landmark.getPosition().y);
                    break;
                case Landmark.RIGHT_CHEEK:
                    my_ls.setRightCheek_x((int)landmark.getPosition().x);
                    my_ls.setRightCheek_y((int)landmark.getPosition().y);
                    break;
                case Landmark.LEFT_EAR:
                    my_ls.setRightCheek_x((int)landmark.getPosition().x);
                    my_ls.setRightCheek_y((int)landmark.getPosition().y);
                    break;
                case Landmark.RIGHT_EAR:
                    my_ls.setRigthEar_x((int)landmark.getPosition().x);
                    my_ls.setRigthEar_y((int)landmark.getPosition().y);
                    break;
                case Landmark.LEFT_EAR_TIP:
                    my_ls.setLeftEarTip_x((int)landmark.getPosition().x);
                    my_ls.setLeftEarTip_y((int)landmark.getPosition().y);
                    break;
                case Landmark.RIGHT_EAR_TIP:
                    my_ls.setRigthEar_x((int)landmark.getPosition().x);
                    my_ls.setLeftEar_y((int)landmark.getPosition().y);
                    break;
                case Landmark.LEFT_EYE:
                    my_ls.setLeftEye_x((int)landmark.getPosition().x);
                    my_ls.setLeftEye_y((int)landmark.getPosition().y);
                    break;
                case Landmark.RIGHT_EYE:
                    my_ls.setRigthEye_x((int)landmark.getPosition().x);
                    my_ls.setRigthEye_y((int)landmark.getPosition().y);
                    break;
                case Landmark.LEFT_MOUTH:
                    my_ls.setLeftMouth_x((int)landmark.getPosition().x);
                    my_ls.setLeftMouth_y((int)landmark.getPosition().y);
                    break;
                case Landmark.RIGHT_MOUTH:
                    my_ls.setRightMouth_x((int)landmark.getPosition().x);
                    my_ls.setRightMouth_y((int)landmark.getPosition().y);
                    break;
            }
        }
        drawLandmarks(my_ls);
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
            canvas.drawCircle(ls.getNoseBase_x(), ls.getNoseBase_y(), 5, paint);
        }else {
            Toast.makeText(this,"NOSE_BASE property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.bottomMouth_isSet()) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(ls.getBottomMouth_x(), ls.getBottomMouth_y(), 5, paint);
        }else {
            Toast.makeText(this,"BOTTOM_MOUTH property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.leftMouth_isSet()) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(ls.getLeftMouth_x(), ls.getLeftMouth_y(), 5, paint);
        }else {
            Toast.makeText(this,"LEFT_MOUTH property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.rightMouth_isSet()) {
            paint.setColor(Color.BLUE);
            canvas.drawCircle(ls.getRightMouth_x(), ls.getRightMouth_y(), 5, paint);
        }else {
            Toast.makeText(this,"RIGHT_MOUTH property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.leftCheek_isSet()) {
            paint.setColor(Color.CYAN);
            canvas.drawCircle(ls.getLeftCheek_x(), ls.getLeftCheek_y(), 5, paint);
        }else {
            Toast.makeText(this,"LEFT_CHEEK property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.rightCheek_isSet()) {
            paint.setColor(Color.CYAN);
            canvas.drawCircle(ls.getRightCheek_x(), ls.getRightCheek_y(), 5, paint);
        }else {
            Toast.makeText(this,"RIGHT_CHEEK property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.leftEye_isSet()) {
            paint.setColor(Color.RED);
            canvas.drawCircle(ls.getLeftEye_x(), ls.getLeftEye_y(), 5, paint);
        }else {
            Toast.makeText(this,"LEFT_EYE property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.rigthEye_isSet()) {
            paint.setColor(Color.RED);
            canvas.drawCircle(ls.getRigthEye_x(), ls.getRigthEye_y(), 5, paint);
        }else {
            Toast.makeText(this,"RIGHT_EYE property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.leftEar_isSet()) {
            paint.setColor(Color.GREEN);
            canvas.drawCircle(ls.getLeftEar_x(), ls.getLeftEar_y(), 5, paint);
        }else {
            Toast.makeText(this,"LEFT_EAR property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.rigthEar_isSet()) {
            paint.setColor(Color.GREEN);
            canvas.drawCircle(ls.getRigthEar_x(), ls.getRigthEar_y(), 5, paint);
        }else {
            Toast.makeText(this,"RIGHT_EAR property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.leftEarTip_isSet()) {
            paint.setColor(Color.MAGENTA);
            canvas.drawCircle(ls.getLeftEarTip_x(), ls.getLeftEarTip_y(), 5, paint);
        }else {
            Toast.makeText(this,"LEFT_EAR_TIP property is empty", Toast.LENGTH_SHORT).show();
        }
        if(ls.rigthEarTip_isSet()) {
            paint.setColor(Color.MAGENTA);
            canvas.drawCircle(ls.getRigthEarTip_x(), ls.getRigthEarTip_y(), 5, paint);
        }else {
            Toast.makeText(this,"RIGHT_EAR_TIP property is empty", Toast.LENGTH_SHORT).show();
        }


        int scaledWidth = eyeBitmap.getScaledWidth(canvas);
        int scaledHeight = eyeBitmap.getScaledHeight(canvas);
        //canvas.drawBitmap(eyeBitmap,cx - (scaledWidth/2), cy - (scaledHeight), null);
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



