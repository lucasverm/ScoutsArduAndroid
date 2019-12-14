package be.ardu.scoutsardu.fontAwesome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class FontAwesomeTextViewSolid extends TextView {


    public FontAwesomeTextViewSolid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontAwesomeTextViewSolid(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontAwesomeTextViewSolid(Context context) {
        super(context);
        init();
    }

    private void init() {

        //Font name should not contain "/".
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fa-solid-900.ttf");
        setTypeface(tf);
    }

}

