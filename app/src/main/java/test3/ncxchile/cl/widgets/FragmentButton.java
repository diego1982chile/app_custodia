package test3.ncxchile.cl.widgets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import test3.ncxchile.cl.login.R;

/**
 * Created by Diego on 16-01-2015.
 */
public class FragmentButton extends android.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.refresh_button, container, false);

    }

}
