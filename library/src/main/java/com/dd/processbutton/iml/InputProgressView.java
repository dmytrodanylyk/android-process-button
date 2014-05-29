package com.dd.processbutton.iml;

import com.dd.processbutton.R;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

public class InputProgressView extends EditText {

    private int mProgress;
    private int mMaxProgress;
    private int mMinProgress;

    private GradientDrawable mProgressDrawable;
    private LayerDrawable mNormalDrawable;

    public InputProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public InputProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InputProgressView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        mMinProgress = 0;
        mMaxProgress = 100;

        mNormalDrawable = (LayerDrawable) getDrawable(R.drawable.rect_input_normal).mutate();
        mProgressDrawable = (GradientDrawable) getDrawable(R.drawable.rect_input_progress).mutate();

        if (attrs != null) {
            initAttributes(context, attrs);
        }
        setBackgroundCompat(mNormalDrawable);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.FlatButton);
        if (attr == null) {
            return;
        }

        try {

            int purple = getColor(R.color.purple_progress);
            int colorProgress = attr.getColor(R.styleable.InputProgressView_colorProgressActive, purple);
            mProgressDrawable.setColor(colorProgress);

        } finally {
            attr.recycle();
        }
    }

    @SuppressLint("NewApi")
    public void setProgress(int progress, Animator.AnimatorListener listener) {

        ValueAnimator animation = ValueAnimator.ofInt(mProgress, progress);
        animation.setDuration(800);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                mProgress = value;
                invalidate();
            }
        });
        animation.addListener(listener);
        animation.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // progress
        if (mProgress > mMinProgress && mProgress <= mMaxProgress) {
            drawLineProgress(canvas);
        }

        super.onDraw(canvas);
    }

    private void drawLineProgress(Canvas canvas) {
        float scale = (float) getProgress() / (float) getMaxProgress();
        float indicatorWidth = (float) getMeasuredWidth() * scale;

        int bottom = (int) (getMeasuredHeight() - getResources().getDimension(R.dimen.layer_padding));
        getProgressDrawable().setBounds(0, bottom, (int) indicatorWidth, getMeasuredHeight());
        getProgressDrawable().draw(canvas);
    }

    public GradientDrawable getProgressDrawable() {
        return mProgressDrawable;
    }

    public int getProgress() {
        return mProgress;
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    protected Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    protected int getColor(int id) {
        return getResources().getColor(id);
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    /**
     * Set the View's background. Masks the API changes made in Jelly Bean.
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundCompat(Drawable drawable) {
        int top = getPaddingTop();
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
        setPadding(left, top, right, bottom);
    }
}
