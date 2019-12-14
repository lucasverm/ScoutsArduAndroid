package be.ardu.scoutsardu.fontAwesome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class FontAwesomeTextViewRegular extends TextView {


    public FontAwesomeTextViewRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontAwesomeTextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontAwesomeTextViewRegular(Context context) {
        super(context);
        init();
    }

    private void init() {

        //Font name should not contain "/".
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fa-regular-400.ttf");
        setTypeface(tf);
    }

}

