package com.dd.processbutton;

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

    private float cornerRadius;

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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(states);
        } else {
            setBackgroundDrawable(states);
        }

    }

    private void initAttributes(Context context, AttributeSet attributeSet, StateListDrawable states) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.FlatButton);
        if(attr == null) {
            return;
        }

        try {

            float defValue = getResources().getDimension(R.dimen.corner_radius);
            cornerRadius = attr.getDimension(R.styleable.FlatButton_cornerRadius, defValue);

            GradientDrawable drawablePressed =
                    (GradientDrawable) getGradientDrawable(R.drawable.rect_pressed).mutate();
            drawablePressed.setCornerRadius(getCornerRadius());

            if(attr.hasValue(R.styleable.FlatButton_colorPressed)) {
                drawablePressed.setColor(getColor(attr, R.styleable.FlatButton_colorPressed));
            }

            LayerDrawable drawableNormal =
                    (LayerDrawable) getDrawable(R.drawable.rect_normal).mutate();;

            GradientDrawable drawableTop =
                    (GradientDrawable) drawableNormal.getDrawable(0).mutate();;
            drawableTop.setCornerRadius(getCornerRadius());

            GradientDrawable drawableBottom =
                    (GradientDrawable) drawableNormal.getDrawable(1).mutate();;
            drawableBottom.setCornerRadius(getCornerRadius());

            if(attr.hasValue(R.styleable.FlatButton_colorNormal)) {
                drawableBottom.setColor(getColor(attr, R.styleable.FlatButton_colorNormal));
            }

            if(attr.hasValue(R.styleable.FlatButton_colorPressed)) {
                drawableTop.setColor(getColor(attr, R.styleable.FlatButton_colorPressed));
            }

            states.addState(new int[] {android.R.attr.state_pressed}, drawablePressed);
            states.addState(new int[] { }, drawableNormal);

        } finally {
            attr.recycle();
        }
    }

    public float getCornerRadius() {
        return cornerRadius;
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

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }
}
