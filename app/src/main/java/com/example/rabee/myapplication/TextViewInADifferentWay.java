package com.example.rabee.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;

public class TextViewInADifferentWay {

    private final String TAG = TextViewInADifferentWay.class.getSimpleName();
    private ConstraintLayout cl;
    private TextView tv_fixed = null;
    private TextView tv_variable = null;
    private TextView tv_variable_plus = null;
    private String text = "";

    public TextViewInADifferentWay(@NonNull Context context,
                                   @NonNull View layout) {
        if (layout instanceof ConstraintLayout) {
            this.cl = (ConstraintLayout) layout;
            if (this.cl.getChildCount() == 2) {
                View child1 = this.cl.getChildAt(0);
                View child2 = this.cl.getChildAt(1);
                if (child1 instanceof TextView
                        && child2 instanceof TextView) {
                    this.tv_fixed = (TextView) child1;
                    this.tv_variable = (TextView) child2;
                    this.text = this.tv_variable.getText().toString();
                }
            } else {
                throw new RuntimeException("Must have two children!!");
            }
        }
        if (tv_fixed != null && tv_variable != null) {

            /**
             * Add a barrier in the bottom of tv_fixed and tv_variable in order to always write to
             * the bottom of the view with the largest height
             */
            ConstraintLayout.LayoutParams params_ = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            Barrier barrier = new Barrier(context);
            int bId = ViewCompat.generateViewId();
            barrier.setId(bId);
            barrier.setType(Barrier.BOTTOM);
            barrier.setReferencedIds(new int[]{tv_fixed.getId(),
                    tv_variable.getId()});
            barrier.setLayoutParams(params_);

            this.cl.addView(barrier);

            ConstraintSet constraintSet_ = new ConstraintSet();
            constraintSet_.clone(this.cl);
            constraintSet_.connect(bId, ConstraintSet.START, this.cl.getId(), ConstraintSet.START);
            constraintSet_.connect(bId, ConstraintSet.END, this.cl.getId(), ConstraintSet.END);
            constraintSet_.applyTo(this.cl);

            /**
             * Add a TextView (tv_variable_plus)
             */
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            tv_variable_plus = new TextView(context);
            int tId = ViewCompat.generateViewId();
            tv_variable_plus.setId(tId);

            //TODO: Clone as much attribute from base textView (tv_variable)
            tv_variable_plus.setTextSize(convertPixelsToDp(tv_variable.getTextSize(),
                    context));
            tv_variable_plus.setTextColor(tv_variable.getCurrentTextColor());
            tv_variable_plus.setTypeface(null, Typeface.BOLD);
            tv_variable_plus.setLayoutParams(params);
            this.cl.addView(tv_variable_plus);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this.cl);
            constraintSet.connect(tv_variable_plus.getId(), ConstraintSet.TOP, bId, ConstraintSet.BOTTOM);
            constraintSet.connect(tv_variable_plus.getId(), ConstraintSet.START, this.cl.getId(), ConstraintSet.START);
            constraintSet.connect(tv_variable_plus.getId(), ConstraintSet.END, this.cl.getId(), ConstraintSet.END);
            constraintSet.applyTo(this.cl);

            //set Text to default text (xml based) in order to re-calculate
            setText(tv_fixed.getText().toString(), text);

        } else {
            throw new RuntimeException("Both TextViews must be non null!!");
        }
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    private static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void calculate() {

        int cTotalCharWidth = 0;
        int cLineCounter = 1;
        int index = 0;
        for (int i = 0; i < text.length(); i++) {
            int cLineHeight = tv_variable.getLineHeight();
            int tLineHeight = tv_fixed.getLineHeight();
            int tAllLineHeight = tv_fixed.getLineCount() * tLineHeight;
            String c = String.valueOf(text.charAt(i));
            float cCharWidth = getStringWidth(tv_variable, c);
            //Log.i(TAG, c + "-- w: " + cCharWidth);
            cTotalCharWidth += cCharWidth;
            if (cTotalCharWidth >= (tv_variable.getWidth() -
                    tv_variable.getPaddingLeft() - tv_variable.getPaddingRight())) {
                //Log.i(TAG, "New Line after " + c + " (" + i + ")");
                cTotalCharWidth = 0;
                if (((2 * cLineCounter) * cLineHeight) >= (tAllLineHeight)) {
                    index = i;
                    break;
                }
                cLineCounter += 1;
            }
        }
        if (index != 0) {
            tv_variable.setText(text.substring(0, index));
            tv_variable_plus.setText(text.substring(index));
            tv_variable_plus.setVisibility(View.VISIBLE);
        } else {
            tv_variable.setText(text);
            tv_variable_plus.setText("");
            tv_variable_plus.setVisibility(View.GONE);
        }
    }

    private float getStringWidth(TextView tv, String text) {
        Paint textPaint = tv.getPaint();
        float sWidth = textPaint.measureText(text); // in pixels
        return sWidth;
    }

    public TextView getTvFixed() {
        return this.tv_fixed;
    }

    public TextView getTvVariable() {
        return this.tv_variable;
    }

    public TextView getTvVariablePlus() {
        return this.tv_variable_plus;
    }


    public void setText(String text1, String text) {
        this.text = text;
        tv_fixed.setText(text1);
        tv_variable.setText(text);
        if (tv_variable_plus != null) {
            tv_variable_plus.setText("");
            /**
             * make sure that views are inflated before calculating
             */
            final ViewTreeObserver viewTreeObserver = this.cl.getViewTreeObserver();
            viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    cl.getViewTreeObserver().removeOnPreDrawListener(this);
                    calculate();
                    return true;
                }
            });
        }
    }

    public String getText() {
        return this.text;
    }

}
