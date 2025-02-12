package com.jawline.exercises.faceyoga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jawline.exercises.faceyoga.other.CountdownTimer;

import java.util.ArrayList;
import java.util.Locale;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class WorkoutActivity extends AppCompatActivity {
    public String workoutDay, challengeDay, currentExerciseName;
    public String[] exerciseNames;
    public int[] exerciseImages, exerciseDurations;
    public int currentExerciseIndex;
    public TextView tvCurrentTime, tvTotalTime, tvExerciseName;
    public ImageView ivExerciseImage, ivPreviousExercise, ivNextExercise, ivPlayPause, btnPauseResume;
    public WorkoutTimer countdownTimer;
    public boolean isWorkoutCompleted, isWorkoutPaused, isTimerRunning;
    public LinearLayout layoutProgressIndicators;
    public int currentProgressIndex = 0;
    public ArrayList<View> progressIndicators = new ArrayList<>();
    public TextToSpeech textToSpeech;
    public Bundle speechParams;
    public SharedPreferences sharedPreferences;
    public RelativeLayout workoutCompleteLayout;
    public AppCompatButton continueButton;
    AppCompatTextView tvcongratulations;

    public TextView tvStepCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        float f9;
        Bundle bundle2;
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(8192);
        setContentView(R.layout.activity_workout);

        initializeViews();

        exerciseNames = getIntent().getStringArrayExtra("Workout Name");
        exerciseImages = getIntent().getIntArrayExtra("Workout Images");
        exerciseDurations = getIntent().getIntArrayExtra("Workout Reps");

        getIntent().getStringExtra("Color");
        String stringExtra = getIntent().getStringExtra("Which Exercise Schedule");
        workoutDay = stringExtra;
        challengeDay = "";
        try {
            String[] split = stringExtra.split(" ");
            if ((split[0] + " " + split[1] + " " + split[2]).equals("Full Face Yoga")) {
                challengeDay = workoutDay.split(" Day ")[1];
            }
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        tvCurrentTime.setTextColor(getColor(R.color.colorPrimary));
        speechParams = new Bundle();
        setupTextToSpeech();
        sharedPreferences = getSharedPreferences(getPackageName(), 0);
        boolean z9 = sharedPreferences.getBoolean("isVolumeOn", true);
        isWorkoutCompleted = z9;
        if (z9) {
            ivPlayPause.setImageResource(R.drawable.volume_up);
            bundle2 = speechParams;
            f9 = 1.0f;
        } else {
            ivPlayPause.setImageResource(R.drawable.volume_off);
            bundle2 = speechParams;
            f9 = 0.0f;
        }
        bundle2.putFloat("volume", f9);
        setupProgressIndicators();

        updateWorkoutProgress();
        currentExerciseIndex = 0;
        updateUI();
    }

    private void initializeViews() {
        isWorkoutPaused = false;
        tvExerciseName = findViewById(R.id.workoutNameTV);
        tvCurrentTime = findViewById(R.id.readInstructionTV);
        tvTotalTime = findViewById(R.id.timerTV);
        ivExerciseImage = findViewById(R.id.workoutImageView);
        ivPlayPause = findViewById(R.id.volumeImageView);
        ivPreviousExercise = findViewById(R.id.nextImageView);
        ivNextExercise = findViewById(R.id.previousImageView);
        btnPauseResume = findViewById(R.id.playPauseButton);
        layoutProgressIndicators = findViewById(R.id.layout_views);
        workoutCompleteLayout = findViewById(R.id.rvDoneExercise);
        continueButton = findViewById(R.id.btnContinue);
        tvcongratulations = findViewById(R.id.tvcongratulations);
        tvStepCounter = findViewById(R.id.tvStepCounter);
        workoutCompleteLayout.setVisibility(4);
    }

    private void setupTextToSpeech() {
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.UK);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language Not Supported");
                } else if (exerciseNames != null && exerciseNames.length > 0) {
                    textToSpeech.speak(exerciseNames[0], TextToSpeech.QUEUE_FLUSH, speechParams, null);
                    textToSpeech.speak(exerciseDurations[0] + " Seconds", TextToSpeech.QUEUE_ADD, speechParams, null);
                    textToSpeech.speak(getExerciseDescription(exerciseNames[0]), TextToSpeech.QUEUE_ADD, speechParams, null);
                }
            } else {
                Log.e("TTS", "Text-To-Speech Initialization Failed");
            }
        });
    }

    private void setupProgressIndicators() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int numExercises = exerciseNames != null ? exerciseNames.length : 0;

        if (numExercises > 0) {
            int indicatorWidth = (screenWidth - (numExercises * 16) - 16) / numExercises;
            progressIndicators = new ArrayList<>();

            for (int i = 0; i < numExercises; i++) {
                View view = new View(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(indicatorWidth, 10);
                layoutParams.setMargins(16, 0, 0, 0);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(getColor(R.color.colorGrey));
                progressIndicators.add(view);
                layoutProgressIndicators.addView(view);
            }
        }
    }


    public final void onDestroy() {
        this.textToSpeech.stop();
        super.onDestroy();
    }

    public final void onPause() {
        this.textToSpeech.stop();
        try {
            WorkoutTimer workoutTimer = this.countdownTimer;
            if (workoutTimer.isRunning) {
                workoutTimer.pause();
            }
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        super.onPause();
    }

    public final void onResume() {
        try {
            if (countdownTimer.isPaused()) {
                countdownTimer.resume();
            }
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        super.onResume();
    }

    public void playPauseCenter(View view) {
        boolean z9;
        if (isTimerRunning) {
            countdownTimer.pause();
            this.btnPauseResume.setImageResource(R.drawable.play_icon);
            z9 = false;
        } else {
            countdownTimer.resume();
            this.btnPauseResume.setImageResource(R.drawable.pause_icon);
            z9 = true;
        }
        this.isTimerRunning = z9;
    }


    public void previous(View view) {
        progressIndicators.get(this.currentProgressIndex).setBackgroundColor(getColor(R.color.colorGrey));
        currentProgressIndex--;
        stopTimer();
        currentExerciseIndex--;
        updateUI();
    }

    public void readInstructions(View view) {
        com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = new com.google.android.material.bottomsheet.BottomSheetDialog(this);
        View inflate = getLayoutInflater().inflate(R.layout.read_instructions, (ViewGroup) null);
        bottomSheetDialog.setContentView(inflate);
        bottomSheetDialog.show();
        ((Button) inflate.findViewById(R.id.okButton)).setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();
        });
        ((TextView) inflate.findViewById(R.id.descriptionTV)).setText(getExerciseDescription(this.currentExerciseName));
    }

    public void volume(View view) {
        boolean z9;
        if (isWorkoutCompleted) {
            ivPlayPause.setImageResource(R.drawable.volume_off);
            speechParams.putFloat("volume", 0.0f);
            textToSpeech.stop();
            z9 = false;
        } else {
            ivPlayPause.setImageResource(R.drawable.volume_up);
            speechParams.putFloat("volume", 1.0f);
            z9 = true;
        }
        isWorkoutCompleted = z9;
        sharedPreferences.edit().putBoolean("isVolumeOn", isWorkoutCompleted).apply();
    }

    public class WorkoutTimer extends CountdownTimer {
        public WorkoutTimer(long j9) {
            super(j9);
        }

        @Override
        public void onTick(long remainingTime) {
            WorkoutActivity.this.isTimerRunning = true;
            long j10 = remainingTime / 1000;
            int i9 = (int) (j10 / 60);
            int i10 = (int) (j10 % 60);
            String num = Integer.toString(i9);
            String num2 = Integer.toString(i10);
            if (i9 <= 9) {
                StringBuilder m9 = new StringBuilder("0");
                m9.append(Integer.toString(i9));
                num = m9.toString();
            }
            if (i10 <= 9) {
                StringBuilder m10 = new StringBuilder("0");
                m10.append(Integer.toString(i10));
                num2 = m10.toString();
            }
            WorkoutActivity.this.tvTotalTime.setText(num + ":" + num2);
            if (remainingTime == 0) {
                WorkoutActivity workoutActivity = WorkoutActivity.this;
                int i11 = workoutActivity.currentExerciseIndex;
                if (i11 == workoutActivity.exerciseNames.length - 1) {

                    workoutCompleteLayout.setVisibility(View.VISIBLE);
                    isWorkoutPaused = true;
                    try {
                        if (!challengeDay.equals("")) {
                            MainActivity.database.execSQL("INSERT INTO challengeDays (day) VALUES (" + (Integer.valueOf(WorkoutActivity.this.challengeDay).intValue() - 1) + ")");
                            Log.i("SQLite Database", "Day " + WorkoutActivity.this.challengeDay + " noted.");
                        }
                    } catch (Exception e10) {
                        e10.printStackTrace();
                    }
                    AppCompatTextView appCompatTextView = tvcongratulations;
                    StringBuilder stringBuilder = new StringBuilder("You have done ");
                    stringBuilder.append(Integer.toString(exerciseNames.length));
                    stringBuilder.append(" exercises today");
                    appCompatTextView.setText(stringBuilder.toString());
                    new WorkoutActivity().workoutReps(WorkoutActivity.this);
                    continueButton.startAnimation(AnimationUtils.loadAnimation(WorkoutActivity.this, R.anim.fade_in_anim));
                    KonfettiView konfettiView = (KonfettiView) findViewById(R.id.viewKonfetti);
                    konfettiView.build()
                            .addColors(Color.parseColor("#5BB6EF"), Color.parseColor("#E2A10F"), Color.parseColor("#D20272"))
                            .setDirection(0.0, 359.0)
                            .setSpeed(1f, 4f)
                            .setFadeOutEnabled(true)
                            .setTimeToLive(2000L)
                            .addShapes(Shape.RECT, Shape.CIRCLE)
                            .addSizes(new Size(12, 5.0f))
                            .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f) // and change position from here
                            .streamFor(300, 5000L);

                    konfettiView.invalidate();
                    return;
                }
                workoutActivity.currentExerciseIndex = i11 + 1;
                workoutActivity.updateUI();
                WorkoutActivity workoutActivity5 = WorkoutActivity.this;
                workoutActivity5.currentProgressIndex++;
                workoutActivity5.updateWorkoutProgress();
            }
        }

        @Override
        public void onFinish() {
            WorkoutActivity.this.isTimerRunning = false;
        }
    }

    public void workoutFinished(View view) {
        try {
            finish();
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        try {
            MainActivity.isWorkoutRunning.finish();
        } catch (Exception e11) {
            e11.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public final void updateUI() {
        if (currentExerciseIndex == 0) {
            ivNextExercise.setVisibility(View.GONE);
        } else {
            ivNextExercise.setVisibility(View.VISIBLE);
        }

        if (currentExerciseIndex == this.exerciseNames.length - 1) {
            ivPreviousExercise.setVisibility(View.GONE);
        } else {
            ivPreviousExercise.setVisibility(View.VISIBLE);
        }

        currentExerciseName = this.exerciseNames[this.currentExerciseIndex];
        btnPauseResume.setImageResource(R.drawable.pause_icon);

        Glide.with(this).load(exerciseImages[currentExerciseIndex]).into(ivExerciseImage);
        ivExerciseImage.setImageResource(exerciseImages[currentExerciseIndex]);
        tvExerciseName.setText(exerciseNames[currentExerciseIndex]);
        tvTotalTime.setText(String.valueOf(exerciseDurations[currentExerciseIndex]));
        int stepNumber = currentExerciseIndex + 1;
        int totalSteps = exerciseNames.length;
        tvStepCounter.setText("Steps " + String.format("%02d", stepNumber) + " / " + totalSteps);

        textToSpeech.speak(currentExerciseName, TextToSpeech.QUEUE_FLUSH, speechParams, null);
        textToSpeech.speak(exerciseDurations[currentExerciseIndex] + " Seconds", TextToSpeech.QUEUE_ADD, speechParams, null);
        textToSpeech.speak(getExerciseDescription(currentExerciseName), TextToSpeech.QUEUE_ADD, speechParams, null);
        countdownTimer = new WorkoutTimer(exerciseDurations[currentExerciseIndex] * 1000L);
        countdownTimer.start();
    }


    public final void workoutReps(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Total Workout Days", 0);
        int i9 = sharedPreferences.getInt("Total Workout Days", 0) + 1;
        sharedPreferences.edit().putInt("Total Workout Days", i9).commit();
        Log.i("Total_Workout_days", Integer.toString(i9));
    }

    public final void updateWorkoutProgress() {
        this.progressIndicators.get(this.currentProgressIndex).setBackgroundColor(getColor(R.color.colorPrimary));
    }

    public final void stopTimer() {
        try {
            countdownTimer.stop();
        } catch (Exception e10) {
            e10.printStackTrace();
        }
    }

    public final String getExerciseDescription(String str) {
        int i9;
        if (str.equals("Blowfish")) {
            i9 = R.string.blowfish;
        } else if (str.equals("Cheek Sculptor")) {
            i9 = R.string.cheekSculptor;
        } else if (str.equals("Firm Up Soggy Cheeks")) {
            i9 = R.string.firmUpSoggyCheeks;
        } else if (str.equals("For Horizontal Wrinkles")) {
            i9 = R.string.forHorizontalWrinkles;
        } else if (str.equals("Hug The Forehead")) {
            i9 = R.string.hugTheForehead;
        } else if (str.equals("Jowl Buster")) {
            i9 = R.string.jowlBuster;
        } else if (str.equals("Lion Pose")) {
            i9 = R.string.lionPose;
        } else if (str.equals("Lip Tuck")) {
            i9 = R.string.lipTuck;
        } else if (str.equals("Relax Your Eye Muscles")) {
            i9 = R.string.relaxYourEyeMuscles;
        } else if (str.equals("Sharpen Your Nose")) {
            i9 = R.string.sharpenYourNose;
        } else if (str.equals("Smooth Out Smile Lines")) {
            i9 = R.string.smoothOutSmileLines;
        } else if (str.equals("Nose to Cheeks")) {
            i9 = R.string.smoothTransitionOfTheNoseToCheeks;
        } else if (str.equals("Surprise Me")) {
            i9 = R.string.surpriseMe;
        } else if (str.equals("The Eyebrow Lift")) {
            i9 = R.string.theEyebrowLift;
        } else if (str.equals("The Handy Face Lift")) {
            i9 = R.string.theHandyFaceLift;
        } else if (str.equals("The Owl")) {
            i9 = R.string.theOwl;
        } else if (str.equals("Tighten Your Eye Area")) {
            i9 = R.string.tightenYourEyeArea;
        } else if (str.equals("Tough Kiss")) {
            i9 = R.string.toughKiss;
        } else if (!str.equals("Wrinkle Relaxer")) {
            return "";
        } else {
            i9 = R.string.wrinklesRelaxer;
        }
        return getString(i9);
    }

    public void next(View view) {
        this.currentProgressIndex++;
        updateWorkoutProgress();
        stopTimer();
        this.currentExerciseIndex++;
        updateUI();
    }

    public final void onBackPressed() {
        if (this.isWorkoutPaused) {
            stopTimer();
            textToSpeech.stop();
            super.onBackPressed();
            finish();
            return;
        }
        ExitTask();
    }


    public final void ExitTask() {
        final Dialog dialog = new Dialog(WorkoutActivity.this, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_exittask);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();

        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
        }


        AppCompatButton btnContinue = dialog.findViewById(R.id.btnContinue);
        AppCompatButton btnexit = dialog.findViewById(R.id.btnexit);

        btnexit.setOnClickListener(v -> {
            stopTimer();
            textToSpeech.stop();
            finish();
            dialog.dismiss();
        });

        btnContinue.setOnClickListener(v -> {

            dialog.dismiss();
        });

        dialog.show();
    }

}