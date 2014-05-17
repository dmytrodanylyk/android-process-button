package com.dd.processbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

public class FlatButton extends Button {

    public FlatButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public FlatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FlatButton(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        StateListDrawable states = new StateListDrawable();
        if (attrs != null) {
            initAttributes(context, attrs, states);
        }
        setBackgroundCompat(states);
    }

    /**
     * Set the View's background. Masks the API changes made in Jelly Bean.
     * 
     * @param bg
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundCompat(Drawable bg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(bg);
        } else {
            setBackgroundDrawable(bg);
        }
    }

    private void initAttributes(Context context, AttributeSet attributeSet,
                                StateListDrawable states) {
        TypedArray attr = getTypedArray(context, attributeSet,
                R.styleable.FlatButton);
        if (attr == null) {
            return;
        }

        try {
            GradientDrawable drawablePressed = (GradientDrawable) getGradientDrawable(
                    R.drawable.rect_pressed).mutate();

            if (attr.hasValue(R.styleable.FlatButton_colorPressed)) {
                drawablePressed.setColor(getColor(attr,
                        R.styleable.FlatButton_colorPressed));
            }

            LayerDrawable drawableNormal = (LayerDrawable) getDrawable(
                    R.drawable.rect_normal).mutate();

            GradientDrawable drawableTop = (GradientDrawable) drawableNormal.getDrawable(
                    0).mutate();
            GradientDrawable drawableBottom = (GradientDrawable) drawableNormal.getDrawable(
                    1).mutate();

            if (attr.hasValue(R.styleable.FlatButton_colorNormal)) {
                drawableBottom.setColor(getColor(attr,
                        R.styleable.FlatButton_colorNormal));
            }

            if (attr.hasValue(R.styleable.FlatButton_colorPressed)) {
                drawableTop.setColor(getColor(attr,
                        R.styleable.FlatButton_colorPressed));
            }

            states.addState(new int[] { android.R.attr.state_pressed },
                    drawablePressed);
            states.addState(new int[] {}, drawableNormal);

        } finally {
            attr.recycle();
        }
    }

    protected GradientDrawable getGradientDrawable(int id) {
        return (GradientDrawable) getDrawable(id);
    }

    protected Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    protected int getColor(TypedArray attr, int index) {
        return attr.getColor(index, 0);
    }

    protected TypedArray getTypedArray(Context context,
                                       AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }
}
