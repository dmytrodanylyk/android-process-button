package com.dd.processbutton.iml;

import com.dd.processbutton.ProcessButton;
import com.dd.processbutton.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/*
 *    The MIT License (MIT)
 *
 *   Copyright (c) 2014 Danylyk Dmytro
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

public class ActionProcessButton extends ProcessButton {

    private ProgressBar mProgressBar;

    private Mode mMode;

    private int mColor1;
    private int mColor2;
    private int mColor3;
    private int mColor4;

    public enum Mode {
        PROGRESS, ENDLESS;
    }

    public ActionProcessButton(Context context) {
        super(context);
        init(context);
    }

    public ActionProcessButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ActionProcessButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        Resources res = context.getResources();

        mMode = Mode.ENDLESS;

        mColor1 = res.getColor(R.color.holo_blue_bright);
        mColor2 = res.getColor(R.color.holo_green_light);
        mColor3 = res.getColor(R.color.holo_orange_light);
        mColor4 = res.getColor(R.color.holo_red_light);
    }

    public void setMode(Mode mode) {
        mMode = mode;
    }

    public void setColorScheme(int color1, int color2, int color3, int color4) {
        mColor1 = color1;
        mColor2 = color2;
        mColor3 = color3;
        mColor4 = color4;
    }

    @Override
    public void drawProgress(Canvas canvas) {
        if(getBackground() != getNormalDrawable()) {
            setBackgroundDrawable(getNormalDrawable());
        }

        switch (mMode) {
            case ENDLESS:
                drawEndlessProgress(canvas);
                break;
            case PROGRESS:
                drawLineProgress(canvas);
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mProgressBar != null) {
            setupProgressBarBounds();
        }
    }

    private void drawLineProgress(Canvas canvas) {
        float scale = (float) getProgress() / (float) getMaxProgress();
        float indicatorWidth = (float) getMeasuredWidth() * scale;

        double indicatorHeightPercent = 0.05; // 5%
        int bottom = (int) (getMeasuredHeight() - getMeasuredHeight() * indicatorHeightPercent);
        getProgressDrawable().setBounds(0, bottom, (int) indicatorWidth, getMeasuredHeight());
        getProgressDrawable().draw(canvas);
    }

    private void drawEndlessProgress(Canvas canvas) {
        if (mProgressBar == null) {
            mProgressBar = new ProgressBar(this);
            setupProgressBarBounds();
            mProgressBar.setColorScheme(mColor1, mColor2, mColor3, mColor4);
            mProgressBar.start();
        }

        if (getProgress() > 0) {
            mProgressBar.draw(canvas);
        }
    }

    private void setupProgressBarBounds() {
        double indicatorHeight = getDimension(R.dimen.layer_padding);
        int bottom = (int) (getMeasuredHeight() - indicatorHeight);
        mProgressBar.setBounds(0, bottom, getMeasuredWidth(), getMeasuredHeight());
    }

    public static class ProgressBar {

        // Default progress animation colors are grays.
        private final static int COLOR1 = 0xB3000000;
        private final static int COLOR2 = 0x80000000;
        private final static int COLOR3 = 0x4d000000;
        private final static int COLOR4 = 0x1a000000;

        // The duration of the animation cycle.
        private static final int ANIMATION_DURATION_MS = 2000;

        // The duration of the animation to clear the bar.
        private static final int FINISH_ANIMATION_DURATION_MS = 1000;

        // Interpolator for varying the speed of the animation.
        private static final Interpolator INTERPOLATOR = new AccelerateDecelerateInterpolator();

        private final Paint mPaint = new Paint();
        private final RectF mClipRect = new RectF();
        private float mTriggerPercentage;
        private long mStartTime;
        private long mFinishTime;
        private boolean mRunning;

        // Colors used when rendering the animation,
        private int mColor1;
        private int mColor2;
        private int mColor3;
        private int mColor4;
        private View mParent;

        private Rect mBounds = new Rect();

        public ProgressBar(View parent) {
            mParent = parent;
            mColor1 = COLOR1;
            mColor2 = COLOR2;
            mColor3 = COLOR3;
            mColor4 = COLOR4;
        }

        /**
         * Set the four colors used in the progress animation. The first color will
         * also be the color of the bar that grows in response to a user swipe
         * gesture.
         *
         * @param color1 Integer representation of a color.
         * @param color2 Integer representation of a color.
         * @param color3 Integer representation of a color.
         * @param color4 Integer representation of a color.
         */
        void setColorScheme(int color1, int color2, int color3, int color4) {
            mColor1 = color1;
            mColor2 = color2;
            mColor3 = color3;
            mColor4 = color4;
        }

        /**
         * Start showing the progress animation.
         */
        void start() {
            if (!mRunning) {
                mTriggerPercentage = 0;
                mStartTime = AnimationUtils.currentAnimationTimeMillis();
                mRunning = true;
                mParent.postInvalidate();
            }
        }

        void draw(Canvas canvas) {
            final int width = mBounds.width();
            final int height = mBounds.height();
            final int cx = width / 2;
            final int cy = height / 2;
            boolean drawTriggerWhileFinishing = false;
            int restoreCount = canvas.save();
            canvas.clipRect(mBounds);

            if (mRunning || (mFinishTime > 0)) {
                long now = AnimationUtils.currentAnimationTimeMillis();
                long elapsed = (now - mStartTime) % ANIMATION_DURATION_MS;
                long iterations = (now - mStartTime) / ANIMATION_DURATION_MS;
                float rawProgress = (elapsed / (ANIMATION_DURATION_MS / 100f));

                // If we're not running anymore, that means we're running through
                // the finish animation.
                if (!mRunning) {
                    // If the finish animation is done, don't draw anything, and
                    // don't repost.
                    if ((now - mFinishTime) >= FINISH_ANIMATION_DURATION_MS) {
                        mFinishTime = 0;
                        return;
                    }

                    // Otherwise, use a 0 opacity alpha layer to clear the animation
                    // from the inside out. This layer will prevent the circles from
                    // drawing within its bounds.
                    long finishElapsed = (now - mFinishTime) % FINISH_ANIMATION_DURATION_MS;
                    float finishProgress = (finishElapsed / (FINISH_ANIMATION_DURATION_MS / 100f));
                    float pct = (finishProgress / 100f);
                    // Radius of the circle is half of the screen.
                    float clearRadius = width / 2 * INTERPOLATOR.getInterpolation(pct);
                    mClipRect.set(cx - clearRadius, 0, cx + clearRadius, height);
                    canvas.saveLayerAlpha(mClipRect, 0, 0);
                    // Only draw the trigger if there is a space in the center of
                    // this refreshing view that needs to be filled in by the
                    // trigger. If the progress view is just still animating, let it
                    // continue animating.
                    drawTriggerWhileFinishing = true;
                }

                // First fill in with the last color that would have finished drawing.
                if (iterations == 0) {
                    canvas.drawColor(mColor1);
                } else {
                    if (rawProgress >= 0 && rawProgress < 25) {
                        canvas.drawColor(mColor4);
                    } else if (rawProgress >= 25 && rawProgress < 50) {
                        canvas.drawColor(mColor1);
                    } else if (rawProgress >= 50 && rawProgress < 75) {
                        canvas.drawColor(mColor2);
                    } else {
                        canvas.drawColor(mColor3);
                    }
                }

                // Then draw up to 4 overlapping concentric circles of varying radii, based on how far
                // along we are in the cycle.
                // progress 0-50 draw mColor2
                // progress 25-75 draw mColor3
                // progress 50-100 draw mColor4
                // progress 75 (wrap to 25) draw mColor1
                if ((rawProgress >= 0 && rawProgress <= 25)) {
                    float pct = (((rawProgress + 25) * 2) / 100f);
                    drawCircle(canvas, cx, cy, mColor1, pct);
                }
                if (rawProgress >= 0 && rawProgress <= 50) {
                    float pct = ((rawProgress * 2) / 100f);
                    drawCircle(canvas, cx, cy, mColor2, pct);
                }
                if (rawProgress >= 25 && rawProgress <= 75) {
                    float pct = (((rawProgress - 25) * 2) / 100f);
                    drawCircle(canvas, cx, cy, mColor3, pct);
                }
                if (rawProgress >= 50 && rawProgress <= 100) {
                    float pct = (((rawProgress - 50) * 2) / 100f);
                    drawCircle(canvas, cx, cy, mColor4, pct);
                }
                if ((rawProgress >= 75 && rawProgress <= 100)) {
                    float pct = (((rawProgress - 75) * 2) / 100f);
                    drawCircle(canvas, cx, cy, mColor1, pct);
                }
                if (mTriggerPercentage > 0 && drawTriggerWhileFinishing) {
                    // There is some portion of trigger to draw. Restore the canvas,
                    // then draw the trigger. Otherwise, the trigger does not appear
                    // until after the bar has finished animating and appears to
                    // just jump in at a larger width than expected.
                    canvas.restoreToCount(restoreCount);
                    restoreCount = canvas.save();
                    canvas.clipRect(mBounds);
                    drawTrigger(canvas, cx, cy);
                }
                // Keep running until we finish out the last cycle.
                ViewCompat.postInvalidateOnAnimation(mParent);
            } else {
                // Otherwise if we're in the middle of a trigger, draw that.
                if (mTriggerPercentage > 0 && mTriggerPercentage <= 1.0) {
                    drawTrigger(canvas, cx, cy);
                }
            }
            canvas.restoreToCount(restoreCount);
        }

        private void drawTrigger(Canvas canvas, int cx, int cy) {
            mPaint.setColor(mColor1);
            canvas.drawCircle(cx, cy, cx * mTriggerPercentage, mPaint);
        }

        /**
         * Draws a circle centered in the view.
         *
         * @param canvas the canvas to draw on
         * @param cx the center x coordinate
         * @param cy the center y coordinate
         * @param color the color to draw
         * @param pct the percentage of the view that the circle should cover
         */
        private void drawCircle(Canvas canvas, float cx, float cy, int color, float pct) {
            mPaint.setColor(color);
            canvas.save();
            canvas.translate(cx, cy);
            float radiusScale = INTERPOLATOR.getInterpolation(pct);
            canvas.scale(radiusScale, radiusScale);
            canvas.drawCircle(0, 0, cx, mPaint);
            canvas.restore();
        }

        /**
         * Set the drawing bounds of this SwipeProgressBar.
         */
        void setBounds(int left, int top, int right, int bottom) {
            mBounds.left = left;
            mBounds.top = top;
            mBounds.right = right;
            mBounds.bottom = bottom;
        }
    }

}
