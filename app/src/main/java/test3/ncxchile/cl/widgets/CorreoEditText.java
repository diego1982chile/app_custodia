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
import test3.ncxchile.cl.validators.CorreoValidator;
import test3.ncxchile.cl.validators.PatenteValidator;

/**
 * Created by android-developer on 30-10-2014.
 */
public class CorreoEditText extends EditText {

    //Context context;
    private Paint pincel;
    private Drawable successIcon;

    public CorreoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        pincel = new Paint();
        pincel.setColor(Color.BLACK);
        pincel.setTextAlign(Paint.Align.LEFT);
        //pincel.setTextSize(28);
        init(context);
    }

    public void init(final Context context){
        successIcon = getResources().getDrawable(R.drawable.green_circle_check);
        successIcon.setBounds(0, 0, (int)getTextSize()+2, (int)getTextSize()+2);

        View.OnFocusChangeListener fieldValidatorText = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String s= getText().toString();
                if(!b)
                {
                    if(s.toString().equals(""))
                        return;
                    if(!CorreoValidator.isFormatValid(s))
                        setError("El correo no es válido");
                    else
                        setError(context.getString(R.string.prompt_valid_correo), successIcon);
                }
                else
                {
                    if(s.length()==6) {
                        if (CorreoValidator.isFormatValid(s))
                            setError(context.getString(R.string.prompt_valid_correo), successIcon);
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
