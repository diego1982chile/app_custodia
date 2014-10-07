package test3.ncxchile.cl.acta;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import test3.ncxchile.cl.models.DatosPDF;

import test3.ncxchile.cl.greenDAO.DaoMaster;
import test3.ncxchile.cl.greenDAO.DaoSession;
import test3.ncxchile.cl.greenDAO.FinalizarActaDao;
import test3.ncxchile.cl.login.R;


public class MyActivity extends Activity implements ActionBar.TabListener {

    // Atributos BD
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private FinalizarActaDao finalizarActaDao;
    private static HashMap<Integer, Fragment> mPageReferenceMap = new HashMap<Integer, Fragment>();

    // Fragment 1
    public String view1_01, view1_02, view1_03, view1_04, view1_05, view1_06, view1_00;
    // Fragment 2
    public String view2_01, view2_02, view2_03, view2_04, view2_05, view2_06;
    // Fragment 3
    public String view3_01, view3_02, view3_03, view3_04, view3_05, view3_06, view3_07, view3_08;
    // Fragment 4
    public String view4_00, view4_01, view4_02, view4_03, view4_05, view4_07, view4_08, view4_04, view4_06, view4_09;
    // Fragment 5
    public boolean view5_01, view5_02;
    public String view5_03, view5_04, view5_05, view5_06, view5_07, view5_08, view5_09, view5_10, view5_11, view5_12, view5_13, view5_14, view5_15, view5_16, view5_17;
    // Fragment 6
    public String view6_01, view6_02, view6_03, view6_04, view6_05, view6_06, view6_07, view6_08, view6_09, view6_10;
    // Fragment 7
    public String view7_01, view7_02, view7_03;
    // Fragment 8
    public ArrayList view8_01;

    public String[] view1, view2, view3, view5, view6, view7;

    public String prueba;

    Fragment a = new FragmentX().newInstance(0);
    Fragment b = new FragmentX2().newInstance(1);
    Fragment c = new FragmentX3().newInstance(2);
    Fragment d = new FragmentX4().newInstance(3);
    Fragment e = new FragmentX5().newInstance(4);
    Fragment f = new FragmentX6().newInstance(5);
    Fragment g = new FragmentX7().newInstance(6);
    Fragment h = new FragmentX8().newInstance(7);
    Fragment i = new FragmentX9().newInstance(8);

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

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
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
        mPageReferenceMap.put(0, a);
        mPageReferenceMap.put(1, b);
        mPageReferenceMap.put(2, c);
        mPageReferenceMap.put(3, d);
        mPageReferenceMap.put(4, e);
        mPageReferenceMap.put(5, f);
        mPageReferenceMap.put(6, g);
        mPageReferenceMap.put(7, h);
        mPageReferenceMap.put(8, i);
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

        mViewPager.setCurrentItem(tab.getPosition());

        if(tab.getPosition() == 1 || tab.getPosition() == 2 || tab.getPosition() == 3 || tab.getPosition() == 4 || tab.getPosition() == 5 || tab.getPosition() == 6 || tab.getPosition() == 7 || tab.getPosition() == 8 ){
            FragmentX f1 = (FragmentX) getFragment(0);
            view1 = new String[7];
            view1 = f1.validarDatosFragment1();
            if (view1[0] != "0" || view1[1] != "0" || view1[2] != "0" || view1[3] != "0" || view1[4] != "0" || view1[5] != "0" || view1[6] != "0"){
                errorFragment(0);
                f1.pintarErrores1(view1);
            }
        }

        if(tab.getPosition() == 2 || tab.getPosition() == 3 || tab.getPosition() == 4 || tab.getPosition() == 5 || tab.getPosition() == 6 || tab.getPosition() == 7 || tab.getPosition() == 8 ){
            FragmentX f1 = (FragmentX) getFragment(0);
            f1.limpiarErrores();
            FragmentX2 f2 = (FragmentX2) getFragment(1);
            view2 = new String[2];
            view2 = f2.validarDatosFragment2();
            if (view2[0] != "0" || view2[1] != "0"){
                errorFragment(1);
                f2.pintarErrores2(view2);
            }
        }

        if(tab.getPosition() == 3 || tab.getPosition() == 4 || tab.getPosition() == 5 || tab.getPosition() == 6 || tab.getPosition() == 7 || tab.getPosition() == 8 ){
            FragmentX2 f2 = (FragmentX2) getFragment(1);
            f2.limpiarErrores();
            FragmentX3 f3 = (FragmentX3) getFragment(2);
            view3 = new String[7];
            view3 = f3.validarDatosFragment3();
            if (view3[0] != "0" || view3[1] != "0" || view3[2] != "0" || view3[3] != "0" || view3[4] != "0" || view3[5] != "0" || view3[6] != "0") {
                errorFragment(2);
                f3.pintarErrores3(view3);
            }
        }

        if(tab.getPosition() == 5 || tab.getPosition() == 6 || tab.getPosition() == 7 || tab.getPosition() == 8 ){
            FragmentX3 f3 = (FragmentX3) getFragment(2);
            f3.limpiarErrores();
            FragmentX5 f5 = (FragmentX5) getFragment(4);
            view5 = new String[6];
            view5 = f5.validarDatosFragment5();
            if (view5[0] != "0" || view5[1] != "0" || view5[2] != "0" || view5[3] != "0" || view5[4] != "0" || view5[5] != "0") {
                errorFragment(4);
                f5.pintarErrores3(view5);
            }
        }

        if(tab.getPosition() == 6 || tab.getPosition() == 7 || tab.getPosition() == 8){
            FragmentX5 f5 = (FragmentX5) getFragment(4);
            f5.limpiarErrores();
            FragmentX6 f6 = (FragmentX6) getFragment(5);
            view6 = new String[3];
            view6 = f6.validarDatosFragment6();
            if (view6[0] == "1" || view6[1] == "1") {
                errorFragment(5);
                f6.pintarErrores6(view6);
            }
            if(view6[2] == "1"){
                errorFragment(5);
                f6.pintarErrores6notMatch(view6);
            }
        }



        if(tab.getPosition() == 7 || tab.getPosition() == 8 ){
            FragmentX6 f6 = (FragmentX6) getFragment(5);
            f6.limpiarErrores();
            FragmentX7 f7 = (FragmentX7) getFragment(6);
            view7 = new String[3];
            view7 = f7.validarDatosFragment7();
            if (view7[0] != "0" || view7[1] != "0" || view7[2] != "0") {
                errorFragment(6);
                f7.pintarErrores7(view7);
            }
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
            }
            return null;//
        }

        @Override
        public int getCount() {
            // Muestra 9 pestañas
            return 9;
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
                    return getString(R.string.title_section5).toUpperCase(l);
                case 5:
                    return getString(R.string.title_section6).toUpperCase(l);
                case 6:
                    return getString(R.string.title_section7).toUpperCase(l);
                case 7:
                    return getString(R.string.title_section8).toUpperCase(l);
                case 8:
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
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
        a.envioDeDatos();
        b.envioDeDatos();
        c.envioDeDatos();
        d.envioDeDatos();
        e.envioDeDatos();
        f.envioDeDatos();
        g.envioDeDatos();
        h.envioDeDatos();

        DatosPDF datospdf = new DatosPDF();
        datospdf.setView1_00(view1_00);
        datospdf.setView1_01(view1_01);
        datospdf.setView1_02(view1_02);
        datospdf.setView1_03(view1_03);
        datospdf.setView1_04(view1_04);
        datospdf.setView1_05(view1_05);
        datospdf.setView1_06(view1_06);

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
        datospdf.setView5_04(view5_04);
        datospdf.setView5_05(view5_05);
        datospdf.setView5_06(view5_06);
        datospdf.setView5_07(view5_07);
        datospdf.setView5_08(view5_08);
        datospdf.setView5_09(view5_09);
        datospdf.setView5_10(view5_10);
        datospdf.setView5_11(view5_11);
        datospdf.setView5_12(view5_12);
        datospdf.setView5_13(view5_13);
        datospdf.setView5_14(view5_14);
        datospdf.setView5_15(view5_15);
        datospdf.setView5_16(view5_16);


        datospdf.setView6_01(view6_01);
        datospdf.setView6_02(view6_02);
        datospdf.setView6_03(view6_03);
        datospdf.setView6_04(view6_04);
        datospdf.setView6_05(view6_05);
        datospdf.setView6_06(view6_06);
        datospdf.setView6_07(view6_07);
        datospdf.setView6_08(view6_08);
        datospdf.setView6_09(view6_09);
        datospdf.setView6_10(view6_10);

        datospdf.setView7_01(view7_01);
        datospdf.setView7_02(view7_02);
        datospdf.setView7_03(view7_03);

        datospdf.setView8_01(view8_01);

        return datospdf;

        // Guardar la base de datos

        /*DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "finalizaracta-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        finalizarActaDao = daoSession.getFinalizarActaDao();

        FinalizarActa finalizarActaPrueba = new FinalizarActa();
        Acta acta = new Acta();
        VehiculoData vehiculoData = new VehiculoData();
        Vehiculo vehiculo = new Vehiculo();
            vehiculo.setMarca(view4_02);
            vehiculo.setModelo(view4_03);
            vehiculo.setAnio(view4_04);
            vehiculo.setColor(view4_05);
            vehiculo.setMatricula(view4_01);
            vehiculo.setModificado(true);
            vehiculo.setCaracteristicas("Null");
            vehiculo.setNumeroChasis(view4_08);
            vehiculo.setNumeroMotor(view4_07);
            vehiculo.setTamano("Null");
            vehiculo.setKilometraje(view4_06);
            vehiculo.setCarpetaVehiculo("Null");
            vehiculo.setServicio(1);
            vehiculo.setVin("Null");
            vehiculo.resetFichaEstadoVisualList();
            vehiculo.setOrigenVehiculo(true);      // asdasdasd
            vehiculo.setPuedeRodar(true);          //asdasdasd
            vehiculo.setParqueaderoSummary(null);  //asdasdasd
            vehiculo.setClonado(false);
        vehiculoData.setVehiculo(vehiculo);
        vehiculoData.resetEspeciasList();
        Cliente conductor = new Cliente();
            conductor.setLicencia(view6_08);
        Cliente propietario = new Cliente();
            propietario.setLicencia(view6_03);
        vehiculoData.setParqueadero(null);
        acta.setIdSolicitud(view1_00);

        finalizarActaPrueba.setActa(acta);
        finalizarActaPrueba.setGeoref("123123");
        finalizarActaPrueba.setFirmaGruero("asdasdasd");
        finalizarActaPrueba.setFirmaAutoridad("asdasdasdasd");
        finalizarActaDao.insert(finalizarActaPrueba);*/

    }

    public void recibeDatosFragmentX(EditText a, EditText b, EditText c, EditText d, EditText e, EditText f, EditText g ){
        view1_00 = a.getText().toString();
        view1_01 = b.getText().toString();
        view1_02 = c.getText().toString();
        view1_03 = d.getText().toString();
        view1_04 = e.getText().toString();
        view1_05 = f.getText().toString();
        view1_06 = g.getText().toString();
    }

    public void errorFragmentX(String error){
        FragmentX9 a = (FragmentX9) getFragment(8);
        a.errorCampoFragmentX(error);
    }

    public void recibeDatosFragmentX2(Spinner a, EditText b, EditText c, EditText d, Spinner e, EditText f){
        view2_01 = a.getSelectedItem().toString();
        view2_02 = b.getText().toString();
        view2_03 = c.getText().toString();
        view2_04 = d.getText().toString();
        view2_05 = e.getSelectedItem().toString();
        view2_06 = f.getText().toString();
    }

    public void recibeDatosFragmentX3(EditText a, EditText b, EditText c, EditText d, EditText e, EditText f, EditText g, EditText h){
        view3_01 = a.getText().toString();
        view3_02 = b.getText().toString();
        view3_03 = c.getText().toString();
        view3_04 = d.getText().toString();
        view3_05 = e.getText().toString();
        view3_06 = f.getText().toString();
        view3_07 = g.getText().toString();
        view3_08 = h.getText().toString();
    }

    public void recibeDatosFragmentX4(Spinner z, EditText a, EditText b, EditText c, EditText d, EditText e, EditText f, EditText g, EditText h, String i){
        view4_00 = z.getSelectedItem().toString();
        view4_01 = a.getText().toString();
        view4_02 = b.getText().toString();
        view4_03 = c.getText().toString();
        view4_04 = d.getText().toString();
        view4_05 = e.getText().toString();
        view4_06 = f.getText().toString();
        view4_07 = g.getText().toString();
        view4_08 = h.getText().toString();
        view4_09 = i;
    }

    public void recibeDatosFragmentX5(boolean boolimg, boolean boolvid, EditText motivo_imgvid, String q1_response, String q2_response,  String q3_response,  String q4_response,  String q5_response, String q6_response, String switch1_response, String switch2_response, String switch3_response, String switch4_response, String switch5_response, String switch6_response, String switch7_response){
        view5_01 = boolimg;
        view5_02 = boolvid;
        view5_03 = motivo_imgvid.getText().toString();
        view5_04 = q1_response;
        view5_05 = q2_response;
        view5_06 = q3_response;
        view5_07 = q4_response;
        view5_08 = q5_response;
        view5_09 = q6_response;
        view5_10 = switch1_response;
        view5_11 = switch2_response;
        view5_12 = switch3_response;
        view5_13 = switch4_response;
        view5_14 = switch5_response;
        view5_15 = switch6_response;
        view5_16 = switch7_response;

    }

    public void recibeDatosFragmentX6(EditText a, EditText b, EditText c, EditText d, EditText e, EditText f, EditText g, EditText h, EditText i, EditText j){
        view6_01 = a.getText().toString();
        view6_02 = b.getText().toString();
        view6_03 = c.getText().toString();
        view6_04 = d.getText().toString();
        view6_05 = e.getText().toString();
        view6_06 = f.getText().toString();
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

    public void onCheckboxClicked(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.isChecked();
    }

    public void conObs1(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.conObs1();
    }

    public void sinObs1(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.sinObs1();
    }

    public void conObs2(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.conObs2();
    }

    public void sinObs2(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.sinObs2();
    }

    public void conObs3(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.conObs3();
    }

    public void sinObs3(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.sinObs3();
    }

    public void conObs4(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.conObs4();
    }

    public void sinObs4(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.sinObs4();
    }

    public void conObs5(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.conObs5();
    }

    public void sinObs5(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.sinObs5();
    }

    public void conObs6(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.conObs6();
    }

    public void sinObs6(View v)
    {
        FragmentX5 frg = (FragmentX5) getFragment(4);
        frg.sinObs6();
    }



}
