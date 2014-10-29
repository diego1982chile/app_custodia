package test3.ncxchile.cl.widgets;

/**
 * Created by android-developer on 15-10-2014.
 */

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

import test3.ncxchile.cl.greenDAO.Institucion;
import test3.ncxchile.cl.login.R;

public class CustomAutoComplete extends AutoCompleteTextView {

    // just to add some initial value
    Institucion[] items = {new Institucion()};
    ArrayAdapter<Institucion> myAdapter;
    // adapter for auto-complete
    Institucion itemSelected= new Institucion();
    String myResource="";

    public CustomAutoComplete(Context context) {
        super(context);
        init(context);
    }

    public CustomAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomAutoComplete(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setSource(String source){
        // just to add some initial value
        myResource=source;
    }

    // this is how to disable AutoCompleteTextView filter
    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }

    /*
     * after a selection we have to capture the new value and append to the existing text
     */
    @Override
    protected void replaceText(final CharSequence text) {
        super.replaceText(text);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            performFiltering(getText(), 0);
        }
    }

    public void init(final Context context) {
     /*
     * Change to type CustomAutoCompleteView instead of AutoCompleteTextView
     * since we are extending to customize the view and disable filter
     * The same with the XML view, type will be CustomAutoCompleteView
     */

        try {
            TextWatcher fieldValidatorTextWatcher = new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // if you want to see in the logcat what the user types

                    // query the database based on the user input
                    items = getItemsFromDb(s.toString(), context);

                    // update the adapater
                    //myAdapter.notifyDataSetChanged();
                    myAdapter = new ArrayAdapter<Institucion>(context, R.layout.item, R.id.item, items);
                    setAdapter(myAdapter);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };

            AdapterView.OnItemClickListener fieldSelectionText = new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemSelected= (Institucion)getAdapter().getItem(0);
                }
            };


            OnFocusChangeListener fieldValidatorText = new OnFocusChangeListener() {
                String mText=getText().toString();

                @Override
                public void onFocusChange(View view, boolean b) {
                    String s = getText().toString();
                    if (b) {
                        mText = s;
                        setText("");
                    } else {
                        setText(itemSelected.getNombre());
                    }
                }
            };

            // add the listeners so it will tries to suggest while the user types
            setOnItemClickListener(fieldSelectionText);
            addTextChangedListener(fieldValidatorTextWatcher);
            setOnFocusChangeListener(fieldValidatorText);

            // set our adapter
            //myAdapter = new ArrayAdapter<Institucion>(context, android.R.layout.simple_dropdown_item_1line, items);
            //setAdapter(myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
     }

    // this function is used in CustomAutoCompleteTextChangedListener.java
    public Institucion[] getItemsFromDb(String searchTerm, Context context){

        // add items on the array dynamically
        DatabaseHandler DbH= new DatabaseHandler(context);
        DbH.setTable(myResource);
        List<Institucion> instituciones = DbH.read(searchTerm);
        int rowCount = instituciones.size();

        Institucion[] mItem = new Institucion[rowCount];
        int x = 0;

        for (Institucion record : instituciones) {

            mItem[x] = record;
            x++;
        }

        System.out.println(mItem.toString());
        return mItem;
    }
  }
