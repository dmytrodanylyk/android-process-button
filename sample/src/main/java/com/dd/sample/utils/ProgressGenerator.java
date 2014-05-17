package com.dd.sample.utils;

import java.util.Random;

import android.os.Handler;

import com.dd.processbutton.ProcessButton;

public class ProgressGenerator {

    private Random random = new Random();

    public interface OnCompleteListener {
        public void onComplete();
    }

    public interface OnFailureListener {
        public void onFailure();
    }

    private OnCompleteListener mCompletionListener;
    private OnFailureListener mFailureListener;
    private int mProgress;

    public ProgressGenerator(OnCompleteListener completionListener,
            OnFailureListener failureListener) {
        mCompletionListener = completionListener;
        mFailureListener = failureListener;
    }

    public void start(final ProcessButton button, final boolean canFail) {
        mProgress = 0;

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                if (canFail && (random.nextInt() % 3 == 0)) {
                    button.setErrorState();
                    if (mFailureListener != null) {
                        mFailureListener.onFailure();
                    }
                    return;
                }

                button.setProgress(mProgress);
                if (mProgress <= button.getMaxProgress()) {
                    handler.postDelayed(this, generateDelay());
                } else {
                    mCompletionListener.onComplete();
                }
            }
        });
    }

    private int generateDelay() {
        return random.nextInt(1000);
    }
}
