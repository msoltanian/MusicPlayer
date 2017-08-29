package com.example.payam1991.msmusic.view.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.payam1991.msmusic.R;


/**
 * Created by USER
 * on 8/6/2016.
 */
public class MsTextView extends AppCompatTextView {
    public MsTextView(Context context) {
        super(context);
        setTypeFace(context, null);
    }

    public MsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeFace(context, attrs);
    }

    public MsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeFace(context, attrs);
    }



    private void setTypeFace(Context mCon, AttributeSet attrs) {
//        if (attrs != null) {
//            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyTextView);
//            String fontName = a.getString(R.styleable.MyTextView_fontName);
//            if (fontName != null) {
//                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
//                this.setTypeface(myTypeface);
//            }
//            a.recycle();
//        }
    }
}
