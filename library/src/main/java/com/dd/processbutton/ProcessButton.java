package com.dd.processbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

public abstract class ProcessButton extends FlatButton {

    private int mProgress;
    private int mMaxProgress;
    private int mMinProgress;

    private GradientDrawable mProgressDrawable;
    private GradientDrawable mCompleteDrawable;
    private Drawable mErrorDrawable;
    private Drawable mNormalDrawable;

    private CharSequence mNormalText;
    private CharSequence mLoadingText;
    private CharSequence mCompleteText;
    private CharSequence mErrorText;

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

        mNormalDrawable = getBackground();
        mNormalText = getText();

        mProgressDrawable = (GradientDrawable) getGradientDrawable(
                R.drawable.rect_progress).mutate();
        mCompleteDrawable = (GradientDrawable) getGradientDrawable(
                R.drawable.rect_complete).mutate();
        mErrorDrawable = getDrawable(R.drawable.rect_error);

        if (attrs != null) {
            initAttributes(context, attrs);
        }
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet,
                R.styleable.ProcessButton);

        if (attr == null) {
            return;
        }

        try {
            mLoadingText = attr.getString(R.styleable.ProcessButton_progressText);
            mCompleteText = attr.getString(R.styleable.ProcessButton_completeText);
            mErrorText = attr.getString(R.styleable.ProcessButton_errorText);

            if (attr.hasValue(R.styleable.ProcessButton_colorProgress)) {
                mProgressDrawable.setColor(getColor(attr,
                        R.styleable.ProcessButton_colorProgress));
            }

            if (attr.hasValue(R.styleable.ProcessButton_colorComplete)) {
                mCompleteDrawable.setColor(getColor(attr,
                        R.styleable.ProcessButton_colorComplete));
            }

            if (attr.hasValue(R.styleable.ProcessButton_errorDrawable)) {
                mErrorDrawable = attr.getDrawable(R.styleable.ProcessButton_errorDrawable);
            }

        } finally {
            attr.recycle();
        }
    }

    public void resetToNormal() {
        setProgress(mMinProgress);
    }

    public void setErrorState() {
        setProgress(-1);
    }

    public void setCompletionState() {
        setProgress(mMaxProgress);
    }

    /**
     * Sets the progress.
     * 
     * @param progress
     *            The new progress value:
     *            <ul>
     *            <li>Negative value will show the error text and background
     *            color.</li>
     *            <li>0 progress will cause the button to go back to normal.</li>
     *            <li>A progress >= than the max progress value, will cause the
     *            button to show the completion text and background color.</li>
     *            <li>Any value in (0..maxProgress) will increment the progress
     *            bar accordingly.</li>
     *            </ul>
     */
    public void setProgress(int progress) {
        mProgress = progress;

        if (mProgress < mMinProgress) {
            onErrorState();
        } else if (mProgress == mMinProgress) {
            onBackToNormal();
        } else if (mProgress >= mMaxProgress) {
            mProgress = mMaxProgress;
            onComplete();
        } else {
            onProgress();
            invalidate();
        }
    }

    protected void onBackToNormal() {
        // No need for null-check in normal state.
        setText(mNormalText);
        setBackgroundCompat(mNormalDrawable);
    }

    public void onProgress() {
        setBackgroundCompat(mNormalDrawable);
        if (mLoadingText != null) {
            setText(mLoadingText);
        }
    }

    protected void onErrorState() {
        if (mErrorText != null) {
            setText(mErrorText);
        }

        if (mErrorDrawable != null) {
            setBackgroundCompat(mErrorDrawable);
        }
    }

    protected void onComplete() {
        if (mCompleteText != null) {
            setText(mCompleteText);
        }

        if (mCompleteDrawable != null) {
            setBackgroundCompat(mCompleteDrawable);
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

    public boolean isInProgress() {
        return (mProgress > 0 && mProgress < mMaxProgress);
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

    public Drawable getErrorDrawable() {
        return mErrorDrawable;
    }

    public void setErrorDrawable(Drawable errorDrawable) {
        mErrorDrawable = errorDrawable;
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

    public CharSequence getErrorText() {
        return mErrorText;
    }

    public void setErrorText(CharSequence errorText) {
        mErrorText = errorText;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.mProgress = mProgress;

        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            mProgress = savedState.mProgress;
            super.onRestoreInstanceState(savedState.getSuperState());
            setProgress(mProgress);
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    public Drawable getNormalDrawable() {
        return mNormalDrawable;
    }

    public void setNormalDrawable(Drawable normalDrawable) {
        mNormalDrawable = normalDrawable;
    }

    public CharSequence getNormalText() {
        return mNormalText;
    }

    public void setNormalText(CharSequence normalText) {
        mNormalText = normalText;
    }

    /**
     * A {@link android.os.Parcelable} representing the
     * {@link com.dd.processbutton.ProcessButton}'s state.
     */
    public static class SavedState extends BaseSavedState {

        private int mProgress;

        public SavedState(Parcelable parcel) {
            super(parcel);
        }

        private SavedState(Parcel in) {
            super(in);
            mProgress = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mProgress);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
