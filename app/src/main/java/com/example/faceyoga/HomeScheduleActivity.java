package com.example.faceyoga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.faceyoga.adapter.HomeScheduleAdapter;
import com.example.faceyoga.model.Exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class HomeScheduleActivity extends AppCompatActivity {

    private boolean isDownloadCompleted = false;
    public static String[] exerciseNames;
    public static int[] exerciseDurations;
    public static int[] exerciseRepetitions;
    public static HomeScheduleActivity instance;

    // Exercise details
    public String exerciseA0 = "Jaw Massage";
    public String exerciseA1 = "V Forehead Pose";
    public int exerciseA2 = R.drawable.neck_massage_with_palms_ss;

    public String exerciseB0 = "Jaw Smoother";
    public String exerciseB1 = "V Point Pose";
    public int exerciseB2 = R.drawable.neck_massage_ss;

    public String exerciseC0 = "Jawline Massage With Fist";
    public String exerciseC1 = "V Pose With Eye Lift";
    public int exerciseC2 = R.drawable.neck_rotation_ss;

    public String exerciseD0 = "Jawline Massage";
    public String exerciseD1 = "Warm Up the Face";
    public int exerciseD2 = R.drawable.neck_to_jawline_ss;

    public String exerciseE0 = "Jawline Smoother Left to Right";
    public String exerciseE1 = "Zigzag on Forehead Horizontal";
    public int exerciseE2 = R.drawable.neck_warmer_ss;

    public String exerciseF0 = "Jawline Smoother Right to Left";
    public String exerciseF1 = "Zigzag on Forehead Vertical";
    public int exerciseF2 = R.drawable.one_sided_upward_strokes_ss;

    public String exerciseG0 = "Kiss the Sky";
    public int exerciseG1 = R.drawable.apply_face_serum_ss;
    public int exerciseG2 = R.drawable.pinch_across_the_eyebrows_ss;

    public String exerciseH0 = "Lip Tuck";
    public int exerciseH1 = R.drawable.back_of_the_neck_ss;
    public int exerciseH2 = R.drawable.pinch_inner_edge_of_eyebrows_ss;


    public String exerciseI0 = "Massage Your Ears";
    public int exerciseI1 = R.drawable.blow_up_ss;
    public int exerciseI2 = R.drawable.pinch_the_neck_ss;

    public String exerciseJ0 = "Massage Your Face";
    public int exerciseJ1 = R.drawable.blowfish_ss;
    public int exerciseJ2 = R.drawable.pinch_your_lips_ss;


    public String currentExercise;
    public String currentStatus = "";
    public ArrayList<String> exerciseList = new ArrayList<>();
    public static File exerciseFile;
    public ProgressDialog progressDialog;
    public ArrayList<String> completedExercises = new ArrayList<>();
    public Map<String, String> exerciseDescriptions = new HashMap<>();
    public static Map<String, Integer> exerciseImages = new HashMap<>();

    public int currentExerciseIndex;
    public int totalExercises;

    public String J0 = "Massage Your Face";
    public String exerciseK0 = "Massage Your Temples";
    public int exerciseK1 = R.drawable.center_of_the_neck_ss;
    public int exerciseK2 = R.drawable.pinches_on_jawline_ss;

    public String exerciseL0 = "Neck Massage With Fingers";
    public int exerciseL1 = R.drawable.cheekbones_massage_ss;
    public int exerciseL2 = R.drawable.pout_pose_ss;


    public String exerciseM0 = "Neck Massage With Palms";
    public int exerciseM1 = R.drawable.cheeks_smoother_ss;
    public int exerciseM2 = R.drawable.press_above_the_nose_ss;

    public String exerciseN0 = "Neck Massage";
    public int exerciseN1 = R.drawable.cheeks_to_temples_left_to_right_ss;
    public int exerciseN2 = R.drawable.press_down_on_lymph_ss;

    public String exerciseO0 = "Neck Rotation";
    public int exerciseO1 = R.drawable.cheeks_to_temples_right_to_left_ss;
    public int exerciseO2 = R.drawable.puff_up_ss;


    public String exerciseP0 = "Neck to Jawline";
    public int exerciseP1 = R.drawable.chew_up_ss;
    public int exerciseP2 = R.drawable.reduce_laughing_lines_ss;

    public String exerciseQ0 = "Neck Warmer";
    public int exerciseQ1 = R.drawable.chin_smoother_ss;
    public int exerciseQ2 = R.drawable.side_of_the_neck_ss;


    public String Q = "Apply Face Serum";


    public String exerciseR0 = "One Sided Upward Strokes";
    public int exerciseR1 = R.drawable.chin_to_jaw_smoother_ss;
    public int exerciseR2 = R.drawable.smooth_around_the_mouth_ss;

    public String exerciseS0 = "Pinch Across the Eyebrows";
    public int exerciseS1 = R.drawable.chin_to_neck_strokes_ss;
    public int exerciseS2 = R.drawable.smooth_out_eyebrow_ss;

    public String RR = "Back of the Neck";

    public String S = "Blow Up";


    public String T = "Blowfish";

    public String exerciseT0 = "Pinch Inner Edge of Eyebrows";
    public int exerciseT1 = R.drawable.circle_around_the_eyes_ss;
    public int exerciseT2 = R.drawable.smooth_out_smile_lines_ss;

    public String U = "Center of the Neck";

    public String exerciseU0 = "Pinch the Neck";
    public int exerciseU1 = R.drawable.circles_on_the_forehead_ss;
    public int exerciseU2 = R.drawable.smooth_over_the_forehead_ss;
    public String V = "Cheek Smoother Left to Right";
    public String exerciseV0 = "Pinch Your Lips";
    public int exerciseV1 = R.drawable.circular_motion_below_nose_ss;
    public int exerciseV2 = R.drawable.smooth_up_dark_circles_ss;
    public String W = "Cheek Smoother Right to Left";
    public String exerciseW0 = "Pinches on Jawline";
    public int exerciseW1 = R.drawable.circular_motion_on_cheeks_ss;
    public int exerciseW2 = R.drawable.smooth_up_frown_lines_ss;
    public String X = "Cheekbones Massage";

    public String exerciseX0 = "Pout Pose";
    public int exerciseX1 = R.drawable.circular_motion_on_eye_ss;
    public int exerciseX2 = R.drawable.smooth_your_cheeks_ss;

    public String Y = "Cheeks Smoother";
    public String exerciseY0 = "Press Above the Nose";
    public int exerciseY1 = R.drawable.circular_motion_on_jawline_and_lymph_ss;
    public int exerciseY2 = R.drawable.squint_and_release_your_eyes_ss;
    public String Z = "Cheeks to Temples Left to Right";
    public String exerciseZ0 = "Press Down on Lymph";
    public int exerciseZ1 = R.drawable.circular_motion_on_neck_ss;
    public int exerciseZ2 = R.drawable.stretch_pose_ss;


    public String cheeksToTemplesRightToLeft = "Cheeks to Temples Right to Left";
    public String puffUp = "Puff Up";
    public int circularMotionsDrawable = R.drawable.circular_motions_ss;
    public int suctionCupWithPalmsDrawable = R.drawable.suction_cup_with_palms_ss;


    public String chewUp = "Chew Up";
    public String reduceLaughingLines = "Reduce Laughing Lines";
    public int crowFeetPreventerDrawable = R.drawable.crow_feet_preventer_ss;
    public int surpriseMeDrawable = R.drawable.suprise_me_ss;


    public String chinSmoother = "Chin Smoother";
    public String sideOfTheNeck = "Side of the Neck";
    public int darkCircleRemoverDrawable = R.drawable.dark_circle_remover_ss;
    public int tapAroundTheEyesDrawable = R.drawable.tap_around_the_eyes_ss;


    public String chinToJawSmoother = "Chin to Jaw Smoother";
    public String smoothAroundTheMouth = "Smooth Around the Mouth";
    public int eyeDePufferDrawable = R.drawable.eye_de_puffer_ss;
    public int tapAroundTheFaceDrawable = R.drawable.tap_around_the_face_ss;

    public String chinToNeckStrokes = "Chin to Neck Strokes";
    public String smoothOutEyebrow = "Smooth Out Eyebrow";
    public int eyebrowSmootherLeftToRightDrawable = R.drawable.eyebrow_smoother_left_to_right_ss;
    public int tapItDrawable = R.drawable.tap_it_ss;


    public String circleAroundTheEyes = "Circle Around the Eyes";
    public String smoothOutSmileLines = "Smooth Out Smile Lines";
    public int eyebrowSmootherRightToLeftDrawable = R.drawable.eyebrow_smoother_right_to_left_ss;
    public int tapOnTheCheeksDrawable = R.drawable.tap_on_the_cheeks_ss;


    public String circlesOnTheForehead = "Circles on the Forehead";
    public String smoothOverTheForehead = "Smooth Over the Forehead";
    public int eyelidMassageDrawable = R.drawable.eyelid_massage_ss;
    public int tapOverTheNeckAndFaceDrawable = R.drawable.tap_over_the_neck_and_face_ss;


    public String circularMotionBelowNose = "Circular Motion Below Nose";
    public String smoothUpDarkCircles = "Smooth Up Dark Circles";
    public int flickYourCheeksDrawable = R.drawable.flick_your_cheeks_ss;
    public int tapYourForeheadDrawable = R.drawable.tap_your_forehead_ss;


    public String circularMotionOnCheeks = "Circular Motion on Cheeks";
    public String smoothUpFrownLines = "Smooth Up Frown Lines";
    public int foreheadDabDrawable = R.drawable.forehead_dab_ss;
    public int theEyebrowLiftDrawable = R.drawable.the_eyebrow_lift_ss;


    public String circularMotionOnEye = "Circular Motion on Eye";
    public String smoothYourCheeks = "Smooth Your Cheeks";
    public int foreheadMassageWithFingersDrawable = R.drawable.forehead_massage_with_fingers_ss;
    public int theForeheadLiftDrawable = R.drawable.the_forehead_lift_ss;
    public String circularMotionOnJawlineAndLymph = "Circular Motion on Jawline and Lymph";
    public String squintAndReleaseYourEyes = "Squint and Release Your Eyes";
    public int foreheadMassageDrawable = R.drawable.forehead_massage_ss;
    public int theOwlDrawable = R.drawable.the_owl_ss;

    public String circularMotionOnNeck = "Circular Motion on Neck";
    public String stretchPose = "Stretch Pose";
    public int halfCirclesDrawable = R.drawable.half_circles_ss;
    public int theThirstyGirlDrawable = R.drawable.the_thirsty_girl_ss;

    public String circularMotions = "Circular Motions";
    public String suctionCupWithPalms = "Suction Cup With Palms";
    public int halfNeckRotationDrawable = R.drawable.half_neck_rotation_ss;
    public int upAndDownDrawable = R.drawable.up_and_down_ss;
    public String crowFeetPreventer = "Crow Feet Preventer";
    public String surpriseMe = "Surprise Me";
    public int holdPoseDrawable = R.drawable.hold_pose_ss;
    public int upwardStrokesDrawable = R.drawable.upward_strokes_ss;
    public String darkCircleRemover = "Dark Circle Remover";
    public String tapAroundTheEyes = "Tap Around the Eyes";
    public int jawMassageDrawable = R.drawable.jaw_massage_ss;
    public int vForeheadPoseDrawable = R.drawable.v_forehead_pose_ss;
    public String eyeDePuffer = "Eye De-puffer";
    public String tapAroundTheFace = "Tap Around the Face";
    public int jawSmootherDrawable = R.drawable.jaw_smoother_ss;
    public int vPointPoseDrawable = R.drawable.v_point_pose_ss;
    public String eyebrowSmootherLeftToRight = "Eyebrow Smoother Left to Right";
    public String tapIt = "Tap It";
    public int jawlineMassageWithFistDrawable = R.drawable.jawline_massage_with_fist_ss;
    public int vPoseWithEyeLiftDrawable = R.drawable.v_pose_with_eye_lift_ss;
    public String eyebrowSmootherRightToLeft = "Eyebrow Smoother Right to Left";
    public String tapOnTheCheeks = "Tap on the Cheeks";
    public int jawlineMassageDrawable = R.drawable.jawline_massage_ss;
    public int warmUpTheFaceDrawable = R.drawable.warm_up_the_face_ss;
    public String eyelidMassage = "Eyelid Massage";
    public String tapOverTheNeckAndFace = "Tap Over the Neck and Face";
    public int jawlineSmootherLeftToRightDrawable = R.drawable.jawline_smoother_left_to_right_ss;
    public int zigzagOnForeheadHorizontalDrawable = R.drawable.zigzag_on_forehead_horizontal_ss;
    public String flickYourCheeks = "Flick Your Cheeks";
    public String tapYourForehead = "Tap Your Forehead";
    public int jawlineSmootherRightToLeftDrawable = R.drawable.jawline_smoother_right_to_left_ss;
    public int zigzagOnForeheadVerticalDrawable = R.drawable.zigzag_on_forehead_vertical_ss;
    public String foreheadDab = "Forehead Dab";
    public String theEyebrowLift = "The Eyebrow Lift";
    public int kissTheSkyDrawable = R.drawable.kiss_the_sky_ss;
    public String foreheadMassageWithFingers = "Forehead Massage With Fingers";
    public String theForeheadLift = "The Forehead Lift";
    public int lipTuckDrawable = R.drawable.lip_tuck_ss;
    public String foreheadMassage = "Forehead Massage";
    public String theOwl = "The Owl";
    public int massageYourEarsDrawable = R.drawable.massage_your_ears_ss;
    public String halfCircles = "Half Circles";
    public String theThirstyGirl = "The Thirsty Girl";
    public int massageYourFaceDrawable = R.drawable.massage_your_face_ss;
    public String halfNeckRotation = "Half Neck Rotation";
    public String upAndDown = "Up and Down";
    public int massageYourTemplesDrawable = R.drawable.massage_your_temples_ss;
    public String holdPose = "Hold Pose";
    public String upwardStrokes = "Upward Strokes";
    public int neckMassageWithFingersDrawable = R.drawable.neck_massage_with_fingers_ss;

//    public class FileDownloadTask extends AsyncTask<ArrayList<String>, Integer, String> {
//        private PowerManager.WakeLock wakeLock;
//
//        public FileDownloadTask() {
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
//            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, FileDownloadTask.class.getName());
//            wakeLock.acquire();
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.setMessage("Please wait while downloading files...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(ArrayList<String>... params) {
//            ArrayList<String> fileUrls = params[0];
//            StringBuilder downloadErrors = new StringBuilder();
//
//            // Define the directory path
//            File videoDirectory = new File(getApplicationContext().getFilesDir(), "Face Yoga Videos");
//
//            // Create the directory if it doesn't exist
//            if (!videoDirectory.exists()) {
//                videoDirectory.mkdirs();
//            }
//
//            for (String fileUrl : fileUrls) {
//                String[] parts = fileUrl.split("-----");
//                if (parts.length != 2) {
//                    downloadErrors.append(fileUrl).append(" is not properly formatted.\n");
//                    continue;
//                }
//
//                String fileName = parts[0];  // Video file name
//                String urlString = parts[1]; // URL to download
//
//                try {
//                    URL url = new URL(urlString);
//                    File outputFile = new File(videoDirectory, fileName); // Save in "Face Yoga Videos" folder
//                    int fileLength = exerciseImages.getOrDefault(fileName, 0); // Expected file length
//
//                    try (InputStream inputStream = new BufferedInputStream(url.openStream());
//                         FileOutputStream outputStream = new FileOutputStream(outputFile)) {
//
//                        byte[] data = new byte[1024];
//                        long totalBytesRead = 0;
//                        int bytesRead;
//
//                        while ((bytesRead = inputStream.read(data)) != -1) {
//                            outputStream.write(data, 0, bytesRead);
//                            totalBytesRead += bytesRead;
//
//                            // Update progress
//                            if (fileLength > 0) {
//                                int progress = (int) ((totalBytesRead * 100) / fileLength);
//                                publishProgress(progress);
//                            }
//                        }
//                        Log.i("FileDownload", fileName + " downloaded successfully to " + outputFile.getAbsolutePath());
//                    }
//                } catch (Exception e) {
//                    downloadErrors.append(fileName).append(" failed: ").append(e.getMessage()).append("\n");
//                    Log.e("FileDownload", "Error downloading " + fileName, e);
//                }
//            }
//            return downloadErrors.length() > 0 ? downloadErrors.toString() : null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            progressDialog.setProgress(values[0]);
//        }
//
//        public void onPostExecute(String result) {
//            super.onPostExecute(result);
//            progressDialog.dismiss();
//            if (result == null) {
//                // Set the flag to true indicating download is complete
//                isDownloadCompleted = true;
//                Toast.makeText(PremiumExerciseSchedule.this, "Download completed.", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(PremiumExerciseSchedule.this, "Download error", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



    public class FileDownloadTask extends AsyncTask<ArrayList<String>, Integer, String> {
        private PowerManager.WakeLock wakeLock;
        private ProgressDialog progressDialog;
        private boolean isDownloadCompleted = false;
        private ExecutorService executorService;

        public FileDownloadTask(ProgressDialog progressDialog) {
            this.progressDialog = progressDialog;
            // Initialize ExecutorService with 4 threads for concurrent downloads
            executorService = Executors.newFixedThreadPool(4);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, FileDownloadTask.class.getName());
            wakeLock.acquire();

            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Please wait while downloading files...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(ArrayList<String>... params) {
            ArrayList<String> fileUrls = params[0];
            StringBuilder downloadErrors = new StringBuilder();

            // Define the directory path
            File videoDirectory = new File(getApplicationContext().getFilesDir(), "Face Yoga Videos");

            // Create the directory if it doesn't exist
            if (!videoDirectory.exists()) {
                videoDirectory.mkdirs();
            }

            // OkHttpClient instance to manage HTTP requests
            OkHttpClient client = new OkHttpClient();

            // Parallel download logic
            for (String fileUrl : fileUrls) {
                String[] parts = fileUrl.split("-----");
                if (parts.length != 2) {
                    downloadErrors.append(fileUrl).append(" is not properly formatted.\n");
                    continue;
                }

                String fileName = parts[0];  // Video file name
                String urlString = parts[1]; // URL to download

                executorService.submit(() -> {
                    try {
                        Request request = new Request.Builder()
                                .url(urlString)
                                .build();

                        // Execute the request
                        Response response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            File outputFile = new File(videoDirectory, fileName); // Save in "Face Yoga Videos" folder
                            try (InputStream inputStream = response.body().byteStream();
                                 FileOutputStream outputStream = new FileOutputStream(outputFile)) {

                                byte[] data = new byte[8192]; // 8 KB buffer size
                                int bytesRead;
                                long totalBytesRead = 0;
                                long fileLength = response.body().contentLength();

                                while ((bytesRead = inputStream.read(data)) != -1) {
                                    outputStream.write(data, 0, bytesRead);
                                    totalBytesRead += bytesRead;

                                    // Update progress
                                    if (fileLength > 0) {
                                        int progress = (int) ((totalBytesRead * 100) / fileLength);
                                        publishProgress(progress);
                                    }
                                }

                                Log.i("FileDownload", fileName + " downloaded successfully to " + outputFile.getAbsolutePath());
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            downloadErrors.append(fileName).append(" failed: ").append(response.message()).append("\n");
                        }
                    } catch (IOException e) {
                        downloadErrors.append(fileName).append(" failed: ").append(e.getMessage()).append("\n");
                        Log.e("FileDownload", "Error downloading " + fileName, e);
                    }
                });
            }

            // Wait for all download threads to complete
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                // Waiting for all tasks to finish
            }

            return downloadErrors.length() > 0 ? downloadErrors.toString() : null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (wakeLock != null && wakeLock.isHeld()) {
                wakeLock.release();
            }

            if (result == null) {
                // Set the flag to true indicating download is complete
                isDownloadCompleted = true;
                Toast.makeText(HomeScheduleActivity.this, "Download completed.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HomeScheduleActivity.this, "Download error: " + result, Toast.LENGTH_SHORT).show();
            }
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String str;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_schedule);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        int i9 = 0;
        File file = new File(getFilesDir().getAbsolutePath() + "/Face Yoga Videos");
        exerciseFile = file;
        if (!file.exists()) {
            exerciseFile.mkdirs();
            str = exerciseFile.getAbsolutePath();
        } else {
            str = "No need to create directory because it already exists";
        }
        instance = this;
        String stringExtra = getIntent().getStringExtra("Selected Workout");
        currentExercise = stringExtra;
        if (stringExtra.equals("Full Face Yoga Routine Day ")) {
            totalExercises = getIntent().getIntExtra("whichChallengeDay", -1);
            currentExercise = currentExercise;
            currentExercise += Integer.toString(totalExercises);
        }
        currentExerciseIndex = getIntent().getIntExtra("Image For Toolbar", -1);

        String description = getIntent().getStringExtra("description");
        String result1 = getIntent().getStringExtra("result1");
        String result2 = getIntent().getStringExtra("result2");

        AppCompatImageView appCompatImageView = (AppCompatImageView) findViewById(R.id.imageViewToolbarPremium);
        if (currentExerciseIndex != -1) {
            Glide.with(this).load(Integer.valueOf(currentExerciseIndex)).into(appCompatImageView);
        }

        findViewById(R.id.iv_close).setOnClickListener(view -> {
            finish();
        });

        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvResult2 = findViewById(R.id.tvResult2);
        TextView tvResult1 = findViewById(R.id.tvResult1);

        tvDescription.setText(description);
        tvResult1.setText(result1);
        tvResult2.setText(result2);
        ((AppCompatTextView) findViewById(R.id.tvSelectedShedule)).setText(currentExercise);
        FaceYogaRoutineDay();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvExercise);
        ArrayList arrayList = new ArrayList();
        while (true) {
            String[] strArr = exerciseNames;
            if (i9 < strArr.length) {
                Exercise rVar = new Exercise();
                rVar.name = strArr[i9];
                rVar.image = exerciseDurations[i9];
                rVar.duration = exerciseRepetitions[i9];
                arrayList.add(rVar);
                i9++;
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setHasFixedSize(true);
                getBaseContext();
                recyclerView.setAdapter(new HomeScheduleAdapter(HomeScheduleActivity.instance, arrayList));
                addIIntoList();
                listOFFile();
                this.progressDialog = new ProgressDialog(this);
                return;
            }
        }
    }



    public void startExercisePremium(View view) {
        String str;
        Intent intent;
        Class<HomeWorkoutActivity> cls = HomeWorkoutActivity.class;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();

        // Check if the download is already completed
        if (isDownloadCompleted) {
            // Skip download and directly go to the next activity
            Toast.makeText(this, "Download already completed.", Toast.LENGTH_SHORT).show();

            // Prepare the list of videos to play
            for (int i = 0; i < exerciseNames.length; i++) {
                arrayList2.add(exerciseNames[i] + ".m4v");
            }
            // Store video names (if required)
            ArrayList<String> videoList = new ArrayList<>(arrayList2);

            // Launch the activity to play videos in sequence
            intent = new Intent(this, HomeWorkoutActivity.class);
//            intent.putExtra("PremiumWorkoutName", videoList.toArray(new String[0])); // Pass video names
            intent.putExtra("PremiumWorkoutName", exerciseNames);// Pass video names
            intent.putExtra("PremiumWorkoutReps", exerciseRepetitions);
            intent.putExtra("PremiumWorkoutImage", exerciseDurations);
            intent.putExtra("FullFaceYogaDay", totalExercises);
            startActivity(intent);
            finish();
            return; // Exit if download is already completed
        }

        // If download is not completed yet, handle download process
        for (int i9 = 0; i9 < exerciseNames.length; i9++) {
            arrayList2.add(exerciseNames[i9] + ".m4v");
        }
        if (!exerciseFile.exists()) {
            exerciseFile.mkdirs();
        }

        listOFFile(); // Call your method for other actions
        File[] listFiles = exerciseFile.listFiles();
        if (listFiles != null) {
            for (File name : listFiles) {
                String name2 = name.getName();
                arrayList.add(name2);
                Log.i("OFFLINE WORKOUTS", name2);
            }
        }
        arrayList2.removeAll(arrayList);
        completedExercises.clear();
        Log.d("WORKOUTS_TO_DOWNLOAD", arrayList2.toString());

        // If there are no more workouts to download
        if (arrayList2.isEmpty()) {
            // Proceed to the next activity if no downloads are pending
            if (currentExercise.equals("Full Face Yoga Routine Day ")) {
                intent = new Intent(this, cls);
                intent.putExtra("PremiumWorkoutName", exerciseNames);
                intent.putExtra("PremiumWorkoutReps", exerciseRepetitions);
                intent.putExtra("PremiumWorkoutImage", exerciseDurations);
                intent.putExtra("FullFaceYogaDay", totalExercises);
            } else {
                intent = new Intent(this, cls);
                intent.putExtra("PremiumWorkoutName", exerciseNames);
                intent.putExtra("PremiumWorkoutReps", exerciseRepetitions);
                intent.putExtra("PremiumWorkoutImage", exerciseDurations);
            }
            startActivity(intent);
            return;
        }


        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            long j9 = 0;
            Long valueOf = Long.valueOf(getCacheDir().getUsableSpace());
            Log.d("TOTAL_FREE_SPACE", String.valueOf((valueOf.longValue() * 100) / getCacheDir().getTotalSpace()) + "%");

            for (int i10 = 0; i10 < arrayList2.size(); i10++) {
                String str2 = arrayList2.get(i10);
                String str3 = (String) exerciseDescriptions.get(str2);
                j9 += (long) ((Integer) exerciseImages.get(str2)).intValue();
                Log.i("DOWNLOADING LINK CREATED", str2 + " :: " + str3);
                completedExercises.add(str2 + "-----" + str3);
            }

            if (j9 > valueOf.longValue()) {
                str = "Insufficient storage";
            } else {

                // Start download task
                new FileDownloadTask(progressDialog).execute(new ArrayList[]{completedExercises});
                return;
            }
        } else {
            str = "No internet available";
        }

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    public final void listOFFile() {
        File[] listFiles = exerciseFile.listFiles();
        for (int i9 = 0; i9 < listFiles.length; i9++) {
            String name = listFiles[i9].getName();
            long length = listFiles[i9].length();
            int intValue = ((Integer) exerciseImages.get(name)).intValue();
            if (((long) intValue) > length) {
                File file = listFiles[i9];
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        StringBuilder m9 = new StringBuilder("Total Files: ");
        m9.append(listFiles.length);
    }

    public final void FaceYogaRoutineDay() {
        if (currentExercise.equals("Full Face Yoga Routine Day 1")) {
            exerciseNames = new String[]{this.exerciseD1, this.tapOverTheNeckAndFace, this.exerciseL0, this.exerciseA0, this.flickYourCheeks, this.circularMotionOnCheeks, this.darkCircleRemover, this.smoothOverTheForehead, this.smoothUpFrownLines};
            exerciseRepetitions = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30};
            exerciseDurations = new int[]{this.warmUpTheFaceDrawable, this.tapOverTheNeckAndFaceDrawable, this.neckMassageWithFingersDrawable, this.jawMassageDrawable, this.flickYourCheeksDrawable, this.exerciseW1, this.darkCircleRemoverDrawable, this.exerciseU2, this.exerciseW2};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 2")) {
            exerciseNames = new String[]{this.upAndDown, this.chewUp, this.exerciseM0, this.circularMotionOnNeck, this.exerciseW0, this.smoothOutSmileLines, this.eyeDePuffer, this.exerciseA1, this.foreheadMassageWithFingers};
            exerciseRepetitions = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30};
            exerciseDurations = new int[]{this.upAndDownDrawable, this.exerciseP1, this.exerciseA2, this.exerciseZ1, this.exerciseK2, this.exerciseT2, this.eyeDePufferDrawable, this.vForeheadPoseDrawable, this.foreheadMassageWithFingersDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 3")) {
            exerciseNames = new String[]{this.exerciseO0, this.exerciseI0, this.exerciseA0, this.flickYourCheeks, this.exerciseV0, this.tapAroundTheEyes, this.crowFeetPreventer, this.smoothAroundTheMouth, this.exerciseF1, this.exerciseA1};
            exerciseRepetitions = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
            exerciseDurations = new int[]{this.exerciseC2, this.massageYourEarsDrawable, this.jawMassageDrawable, this.flickYourCheeksDrawable, this.exerciseJ2, this.tapAroundTheEyesDrawable, this.crowFeetPreventerDrawable, this.exerciseR2, this.zigzagOnForeheadVerticalDrawable, this.vForeheadPoseDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 4")) {
            exerciseNames = new String[]{this.surpriseMe, this.exerciseU0, this.exerciseD0, this.stretchPose, this.exerciseG0, this.theThirstyGirl, this.Y, this.smoothOverTheForehead, this.holdPose};
            exerciseRepetitions = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30};
            exerciseDurations = new int[]{this.surpriseMeDrawable, this.exerciseI2, this.jawlineMassageDrawable, this.exerciseZ2, this.kissTheSkyDrawable, this.theThirstyGirlDrawable, this.exerciseM1, this.exerciseU2, this.holdPoseDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 5")) {
            exerciseNames = new String[]{this.chinToNeckStrokes, this.exerciseX0, this.exerciseH0, this.reduceLaughingLines, this.exerciseZ0, this.tapIt, this.circularMotionOnCheeks, this.smoothUpDarkCircles, this.smoothOverTheForehead};
            exerciseRepetitions = new int[]{30, 30, 45, 45, 30, 30, 30, 30, 30};
            exerciseDurations = new int[]{this.exerciseS1, this.exerciseL2, this.lipTuckDrawable, this.exerciseP2, this.exerciseN2, this.tapItDrawable, this.exerciseW1, this.exerciseV2, this.exerciseU2};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 6")) {
            exerciseNames = new String[]{this.exerciseU0, this.tapAroundTheFace, this.T, this.chinSmoother, this.exerciseT0, this.eyelidMassage, this.tapOnTheCheeks, this.exerciseE1, this.exerciseF1};
            exerciseRepetitions = new int[]{45, 45, 30, 30, 30, 30, 30, 30, 30};
            exerciseDurations = new int[]{this.exerciseI2, this.tapAroundTheFaceDrawable, this.exerciseJ1, this.exerciseQ1, this.exerciseH2, this.eyelidMassageDrawable, this.tapOnTheCheeksDrawable, this.zigzagOnForeheadHorizontalDrawable, this.zigzagOnForeheadVerticalDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 7")) {
            exerciseNames = new String[]{this.tapOverTheNeckAndFace, this.exerciseQ0, this.circularMotionOnNeck, this.exerciseD0, this.exerciseH0, this.circularMotionBelowNose, this.X, this.exerciseT0, this.tapYourForehead};
            exerciseRepetitions = new int[]{45, 45, 45, 30, 45, 30, 30, 30, 30};
            exerciseDurations = new int[]{this.tapOverTheNeckAndFaceDrawable, this.exerciseE2, this.exerciseZ1, this.jawlineMassageDrawable, this.lipTuckDrawable, this.exerciseV1, this.exerciseL1, this.exerciseH2, this.tapYourForeheadDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 8")) {
            exerciseNames = new String[]{this.J0, this.squintAndReleaseYourEyes, this.theOwl, this.foreheadDab, this.halfCircles, this.exerciseC0, this.exerciseK0, this.exerciseZ0, this.chinToJawSmoother};
            exerciseRepetitions = new int[]{60, 30, 30, 30, 45, 30, 30, 30, 45};
            exerciseDurations = new int[]{this.massageYourFaceDrawable, this.exerciseY2, this.theOwlDrawable, this.foreheadDabDrawable, this.halfCirclesDrawable, this.jawlineMassageWithFistDrawable, this.massageYourTemplesDrawable, this.exerciseN2, this.exerciseR1};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 9")) {
            exerciseNames = new String[]{this.exerciseL0, this.chewUp, this.exerciseG0, this.upwardStrokes, this.X, this.exerciseW0, this.circularMotionBelowNose, this.darkCircleRemover, this.suctionCupWithPalms, this.theThirstyGirl};
            exerciseRepetitions = new int[]{60, 45, 45, 60, 60, 45, 45, 45, 60, 60};
            exerciseDurations = new int[]{this.neckMassageWithFingersDrawable, this.exerciseP1, this.kissTheSkyDrawable, this.upwardStrokesDrawable, this.exerciseL1, this.exerciseK2, this.exerciseV1, this.darkCircleRemoverDrawable, this.suctionCupWithPalmsDrawable, this.theThirstyGirlDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 10")) {
            exerciseNames = new String[]{this.halfNeckRotation, this.exerciseN0, this.exerciseR0, this.puffUp, this.exerciseH0, this.crowFeetPreventer, this.circularMotionOnCheeks, this.exerciseS0, this.smoothOutEyebrow, this.smoothOverTheForehead};
            exerciseRepetitions = new int[]{60, 60, 60, 45, 45, 45, 60, 45, 60, 60};
            exerciseDurations = new int[]{this.halfNeckRotationDrawable, this.exerciseB2, this.exerciseF2, this.exerciseO2, this.lipTuckDrawable, this.crowFeetPreventerDrawable, this.exerciseW1, this.exerciseG2, this.exerciseS2, this.exerciseU2};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 11")) {
            exerciseNames = new String[]{this.exerciseO0, this.exerciseD1, this.reduceLaughingLines, this.exerciseC0, this.exerciseW0, this.eyelidMassage, this.exerciseB1, this.smoothUpFrownLines, this.tapAroundTheEyes, this.theThirstyGirl};
            exerciseRepetitions = new int[]{60, 60, 45, 60, 60, 45, 60, 60, 60, 60};
            exerciseDurations = new int[]{this.exerciseC2, this.warmUpTheFaceDrawable, this.exerciseP2, this.jawlineMassageWithFistDrawable, this.exerciseK2, this.eyelidMassageDrawable, this.vPointPoseDrawable, this.exerciseW2, this.tapAroundTheEyesDrawable, this.theThirstyGirlDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 12")) {
            exerciseNames = new String[]{this.exerciseM0, this.exerciseU0, this.chinToJawSmoother, this.T, this.X, this.tapAroundTheFace, this.circularMotions, this.theEyebrowLift, this.smoothUpDarkCircles, this.foreheadMassage, this.smoothOverTheForehead};
            exerciseRepetitions = new int[]{60, 60, 45, 60, 60, 60, 60, 45, 45, 45, 60};
            exerciseDurations = new int[]{this.exerciseA2, this.exerciseI2, this.exerciseR1, this.exerciseJ1, this.exerciseL1, this.tapAroundTheFaceDrawable, this.circularMotionsDrawable, this.theEyebrowLiftDrawable, this.exerciseV2, this.foreheadMassageDrawable, this.exerciseU2};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 13")) {
            exerciseNames = new String[]{this.exerciseL0, this.exerciseQ0, this.reduceLaughingLines, this.exerciseC0, this.Y, this.chinSmoother, this.crowFeetPreventer, this.eyeDePuffer, this.holdPose, this.foreheadDab, this.foreheadMassageWithFingers};
            exerciseRepetitions = new int[]{60, 60, 30, 45, 60, 60, 45, 45, 45, 60, 60};
            exerciseDurations = new int[]{this.neckMassageWithFingersDrawable, this.exerciseE2, this.exerciseP2, this.jawlineMassageWithFistDrawable, this.exerciseM1, this.exerciseQ1, this.crowFeetPreventerDrawable, this.eyeDePufferDrawable, this.holdPoseDrawable, this.foreheadDabDrawable, this.foreheadMassageWithFingersDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 14")) {
            exerciseNames = new String[]{this.halfNeckRotation, this.circularMotionOnNeck, this.tapIt, this.theEyebrowLift, this.exerciseI0, this.exerciseE1, this.surpriseMe, this.exerciseB0, this.exerciseG0, this.exerciseC1, this.tapAroundTheEyes};
            exerciseRepetitions = new int[]{60, 60, 45, 45, 30, 60, 45, 45, 60, 60, 60};
            exerciseDurations = new int[]{this.halfNeckRotationDrawable, this.exerciseZ1, this.tapItDrawable, this.theEyebrowLiftDrawable, this.massageYourEarsDrawable, this.zigzagOnForeheadHorizontalDrawable, this.surpriseMeDrawable, this.jawSmootherDrawable, this.kissTheSkyDrawable, this.vPoseWithEyeLiftDrawable, this.tapAroundTheEyesDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 15")) {
            exerciseNames = new String[]{this.exerciseM0, this.squintAndReleaseYourEyes, this.theOwl, this.X, this.eyelidMassage, this.stretchPose, this.smoothUpDarkCircles, this.exerciseZ0, this.theThirstyGirl, this.exerciseR0, this.exerciseC0};
            exerciseRepetitions = new int[]{60, 45, 60, 60, 60, 45, 45, 60, 60, 60, 60};
            exerciseDurations = new int[]{this.exerciseA2, this.exerciseY2, this.theOwlDrawable, this.exerciseL1, this.eyelidMassageDrawable, this.exerciseZ2, this.exerciseV2, this.exerciseN2, this.theThirstyGirlDrawable, this.exerciseF2, this.jawlineMassageWithFistDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 16")) {
            exerciseNames = new String[]{this.exerciseD1, this.exerciseM0, this.exerciseG0, this.exerciseS0, this.eyelidMassage, this.foreheadMassage, this.exerciseH0, this.circularMotionOnCheeks, this.exerciseW0, this.surpriseMe, this.Y};
            exerciseRepetitions = new int[]{90, 60, 60, 45, 45, 60, 45, 60, 60, 60, 45};
            exerciseDurations = new int[]{this.warmUpTheFaceDrawable, this.exerciseA2, this.kissTheSkyDrawable, this.exerciseG2, this.eyelidMassageDrawable, this.foreheadMassageDrawable, this.lipTuckDrawable, this.exerciseW1, this.exerciseK2, this.surpriseMeDrawable, this.exerciseM1};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 17")) {
            exerciseNames = new String[]{this.exerciseL0, this.smoothAroundTheMouth, this.holdPose, this.crowFeetPreventer, this.upAndDown, this.exerciseX0, this.T, this.circularMotionOnJawlineAndLymph, this.exerciseF1, this.exerciseE1, this.chewUp, this.Y};
            exerciseRepetitions = new int[]{60, 60, 45, 45, 60, 45, 30, 45, 30, 30, 60, 60};
            exerciseDurations = new int[]{this.neckMassageWithFingersDrawable, this.exerciseR2, this.holdPoseDrawable, this.crowFeetPreventerDrawable, this.upAndDownDrawable, this.exerciseL2, this.exerciseJ1, this.exerciseY1, this.zigzagOnForeheadVerticalDrawable, this.zigzagOnForeheadHorizontalDrawable, this.exerciseP1, this.exerciseM1};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 18")) {
            exerciseNames = new String[]{this.exerciseD1, this.halfCircles, this.exerciseV0, this.foreheadDab, this.tapOnTheCheeks, this.flickYourCheeks, this.exerciseK0, this.exerciseA0, this.theOwl, this.exerciseU0, this.smoothOutSmileLines, this.tapAroundTheEyes};
            exerciseRepetitions = new int[]{60, 45, 45, 30, 45, 45, 60, 60, 30, 45, 60, 60};
            exerciseDurations = new int[]{this.warmUpTheFaceDrawable, this.halfCirclesDrawable, this.exerciseJ2, this.foreheadDabDrawable, this.tapOnTheCheeksDrawable, this.flickYourCheeksDrawable, this.massageYourTemplesDrawable, this.jawMassageDrawable, this.theOwlDrawable, this.exerciseI2, this.exerciseT2, this.tapAroundTheEyesDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 19")) {
            exerciseNames = new String[]{this.exerciseM0, this.circlesOnTheForehead, this.upwardStrokes, this.chinToNeckStrokes, this.circularMotions, this.exerciseW0, this.surpriseMe, this.exerciseC0, this.smoothUpDarkCircles, this.exerciseX0, this.exerciseT0, this.smoothOverTheForehead};
            exerciseRepetitions = new int[]{60, 45, 45, 45, 60, 60, 60, 45, 60, 45, 60, 60};
            exerciseDurations = new int[]{this.exerciseA2, this.exerciseU1, this.upwardStrokesDrawable, this.exerciseS1, this.circularMotionsDrawable, this.exerciseK2, this.surpriseMeDrawable, this.jawlineMassageWithFistDrawable, this.exerciseV2, this.exerciseL2, this.exerciseH2, this.exerciseU2};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 20")) {
            exerciseNames = new String[]{this.tapOverTheNeckAndFace, this.foreheadMassageWithFingers, this.smoothUpFrownLines, this.stretchPose, this.exerciseC1, this.S, this.exerciseB0, this.exerciseI0, this.suctionCupWithPalms, this.circularMotionBelowNose, this.chewUp, this.chinToJawSmoother};
            exerciseRepetitions = new int[]{60, 60, 45, 30, 45, 60, 60, 30, 60, 30, 45, 60};
            exerciseDurations = new int[]{this.tapOverTheNeckAndFaceDrawable, this.foreheadMassageWithFingersDrawable, this.exerciseW2, this.exerciseZ2, this.vPoseWithEyeLiftDrawable, this.exerciseI1, this.jawSmootherDrawable, this.massageYourEarsDrawable, this.suctionCupWithPalmsDrawable, this.exerciseV1, this.exerciseP1, this.exerciseR1};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 21")) {
            exerciseNames = new String[]{this.exerciseO0, this.surpriseMe, this.reduceLaughingLines, this.exerciseZ0, this.crowFeetPreventer, this.exerciseL0, this.X, this.foreheadMassage, this.exerciseT0, this.tapAroundTheFace, this.exerciseR0, this.theEyebrowLift};
            exerciseRepetitions = new int[]{60, 60, 60, 45, 45, 60, 60, 60, 45, 45, 60, 60};
            exerciseDurations = new int[]{this.exerciseC2, this.surpriseMeDrawable, this.exerciseP2, this.exerciseN2, this.crowFeetPreventerDrawable, this.neckMassageWithFingersDrawable, this.exerciseL1, this.foreheadMassageDrawable, this.exerciseH2, this.tapAroundTheFaceDrawable, this.exerciseF2, this.theEyebrowLiftDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 22")) {
            exerciseNames = new String[]{this.J0, this.squintAndReleaseYourEyes, this.circleAroundTheEyes, this.eyelidMassage, this.tapAroundTheEyes, this.chinSmoother, this.exerciseC0, this.exerciseG0, this.puffUp, this.exerciseW0, this.circlesOnTheForehead, this.X, this.darkCircleRemover};
            exerciseRepetitions = new int[]{60, 60, 60, 45, 60, 60, 45, 60, 45, 60, 60, 60, 60};
            exerciseDurations = new int[]{this.massageYourFaceDrawable, this.exerciseY2, this.exerciseT1, this.eyelidMassageDrawable, this.tapAroundTheEyesDrawable, this.exerciseQ1, this.jawlineMassageWithFistDrawable, this.kissTheSkyDrawable, this.exerciseO2, this.exerciseK2, this.exerciseU1, this.exerciseL1, this.darkCircleRemoverDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 23")) {
            exerciseNames = new String[]{this.exerciseQ0, this.exerciseM0, this.stretchPose, this.exerciseD0, this.flickYourCheeks, this.smoothYourCheeks, this.exerciseK0, this.eyeDePuffer, this.exerciseH0, this.smoothUpDarkCircles, this.exerciseI0, this.theThirstyGirl, this.upwardStrokes};
            exerciseRepetitions = new int[]{90, 60, 45, 60, 60, 60, 45, 60, 45, 60, 60, 60, 60};
            exerciseDurations = new int[]{this.exerciseE2, this.exerciseA2, this.exerciseZ2, this.jawlineMassageDrawable, this.flickYourCheeksDrawable, this.exerciseX2, this.massageYourTemplesDrawable, this.eyeDePufferDrawable, this.lipTuckDrawable, this.exerciseV2, this.massageYourEarsDrawable, this.theThirstyGirlDrawable, this.upwardStrokesDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 24")) {
            exerciseNames = new String[]{this.tapOverTheNeckAndFace, this.reduceLaughingLines, this.exerciseV0, this.exerciseX0, this.theOwl, this.chinSmoother, this.exerciseC0, this.exerciseZ0, this.exerciseG0, this.suctionCupWithPalms, this.surpriseMe, this.foreheadDab, this.circularMotionOnEye};
            exerciseRepetitions = new int[]{60, 45, 45, 45, 60, 60, 60, 60, 60, 60, 60, 45, 60};
            exerciseDurations = new int[]{this.tapOverTheNeckAndFaceDrawable, this.exerciseP2, this.exerciseJ2, this.exerciseL2, this.theOwlDrawable, this.exerciseQ1, this.jawlineMassageWithFistDrawable, this.exerciseN2, this.kissTheSkyDrawable, this.suctionCupWithPalmsDrawable, this.surpriseMeDrawable, this.foreheadDabDrawable, this.exerciseX1};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 25")) {
            exerciseNames = new String[]{this.upAndDown, this.exerciseO0, this.J0, this.exerciseL0, this.tapOnTheCheeks, this.X, this.stretchPose, this.circlesOnTheForehead, this.tapIt, this.exerciseI0, this.Y, this.smoothOutEyebrow, this.crowFeetPreventer};
            exerciseRepetitions = new int[]{90, 90, 60, 60, 45, 60, 45, 60, 60, 30, 60, 45, 45};
            exerciseDurations = new int[]{this.upAndDownDrawable, this.exerciseC2, this.massageYourFaceDrawable, this.neckMassageWithFingersDrawable, this.tapOnTheCheeksDrawable, this.exerciseL1, this.exerciseZ2, this.exerciseU1, this.tapItDrawable, this.massageYourEarsDrawable, this.exerciseM1, this.exerciseS2, this.crowFeetPreventerDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 26")) {
            exerciseNames = new String[]{this.halfNeckRotation, this.chinToJawSmoother, this.upwardStrokes, this.circularMotionOnNeck, this.exerciseZ0, this.theOwl, this.foreheadMassage, this.exerciseD0, this.darkCircleRemover, this.smoothOutEyebrow, this.flickYourCheeks, this.exerciseA0, this.smoothUpFrownLines};
            exerciseRepetitions = new int[]{90, 90, 90, 60, 60, 60, 90, 90, 45, 60, 45, 60, 60};
            exerciseDurations = new int[]{this.halfNeckRotationDrawable, this.exerciseR1, this.upwardStrokesDrawable, this.exerciseZ1, this.exerciseN2, this.theOwlDrawable, this.foreheadMassageDrawable, this.jawlineMassageDrawable, this.darkCircleRemoverDrawable, this.exerciseS2, this.flickYourCheeksDrawable, this.jawMassageDrawable, this.exerciseW2};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 27")) {
            exerciseNames = new String[]{this.exerciseQ0, this.smoothAroundTheMouth, this.squintAndReleaseYourEyes, this.exerciseX0, this.reduceLaughingLines, this.chinSmoother, this.exerciseK0, this.exerciseT0, this.exerciseC0, this.S, this.tapYourForehead, this.puffUp, this.circlesOnTheForehead};
            exerciseRepetitions = new int[]{90, 60, 45, 45, 45, 60, 60, 60, 60, 45, 90, 60, 90};
            exerciseDurations = new int[]{this.exerciseE2, this.exerciseR2, this.exerciseY2, this.exerciseL2, this.exerciseP2, this.exerciseQ1, this.massageYourTemplesDrawable, this.exerciseH2, this.jawlineMassageWithFistDrawable, this.exerciseI1, this.tapYourForeheadDrawable, this.exerciseO2, this.exerciseU1};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 28")) {
            exerciseNames = new String[]{this.exerciseQ0, this.exerciseH0, this.exerciseZ0, this.flickYourCheeks, this.tapOnTheCheeks, this.stretchPose, this.S, this.exerciseB0, this.chewUp, this.chinToJawSmoother, this.exerciseF1, this.foreheadMassageWithFingers, this.halfCircles};
            exerciseRepetitions = new int[]{90, 60, 60, 90, 90, 60, 45, 60, 90, 90, 30, 30, 90};
            exerciseDurations = new int[]{this.exerciseE2, this.lipTuckDrawable, this.exerciseN2, this.flickYourCheeksDrawable, this.tapOnTheCheeksDrawable, this.exerciseZ2, this.exerciseI1, this.jawSmootherDrawable, this.exerciseP1, this.exerciseR1, this.zigzagOnForeheadVerticalDrawable, this.foreheadMassageWithFingersDrawable, this.halfCirclesDrawable};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 29")) {
            exerciseNames = new String[]{this.tapOverTheNeckAndFace, this.exerciseN0, this.smoothUpFrownLines, this.tapAroundTheEyes, this.T, this.smoothUpDarkCircles, this.exerciseG0, this.circularMotionOnNeck, this.X, this.eyelidMassage, this.exerciseR0, this.surpriseMe, this.circularMotions, this.Y};
            exerciseRepetitions = new int[]{90, 90, 60, 60, 45, 60, 60, 60, 60, 60, 90, 60, 90, 90};
            exerciseDurations = new int[]{this.tapOverTheNeckAndFaceDrawable, this.exerciseB2, this.exerciseW2, this.tapAroundTheEyesDrawable, this.exerciseJ1, this.exerciseV2, this.kissTheSkyDrawable, this.exerciseZ1, this.exerciseL1, this.eyelidMassageDrawable, this.exerciseF2, this.surpriseMeDrawable, this.circularMotionsDrawable, this.exerciseM1};
        } else if (currentExercise.equals("Full Face Yoga Routine Day 30")) {
            exerciseNames = new String[]{this.J0, this.exerciseX0, this.reduceLaughingLines, this.flickYourCheeks, this.crowFeetPreventer, this.chinToNeckStrokes, this.exerciseB1, this.suctionCupWithPalms, this.exerciseW0, this.circularMotionBelowNose, this.foreheadDab, this.smoothOverTheForehead, this.exerciseC1, this.exerciseZ0};
            exerciseRepetitions = new int[]{90, 60, 90, 90, 60, 60, 45, 90, 60, 60, 60, 90, 60, 90};
            exerciseDurations = new int[]{this.massageYourFaceDrawable, this.exerciseL2, this.exerciseP2, this.flickYourCheeksDrawable, this.crowFeetPreventerDrawable, this.exerciseS1, this.vPointPoseDrawable, this.suctionCupWithPalmsDrawable, this.exerciseK2, this.exerciseV1, this.foreheadDabDrawable, this.exerciseU2, this.vPoseWithEyeLiftDrawable, this.exerciseN2};
        } else if (currentExercise.equals(getString(R.string.softer_forehead_lines))) {
            exerciseNames = new String[]{this.tapYourForehead, this.foreheadMassageWithFingers, this.smoothOverTheForehead, this.foreheadDab, this.exerciseA1, this.exerciseF1, this.exerciseE1, this.halfCircles, this.exerciseD1};
            exerciseRepetitions = new int[]{60, 60, 45, 30, 30, 30, 30, 45, 60};
            exerciseDurations = new int[]{this.tapYourForeheadDrawable, this.foreheadMassageWithFingersDrawable, this.exerciseU2, this.foreheadDabDrawable, this.vForeheadPoseDrawable, this.zigzagOnForeheadVerticalDrawable, this.zigzagOnForeheadHorizontalDrawable, this.halfCirclesDrawable, this.warmUpTheFaceDrawable};
        } else if (currentExercise.equals(getString(R.string.eye_circles))) {
            exerciseNames = new String[]{this.eyelidMassage, this.darkCircleRemover, this.exerciseC1, this.crowFeetPreventer, this.suctionCupWithPalms, this.smoothUpDarkCircles, this.circleAroundTheEyes, this.eyeDePuffer, this.theEyebrowLift};
            exerciseRepetitions = new int[]{45, 60, 45, 45, 60, 60, 60, 45, 60};
            exerciseDurations = new int[]{this.eyelidMassageDrawable, this.darkCircleRemoverDrawable, this.vPoseWithEyeLiftDrawable, this.crowFeetPreventerDrawable, this.suctionCupWithPalmsDrawable, this.exerciseV2, this.exerciseT1, this.eyeDePufferDrawable, this.theEyebrowLiftDrawable};
        } else if (currentExercise.equals(getString(R.string.brow_smoother))) {
            exerciseNames = new String[]{this.eyeDePuffer, this.exerciseS0, this.theEyebrowLift, this.exerciseT0, this.smoothOutEyebrow, this.tapYourForehead, this.foreheadDab};
            exerciseRepetitions = new int[]{45, 60, 60, 45, 60, 90, 60};
            exerciseDurations = new int[]{this.eyeDePufferDrawable, this.exerciseG2, this.theEyebrowLiftDrawable, this.exerciseH2, this.exerciseS2, this.tapYourForeheadDrawable, this.foreheadDabDrawable};
        } else if (currentExercise.equals(getString(R.string.jaw_unlocker))) {
            exerciseNames = new String[]{this.exerciseA0, this.exerciseC0, this.stretchPose, this.exerciseG0, this.exerciseD0, this.exerciseB0, this.exerciseW0, this.chinToJawSmoother};
            exerciseRepetitions = new int[]{60, 60, 45, 45, 60, 60, 45, 60};
            exerciseDurations = new int[]{this.jawMassageDrawable, this.jawlineMassageWithFistDrawable, this.exerciseZ2, this.kissTheSkyDrawable, this.jawlineMassageDrawable, this.jawSmootherDrawable, this.exerciseK2, this.exerciseR1};
        } else if (currentExercise.equals(getString(R.string.neck_massage))) {
            exerciseNames = new String[]{this.exerciseO0, this.halfNeckRotation, this.tapOverTheNeckAndFace, this.exerciseN0, this.exerciseQ0, this.circularMotionOnNeck, this.exerciseL0, this.exerciseU0, this.exerciseM0};
            exerciseRepetitions = new int[]{90, 60, 90, 60, 45, 60, 60, 45, 60};
            exerciseDurations = new int[]{this.exerciseC2, this.halfNeckRotationDrawable, this.tapOverTheNeckAndFaceDrawable, this.exerciseB2, this.exerciseE2, this.exerciseZ1, this.neckMassageWithFingersDrawable, this.exerciseI2, this.exerciseA2};
        } else if (currentExercise.equals(getString(R.string.remove_double_chin))) {
            exerciseNames = new String[]{this.tapOverTheNeckAndFace, this.chinSmoother, this.chinToNeckStrokes, this.chinToJawSmoother, this.exerciseC0, this.upAndDown, this.theThirstyGirl, this.chewUp, this.exerciseN0};
            exerciseRepetitions = new int[]{90, 60, 90, 90, 60, 60, 45, 45, 60};
            exerciseDurations = new int[]{this.tapOverTheNeckAndFaceDrawable, this.exerciseQ1, this.exerciseS1, this.exerciseR1, this.jawlineMassageWithFistDrawable, this.upAndDownDrawable, this.theThirstyGirlDrawable, this.exerciseP1, this.exerciseB2};
        } else if (currentExercise.equals(getString(R.string.tension_relief_face_yoga))) {
            exerciseNames = new String[]{this.tapAroundTheFace, this.Y, this.circularMotionBelowNose, this.flickYourCheeks, this.eyeDePuffer, this.smoothOutEyebrow, this.surpriseMe, this.exerciseK0, this.exerciseD1};
            exerciseRepetitions = new int[]{90, 60, 45, 60, 45, 90, 60, 45, 90};
            exerciseDurations = new int[]{this.tapAroundTheFaceDrawable, this.exerciseM1, this.exerciseV1, this.flickYourCheeksDrawable, this.eyeDePufferDrawable, this.exerciseS2, this.surpriseMeDrawable, this.massageYourTemplesDrawable, this.warmUpTheFaceDrawable};
        } else if (currentExercise.equals(getString(R.string.good_morning_face_yoga))) {
            exerciseNames = new String[]{this.exerciseD1, this.tapOverTheNeckAndFace, this.smoothOverTheForehead, this.foreheadDab, this.tapAroundTheEyes, this.eyeDePuffer, this.smoothOutSmileLines, this.T, this.exerciseW0, this.exerciseH0};
            exerciseRepetitions = new int[]{90, 90, 60, 45, 60, 45, 60, 45, 60, 45};
            exerciseDurations = new int[]{this.warmUpTheFaceDrawable, this.tapOverTheNeckAndFaceDrawable, this.exerciseU2, this.foreheadDabDrawable, this.tapAroundTheEyesDrawable, this.eyeDePufferDrawable, this.exerciseT2, this.exerciseJ1, this.exerciseK2, this.lipTuckDrawable};
        } else if (currentExercise.equals(getString(R.string.before_bed_face_yoga))) {
            exerciseNames = new String[]{this.tapOverTheNeckAndFace, this.smoothAroundTheMouth, this.flickYourCheeks, this.chewUp, this.exerciseE1, this.circularMotionOnJawlineAndLymph, this.exerciseC1, this.exerciseM0, this.crowFeetPreventer, this.circularMotions};
            exerciseRepetitions = new int[]{90, 60, 90, 45, 45, 60, 45, 60, 60, 90};
            exerciseDurations = new int[]{this.tapOverTheNeckAndFaceDrawable, this.exerciseR2, this.flickYourCheeksDrawable, this.exerciseP1, this.zigzagOnForeheadHorizontalDrawable, this.exerciseY1, this.vPoseWithEyeLiftDrawable, this.exerciseA2, this.crowFeetPreventerDrawable, this.circularMotionsDrawable};
        } else if (currentExercise.equals(getString(R.string.eleven_minute_face_yoga))) {
            exerciseNames = new String[]{this.exerciseD1, this.flickYourCheeks, this.upwardStrokes, this.exerciseF1, this.puffUp, this.chinSmoother, this.exerciseI0, this.reduceLaughingLines, this.smoothUpFrownLines, this.darkCircleRemover, this.exerciseL0};
            exerciseRepetitions = new int[]{90, 60, 60, 45, 45, 60, 45, 45, 60, 90, 60};
            exerciseDurations = new int[]{this.warmUpTheFaceDrawable, this.flickYourCheeksDrawable, this.upwardStrokesDrawable, this.zigzagOnForeheadVerticalDrawable, this.exerciseO2, this.exerciseQ1, this.massageYourEarsDrawable, this.exerciseP2, this.exerciseW2, this.darkCircleRemoverDrawable, this.neckMassageWithFingersDrawable};
        } else if (currentExercise.equals(getString(R.string.face_yoga_to_reduce_wrinkles))) {
            exerciseNames = new String[]{this.reduceLaughingLines, this.theOwl, this.S, this.suctionCupWithPalms, this.theEyebrowLift, this.X, this.stretchPose, this.exerciseA1, this.exerciseX0, this.smoothUpDarkCircles};
            exerciseRepetitions = new int[]{60, 60, 45, 90, 60, 60, 45, 60, 60, 90};
            exerciseDurations = new int[]{this.exerciseP2, this.theOwlDrawable, this.exerciseI1, this.suctionCupWithPalmsDrawable, this.theEyebrowLiftDrawable, this.exerciseL1, this.exerciseZ2, this.vForeheadPoseDrawable, this.exerciseL2, this.exerciseV2};
        } else if (currentExercise.equals(getString(R.string.anti_aging_face_yoga))) {
            exerciseNames = new String[]{this.exerciseN0, this.exerciseD0, this.X, this.eyelidMassage, this.foreheadMassage, this.halfCircles, this.exerciseK0, this.foreheadDab, this.exerciseH0, this.exerciseR0, this.darkCircleRemover};
            exerciseRepetitions = new int[]{90, 90, 60, 45, 90, 60, 60, 45, 45, 90, 60};
            exerciseDurations = new int[]{this.exerciseB2, this.jawlineMassageDrawable, this.exerciseL1, this.eyelidMassageDrawable, this.foreheadMassageDrawable, this.halfCirclesDrawable, this.massageYourTemplesDrawable, this.foreheadDabDrawable, this.lipTuckDrawable, this.exerciseF2, this.darkCircleRemoverDrawable};
        } else if (currentExercise.equals(getString(R.string.face_tapping))) {
            exerciseNames = new String[]{this.exerciseD1, this.tapOverTheNeckAndFace, this.tapOnTheCheeks, this.tapAroundTheEyes, this.tapYourForehead, this.tapIt, this.tapAroundTheFace, this.flickYourCheeks};
            exerciseRepetitions = new int[]{60, 90, 60, 60, 45, 60, 45, 60};
            exerciseDurations = new int[]{this.warmUpTheFaceDrawable, this.tapOverTheNeckAndFaceDrawable, this.tapOnTheCheeksDrawable, this.tapAroundTheEyesDrawable, this.tapYourForeheadDrawable, this.tapItDrawable, this.tapAroundTheFaceDrawable, this.flickYourCheeksDrawable};
        } else if (currentExercise.equals(getString(R.string.face_yoga_for_glowing_skin))) {
            exerciseNames = new String[]{this.smoothOutSmileLines, this.exerciseH0, this.exerciseS0, this.halfCircles, this.exerciseW0, this.exerciseA1, this.exerciseV0, this.exerciseL0, this.chewUp, this.exerciseF1, this.smoothYourCheeks};
            exerciseRepetitions = new int[]{90, 60, 45, 60, 45, 45, 45, 90, 90, 60, 60};
            exerciseDurations = new int[]{this.exerciseT2, this.lipTuckDrawable, this.exerciseG2, this.halfCirclesDrawable, this.exerciseK2, this.vForeheadPoseDrawable, this.exerciseJ2, this.neckMassageWithFingersDrawable, this.exerciseP1, this.zigzagOnForeheadVerticalDrawable, this.exerciseX2};
        } else if (currentExercise.equals(getString(R.string.gua_sha_massage))) {
            String str = this.eyebrowSmootherLeftToRight;
            String str2 = this.eyebrowSmootherRightToLeft;
            exerciseNames = new String[]{this.Q, this.RR, this.sideOfTheNeck, this.exerciseP0, this.U, this.exerciseF0, this.exerciseE0, this.Y, this.Z, this.cheeksToTemplesRightToLeft, str, str2, this.exerciseY0, str, str2, this.theForeheadLift};
            exerciseRepetitions = new int[]{30, 45, 60, 45, 30, 30, 30, 45, 30, 30, 30, 30, 45, 30, 30, 45};
            int i9 = this.eyebrowSmootherLeftToRightDrawable;
            int i10 = this.eyebrowSmootherRightToLeftDrawable;
            exerciseDurations = new int[]{this.exerciseG1, this.exerciseH1, this.exerciseQ2, this.exerciseD2, this.exerciseK1, this.jawlineSmootherRightToLeftDrawable, this.jawlineSmootherLeftToRightDrawable, this.exerciseM1, this.exerciseN1, this.exerciseO1, i9, i10, this.exerciseM2, i9, i10, this.theForeheadLiftDrawable};
        }
    }

    public final void addIIntoList() {
        exerciseList.add(this.Q);
        exerciseList.add(this.RR);
        exerciseList.add(this.S);
        exerciseList.add(this.T);
        exerciseList.add(this.U);
        exerciseList.add(this.V);
        exerciseList.add(this.W);
        exerciseList.add(this.X);
        exerciseList.add(this.Y);
        exerciseList.add(this.Z);
        exerciseList.add(this.cheeksToTemplesRightToLeft);
        exerciseList.add(this.chewUp);
        exerciseList.add(this.chinSmoother);
        exerciseList.add(this.chinToJawSmoother);
        exerciseList.add(this.chinToNeckStrokes);
        exerciseList.add(this.circleAroundTheEyes);
        exerciseList.add(this.circlesOnTheForehead);
        exerciseList.add(this.circularMotionBelowNose);
        exerciseList.add(this.circularMotionOnCheeks);
        exerciseList.add(this.circularMotionOnEye);
        exerciseList.add(this.circularMotionOnJawlineAndLymph);
        exerciseList.add(this.circularMotionOnNeck);
        exerciseList.add(this.circularMotions);
        exerciseList.add(this.crowFeetPreventer);
        exerciseList.add(this.darkCircleRemover);
        exerciseList.add(this.eyeDePuffer);
        exerciseList.add(this.eyebrowSmootherLeftToRight);
        exerciseList.add(this.eyebrowSmootherRightToLeft);
        exerciseList.add(this.eyelidMassage);
        exerciseList.add(this.flickYourCheeks);
        exerciseList.add(this.foreheadDab);
        exerciseList.add(this.foreheadMassageWithFingers);
        exerciseList.add(this.foreheadMassage);
        exerciseList.add(this.halfCircles);
        exerciseList.add(this.halfNeckRotation);
        exerciseList.add(this.holdPose);
        exerciseList.add(this.exerciseA0);
        exerciseList.add(this.exerciseB0);
        exerciseList.add(this.exerciseC0);
        exerciseList.add(this.exerciseD0);
        exerciseList.add(this.exerciseE0);
        exerciseList.add(this.exerciseF0);
        exerciseList.add(this.exerciseG0);
        exerciseList.add(this.exerciseH0);
        exerciseList.add(this.exerciseI0);
        exerciseList.add(this.J0);
        exerciseList.add(this.exerciseK0);
        exerciseList.add(this.exerciseL0);
        exerciseList.add(this.exerciseM0);
        exerciseList.add(this.exerciseN0);
        exerciseList.add(this.exerciseO0);
        exerciseList.add(this.exerciseP0);
        exerciseList.add(this.exerciseQ0);
        exerciseList.add(this.exerciseR0);
        exerciseList.add(this.exerciseS0);
        exerciseList.add(this.exerciseT0);
        exerciseList.add(this.exerciseU0);
        exerciseList.add(this.exerciseV0);
        exerciseList.add(this.exerciseW0);
        exerciseList.add(this.exerciseX0);
        exerciseList.add(this.exerciseY0);
        exerciseList.add(this.exerciseZ0);
        exerciseList.add(this.puffUp);
        exerciseList.add(this.reduceLaughingLines);
        exerciseList.add(this.sideOfTheNeck);
        exerciseList.add(this.smoothAroundTheMouth);
        exerciseList.add(this.smoothOutEyebrow);
        exerciseList.add(this.smoothOutSmileLines);
        exerciseList.add(this.smoothOverTheForehead);
        exerciseList.add(this.smoothUpDarkCircles);
        exerciseList.add(this.smoothUpFrownLines);
        exerciseList.add(this.smoothYourCheeks);
        exerciseList.add(this.squintAndReleaseYourEyes);
        exerciseList.add(this.stretchPose);
        exerciseList.add(this.suctionCupWithPalms);
        exerciseList.add(this.surpriseMe);
        exerciseList.add(this.tapAroundTheEyes);
        exerciseList.add(this.tapAroundTheFace);
        exerciseList.add(this.tapIt);
        exerciseList.add(this.tapOnTheCheeks);
        exerciseList.add(this.tapOverTheNeckAndFace);
        exerciseList.add(this.tapYourForehead);
        exerciseList.add(this.theEyebrowLift);
        exerciseList.add(this.theForeheadLift);
        exerciseList.add(this.theOwl);
        exerciseList.add(this.theThirstyGirl);
        exerciseList.add(this.upAndDown);
        exerciseList.add(this.upwardStrokes);
        exerciseList.add(this.exerciseA1);
        exerciseList.add(this.exerciseB1);
        exerciseList.add(this.exerciseC1);
        exerciseList.add(this.exerciseD1);
        exerciseList.add(this.exerciseE1);
        exerciseList.add(this.exerciseF1);


        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(this.RR);
        keyBuilder.append(".m4v");

        String key = keyBuilder.toString();

        exerciseDescriptions.put(key, getString(R.string.Back_of_the_Neck));
        exerciseDescriptions.put(this.Q + ".m4v", getString(R.string.Apply_Face_Serum));
        exerciseDescriptions.put(this.RR + ".m4v", getString(R.string.Back_of_the_Neck));
        exerciseDescriptions.put(this.S + ".m4v", getString(R.string.Blow_Up));
        exerciseDescriptions.put(this.T + ".m4v", getString(R.string.Blowfish));
        exerciseDescriptions.put(this.U + ".m4v", getString(R.string.Center_of_the_Neck));
        exerciseDescriptions.put(this.V + ".m4v", getString(R.string.Cheek_Smoother_Left_to_Right));
        exerciseDescriptions.put(this.W + ".m4v", getString(R.string.Cheek_Smoother_Right_to_Left));
        exerciseDescriptions.put(this.X + ".m4v", getString(R.string.Cheekbones_Massage));
        exerciseDescriptions.put(this.Y + ".m4v", getString(R.string.Cheeks_Smoother));
        exerciseDescriptions.put(this.Z + ".m4v", getString(R.string.Cheeks_to_Temples_Left_to_Right));
        exerciseDescriptions.put(this.cheeksToTemplesRightToLeft + ".m4v", getString(R.string.Cheeks_to_Temples_Right_to_Left));
        exerciseDescriptions.put(this.chewUp + ".m4v", getString(R.string.Chew_Up));
        exerciseDescriptions.put(this.chinSmoother + ".m4v", getString(R.string.Chin_Smoother));
        exerciseDescriptions.put(this.chinToJawSmoother + ".m4v", getString(R.string.Chin_to_Jaw_Smoother));
        exerciseDescriptions.put(this.chinToNeckStrokes + ".m4v", getString(R.string.Chin_to_Neck_Strokes));
        exerciseDescriptions.put(this.circleAroundTheEyes + ".m4v", getString(R.string.Circle_Around_the_Eyes));
        exerciseDescriptions.put(this.circlesOnTheForehead + ".m4v", getString(R.string.Circles_on_the_Forehead));
        exerciseDescriptions.put(this.circularMotionBelowNose + ".m4v", getString(R.string.Circular_Motion_Below_Nose));
        exerciseDescriptions.put(this.circularMotionOnCheeks + ".m4v", getString(R.string.Circular_Motion_on_Cheeks));
        exerciseDescriptions.put(this.circularMotionOnEye + ".m4v", getString(R.string.Circular_Motion_on_Eye));
        exerciseDescriptions.put(this.circularMotionOnJawlineAndLymph + ".m4v", getString(R.string.Circular_Motion_on_Jawline_and_Lymph));
        exerciseDescriptions.put(this.circularMotionOnNeck + ".m4v", getString(R.string.Circular_Motion_on_Neck));
        exerciseDescriptions.put(this.circularMotions + ".m4v", getString(R.string.Circular_Motions));
        exerciseDescriptions.put(this.crowFeetPreventer + ".m4v", getString(R.string.Crow_Feet_Preventer));
        exerciseDescriptions.put(this.darkCircleRemover + ".m4v", getString(R.string.Dark_Circle_Remover));
        exerciseDescriptions.put(this.eyeDePuffer + ".m4v", getString(R.string.Eye_De_Puffer));
        exerciseDescriptions.put(this.eyebrowSmootherLeftToRight + ".m4v", getString(R.string.Eyebrow_Smoother_Left_to_Right));
        exerciseDescriptions.put(this.eyebrowSmootherRightToLeft + ".m4v", getString(R.string.Eyebrow_Smoother_Right_to_Left));
        exerciseDescriptions.put(this.eyelidMassage + ".m4v", getString(R.string.Eyelid_Massage));
        exerciseDescriptions.put(this.flickYourCheeks + ".m4v", getString(R.string.Flick_Your_Cheeks));
        exerciseDescriptions.put(this.foreheadDab + ".m4v", getString(R.string.Forehead_Dab));
        exerciseDescriptions.put(this.foreheadMassageWithFingers + ".m4v", getString(R.string.Forehead_Massage_With_Fingers));
        exerciseDescriptions.put(this.foreheadMassage + ".m4v", getString(R.string.Forehead_Massage));
        exerciseDescriptions.put(this.halfCircles + ".m4v", getString(R.string.Half_Circles));
        exerciseDescriptions.put(this.halfNeckRotation + ".m4v", getString(R.string.Half_Neck_Rotation));
        exerciseDescriptions.put(this.holdPose + ".m4v", getString(R.string.Hold_Pose));
        exerciseDescriptions.put(this.exerciseA0 + ".m4v", getString(R.string.Jaw_Massage));
        exerciseDescriptions.put(this.exerciseB0 + ".m4v", getString(R.string.Jaw_Smoother));
        exerciseDescriptions.put(this.exerciseC0 + ".m4v", getString(R.string.Jawline_Massage_With_Fist));
        exerciseDescriptions.put(this.exerciseD0 + ".m4v", getString(R.string.Jawline_Massage));
        exerciseDescriptions.put(this.exerciseE0 + ".m4v", getString(R.string.Jawline_Smoother_Left_to_Right));
        exerciseDescriptions.put(this.exerciseF0 + ".m4v", getString(R.string.Jawline_Smoother_Right_to_Left));
        exerciseDescriptions.put(this.exerciseG0 + ".m4v", getString(R.string.Kiss_the_Sky));
        exerciseDescriptions.put(this.exerciseH0 + ".m4v", getString(R.string.Lip_Tuck));
        exerciseDescriptions.put(this.exerciseI0 + ".m4v", getString(R.string.Massage_Your_Ears));
        exerciseDescriptions.put(this.J0 + ".m4v", getString(R.string.Massage_Your_Face));
        exerciseDescriptions.put(this.exerciseK0 + ".m4v", getString(R.string.Massage_Your_Temples));
        exerciseDescriptions.put(this.exerciseL0 + ".m4v", getString(R.string.Neck_Massage_With_Fingers));
        exerciseDescriptions.put(this.exerciseM0 + ".m4v", getString(R.string.Neck_Massage_With_Palms));
        exerciseDescriptions.put(this.exerciseN0 + ".m4v", getString(R.string.Neck_Massage));
        exerciseDescriptions.put(this.exerciseO0 + ".m4v", getString(R.string.Neck_Rotation));
        exerciseDescriptions.put(this.exerciseP0 + ".m4v", getString(R.string.Neck_to_Jawline));
        exerciseDescriptions.put(this.exerciseQ0 + ".m4v", getString(R.string.Neck_Warmer));
        exerciseDescriptions.put(this.exerciseR0 + ".m4v", getString(R.string.One_Sided_Upward_Strokes));
        exerciseDescriptions.put(this.exerciseS0 + ".m4v", getString(R.string.Pinch_Across_the_Eyebrows));
        exerciseDescriptions.put(this.exerciseT0 + ".m4v", getString(R.string.Pinch_Inner_Edge_of_Eyebrows));
        exerciseDescriptions.put(this.exerciseU0 + ".m4v", getString(R.string.Pinch_the_Neck));
        exerciseDescriptions.put(this.exerciseV0 + ".m4v", getString(R.string.Pinch_Your_Lips));
        exerciseDescriptions.put(this.exerciseW0 + ".m4v", getString(R.string.Pinches_on_Jawline));
        exerciseDescriptions.put(this.exerciseX0 + ".m4v", getString(R.string.Pout_Pose));
        exerciseDescriptions.put(this.exerciseY0 + ".m4v", getString(R.string.Press_Above_the_Nose));
        exerciseDescriptions.put(this.exerciseZ0 + ".m4v", getString(R.string.Press_Down_on_Lymph));
        exerciseDescriptions.put(this.puffUp + ".m4v", getString(R.string.Puff_Up));
        exerciseDescriptions.put(this.reduceLaughingLines + ".m4v", getString(R.string.Reduce_Laughing_Lines));
        exerciseDescriptions.put(this.sideOfTheNeck + ".m4v", getString(R.string.Side_of_the_Neck));
        exerciseDescriptions.put(this.smoothAroundTheMouth + ".m4v", getString(R.string.Smooth_Around_the_Mouth));
        exerciseDescriptions.put(this.smoothOutEyebrow + ".m4v", getString(R.string.Smooth_Out_Eyebrow));
        exerciseDescriptions.put(this.smoothOutSmileLines + ".m4v", getString(R.string.Smooth_Out_Smile_Lines));
        exerciseDescriptions.put(this.smoothOverTheForehead + ".m4v", getString(R.string.Smooth_Over_the_Forehead));
        exerciseDescriptions.put(this.smoothUpDarkCircles + ".m4v", getString(R.string.Smooth_Up_Dark_Circles));
        exerciseDescriptions.put(this.smoothUpFrownLines + ".m4v", getString(R.string.Smooth_Up_Frown_Lines));
        exerciseDescriptions.put(this.smoothYourCheeks + ".m4v", getString(R.string.Smooth_Your_Cheeks));
        exerciseDescriptions.put(this.squintAndReleaseYourEyes + ".m4v", getString(R.string.Squint_and_Release_Your_Eyes));
        exerciseDescriptions.put(this.stretchPose + ".m4v", getString(R.string.Stretch_Pose));
        exerciseDescriptions.put(this.suctionCupWithPalms + ".m4v", getString(R.string.Suction_Cup_With_Palms));
        exerciseDescriptions.put(this.surpriseMe + ".m4v", getString(R.string.Surprise_Me));
        exerciseDescriptions.put(this.tapAroundTheEyes + ".m4v", getString(R.string.Tap_Around_the_Eyes));
        exerciseDescriptions.put(this.tapAroundTheFace + ".m4v", getString(R.string.Tap_Around_the_Face));
        exerciseDescriptions.put(this.tapIt + ".m4v", getString(R.string.Tap_It));
        exerciseDescriptions.put(this.tapOnTheCheeks + ".m4v", getString(R.string.Tap_on_the_Cheeks));
        exerciseDescriptions.put(this.tapOverTheNeckAndFace + ".m4v", getString(R.string.Tap_Over_the_Neck_and_Face));
        exerciseDescriptions.put(this.tapYourForehead + ".m4v", getString(R.string.Tap_Your_Forehead));
        exerciseDescriptions.put(this.theEyebrowLift + ".m4v", getString(R.string.The_Eyebrow_Lift));
        exerciseDescriptions.put(this.theForeheadLift + ".m4v", getString(R.string.The_Forehead_Lift));
        exerciseDescriptions.put(this.theOwl + ".m4v", getString(R.string.The_Owl));
        exerciseDescriptions.put(this.theThirstyGirl + ".m4v", getString(R.string.The_Thirsty_Girl));
        exerciseDescriptions.put(this.upAndDown + ".m4v", getString(R.string.Up_and_Down));
        exerciseDescriptions.put(this.upwardStrokes + ".m4v", getString(R.string.Upward_Strokes));
        exerciseDescriptions.put(this.exerciseA1 + ".m4v", getString(R.string.V_Forehead_Pose));
        exerciseDescriptions.put(this.exerciseB1 + ".m4v", getString(R.string.V_Point_Pose));
        exerciseDescriptions.put(this.exerciseC1 + ".m4v", getString(R.string.V_Pose_With_Eye_Lift));
        exerciseDescriptions.put(this.exerciseD1 + ".m4v", getString(R.string.Warm_Up_the_Face));
        exerciseDescriptions.put(this.exerciseE1 + ".m4v", getString(R.string.Zigzag_on_Forehead_Horizontal));
        exerciseDescriptions.put(this.exerciseF1 + ".m4v", getString(R.string.Zigzag_on_Forehead_Vertical));

        exerciseImages.put(this.Q + ".m4v", getResources().getInteger(R.integer.Apply_Face_Serum_Length));
        exerciseImages.put(this.RR + ".m4v", getResources().getInteger(R.integer.Back_of_the_Neck_Length));
        exerciseImages.put(this.S + ".m4v", getResources().getInteger(R.integer.Blow_Up_Length));
        exerciseImages.put(this.T + ".m4v", getResources().getInteger(R.integer.Blowfish_Length));
        exerciseImages.put(this.U + ".m4v", getResources().getInteger(R.integer.Center_of_the_Neck_Length));
        exerciseImages.put(this.V + ".m4v", getResources().getInteger(R.integer.Cheek_Smoother_Left_to_Right_Length));
        exerciseImages.put(this.W + ".m4v", getResources().getInteger(R.integer.Cheek_Smoother_Right_to_Left_Length));
        exerciseImages.put(this.X + ".m4v", getResources().getInteger(R.integer.Cheekbones_Massage_Length));
        exerciseImages.put(this.Y + ".m4v", getResources().getInteger(R.integer.Cheeks_Smoother_Length));
        exerciseImages.put(this.Z + ".m4v", getResources().getInteger(R.integer.Cheeks_to_Temples_Left_to_Right_Length));
        exerciseImages.put(this.cheeksToTemplesRightToLeft + ".m4v", getResources().getInteger(R.integer.Cheeks_to_Temples_Right_to_Left_Length));
        exerciseImages.put(this.chewUp + ".m4v", getResources().getInteger(R.integer.Chew_Up_Length));
        exerciseImages.put(this.chinSmoother + ".m4v", getResources().getInteger(R.integer.Chin_Smoother_Length));
        exerciseImages.put(this.chinToJawSmoother + ".m4v", getResources().getInteger(R.integer.Chin_to_Jaw_Smoother_Length));
        exerciseImages.put(this.chinToNeckStrokes + ".m4v", getResources().getInteger(R.integer.Chin_to_Neck_Strokes_Length));
        exerciseImages.put(this.circleAroundTheEyes + ".m4v", getResources().getInteger(R.integer.Circle_Around_the_Eyes_Length));
        exerciseImages.put(this.circlesOnTheForehead + ".m4v", getResources().getInteger(R.integer.Circles_on_the_Forehead_Length));
        exerciseImages.put(this.circularMotionBelowNose + ".m4v", getResources().getInteger(R.integer.Circular_Motion_Below_Nose_Length));
        exerciseImages.put(this.circularMotionOnCheeks + ".m4v", getResources().getInteger(R.integer.Circular_Motion_on_Cheeks_Length));
        exerciseImages.put(this.circularMotionOnEye + ".m4v", getResources().getInteger(R.integer.Circular_Motion_on_Eye_Length));
        exerciseImages.put(this.circularMotionOnJawlineAndLymph + ".m4v", getResources().getInteger(R.integer.Circular_Motion_on_Jawline_and_Lymph_Length));
        exerciseImages.put(this.circularMotionOnNeck + ".m4v", getResources().getInteger(R.integer.Circular_Motion_on_Neck_Length));
        exerciseImages.put(this.circularMotions + ".m4v", getResources().getInteger(R.integer.Circular_Motions_Length));
        exerciseImages.put(this.crowFeetPreventer + ".m4v", getResources().getInteger(R.integer.Crow_Feet_Preventer_Length));
        exerciseImages.put(this.darkCircleRemover + ".m4v", getResources().getInteger(R.integer.Dark_Circle_Remover_Length));
        exerciseImages.put(this.eyeDePuffer + ".m4v", getResources().getInteger(R.integer.Eye_De_Puffer_Length));
        exerciseImages.put(this.eyebrowSmootherLeftToRight + ".m4v", getResources().getInteger(R.integer.Eyebrow_Smoother_Left_to_Right_Length));
        exerciseImages.put(this.eyebrowSmootherRightToLeft + ".m4v", getResources().getInteger(R.integer.Eyebrow_Smoother_Right_to_Left_Length));
        exerciseImages.put(this.eyelidMassage + ".m4v", getResources().getInteger(R.integer.Eyelid_Massage_Length));
        exerciseImages.put(this.flickYourCheeks + ".m4v", getResources().getInteger(R.integer.Flick_Your_Cheeks_Length));
        exerciseImages.put(this.foreheadDab + ".m4v", getResources().getInteger(R.integer.Forehead_Dab_Length));
        exerciseImages.put(this.foreheadMassageWithFingers + ".m4v", getResources().getInteger(R.integer.Forehead_Massage_With_Fingers_Length));
        exerciseImages.put(this.foreheadMassage + ".m4v", getResources().getInteger(R.integer.Forehead_Massage_Length));
        exerciseImages.put(this.halfCircles + ".m4v", getResources().getInteger(R.integer.Half_Circles_Length));
        exerciseImages.put(this.halfNeckRotation + ".m4v", getResources().getInteger(R.integer.Half_Neck_Rotation_Length));
        exerciseImages.put(this.holdPose + ".m4v", getResources().getInteger(R.integer.Hold_Pose_Length));
        exerciseImages.put(this.exerciseA0 + ".m4v", getResources().getInteger(R.integer.Jaw_Massage_Length));
        exerciseImages.put(this.exerciseB0 + ".m4v", getResources().getInteger(R.integer.Jaw_Smoother_Length));
        exerciseImages.put(this.exerciseC0 + ".m4v", getResources().getInteger(R.integer.Jawline_Massage_With_Fist_Length));
        exerciseImages.put(this.exerciseD0 + ".m4v", getResources().getInteger(R.integer.Jawline_Massage_Length));
        exerciseImages.put(this.exerciseE0 + ".m4v", getResources().getInteger(R.integer.Jawline_Smoother_Left_to_Right_Length));
        exerciseImages.put(this.exerciseF0 + ".m4v", getResources().getInteger(R.integer.Jawline_Smoother_Right_to_Left_Length));
        exerciseImages.put(this.exerciseG0 + ".m4v", getResources().getInteger(R.integer.Kiss_the_Sky_Length));
        exerciseImages.put(this.exerciseH0 + ".m4v", getResources().getInteger(R.integer.Lip_Tuck_Length));
        exerciseImages.put(this.exerciseI0 + ".m4v", getResources().getInteger(R.integer.Massage_Your_Ears_Length));
        exerciseImages.put(this.J0 + ".m4v", getResources().getInteger(R.integer.Massage_Your_Face_Length));
        exerciseImages.put(this.exerciseK0 + ".m4v", getResources().getInteger(R.integer.Massage_Your_Temples_Length));
        exerciseImages.put(this.exerciseL0 + ".m4v", getResources().getInteger(R.integer.Neck_Massage_With_Fingers_Length));
        exerciseImages.put(this.exerciseM0 + ".m4v", getResources().getInteger(R.integer.Neck_Massage_With_Palms_Length));
        exerciseImages.put(this.exerciseN0 + ".m4v", getResources().getInteger(R.integer.Neck_Massage_Length));
        exerciseImages.put(this.exerciseO0 + ".m4v", getResources().getInteger(R.integer.Neck_Rotation_Length));
        exerciseImages.put(this.exerciseP0 + ".m4v", getResources().getInteger(R.integer.Neck_to_Jawline_Length));
        exerciseImages.put(this.exerciseQ0 + ".m4v", getResources().getInteger(R.integer.Neck_Warmer_Length));
        exerciseImages.put(this.exerciseR0 + ".m4v", getResources().getInteger(R.integer.One_Sided_Upward_Strokes_Length));
        exerciseImages.put(this.exerciseS0 + ".m4v", getResources().getInteger(R.integer.Pinch_Across_the_Eyebrows_Length));
        exerciseImages.put(this.exerciseT0 + ".m4v", getResources().getInteger(R.integer.Pinch_Inner_Edge_of_Eyebrows_Length));
        exerciseImages.put(this.exerciseU0 + ".m4v", getResources().getInteger(R.integer.Pinch_the_Neck_Length));
        exerciseImages.put(this.exerciseV0 + ".m4v", getResources().getInteger(R.integer.Pinch_Your_Lips_Length));
        exerciseImages.put(this.exerciseW0 + ".m4v", getResources().getInteger(R.integer.Pinches_on_Jawline_Length));
        exerciseImages.put(this.exerciseX0 + ".m4v", getResources().getInteger(R.integer.Pout_Pose_Length));
        exerciseImages.put(this.exerciseY0 + ".m4v", getResources().getInteger(R.integer.Press_Above_the_Nose_Length));
        exerciseImages.put(this.exerciseZ0 + ".m4v", getResources().getInteger(R.integer.Press_Down_on_Lymph_Length));
        exerciseImages.put(this.puffUp + ".m4v", getResources().getInteger(R.integer.Puff_Up_Length));
        exerciseImages.put(this.reduceLaughingLines + ".m4v", getResources().getInteger(R.integer.Reduce_Laughing_Lines_Length));
        exerciseImages.put(this.sideOfTheNeck + ".m4v", getResources().getInteger(R.integer.Side_of_the_Neck_Length));
        exerciseImages.put(this.smoothAroundTheMouth + ".m4v", getResources().getInteger(R.integer.Smooth_Around_the_Mouth_Length));
        exerciseImages.put(this.smoothOutEyebrow + ".m4v", getResources().getInteger(R.integer.Smooth_Out_Eyebrow_Length));
        exerciseImages.put(this.smoothOutSmileLines + ".m4v", getResources().getInteger(R.integer.Smooth_Out_Smile_Lines_Length));
        exerciseImages.put(this.smoothOverTheForehead + ".m4v", getResources().getInteger(R.integer.Smooth_Over_the_Forehead_Length));
        exerciseImages.put(this.smoothUpDarkCircles + ".m4v", getResources().getInteger(R.integer.Smooth_Up_Dark_Circles_Length));
        exerciseImages.put(this.smoothUpFrownLines + ".m4v", getResources().getInteger(R.integer.Smooth_Up_Frown_Lines_Length));
        exerciseImages.put(this.smoothYourCheeks + ".m4v", getResources().getInteger(R.integer.Smooth_Your_Cheeks_Length));
        exerciseImages.put(this.squintAndReleaseYourEyes + ".m4v", getResources().getInteger(R.integer.Squint_and_Release_Your_Eyes_Length));
        exerciseImages.put(this.stretchPose + ".m4v", getResources().getInteger(R.integer.Stretch_Pose_Length));
        exerciseImages.put(this.suctionCupWithPalms + ".m4v", getResources().getInteger(R.integer.Suction_Cup_With_Palms_Length));
        exerciseImages.put(this.surpriseMe + ".m4v", getResources().getInteger(R.integer.Surprise_Me_Length));
        exerciseImages.put(this.tapAroundTheEyes + ".m4v", getResources().getInteger(R.integer.Tap_Around_the_Eyes_Length));
        exerciseImages.put(this.tapAroundTheFace + ".m4v", getResources().getInteger(R.integer.Tap_Around_the_Face_Length));
        exerciseImages.put(this.tapIt + ".m4v", getResources().getInteger(R.integer.Tap_It_Length));
        exerciseImages.put(this.tapOnTheCheeks + ".m4v", getResources().getInteger(R.integer.Tap_on_the_Cheeks_Length));
        exerciseImages.put(this.tapOverTheNeckAndFace + ".m4v", getResources().getInteger(R.integer.Tap_Over_the_Neck_and_Face_Length));
        exerciseImages.put(this.tapYourForehead + ".m4v", getResources().getInteger(R.integer.Tap_Your_Forehead_Length));
        exerciseImages.put(this.theEyebrowLift + ".m4v", getResources().getInteger(R.integer.The_Eyebrow_Lift_Length));
        exerciseImages.put(this.theForeheadLift + ".m4v", getResources().getInteger(R.integer.The_Forehead_Lift_Length));
        exerciseImages.put(this.theOwl + ".m4v", getResources().getInteger(R.integer.The_Owl_Length));
        exerciseImages.put(this.theThirstyGirl + ".m4v", getResources().getInteger(R.integer.The_Thirsty_Girl_Length));
        exerciseImages.put(this.upAndDown + ".m4v", getResources().getInteger(R.integer.Up_and_Down_Length));
        exerciseImages.put(this.upwardStrokes + ".m4v", getResources().getInteger(R.integer.Upward_Strokes_Length));
        exerciseImages.put(this.exerciseA1 + ".m4v", getResources().getInteger(R.integer.V_Forehead_Pose_Length));
        exerciseImages.put(this.exerciseB1 + ".m4v", getResources().getInteger(R.integer.V_Point_Pose_Length));
        exerciseImages.put(this.exerciseC1 + ".m4v", getResources().getInteger(R.integer.V_Pose_With_Eye_Lift_Length));
        exerciseImages.put(this.exerciseD1 + ".m4v", getResources().getInteger(R.integer.Warm_Up_the_Face_Length));
        exerciseImages.put(this.exerciseE1 + ".m4v", getResources().getInteger(R.integer.Zigzag_on_Forehead_Horizontal_Length));
        exerciseImages.put(this.exerciseF1 + ".m4v", getResources().getInteger(R.integer.Zigzag_on_Forehead_Vertical_Length));

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}