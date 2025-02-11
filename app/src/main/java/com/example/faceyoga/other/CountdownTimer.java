package com.example.faceyoga.other;

public abstract class CountdownTimer implements CountdownCallback {
    public boolean isRunning;
    protected boolean isPaused;
    protected long totalDuration;
    protected long elapsedTime;
    protected long interval;
    protected CountdownHandler handler;

    public CountdownTimer(long duration) {
        setDuration(duration);
        if (!this.isRunning) {
            this.interval = 1000; // Default interval: 1 second
        }
        this.handler = new CountdownHandler(this);
    }

    public final synchronized boolean isPaused() {
        return this.isPaused;
    }

    public final synchronized void pause() {
        synchronized (this) {
            this.isPaused = true;
        }
    }
    public final synchronized void resume() {
        synchronized (this) {
            this.isPaused = false;
            handler.sendMessage(handler.obtainMessage(1));
        }
    }

    public final void setDuration(long duration) {
        if (!this.isRunning) {
            if (this.totalDuration <= 0 && duration < 0) {
                duration *= -1;
            }
            this.totalDuration = duration;
        }
    }
    public final void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.isPaused = false;
            this.elapsedTime = 0;
            handler.sendMessage(handler.obtainMessage(1));
        }
    }
    public final void stop() {
        this.isRunning = false;
        handler.removeMessages(1);
        onFinish();
    }
}
