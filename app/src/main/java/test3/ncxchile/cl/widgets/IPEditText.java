package test3.ncxchile.cl.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.validators.RutValidator;

/**
 * Created by Diego on 20-01-2015.
 */
public class IPEditText extends EditText {

    //Context context;
    private Paint pincel;
    private Drawable successIcon;
    private String text="";

    public IPEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        pincel = new Paint();
        pincel.setColor(Color.BLACK);
        pincel.setTextAlign(Paint.Align.LEFT);
        //pincel.setTextSize(28);
        init(context);
    }

    public void init(final Context context) {
        successIcon = getResources().getDrawable(R.drawable.green_circle_check);
        Drawable errorIcon = getResources().getDrawable(R.drawable.red_circle_exclamation);

        successIcon.setBounds(0, 0, (int) getTextSize() + 2, (int) getTextSize() + 2);

        View.OnFocusChangeListener fieldValidatorText = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String s = getText().toString();
                if (!b) {
                    setText(text);
                } else {
                   text=getText().toString();
                   setText("");
                }
            }
        };

        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {

            String cadenaValida = "";
            boolean error = false;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("getTag().toString()="+getTag().toString());
                if(s.length()==0)
                    return;
                if(s.length()!=0)
                    text=s.toString();
                if(Integer.parseInt(getTag().toString())==5){
                    if(s.length()==5){
                        if(Integer.parseInt(s.toString())>65535)
                            setText("65535");
                    }
                }
                else{
                    if(s.length()==3){
                        switch (Integer.parseInt(getTag().toString())){
                            case 1:
                                if(Integer.parseInt(s.toString())>223)
                                    setText("223");
                                if(Integer.parseInt(s.toString())<1)
                                    setText("1");
                                break;
                            default:
                                if(Integer.parseInt(s.toString())>223)
                                    setText("255");
                                break;
                        }
                    }
                    if(focusSearch(FOCUS_RIGHT)!=null)
                        focusSearch(FOCUS_RIGHT).requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

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
