package test3.ncxchile.cl.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import test3.ncxchile.cl.greenDAO.Institucion;
import test3.ncxchile.cl.login.R;

/**
 * Created by android-developer on 16-10-2014.
 */
public class CustomSpinner extends Spinner{

    Context mContext;
    ArrayList<Institucion> myItems;
    private Paint pincel;
    String myResource="motivos";

    public CustomSpinner(Context context) {
        super(context);
        mContext=context;
        pincel = new Paint();
        pincel.setColor(Color.BLACK);
        pincel.setTextAlign(Paint.Align.LEFT);
        init(context);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        pincel = new Paint();
        pincel.setColor(Color.BLACK);
        pincel.setTextAlign(Paint.Align.LEFT);
        init(context);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
        pincel = new Paint();
        pincel.setColor(Color.BLACK);
        pincel.setTextAlign(Paint.Align.LEFT);
        init(context);
    }

    public void setSource(String source){
        myResource=source;
        // just to add some initial value
        myItems= populateList(mContext, myResource);
        // adapter for spinner
        ArrayAdapter myAdapter = new ArrayAdapter<Institucion>(mContext, android.R.layout.simple_spinner_item, myItems);
        setAdapter(myAdapter);
    }

    public void init(final Context context) {
     /*
     * Change to type CustomAutoCompleteView instead of AutoCompleteTextView
     * since we are extending to customize the view and disable filter
     * The same with the XML view, type will be CustomAutoCompleteView
     */
        // just to add some initial value
        //myItems= populateList(context, myResource);

        // adapter for spinner

        ArrayAdapter myAdapter = new ArrayAdapter<Institucion>(context, android.R.layout.simple_spinner_item, myItems);

        setAdapter(myAdapter);

        setOnItemSelectedListener(new OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //setBackgroundResource(android.R.drawable.arrow_down_float);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                setBackgroundResource(R.drawable.error);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Rect rect = new Rect();

        //int lineaBase = getLineBounds(0, rect);
        //canvas.drawLine(rect.left, lineaBase + 2, rect.right, lineaBase + 2, pincel);
        //canvas.drawText("" + (1), getWidth() - 2, lineaBase, pincel);

    }

    public ArrayList<Institucion> populateList(Context context,String resource)
    {
        ArrayList<Institucion> customObjects = new ArrayList<Institucion>();
        InputStream myInput=null;
        BufferedReader br = null;
        String thisLine = null;

        try {
            myInput = context.getAssets().open(resource+".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br = new BufferedReader((new InputStreamReader(myInput)));
            long id = 1;
            while ((thisLine = br.readLine()) != null) {
                customObjects.add(new Institucion(id, thisLine.toString()));
                ++id;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {                       // always close the file
            if (br != null) try {
                br.close();
            } catch (IOException ioe2) {
                // just ignore it
            }
        }
        return customObjects;
    }
}
