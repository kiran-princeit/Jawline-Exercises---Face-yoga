package com.jawline.exercises.faceyoga.other;

import android.os.Handler;
import android.os.Message;

public final class CountdownHandler extends Handler {

    private final CountdownTimer countdownTimer;

    public CountdownHandler(CountdownTimer countdownTimer) {
        this.countdownTimer = countdownTimer;
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (message.what == 1) {
            CountdownTimer timer = this.countdownTimer;
            if (!timer.isPaused) {
                long elapsedTime = timer.elapsedTime;
                long totalDuration = timer.totalDuration;
                if (elapsedTime <= totalDuration) {
                    timer.onTick(totalDuration - elapsedTime);
                    CountdownTimer timerInstance = this.countdownTimer;
                    timerInstance.elapsedTime += timerInstance.interval;
                    sendMessageDelayed(timerInstance.handler.obtainMessage(1), this.countdownTimer.interval);
                    return;
                }
                timer.stop();
            }
        }
    }
}
