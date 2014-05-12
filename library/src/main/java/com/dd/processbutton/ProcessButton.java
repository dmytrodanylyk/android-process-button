package com.dd.processbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

public abstract class ProcessButton extends FlatButton {

    private int mProgress;
    private int mMaxProgress;
    private int mMinProgress;

    private boolean mIsLoadingComplete;

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

        mProgressDrawable =
                (GradientDrawable) getGradientDrawable(R.drawable.rect_progress).mutate();
        mCompleteDrawable =
                (GradientDrawable) getGradientDrawable(R.drawable.rect_complete).mutate();

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

            if (attr.hasValue(R.styleable.ProcessButton_colorProgress)) {
                mProgressDrawable.setColor(getColor(attr, R.styleable.ProcessButton_colorProgress));
            }

            if (attr.hasValue(R.styleable.ProcessButton_colorComplete)) {
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
            mIsLoadingComplete = false;
        } else if (progress >= mMaxProgress) {
            mProgress = mMaxProgress;
            mIsLoadingComplete = true;
        } else {
            mProgress = progress;
            mIsLoadingComplete = false;
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
        return mIsLoadingComplete;
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

    /**
     * A {@link android.os.Parcelable} representing the {@link com.dd.processbutton.ProcessButton}'s
     * state.
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
