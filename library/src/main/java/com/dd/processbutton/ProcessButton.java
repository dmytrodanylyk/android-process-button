package com.dd.processbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

public abstract class ProcessButton extends FlatButton {

    public enum State {
        NORMAL,
        PROGRESS,
        COMPLETE,
        ERROR;
    }

    private int mProgress;
    private int mMaxProgress;
    private int mMinProgress;

    private GradientDrawable mProgressDrawable;
    private GradientDrawable mCompleteDrawable;
    private GradientDrawable mErrorDrawable;
    private GradientDrawable mDisabledDrawable;

    private CharSequence mLoadingText;
    private CharSequence mCompleteText;
    private CharSequence mErrorText;
    private CharSequence mDisabledText;

    private State mCurrentState;

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

        mProgressDrawable = (GradientDrawable) getDrawable(R.drawable.rect_progress).mutate();
        mProgressDrawable.setCornerRadius(getCornerRadius());

        mCompleteDrawable = (GradientDrawable) getDrawable(R.drawable.rect_complete).mutate();
        mCompleteDrawable.setCornerRadius(getCornerRadius());

        mErrorDrawable = (GradientDrawable) getDrawable(R.drawable.rect_error).mutate();
        mErrorDrawable.setCornerRadius(getCornerRadius());

        mCurrentState = State.NORMAL;

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
            mLoadingText = attr.getString(R.styleable.ProcessButton_pb_textProgress);
            mCompleteText = attr.getString(R.styleable.ProcessButton_pb_textComplete);
            mErrorText = attr.getString(R.styleable.ProcessButton_pb_textError);
            mDisabledText = attr.getString(R.styleable.ProcessButton_pb_textDisabled);

            int purple = getColor(R.color.purple_progress);
            int colorProgress = attr.getColor(R.styleable.ProcessButton_pb_colorProgress, purple);
            mProgressDrawable.setColor(colorProgress);

            int green = getColor(R.color.green_complete);
            int colorComplete = attr.getColor(R.styleable.ProcessButton_pb_colorComplete, green);
            mCompleteDrawable.setColor(colorComplete);

            int red = getColor(R.color.red_error);
            int colorError = attr.getColor(R.styleable.ProcessButton_pb_colorError, red);
            mErrorDrawable.setColor(colorError);

            if (attr.hasValue(R.styleable.ProcessButton_pb_colorDisabled)) {
                mDisabledDrawable = (GradientDrawable) getDrawable(R.drawable.rect_disabled).mutate();
                mDisabledDrawable.setCornerRadius(getCornerRadius());
                mDisabledDrawable.setColor(attr.getColor(R.styleable.ProcessButton_pb_colorDisabled, getColor(R.color.grey_disabled)));
            }

        } finally {
            attr.recycle();
        }
    }

    public void setProgress(int progress) {
        mProgress = progress;

        if (mProgress == mMinProgress) {
            onNormalState();
        } else if (mProgress == mMaxProgress) {
            onCompleteState();
        } else if (mProgress < mMinProgress){
            onErrorState();
        } else {
            onProgressState();
        }

        invalidate();
    }

    protected void onErrorState() {
        if(getErrorText() != null) {
            setText(getErrorText());
        }

        setBackgroundCompat(getErrorDrawable());
        mCurrentState = State.ERROR;
    }

    protected void onProgressState() {
        if(getLoadingText() != null) {
            setText(getLoadingText());
        }
        setBackgroundCompat(getNormalDrawable());
        mCurrentState = State.PROGRESS;
    }

    protected void onCompleteState() {
        if(getCompleteText() != null) {
            setText(getCompleteText());
        }
        setBackgroundCompat(getCompleteDrawable());
        mCurrentState = State.COMPLETE;
    }

    protected void onNormalState() {
        if(getNormalText() != null) {
            setText(getNormalText());
        }
        setBackgroundCompat(getNormalDrawable());
        mCurrentState = State.NORMAL;
    }

    protected void onDisabledState() {
        if(getDisabledText() != null) {
            setText(getDisabledText());
        }
        if(getDisabledDrawable() != null) {
            setBackgroundCompat(getDisabledDrawable());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // progress
        if(mProgress > mMinProgress && mProgress < mMaxProgress) {
            drawProgress(canvas);
        }

        super.onDraw(canvas);
    }

    public abstract void drawProgress(Canvas canvas);

    public int getProgress() {
        return mProgress;
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public int getMinProgress() {
        return mMinProgress;
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

    public State getState() {
        return mCurrentState;
    }

    public CharSequence getDisabledText() {
        return mDisabledText;
    }

    public Drawable getDisabledDrawable() {
        return mDisabledDrawable;
    }

    public void setDisabledDrawable(GradientDrawable disabledDrawable) {
        mDisabledDrawable = disabledDrawable;
    }

    public void setProgressDrawable(GradientDrawable progressDrawable) {
        mProgressDrawable = progressDrawable;
    }

    public void setCompleteDrawable(GradientDrawable completeDrawable) {
        mCompleteDrawable = completeDrawable;
    }

    public void setNormalText(CharSequence normalText) {
        super.setNormalText(normalText);
        if (mProgress == mMinProgress) {
            setText(normalText);
        }
    }

    public void setLoadingText(CharSequence loadingText) {
        mLoadingText = loadingText;
    }

    public void setCompleteText(CharSequence completeText) {
        mCompleteText = completeText;
    }

    public void setDisabledText(CharSequence disabledText) {
        mDisabledText = disabledText;
    }

    public GradientDrawable getErrorDrawable() {
        return mErrorDrawable;
    }

    public void setErrorDrawable(GradientDrawable errorDrawable) {
        mErrorDrawable = errorDrawable;
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

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled != super.isEnabled()) {
            if (!enabled) {
                onDisabledState();
            } else {
                switch (getState()) {
                    case NORMAL:
                        onNormalState();
                        break;
                    case PROGRESS:
                        onProgressState();
                        break;
                    case ERROR:
                        onErrorState();
                        break;
                    case COMPLETE:
                        onCompleteState();
                        break;
                }
            }
            super.setEnabled(enabled);
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
