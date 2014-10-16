package test3.ncxchile.cl.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.login.Validator;

/**
 * Created by android-developer on 15-10-2014.
 */
public class RequiredEditText extends EditText {

    //Context context;
    private Paint pincel;
    private Drawable successIcon;

    public RequiredEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        pincel = new Paint();
        pincel.setColor(Color.BLACK);
        pincel.setTextAlign(Paint.Align.LEFT);
        //pincel.setTextSize(28);
        init(context);
    }

    public void init(final Context context){
        successIcon = getResources().getDrawable(R.drawable.green_circle_check);
        successIcon.setBounds(new Rect(0, 0, 20, 20));

        View.OnFocusChangeListener fieldValidatorText = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String s= getText().toString();
                if(!b)
                {
                    if(s.equals(""))
                        setError(context.getString(R.string.error_field_required));
                }
            }
        };

        setOnFocusChangeListener(fieldValidatorText);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Rect rect = new Rect();

        //int lineaBase = getLineBounds(0, rect);
        //canvas.drawLine(rect.left, lineaBase + 2, rect.right, lineaBase + 2, pincel);
        //canvas.drawText("" + (1), getWidth() - 2, lineaBase, pincel);

    }
}
