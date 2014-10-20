package test3.ncxchile.cl.widgets;

/**
 * Created by android-developer on 15-10-2014.
 */

import android.content.Context;
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

public class CustomAutoCompleteView extends AutoCompleteTextView {

    public CustomAutoCompleteView(Context context) {
        super(context);
        init(context);
        // TODO Auto-generated constructor stub
    }

    public CustomAutoCompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        // TODO Auto-generated constructor stub
    }

    public CustomAutoCompleteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        // TODO Auto-generated constructor stub
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

    public void init(final Context context) {
     /*
     * Change to type CustomAutoCompleteView instead of AutoCompleteTextView
     * since we are extending to customize the view and disable filter
     * The same with the XML view, type will be CustomAutoCompleteView
     */

        // just to add some initial value
        final String[][] item = {new String[]{"Please search..."}};

        // adapter for auto-complete
        final ArrayAdapter<String>[] myAdapter = new ArrayAdapter[1];

        try {
            TextWatcher fieldValidatorTextWatcher = new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // if you want to see in the logcat what the user types

                    // query the database based on the user input
                    item[0] = getItemsFromDb(s.toString(), context);

                    // update the adapater
                    myAdapter[0].notifyDataSetChanged();
                    myAdapter[0] = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, item[0]);
                    setAdapter(myAdapter[0]);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void afterTextChanged(Editable s) {}
            };

            AdapterView.OnItemSelectedListener fieldSelectionText = new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    setError(context.getString(R.string.error_field_required));
                }
            };

            OnFocusChangeListener fieldValidatorText = new OnFocusChangeListener() {
                String mText=getText().toString();

                @Override
                public void onFocusChange(View view, boolean b) {
                String s= getText().toString();
                if(b) {
                    mText=s;
                    setText("");
                    }
                }
            };

            // add the listeners so it will tries to suggest while the user types
            addTextChangedListener(fieldValidatorTextWatcher);
            setOnFocusChangeListener(fieldValidatorText);
            setOnItemSelectedListener(fieldSelectionText);

            // set our adapter
            myAdapter[0] = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, item[0]);
            setAdapter(myAdapter[0]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
     }

    // this function is used in CustomAutoCompleteTextChangedListener.java
    public String[] getItemsFromDb(String searchTerm, Context context){

        // add items on the array dynamically
        DatabaseHandler DbH= new DatabaseHandler(context);
        List<Institucion> instituciones = DbH.read(searchTerm);
        int rowCount = instituciones.size();

        String[] mItem = new String[rowCount];
        int x = 0;

        for (Institucion record : instituciones) {

            mItem[x] = record.getNombre();
            x++;
        }

        System.out.println(mItem.toString());
        return mItem;
    }
  }
