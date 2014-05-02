package com.dd.processbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

public class ProcessButton extends Button {

    private int mProgress;
    private int mMaxProgress;
    private int mMinProgress;

    private Drawable mProgressDrawable;
    private Drawable mCompleteDrawable;

    private CharSequence mLoadingText;
    private CharSequence mCompleteText;

    private ProgressStyle mProgressStyle;

    private boolean isLoadingComplete;

    // TODO save instance state

    public enum ProgressStyle {
        Submit, Action, Generate;
    }

    public ProcessButton(Context context) {
        super(context);
        init(context, null);
    }

    public ProcessButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProcessButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mMinProgress = 0;
        mMaxProgress = 100;
        mProgressStyle = ProgressStyle.Submit;

        if (attrs != null) {
            initAttributes(context, attrs);
        }
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray attributes =
                context.obtainStyledAttributes(attrs, R.styleable.ProcessButton, 0, 0);
        try {
            mLoadingText = attributes.getString(R.styleable.ProcessButton_progressText);
            mCompleteText = attributes.getString(R.styleable.ProcessButton_completeText);

            mProgressDrawable = attributes.getDrawable(R.styleable.ProcessButton_progressDrawable);
            mCompleteDrawable = attributes.getDrawable(R.styleable.ProcessButton_completeDrawable);

            int index = attributes.getInteger(R.styleable.ProcessButton_progressStyle, 0);
            mProgressStyle = ProgressStyle.values()[index];
        } finally {
            attributes.recycle();
        }
    }

    public void setProgressDrawable(Drawable progressDrawable) {
        mProgressDrawable = progressDrawable;
    }

    public void setCompleteDrawable(Drawable completeDrawable) {
        mCompleteDrawable = completeDrawable;
    }

    public void setCompleteText(String loadingText) {
        mCompleteText = loadingText;
    }

    public void setLoadingText(String loadingText) {
        mLoadingText = loadingText;
    }

    public void setProgress(int progress) {
        setText(mLoadingText);

        if (progress < mMinProgress) {
            mProgress = mMinProgress;
            isLoadingComplete = false;
        } else if (progress > mMaxProgress) {
            mProgress = mMaxProgress;
            isLoadingComplete = true;
        } else {
            mProgress = progress;
            isLoadingComplete = false;
        }

        invalidate();
    }

    private void onLoadingComplete() {
        if (mCompleteText != null) {
            setText(mCompleteText);
        }

        if (mCompleteDrawable != null) {
            setBackgroundDrawable(mCompleteDrawable);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isLoadingComplete) {
            onLoadingComplete();
        } else {
            switch (mProgressStyle) {
                case Action:
                    drawActionButton(canvas);
                    break;
                case Submit:
                    drawSubmitButton(canvas);
                    break;
                case Generate:
                    drawGenerateButton(canvas);
                    break;
            }
        }

        super.onDraw(canvas);
    }

    private void drawGenerateButton(Canvas canvas) {
        float scale = (float) mProgress / (float) mMaxProgress;
        float indicatorHeight = (float) getMeasuredHeight() * scale;

        mProgressDrawable.setBounds(0, 0, getMeasuredWidth(), (int) indicatorHeight);
        mProgressDrawable.draw(canvas);
    }

    private void drawActionButton(Canvas canvas) {
        float scale = (float) mProgress / (float) mMaxProgress;
        float indicatorWidth = (float) getMeasuredWidth() * scale;

        double indicatorHeightPercent = 0.05; // 5%
        int bottom = (int) (getMeasuredHeight() - getMeasuredHeight() * indicatorHeightPercent);
        mProgressDrawable.setBounds(0, bottom, (int) indicatorWidth, getMeasuredHeight());
        mProgressDrawable.draw(canvas);
    }

    private void drawSubmitButton(Canvas canvas) {
        float scale = (float) mProgress / (float) mMaxProgress;
        float indicatorWidth = (float) getMeasuredWidth() * scale;

        mProgressDrawable.setBounds(0, 0, (int) indicatorWidth, getMeasuredHeight());
        mProgressDrawable.draw(canvas);
    }

}
