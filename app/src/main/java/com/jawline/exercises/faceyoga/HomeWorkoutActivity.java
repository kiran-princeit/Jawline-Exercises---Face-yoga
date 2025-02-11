package com.jawline.exercises.faceyoga;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.jawline.exercises.faceyoga.other.CountdownTimer;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class HomeWorkoutActivity extends AppCompatActivity {

    // Arrays storing workout details
    public String[] workoutNames;
    public int[] workoutReps;
    public int[] workoutImages;

    // Video playback
    public VideoView workoutVideoView;

    // Control buttons
    public ImageView playPauseButton;
    public ImageView nextWorkoutButton;
    public ImageView previousWorkoutButton;

    // Boolean flags
    public boolean isWorkoutRunning;
    public boolean isWorkoutPaused;
    public boolean isCountdownRunning = false;

    // UI Elements
    public CircularProgressIndicator videoLoadingIndicator;
    public int totalWorkoutCount;
    public int selectedWorkoutDay;
    public double progressTime = -0.5d;


    public RelativeLayout restWorkoutLayout;
    public RelativeLayout workoutCompleteLayout;
    public RelativeLayout workoutContainer;


    public AppCompatTextView workoutNameTextView;
    public AppCompatTextView workoutTimerTextView;
    public AppCompatTextView congratulationsTextView;


    public AppCompatImageView nextExerciseImageView;
    public AppCompatImageView ivVolume;

    public AppCompatButton continueButton;


    public TextView countdownTextView;
    public TextView nextExerciseTitle;
    public TextView nextExerciseReps;

    public ProgressBar workoutProgressBar;
    public ProgressBar restProgressBar;

    public WorkoutCountdownTimer workoutCountdownTimer;

    public long remainingWorkoutTime;

    public String subscriptionStatus = "WorkoutPremium";
    public MediaPlayer mediaPlayer;
    public ResetCountdownTimer restCountdownTimer;
    public File workoutVideoDirectory;
    public TextToSpeech textToSpeech;
    public Bundle workoutBundle;
    public SharedPreferences sharedPreferences;
    //    public boolean isSubscriptionActive;
    TextView tvStepCounterhome;

    public final void onCreate(Bundle bundle) {
        Bundle bundle2;
        float f9;
        super.onCreate(bundle);
        getWindow().setStatusBarColor(getResources().getColor(R.color.videoStatusBarColor));
        setContentView((int) R.layout.activity_workout_home);

        this.isWorkoutRunning = false;
        File file = new File(getFilesDir().getAbsolutePath() + "/Face Yoga Videos");
        this.workoutVideoDirectory = file;
        if (!file.exists()) {
            this.workoutVideoDirectory.mkdirs();
        }
        this.workoutNames = getIntent().getStringArrayExtra("PremiumWorkoutName");
        this.workoutReps = getIntent().getIntArrayExtra("PremiumWorkoutReps");
        this.workoutImages = getIntent().getIntArrayExtra("PremiumWorkoutImage");
        this.selectedWorkoutDay = getIntent().getIntExtra("FullFaceYogaDay", -1);

        this.totalWorkoutCount = this.workoutNames.length;
        this.progressTime = 0.0d;
        this.nextExerciseImageView = (AppCompatImageView) findViewById(R.id.nextExerciseImageView);
        this.ivVolume = (AppCompatImageView) findViewById(R.id.volumeImageViewPremium);

        this.continueButton = (AppCompatButton) findViewById(R.id.continueButtonAfterWorkoutFinished);
        this.nextExerciseTitle = (TextView) findViewById(R.id.nextExerciseNameTextView);
        this.nextExerciseReps = (TextView) findViewById(R.id.nextExerciseRepsTextView);
        this.congratulationsTextView = (AppCompatTextView) findViewById(R.id.congratulationsTextView);
        this.countdownTextView = (TextView) findViewById(R.id.counterTextView);
        this.restProgressBar = (ProgressBar) findViewById(R.id.takeARestProgressBar);
        this.workoutVideoView = (VideoView) findViewById(R.id.videoView);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarLinear);
        this.workoutProgressBar = progressBar;
        progressBar.setMax(this.workoutNames.length);
        this.videoLoadingIndicator = (CircularProgressIndicator) findViewById(R.id.videoLoadingProgressBar);
        this.workoutNameTextView = (AppCompatTextView) findViewById(R.id.exerciseNameTV);
        this.workoutTimerTextView = (AppCompatTextView) findViewById(R.id.workoutSecondTextView);
        this.playPauseButton = (ImageView) findViewById(R.id.centerButton);
        this.nextWorkoutButton = (ImageView) findViewById(R.id.nextVideoButton);
        this.previousWorkoutButton = (ImageView) findViewById(R.id.previousVideoButton);
        this.nextWorkoutButton.setImageResource(R.drawable.next_navigation_icon);
        this.previousWorkoutButton.setImageResource(R.drawable.previous);
        this.playPauseButton.setImageResource(R.drawable.pause_icon);
        this.workoutContainer = (RelativeLayout) findViewById(R.id.workoutLinearLayout);
        this.restWorkoutLayout = (RelativeLayout) findViewById(R.id.restWorkoutRelativeLayout);
        this.workoutCompleteLayout = (RelativeLayout) findViewById(R.id.doneExerciseRelativeLayout);
        this.tvStepCounterhome =  findViewById(R.id.tvStepCounterhome);


        findViewById(R.id.iv_close).setOnClickListener(view -> {
            finish();
        });

        this.workoutCompleteLayout.setVisibility(4);
        this.workoutBundle = new Bundle();

        this.textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                String str;
                String str2;
                if (i == 0) {
                    int language = textToSpeech.setLanguage(Locale.UK);

                    String str3 = workoutNames[0];
                    int i10 = workoutReps[0];
                    textToSpeech.speak("First Exercise is...", 0, workoutBundle, (String) null);
                    if (textToSpeech == null) {
                        textToSpeech = new TextToSpeech(HomeWorkoutActivity.this, new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if (status == TextToSpeech.SUCCESS) {
                                    // Set the language if needed
                                    int langResult = textToSpeech.setLanguage(Locale.US);
                                    if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        Log.e("TextToSpeech", "Language not supported or missing data");
                                    }
                                } else {
                                    Log.e("TextToSpeech", "Initialization failed");
                                }
                            }
                        });
                    }

                    String textToSpeak = str3 + ".";
                    textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null);


                    TextToSpeech textToSpeech1 = textToSpeech;
                    textToSpeech1.speak(String.valueOf(i10) + " Seconds.", 1, workoutBundle, (String) null);
                    if (language == -2 || language == -1) {
                        str = subscriptionStatus;
                        str2 = "onInit: Language NOT Supported!";
                    } else {
                        return;
                    }
                } else {
                    str = subscriptionStatus;
                    str2 = "onInit: Text To Speech Initialization Failed!";
                }
                Log.e(str, str2);
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), 0);
        this.sharedPreferences = sharedPreferences;
        boolean z9 = sharedPreferences.getBoolean("isVolumeOn", true);
        this.isWorkoutPaused = z9;
        if (z9) {
            this.ivVolume.setImageResource(R.drawable.volume_up);
            bundle2 = this.workoutBundle;
            f9 = 1.0f;
        } else {
            this.ivVolume.setImageResource(R.drawable.volume_off);
            bundle2 = this.workoutBundle;
            f9 = 0.0f;
        }
        bundle2.putFloat("volume", f9);
        proceedToNextWorkout();
    }


    public class ResetCountdownTimer extends CountdownTimer {
        public ResetCountdownTimer(long j9) {
            super(j9);
        }

        @Override
        public void onTick(long remainingTime) {
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            long minutes = timeUnit.toMinutes(remainingTime);
            String valueOf = String.valueOf(timeUnit.toSeconds(remainingTime) % 60);
            String valueOf2 = String.valueOf(minutes);

            HomeWorkoutActivity.this.workoutTimerTextView.setText(valueOf2 + ":" + valueOf);

            HomeWorkoutActivity.this.videoLoadingIndicator.setProgress(((int) remainingTime) / 1000);
            if (remainingTime / 1000 == 0) {
                HomeWorkoutActivity HomeWorkoutActivity = HomeWorkoutActivity.this;
                if (HomeWorkoutActivity.isWorkoutRunning) {
                    HomeWorkoutActivity.startWorkoutTimer();
                } else {
                    HomeWorkoutActivity.startRestTimer();
                }
            }
        }

        @Override
        public void onFinish() {
            Log.d(HomeWorkoutActivity.this.subscriptionStatus, "onTimerFinish: workoutSecondsCountDownTimer Stopped");
        }
    }

    public class WorkoutCountdownTimer extends CountdownTimer {
        public WorkoutCountdownTimer(long j9) {
            super(j9);
        }

        @Override
        public void onTick(long remainingTime) {
            int i9 = ((int) remainingTime) / 1000;
            HomeWorkoutActivity.this.restProgressBar.setProgress(i9);
            HomeWorkoutActivity HomeWorkoutActivity = HomeWorkoutActivity.this;
            HomeWorkoutActivity.remainingWorkoutTime = remainingTime;
            if (remainingTime / 1000 != 0) {
                HomeWorkoutActivity.countdownTextView.setTextSize(35.0f);
                HomeWorkoutActivity.this.countdownTextView.setText(Integer.toString(i9));
                Objects.requireNonNull(HomeWorkoutActivity.this);
                return;
            }
            HomeWorkoutActivity.countdownTextView.setTextSize(30.0f);
            HomeWorkoutActivity.this.countdownTextView.setText("GO!");
            Objects.requireNonNull(HomeWorkoutActivity.this);
            HomeWorkoutActivity homeWorkoutActivity2 = HomeWorkoutActivity.this;
            homeWorkoutActivity2.progressTime += 0.5d;
            homeWorkoutActivity2.proceedToNextWorkout();
        }

        @Override
        public void onFinish() {

        }
    }


    public final void proceedToNextWorkout() {
        Log.d("WorkoutPremium", "doExercise: ");
        this.playPauseButton.setImageResource(R.drawable.pause_icon);
        getWindow().setStatusBarColor(getResources().getColor(R.color.videoStatusBarColor));
        if (this.progressTime <= ((double) this.totalWorkoutCount)) {
            this.workoutContainer.setVisibility(View.VISIBLE);
            this.restWorkoutLayout.setVisibility(View.INVISIBLE);

            if (this.progressTime == 0.0d) {
                this.previousWorkoutButton.setVisibility(View.INVISIBLE);
            } else {
                this.previousWorkoutButton.setVisibility(View.VISIBLE);
            }

            double d10 = this.progressTime;
            if (d10 != ((double) (this.totalWorkoutCount - 1))) {
                this.isWorkoutRunning = false;
            } else {
                this.isWorkoutRunning = true;
            }

            String str = this.workoutNames[(int) d10];
            this.workoutVideoView.setVisibility(View.VISIBLE);
            this.workoutVideoView.setZOrderOnTop(true);
            this.workoutVideoView.setVideoPath(this.workoutVideoDirectory.getAbsolutePath() + "/" + str + ".m4v");
            this.workoutVideoView.start();

            int i9 = this.workoutReps[(int) this.progressTime];

            if (this.textToSpeech == null) {
                this.textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int langResult = textToSpeech.setLanguage(Locale.US);
                            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Log.e("TextToSpeech", "Language not supported or missing data");
                            }
                        } else {
                            Log.e("TextToSpeech", "Initialization failed");
                        }
                    }
                });
            }

            String textToSpeak = str + ".";
            this.textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, this.workoutBundle, null);
            this.textToSpeech.speak(String.valueOf(i9) + " Seconds", 1, this.workoutBundle, (String) null);

            this.workoutNameTextView.setText(str);

            long j9 = (long) (i9 * 1000);
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            long minutes = timeUnit.toMinutes(j9);
            this.workoutTimerTextView.setText(String.valueOf(minutes) + ":" + String.valueOf(timeUnit.toSeconds(j9) % 60));

            this.workoutProgressBar.setProgress((int) this.progressTime);
            this.videoLoadingIndicator.setMax(i9);

            // Update tvStepCounter to show current step
            tvStepCounterhome.setText((int) progressTime + 1 + " / " + totalWorkoutCount);

            ResetCountdownTimer resetCountdownTimer = new ResetCountdownTimer(j9);
            this.restCountdownTimer = resetCountdownTimer;
            resetCountdownTimer.start();

            this.workoutVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0.0f, 0.0f);
                    HomeWorkoutActivity.this.mediaPlayer = mediaPlayer;
                }
            });

            this.workoutVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    HomeWorkoutActivity.this.workoutVideoView.start();
                }
            });
            this.progressTime += 0.5d;
        }
    }


    public final void startWorkoutTimer() {
        if (this.isWorkoutRunning) {
            stopRestTimer();
            this.workoutCompleteLayout.setVisibility(View.VISIBLE);
            this.restWorkoutLayout.setVisibility(View.INVISIBLE);
            this.workoutContainer.setVisibility(View.INVISIBLE);
            this.workoutVideoView.setVisibility(View.INVISIBLE);
            this.ivVolume.setVisibility(View.INVISIBLE);
            AppCompatTextView appCompatTextView = this.congratulationsTextView;
            StringBuilder stringBuilder = new StringBuilder("You have done ");
            stringBuilder.append(Integer.toString(this.totalWorkoutCount));
            stringBuilder.append(" exercises today");
            appCompatTextView.setText(stringBuilder.toString());
            new WorkoutActivity().workoutReps(this);
            KonfettiView konfettiView = (KonfettiView) findViewById(R.id.viewKonfetti);
            this.continueButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_anim));
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
            SharedPreferences sharedPreferences = getSharedPreferences("Challenge Workout Day Of Premium", 0);
            int i11 = this.selectedWorkoutDay;
            if (i11 != -1) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                (i11 < 30 ? edit.putInt("WorkoutChallengeName", this.selectedWorkoutDay + 1) : edit.putInt("WorkoutChallengeName", 1)).apply();
                return;
            }
            return;
        }
        Log.d("TAG", "nextVideo: ");
        double d10 = this.progressTime;
        if (d10 == Math.floor(d10)) {
            proceedToNextWorkout();
            return;
        }
        stopRestTimer();
        startRestTimer();
    }

    public final void stopRestTimer() {
        try {
            if (restCountdownTimer.isRunning) {
                restCountdownTimer.stop();
            }
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        try {

            if (workoutCountdownTimer.isRunning) {
                workoutCountdownTimer.stop();
            }
        } catch (Exception e11) {
            e11.printStackTrace();
        }
    }

    public final void startRestTimer() {
        Log.d("WorkoutPremium", "takeARest: ");
        this.textToSpeech.speak("Take a rest.", 0, this.workoutBundle, (String) null);
        this.isWorkoutRunning = false;
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        this.workoutVideoView.pause();
        stopRestTimer();
        this.restWorkoutLayout.setVisibility(View.VISIBLE);
        this.workoutContainer.setVisibility(View.INVISIBLE);
        this.workoutCompleteLayout.setVisibility(View.INVISIBLE);
        this.workoutVideoView.setVisibility(View.INVISIBLE);
        this.restProgressBar.setMax(20);
        this.countdownTextView.setText("");
        workoutCountdownTimer = new WorkoutCountdownTimer((long) 20000);
        workoutCountdownTimer.start();
        this.nextExerciseTitle.setText(this.workoutNames[(int) (this.progressTime + 0.5d)]);
        Glide.with(this).load(Integer.valueOf(this.workoutImages[(int) (this.progressTime + 0.5d)])).into(this.nextExerciseImageView);
        TextView textView = this.nextExerciseReps;
        textView.setText(Integer.toString(this.workoutReps[(int) (this.progressTime + 0.5d)]) + " Seconds");
    }

    public void centerButton(View view) {
        if (this.workoutVideoView.isPlaying()) {
            this.playPauseButton.setImageResource(R.drawable.play_icon);
            this.restCountdownTimer.pause();
            this.mediaPlayer.pause();
            return;
        }
        this.playPauseButton.setImageResource(R.drawable.pause_icon);
        this.restCountdownTimer.resume();
        this.mediaPlayer.start();
    }

    public void continueButtonAfterWorkoutFinished(View view) {
        try {
            HomeScheduleActivity.instance.finish();
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

    public void nextVideo(View view) {
        startWorkoutTimer();
    }


    public final void onBackPressed() {
        if (this.isCountdownRunning) {
            super.onBackPressed();
            stopRestTimer();
            this.textToSpeech.stop();
            finish();
            return;
        }
        this.isCountdownRunning = true;
        ExitTask();


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeWorkoutActivity.this.isCountdownRunning = false;
            }
        }, 2000);
    }

    public final void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;  // Optional: Set it to null to avoid using it after release
        }
        stopRestTimer();
    }

    public final void ExitTask() {
        final Dialog dialog = new Dialog(HomeWorkoutActivity.this, R.style.CustomDialog);
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
        AppCompatButton btnallow = dialog.findViewById(R.id.btnexit);

        btnallow.setOnClickListener(v -> {
            stopRestTimer();
            this.textToSpeech.stop();
            finish();
            dialog.dismiss();
        });

        btnContinue.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    public final void onPause() {
        super.onPause();
        Log.d(this.subscriptionStatus, "onPause: Called");
        this.workoutVideoView.pause();
        try {
            ResetCountdownTimer aVar = this.restCountdownTimer;
            if (aVar.isRunning) {
                aVar.pause();
            }
        } catch (Exception e10) {
            e10.printStackTrace();
        }
        try {
            WorkoutCountdownTimer dVar = this.workoutCountdownTimer;
            if (dVar.isRunning) {
                dVar.pause();
            }
        } catch (Exception e11) {
            e11.printStackTrace();
        }
        this.textToSpeech.stop();
    }

    public final void onResume() {
        super.onResume();
        Log.d(this.subscriptionStatus, "onResume: Called");
        if (this.mediaPlayer != null) {
            this.workoutVideoView.start();
            try {
                if (this.restCountdownTimer.isPaused()) {
                    this.restCountdownTimer.resume();
                }
            } catch (Exception e10) {
                e10.printStackTrace();
            }
            try {
                if (this.workoutCountdownTimer.isPaused()) {
                    this.workoutCountdownTimer.resume();
                }
            } catch (Exception e11) {
                e11.printStackTrace();
            }
        }
    }

    public void plusTwentySecondsButton(View view) {
        Log.d("TAG", "plusTwentySecondsButton: Adding 15 seconds");

        // Stop the existing timer
        if (this.workoutCountdownTimer != null) {
            this.workoutCountdownTimer.stop();
        }

        // Increase remaining time by 15 seconds
        this.remainingWorkoutTime += 15000;

        // Reset progress bar values
        this.restProgressBar.setMax((int) (this.remainingWorkoutTime / 1000));
        this.restProgressBar.setProgress((int) (this.remainingWorkoutTime / 1000));

        // Restart the timer with updated time
        this.workoutCountdownTimer.setDuration(this.remainingWorkoutTime);
        this.workoutCountdownTimer.start();
    }

    public void previousVideo(View view) {
        Log.d("TAG", "previousVideo: ");
        this.progressTime -= 2.0d;
        startRestTimer();
    }

    public void setVolumePremium(View view) {
        boolean z9;
        if (this.isWorkoutPaused) {
            this.ivVolume.setImageResource(R.drawable.volume_off);
            this.workoutBundle.putFloat("volume", 0.0f);
            this.textToSpeech.stop();
            z9 = false;
        } else {
            this.ivVolume.setImageResource(R.drawable.volume_up);
            this.workoutBundle.putFloat("volume", 1.0f);
            z9 = true;
        }
        this.isWorkoutPaused = z9;
        this.sharedPreferences.edit().putBoolean("isVolumeOn", this.isWorkoutPaused).apply();
    }

    public void skipNextAfterRestButton(View view) {
        Log.d("TAG", "skipNextAfterRestButton: ");
        this.workoutCountdownTimer.stop();
        this.progressTime += 0.5d;
        proceedToNextWorkout();
    }

}
