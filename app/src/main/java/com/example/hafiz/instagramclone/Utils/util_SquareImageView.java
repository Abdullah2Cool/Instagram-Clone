package com.example.hafiz.instagramclone.Utils;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by hafiz on 9/22/2017.
 */

// our custom imageview for the grid
public class util_SquareImageView extends AppCompatImageView {
    public util_SquareImageView(Context context) {
        super(context);
    }

    public util_SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public util_SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // turn the image in a square
    }
}
