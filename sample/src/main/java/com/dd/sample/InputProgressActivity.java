package com.dd.sample;

import com.dd.processbutton.iml.InputProgressView;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;


public class InputProgressActivity extends Activity implements View.OnClickListener, TextWatcher {

    private String[] mQuestions = {
            "What's your email",
            "Enter your password",
            "Confirm password",
    };

    private int mStepProgress;
    private int mCurrentStep;
    private int mTotalStep;
    private InputProgressView mInputProgressView;
    private TextView mTxtTitle;
    private TextView mTxtStep;
    private TextView mTxtRequired;

    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, InputProgressActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_input_progress);

        mCurrentStep = 0;
        mTotalStep = mQuestions.length;

        initView();
        showNextStep();
    }

    private void initView() {
        setTitle("Sign Up");

        mTxtRequired = (TextView) findViewById(R.id.txtRequired);
        mTxtTitle = (TextView) findViewById(R.id.txtTitle);
        mTxtStep = (TextView) findViewById(R.id.txtStep);
        mInputProgressView = (InputProgressView) findViewById(R.id.processEditText);
        mInputProgressView.addTextChangedListener(this);
        mStepProgress = mInputProgressView.getMaxProgress() / mTotalStep;

        mTxtRequired.setVisibility(View.GONE);

        findViewById(R.id.imgNext).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgNext:
                onNextClicked();
                break;
        }
    }

    private void onNextClicked() {
        if(mInputProgressView.getText().toString().isEmpty()) {
            mTxtRequired.setVisibility(View.VISIBLE);
        } else {
            if(mCurrentStep == mTotalStep) {
                onComplete();
            } else {
                showNextStep();
            }
        }
    }

    private void showNextStep() {
        final String questions = mQuestions[mCurrentStep];
        // mInputProgressView.getText(); we need to save text in real app
        mInputProgressView.setProgress(mCurrentStep * mStepProgress, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mInputProgressView.setText(null);

                mTxtStep.setText(String.format("%d / %d", mCurrentStep + 1, mTotalStep));
                mTxtTitle.setText(questions);
                mCurrentStep++;

                // last two questions are about password
                if(mCurrentStep > 1) {
                    mInputProgressView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void onComplete() {
        mInputProgressView.setProgress(mInputProgressView.getMaxProgress(), new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showRegisteredScreen();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void showRegisteredScreen() {
        View formView = findViewById(R.id.formView);

        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setFillAfter(true);
        fadeOut.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeOut.setDuration(300);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                final TextView txtRegistered = (TextView) findViewById(R.id.txtRegistered);

                AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setFillAfter(true);
                fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
                fadeIn.setDuration(300);
                fadeIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        txtRegistered.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                txtRegistered.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        formView.startAnimation(fadeOut);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mTxtRequired.setVisibility(View.GONE);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
