
package com.sq.sohel.blooddonor.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.print.PrintAttributes;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.sq.sohel.blooddonor.R;


public final class ViewUtils {

    private ViewUtils() {
        // This class is not publicly instantiable
    }

    public static void changeIconDrawableToGray(Context context, Drawable drawable) {
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(ContextCompat.getColor(context, R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static void setImageOnTextView(Context context, TextView textView, int imageResource, float imageScale, int position) {

        try {
            Drawable drawable = ContextCompat.getDrawable(context, imageResource);
            int pixelDrawableSize = (int) Math.round(textView.getLineHeight() * imageScale); // Or the percentage you like (0.8, 0.9, etc.)
            drawable.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize); // setBounds(int left, int top, int right, int bottom), in this case, drawable is a square image
            switch (position) {
                case 1:
                    textView.setCompoundDrawables(
                            drawable, //left
                            null, //top
                            null, //right
                            null //bottom
                    );
                    break;
                case 2:
                    textView.setCompoundDrawables(
                            null, //left
                            drawable, //top
                            null, //right
                            null //bottom
                    );
                    break;
                case 3:
                    textView.setCompoundDrawables(
                            null, //left
                            null, //top
                            drawable, //right
                            null //bottom
                    );
                    break;
                case 4:
                    textView.setCompoundDrawables(
                            null, //left
                            null, //top
                            null, //right
                            drawable //bottom
                    );
                    break;
                case 0:
                    textView.setCompoundDrawables(
                            drawable, //left
                            drawable, //top
                            drawable, //right
                            drawable //bottom
                    );
                    break;
            }

        } catch (Exception e) {
        }

    }
}
