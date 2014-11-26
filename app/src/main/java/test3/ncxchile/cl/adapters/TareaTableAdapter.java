package test3.ncxchile.cl.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TableRow;
import android.widget.TextView;

public class TareaTableAdapter<T> extends BaseTableAdapter {

    private final static int WIDTH_DIP = 110;
    private final static int HEIGHT_DIP = 45;

    private final Context context;

    private T[][] table;

    private final int width;
    private final int height;

    public TareaTableAdapter(Context context) {
        this(context, null);
    }

    public TareaTableAdapter(Context context, T[][] table) {
        this.context = context;
        Resources r = context.getResources();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        width = display.getWidth();
        height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_DIP, r.getDisplayMetrics()));

        setInformation(table);
    }

    public void setInformation(T[][] table) {
        this.table = table;
    }

    @Override
    public int getRowCount() {
        return table.length - 1;
    }

    @Override
    public int getColumnCount() {
        return table[0].length - 1;
    }

    @Override
    public View getView(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(context);
            float weight=1f;
            switch(column){
                case 0:
                    weight=1f;
                    break;
                case 1:
                    weight=3f;
                    break;
                case 2:
                    weight=2f;
                    break;
                case 3:
                    weight=2f;
                    break;
                case 4:
                    weight=2f;
                    break;
            }

            TableRow.LayoutParams params = new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,weight);
            params.setMargins(5, 5, 5, 5);
            convertView.setLayoutParams(params);
            ((TextView) convertView).setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        }
        ((TextView) convertView).setText(table[row + 1][column + 1].toString().replaceAll("\"", ""));

        return convertView;
    }

    @Override
    public int getHeight(int row) {
        return height;
    }

    @Override
    public double getWidth(int column) {
        switch(column){
            case 0:
                return width*0.1;
            case 1:
                return width*0.3;
            case 2:
                return width*0.2;
            case 3:
                return width*0.2;
            case 4:
                return width*0.2;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int row, int column) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
