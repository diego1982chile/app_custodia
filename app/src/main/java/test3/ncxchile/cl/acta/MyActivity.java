package test3.ncxchile.cl.acta;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import test3.ncxchile.cl.greenDAO.Acta;
import test3.ncxchile.cl.greenDAO.FichaEstadoVisual;
import test3.ncxchile.cl.greenDAO.Tribunal;
import test3.ncxchile.cl.models.DatosPDF;

import test3.ncxchile.cl.greenDAO.FinalizarActaDao;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.widgets.CustomAutoComplete;
import test3.ncxchile.cl.widgets.CustomSpinner;
import test3.ncxchile.cl.widgets.RequiredEditText;


public class MyActivity extends Activity implements ActionBar.TabListener {

    private FinalizarActaDao finalizarActaDao;
    private static HashMap<Integer, Fragment> mPageReferenceMap = new HashMap<Integer, Fragment>();
    ActaController actaController;
    FragmentManager mFragmentManager;
    // Session Manager Class
    SessionManager session;
    public Acta acta;

    // Fragment 1
    public String view1_01, view1_02, view1_02_paterno, view1_02_materno, view1_02_telefono, view1_02_correo, view1_03, view1_04, view1_05, view1_06, view1_00;
    // Fragment 2
    public boolean view2_00;
    public String view2_01, view2_02, view2_03, view2_04, view2_05, view2_06;
    // Fragment 3
    public String view3_01, view3_02, view3_03, view3_04, view3_05, view3_06, view3_07;
    public long  view3_08;
    // Fragment 4
    public String view4_00, view4_01, view4_02, view4_03, view4_05, view4_07, view4_08, view4_04, view4_06;
    public boolean view4_09;
    // Fragment fotovideo
    // Fragment 5
    public boolean view5_01, view5_02;

    public String view5_03;//, view5_04, view5_05, view5_06, view5_07, view5_08, view5_09;
    //public boolean view5_10, view5_11, view5_12, view5_13, view5_14, view5_15, view5_16, view5_17;

    public ArrayList<FichaEstadoVisual> view5;
    // Fragment 6
    public String view6_01, view6_02, view6_02_paterno, view6_02_materno, view6_03, view6_04, view6_05, view6_06, view6_06_paterno, view6_06_materno, view6_07, view6_08, view6_09, view6_10;
    // Fragment 7
    public String view7_01, view7_02, view7_03;
    // Fragment 8
    public ArrayList view8_01;


    public String prueba;

    public Fragment a = new FragmentX().newInstance(0);
    Fragment b = new FragmentX2().newInstance(1);
    Fragment c = new FragmentX3().newInstance(2);
    Fragment d = new FragmentX4().newInstance(3);
    Fragment e = new FragmentX5().newInstance(4); // Fragment FotoVideo
    Fragment f = new FragmentX6().newInstance(5);
    Fragment g = new FragmentX7().newInstance(6);
    Fragment h = new FragmentX8().newInstance(7);
    Fragment i = new FragmentX9().newInstance(8);
    Fragment j = new FragmentX10().newInstance(9);

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actaController = new ActaController(this);
        // Session class instance
        session = new SessionManager(getApplicationContext());
        acta= actaController.getActaByTarea(session.getTareaActiva());
        //System.out.println("Myctivity: idActa="+acta.getId()+" Nombre="+acta.getPersona().getNombre());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(9);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
        actionBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public void onStart() {
       // mapeo de vistas con su respectivo ID
        super.onStart();
        /*
        Fragment a = new FragmentX().newInstance(0);
        Fragment b = new FragmentX2().newInstance(1);
        Fragment c = new FragmentX3().newInstance(2);
        Fragment d = new FragmentX4().newInstance(3);
        Fragment e = new FragmentX5().newInstance(4); // Fragment FotoVideo
        Fragment f = new FragmentX6().newInstance(5);
        Fragment g = new FragmentX7().newInstance(6);
        Fragment h = new FragmentX8().newInstance(7);
        Fragment i = new FragmentX9().newInstance(8);
        Fragment j = new FragmentX10().newInstance(9);
        */
        mPageReferenceMap.put(0, a);
        mPageReferenceMap.put(1, b);
        mPageReferenceMap.put(2, c);
        mPageReferenceMap.put(3, d);
        mPageReferenceMap.put(4, e);
        mPageReferenceMap.put(5, f);
        mPageReferenceMap.put(6, g);
        mPageReferenceMap.put(7, h);
        mPageReferenceMap.put(8, i);
        mPageReferenceMap.put(9, j);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // Aqui hago las validaciones de los campos, al hacer swap

        if(getResources().getConfiguration().orientation == 2) // si tiene orientacion landscape, no hacer nada
            return;

        mViewPager.setCurrentItem(tab.getPosition());

        if(tab.getPosition() == 1 /*|| tab.getPosition() == 2 || tab.getPosition() == 3 || tab.getPosition() == 4 || tab.getPosition() == 5 || tab.getPosition() == 6 || tab.getPosition() == 7 || tab.getPosition() == 8 */){
            FragmentX f1 = (FragmentX) getFragment(0);

            if(!f1.validarDatosFragment1())
                errorFragment(0);
        }

        if(tab.getPosition() == 2 /*|| tab.getPosition() == 3 || tab.getPosition() == 4 || tab.getPosition() == 5 || tab.getPosition() == 6 || tab.getPosition() == 7 || tab.getPosition() == 8 */){
            FragmentX2 f2 = (FragmentX2) getFragment(1);

            if (!f2.validarDatosFragment2())
                errorFragment(1);
        }

        if(tab.getPosition() == 3 /*|| tab.getPosition() == 4 || tab.getPosition() == 5 || tab.getPosition() == 6 || tab.getPosition() == 7 || tab.getPosition() == 8 */){
            //FragmentX2 f2 = (FragmentX2) getFragment(1);
            FragmentX3 f3 = (FragmentX3) getFragment(2);

            if (!f3.validarDatosFragment3()) {
                errorFragment(2);
            }
        }

        if(tab.getPosition() == 5 /*|| tab.getPosition() == 6 || tab.getPosition() == 7 || tab.getPosition() == 8*/ ){
            //FragmentX3 f3 = (FragmentX3) getFragment(2);
            FragmentX5 f5 = (FragmentX5) getFragment(4);

            if (!f5.validarDatosFragmentFotoVideo()) {
                errorFragment(4);
            }

        }

        if(tab.getPosition() == 6 /*|| tab.getPosition() == 6 || tab.getPosition() == 7 || tab.getPosition() == 8*/ ){
            //FragmentX3 f3 = (FragmentX3) getFragment(2);
            FragmentX6 f6 = (FragmentX6) getFragment(5);

            if (!f6.validarDatosFragment5()) {
                errorFragment(5);
            }

        }

        if(tab.getPosition() == 7 /*|| tab.getPosition() == 8 || tab.getPosition() == 9*/){
            FragmentX7 f7 = (FragmentX7) getFragment(6);

            if (!f7.validarDatosFragment6()) {
                errorFragment(6);
            }
        }

        if(tab.getPosition() == 8 /*|| tab.getPosition() == 9 */){
            FragmentX8 f8 = (FragmentX8) getFragment(7);

            if (!f8.validarDatosFragment7()) {
                errorFragment(7);
            }
        }

        if(tab.getPosition() == 9 /*|| tab.getPosition() == 9 */){
            FragmentX9 f9 = (FragmentX9) getFragment(8);
            FragmentX f1 = (FragmentX) getFragment(0);

            /*
            android.app.FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.remove(a);

            Fragment newInstance = recreateFragment(a);
            ft.add(R.layout.fragment1, newInstance);
            ft.commit();
            */


            /*
            if (!f8.validarDatosFragment7()) {
                errorFragment(7);
            }
            */
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public void errorFragment(int position){
        mViewPager.setCurrentItem(position, true);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Metodo me retorna posicion del fragment
            switch (position) {
                case 0:
                    return a;
                case 1:
                    return b;
                case 2:
                    return c;
                case 3:
                    return d;
                case 4:
                    return e;
                case 5:
                    return f;
                case 6:
                    return g;
                case 7:
                    return h;
                case 8:
                    return i;
                case 9:
                    return j;
                /*
                mPageReferenceMap.put(0, a);
                mPageReferenceMap.put(1, b);
                mPageReferenceMap.put(2, c);
                mPageReferenceMap.put(3, d);
                mPageReferenceMap.put(4, e);
                mPageReferenceMap.put(5, f);
                mPageReferenceMap.put(6, g);
                mPageReferenceMap.put(7, h);
                mPageReferenceMap.put(8, i);
                */
            }
            return null;//
        }

        @Override
        public int getCount() {
            // Muestra 10 pestañas
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Metodo que me muestra los titulos
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 4:
                    return getString(R.string.title_section5_1).toUpperCase(l);
                case 5:
                    return getString(R.string.title_section5_2).toUpperCase(l);
                case 6:
                    return getString(R.string.title_section6).toUpperCase(l);
                case 7:
                    return getString(R.string.title_section7).toUpperCase(l);
                case 8:
                    return getString(R.string.title_section8).toUpperCase(l);
                case 9:
                    return getString(R.string.title_section9).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment1, container, false);
            return rootView;
        }

    }
    public Fragment getFragment(int key) {
        return mPageReferenceMap.get(key);
    }

    public DatosPDF callBackButton (){
        // esta funcion guarda todos los datos de los fragment, y los envia a la siguiente vista
        FragmentX a = (FragmentX) getFragment(0);
        FragmentX2 b = (FragmentX2) getFragment(1);
        FragmentX3 c = (FragmentX3) getFragment(2);
        FragmentX4 d = (FragmentX4) getFragment(3);
        FragmentX5 e = (FragmentX5) getFragment(4);
        FragmentX6 f = (FragmentX6) getFragment(5);
        FragmentX7 g = (FragmentX7) getFragment(6);
        FragmentX8 h = (FragmentX8) getFragment(7);
        FragmentX9 i = (FragmentX9) getFragment(8);
        //FragmentX9 j = (FragmentX9) getFragment(9);
        a.envioDeDatos();
        b.envioDeDatos();
        c.envioDeDatos();
        d.envioDeDatos();
        e.envioDeDatos();
        f.envioDeDatos();
        g.envioDeDatos();
        h.envioDeDatos();
        i.envioDeDatos();

        DatosPDF datospdf = new DatosPDF();
        datospdf.setView1_00(view1_00);
        datospdf.setView1_01(view1_01);
        datospdf.setView1_02(view1_02);
        datospdf.setView1_02_paterno(view1_02_paterno);
        datospdf.setView1_02_materno(view1_02_materno);
        datospdf.setView1_02_telefono(view1_02_telefono);
        datospdf.setView1_02_correo(view1_02_correo);
        datospdf.setView1_03(view1_03);
        datospdf.setView1_04(view1_04);
        datospdf.setView1_05(view1_05);
        datospdf.setView1_06(view1_06);
        // Crear

        datospdf.setView2_00(view2_00);
        datospdf.setView2_01(view2_01);
        datospdf.setView2_02(view2_02);
        datospdf.setView2_03(view2_03);
        datospdf.setView2_04(view2_04);
        datospdf.setView2_05(view2_05);
        datospdf.setView2_06(view2_06);

        datospdf.setView3_01(view3_01);
        datospdf.setView3_02(view3_02);
        datospdf.setView3_03(view3_03);
        datospdf.setView3_04(view3_04);
        datospdf.setView3_05(view3_05);
        datospdf.setView3_06(view3_06);
        datospdf.setView3_07(view3_07);
        datospdf.setView3_08(view3_08);

        datospdf.setView4_00(view4_00);
        datospdf.setView4_01(view4_01);
        datospdf.setView4_02(view4_02);
        datospdf.setView4_03(view4_03);
        datospdf.setView4_04(view4_04);
        datospdf.setView4_05(view4_05);
        datospdf.setView4_06(view4_06);
        datospdf.setView4_07(view4_07);
        datospdf.setView4_08(view4_08);
        datospdf.setView4_09(view4_09);

        datospdf.setView5_01(view5_01);
        datospdf.setView5_02(view5_02);
        datospdf.setView5_03(view5_03);
        datospdf.setView5(view5);

        datospdf.setView6_01(view6_01);
        datospdf.setView6_02(view6_02);
        datospdf.setView6_02_paterno(view6_02_paterno);
        datospdf.setView6_02_materno(view6_02_materno);
        datospdf.setView6_03(view6_03);
        datospdf.setView6_04(view6_04);
        datospdf.setView6_05(view6_05);
        datospdf.setView6_06(view6_06);
        datospdf.setView6_06_paterno(view6_06_paterno);
        datospdf.setView6_06_materno(view6_06_materno);
        datospdf.setView6_07(view6_07);
        datospdf.setView6_08(view6_08);
        datospdf.setView6_09(view6_09);
        datospdf.setView6_10(view6_10);

        datospdf.setView7_01(view7_01);
        datospdf.setView7_02(view7_02);
        datospdf.setView7_03(view7_03);

        datospdf.setView8_01(view8_01);

        actaController.completarActa(datospdf);

        return datospdf;
    }

    public void recibeDatosFragmentX(EditText a, EditText b, EditText c, RequiredEditText c1, EditText c2, EditText c3, EditText c4, EditText d, EditText e, EditText f, EditText g ){
        view1_00 = a.getText().toString();
        view1_01 = b.getText().toString();
        view1_02 = c.getText().toString();
        view1_02_paterno = c1.getText().toString();
        view1_02_materno = c2.getText().toString();
        view1_02_telefono = c3.getText().toString();
        view1_02_correo = c4.getText().toString();
        view1_03 = d.getText().toString();
        view1_04 = e.getText().toString();
        view1_05 = f.getText().toString();
        view1_06 = g.getText().toString();
    }

    public void recibeDatosFragmentX2(boolean fiscalia, CustomAutoComplete a, EditText b, EditText c, EditText d, CustomAutoComplete e, EditText f){
        view2_00 = fiscalia;
        view2_01 = a.getText().toString();
        view2_02 = b.getText().toString();
        view2_03 = c.getText().toString();
        view2_04 = d.getText().toString();
        view2_05 = e.getText().toString();
        view2_06 = f.getText().toString();
    }

    public void recibeDatosFragmentX3(EditText a, EditText b, EditText c, EditText d, EditText e, EditText f, EditText g, CustomAutoComplete h){
        view3_01 = a.getText().toString();
        view3_02 = b.getText().toString();
        view3_03 = c.getText().toString();
        view3_04 = d.getText().toString();
        view3_05 = e.getText().toString();
        view3_06 = f.getText().toString();
        view3_07 = g.getText().toString();
        Tribunal tribunal=(Tribunal)h.getSelectedItem();
        view3_08 = tribunal.getId();
    }

    public void recibeDatosFragmentX4(CustomAutoComplete z, EditText a, CustomAutoComplete b, EditText c, CustomSpinner d, EditText e, EditText f, EditText g, EditText h, boolean i){
        view4_00 = z.getText().toString();
        view4_01 = a.getText().toString();
        view4_02 = b.getText().toString();
        view4_03 = c.getText().toString();
        view4_04 = d.getSelectedItem().toString();
        view4_05 = e.getText().toString();
        view4_06 = f.getText().toString();
        view4_07 = g.getText().toString();
        view4_08 = h.getText().toString();
        view4_09 = i;
    }

    public void recibeDatosFragmentX5(boolean boolimg, boolean boolvid, String motivo_imgvid){
        view5_01 = boolimg;
        view5_02 = boolvid;
        view5_03 = motivo_imgvid;
    }

    public void recibeDatosFragmentX6(ArrayList<FichaEstadoVisual> fichaEstadoVisualList){
        view5 = fichaEstadoVisualList;
    }

    public void recibeDatosFragmentX7(EditText a, EditText b, EditText b1, EditText b2, EditText b3, EditText b4, EditText c, EditText d, EditText e, EditText f, EditText g, EditText h, EditText i, EditText j){
        view6_01 = a.getText().toString();
        view6_02 = b.getText().toString();
        view6_02_paterno = b1.getText().toString();
        view6_02_materno = b2.getText().toString();
        view6_03 = c.getText().toString();
        view6_04 = d.getText().toString();
        view6_05 = e.getText().toString();
        view6_06 = f.getText().toString();
        view6_06_paterno = b3.getText().toString();
        view6_06_materno = b4.getText().toString();
        view6_07 = g.getText().toString();
        view6_08 = h.getText().toString();
        view6_09 = i.getText().toString();
        view6_10 = j.getText().toString();
    }

    public void recibeDatosFragmentX7(EditText a, EditText b, EditText c){
        view7_01 = a.getText().toString();
        view7_02 = b.getText().toString();
        view7_03 = c.getText().toString();
    }

    public void recibeDatosFragmentX8(ArrayList a){
        view8_01 = a;
    }

    /*
    public void onCheckboxClicked(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(4);
        frg.isChecked();
    }

    public void conObs1(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.conObs1();
    }

    public void sinObs1(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.sinObs1();
    }

    public void conObs2(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.conObs2();
    }

    public void sinObs2(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.sinObs2();
    }

    public void conObs3(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.conObs3();
    }

    public void sinObs3(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.sinObs3();
    }

    public void conObs4(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.conObs4();
    }

    public void sinObs4(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.sinObs4();
    }

    public void conObs5(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.conObs5();
    }

    public void sinObs5(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.sinObs5();
    }

    public void conObs6(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.conObs6();
    }

    public void sinObs6(View v)
    {
        FragmentX6 frg = (FragmentX6) getFragment(5);
        frg.sinObs6();
    }
    */

    private Fragment recreateFragment(Fragment f)
    {
        try {
            Fragment.SavedState savedState = mFragmentManager.saveFragmentInstanceState(f);

            Fragment newInstance = f.getClass().newInstance();
            newInstance.setInitialSavedState(savedState);

            return newInstance;
        }
        catch (Exception e) // InstantiationException, IllegalAccessException
        {
            throw new RuntimeException("Cannot reinstantiate fragment " + f.getClass().getName(), e);
        }
    }
}
