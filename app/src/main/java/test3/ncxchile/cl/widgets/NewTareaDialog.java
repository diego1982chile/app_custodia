package test3.ncxchile.cl.widgets;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import test3.ncxchile.cl.login.R;

/**
 * Created by android-developer on 28-10-2014.
 */

public class NewTareaDialog extends AlertDialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes;

    public NewTareaDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.error_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                //c.finish();
                break;
            default:
                break;
        }
        dismiss();
    }
}
