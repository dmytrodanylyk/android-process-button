package com.dd.processbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

public abstract class ProcessButton extends FlatButton {

    private int mProgress;
    private int mMaxProgress;
    private int mMinProgress;

    private boolean isLoadingComplete;

    private GradientDrawable mProgressDrawable;
    private GradientDrawable mCompleteDrawable;

    private CharSequence mLoadingText;
    private CharSequence mCompleteText;

    public ProcessButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public ProcessButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProcessButton(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mMinProgress = 0;
        mMaxProgress = 100;

        mProgressDrawable = getGradientDrawable(R.drawable.rect_progress);
        mCompleteDrawable = getGradientDrawable(R.drawable.rect_complete);

        if (attrs != null) {
            initAttributes(context, attrs);
        }
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.ProcessButton);

        if (attr == null) {
            return;
        }

        try {
            mLoadingText = attr.getString(R.styleable.ProcessButton_progressText);
            mCompleteText = attr.getString(R.styleable.ProcessButton_completeText);

            if(attr.hasValue(R.styleable.ProcessButton_colorProgress)) {
                mProgressDrawable.setColor(getColor(attr, R.styleable.ProcessButton_colorProgress));
            }

            if(attr.hasValue(R.styleable.ProcessButton_colorComplete)) {
                mCompleteDrawable.setColor(getColor(attr, R.styleable.ProcessButton_colorComplete));
            }

        } finally {
            attr.recycle();
        }
    }

    public void setProgress(int progress) {
        setText(getLoadingText());

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

    protected void onLoadingComplete() {
        if (getCompleteText() != null) {
            setText(getCompleteText());
        }

        if (getCompleteDrawable() != null) {
            setBackgroundDrawable(getCompleteDrawable());
        }
    }

    public int getProgress() {
        return mProgress;
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public int getMinProgress() {
        return mMinProgress;
    }

    public boolean isLoadingComplete() {
        return isLoadingComplete;
    }

    public GradientDrawable getProgressDrawable() {
        return mProgressDrawable;
    }

    public GradientDrawable getCompleteDrawable() {
        return mCompleteDrawable;
    }

    public CharSequence getLoadingText() {
        return mLoadingText;
    }

    public CharSequence getCompleteText() {
        return mCompleteText;
    }

    public void setProgressDrawable(GradientDrawable progressDrawable) {
        mProgressDrawable = progressDrawable;
    }

    public void setCompleteDrawable(GradientDrawable completeDrawable) {
        mCompleteDrawable = completeDrawable;
    }

    public void setLoadingText(CharSequence loadingText) {
        mLoadingText = loadingText;
    }

    public void setCompleteText(CharSequence completeText) {
        mCompleteText = completeText;
    }
}
