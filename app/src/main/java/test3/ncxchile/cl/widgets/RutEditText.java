package test3.ncxchile.cl.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.login.Validator;

/**
 * Created by android-developer on 13-10-2014.
 */
public class RutEditText extends EditText {

    //Context context;
    private Paint pincel;
    private Drawable successIcon;

    public RutEditText(Context context, AttributeSet attrs) {
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
                    if(!Validator.isRutValid(s))
                        setError(context.getString(R.string.error_invalid_email));
                    else
                        setError(context.getString(R.string.prompt_valid_rut), successIcon);
                }
                else
                {
                    if(s.length()==9) {
                        if (Validator.isRutValid(s))
                            setError(context.getString(R.string.prompt_valid_rut), successIcon);
                    }
                }
            }
        };

        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!Validator.isCurrentFormatValid(s))
                {
                    System.out.println("Rut actual="+s+" no es valido");
                    setError(context.getString(R.string.error_invalid_email));
                }
                if(s.length()==9) {
                    if (Validator.isRutValid(s)) {
                        setError(context.getString(R.string.prompt_valid_rut), successIcon);
                        focusSearch(FOCUS_DOWN).requestFocus();
                    }
                }
                if(s.length()==9) {
                    if (!Validator.isRutValid(s)) {
                        setError(context.getString(R.string.error_invalid_email));
                    }
                }
            }
        };
        setOnFocusChangeListener(fieldValidatorText);
        addTextChangedListener(fieldValidatorTextWatcher);
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
