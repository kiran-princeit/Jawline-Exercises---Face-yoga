package com.example.faceyoga;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CaptureImageActivity extends AppCompatActivity implements View.OnClickListener {
    private View captureButton;
    private PreviewView previewView;
    private AppCompatImageView previewImageView;
    private ImageView doneButton;
    private boolean imageCaptured;
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        getWindow().getDecorView().setSystemUiVisibility(3590);

        captureButton = findViewById(R.id.btnCapture);
        previewView = findViewById(R.id.previewView);
        previewImageView = findViewById(R.id.previewAppCompatImageView);
        doneButton = findViewById(R.id.doneButtonAfterPreview);

        previewImageView.setVisibility(View.INVISIBLE);
        doneButton.setVisibility(View.INVISIBLE);
        captureButton.setVisibility(View.VISIBLE);
        previewView.setVisibility(View.VISIBLE);

        captureButton.setOnClickListener(this);
        doneButton.setOnClickListener(this);

        captureButton.setVisibility(View.VISIBLE);
        previewView.setVisibility(View.VISIBLE);

        startCamera(); // Start Front Camera

        imageCaptured = false;
    }

    private void resetCaptureView() {
        previewView.setVisibility(View.VISIBLE);
        captureButton.setVisibility(View.VISIBLE);
        previewImageView.setVisibility(View.INVISIBLE);
        doneButton.setVisibility(View.INVISIBLE);

        if (imageFile != null && imageFile.exists()) {
            imageFile.delete();
        }
        imageCaptured = false;
    }

    @Override
    public void onBackPressed() {
        if (imageCaptured) {
            resetCaptureView();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnCapture) {
            captureImage();
        } else if (id == R.id.doneButtonAfterPreview) {
            saveImagePath();
            finish();
        }
    }

    private void captureImage() {
        MediaPlayer.create(this, R.raw.camera_shutter_sound).start();
        File directory = new File(getFilesDir().getAbsolutePath());
        imageFile = new File(directory, System.currentTimeMillis() + ".jpg");
        imageCaptured = true;
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();

                ImageCapture imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                        .build();

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                captureButton.setOnClickListener(v -> takePhoto(imageCapture));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto(ImageCapture imageCapture) {
        MediaPlayer.create(this, R.raw.camera_shutter_sound).start(); // Play capture sound

        File directory = new File(getFilesDir().getAbsolutePath());
        imageFile = new File(directory, System.currentTimeMillis() + ".jpg");

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(imageFile).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                runOnUiThread(() -> showPreview());
            }

            @Override
            public void onError(ImageCaptureException error) {
                error.printStackTrace();
            }
        });
    }

    private void showPreview() {
        previewView.setVisibility(View.INVISIBLE);
        captureButton.setVisibility(View.INVISIBLE);
        previewImageView.setVisibility(View.VISIBLE);
        doneButton.setVisibility(View.VISIBLE);
        imageCaptured = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        previewImageView.setImageBitmap(bitmap);
    }
    private void saveImagePath() {
        try {
            // Use current time in milliseconds
            long timestamp = System.currentTimeMillis();

            // Format the timestamp to date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            String formattedDate = sdf.format(new Date(timestamp));

            SQLiteDatabase db = openOrCreateDatabase("ImagePathDatabase", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS imagePath (date VARCHAR, path VARCHAR)");

            // Insert the formatted date and image path
            String insertQuery = "INSERT INTO imagePath (date, path) VALUES ('" + formattedDate + "', '" + imageFile.getAbsolutePath() + "')";
            db.execSQL(insertQuery);
            Log.d("Database Insert", "Image path inserted: " + imageFile.getAbsolutePath());

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

