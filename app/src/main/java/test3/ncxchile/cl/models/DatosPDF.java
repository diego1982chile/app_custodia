package test3.ncxchile.cl.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import test3.ncxchile.cl.greenDAO.*;
import test3.ncxchile.cl.greenDAO.FichaEstadoVisual;

/**
 * Created by BOBO on 07-08-2014.
 */
@SuppressWarnings("serial")
public class DatosPDF implements Serializable {
    // Fragment 1
    public String view1_01, view1_02, view1_03, view1_04, view1_05, view1_06, view1_00, view1_02_apellidopaterno, view1_02_apellidomaterno, view1_02_telefonos, view1_02_correos;
    // Fragment 2
    boolean view2_00;
    public String view2_01, view2_02, view2_03, view2_04, view2_05, view2_06;
    // Fragment 3
    public String view3_01, view3_02, view3_03, view3_04, view3_05, view3_06, view3_07;
    public long view3_08;
    // Fragment 4
    public String view4_00, view4_01, view4_02, view4_03, view4_05, view4_07, view4_08, view4_04, view4_06;
    boolean view4_09;
    // Fragment 5
    public boolean view5_01, view5_02;
    public String view5_03; //view5_04, view5_05, view5_06, view5_07, view5_08, view5_09;
    //public boolean view5_10, view5_11, view5_12, view5_13, view5_14, view5_15, view5_16, view5_17;

    public ArrayList<FichaEstadoVisual> view5;
    // Fragment 6
    public String view6_01, view6_02, view6_02_paterno, view6_02_materno, view6_03, view6_04, view6_05, view6_06, view6_06_paterno, view6_06_materno, view6_07, view6_08, view6_09, view6_10;
    // Fragment 7
    public String view7_01, view7_02, view7_03;
    // Fragment 8
    public ArrayList view8_01;

    public String getView1_01() {
        return view1_01;
    }

    public void setView1_01(String view1_01) {
        this.view1_01 = view1_01;
    }

    public String getView1_02() {
        return view1_02;
    }

    public void setView1_02(String view1_02) {
        this.view1_02 = view1_02;
    }

    public String getView1_02_apellidopaterno() {
        return view1_02_apellidopaterno;
    }

    public void setView1_02_apellidopaterno(String view1_02_apellidopaterno) {
        this.view1_02_apellidopaterno = view1_02_apellidopaterno;
    }

    public String getView1_02_apellidomaterno() {
        return view1_02_apellidomaterno;
    }

    public void setView1_02_apellidomaterno(String view1_02_apellidomaterno) {
        this.view1_02_apellidomaterno = view1_02_apellidomaterno;
    }

    public String getView1_02_telefonos() {
        return view1_02_telefonos;
    }

    public void setView1_02_telefonos(String view1_02_telefonos) {
        this.view1_02_telefonos = view1_02_telefonos;
    }

    public String getView1_02_correos() {
        return view1_02_correos;
    }

    public void setView1_02_correos(String view1_02_correos) {
        this.view1_02_correos = view1_02_correos;
    }

    public String getView1_03() {
        return view1_03;
    }

    public void setView1_03(String view1_03) {
        this.view1_03 = view1_03;
    }

    public String getView1_04() {
        return view1_04;
    }

    public void setView1_04(String view1_04) {
        this.view1_04 = view1_04;
    }

    public String getView1_05() {
        return view1_05;
    }

    public void setView1_05(String view1_05) {
        this.view1_05 = view1_05;
    }

    public String getView1_06() {
        return view1_06;
    }

    public void setView1_06(String view1_06) {
        this.view1_06 = view1_06;
    }

    public String getView1_02_paterno() {
        return view1_02_apellidopaterno;
    }

    public void setView1_02_paterno(String view1_00) {
        this.view1_02_apellidopaterno = view1_00;
    }

    public String getView1_02_materno() {
        return view1_02_apellidomaterno;
    }

    public void setView1_02_materno(String view1_00) {
        this.view1_02_apellidomaterno = view1_00;
    }

    public String getView1_02_telefono() {
        return view1_02_telefonos;
    }

    public void setView1_02_telefono(String view1_00) {
        this.view1_02_telefonos = view1_00;
    }

    public String getView1_02_correo() {
        return view1_02_correos;
    }

    public void setView1_02_correo(String view1_00) {
        this.view1_02_correos = view1_00;
    }

    public String getView1_00() {
        return view1_00;
    }

    public void setView1_00(String view1_00) {
        this.view1_00 = view1_00;
    }

    public boolean getView2_00() {
        return view2_00;
    }

    public void setView2_00(boolean view2_00) {
        this.view2_00 = view2_00;
    }

    public String getView2_01() {
        return view2_01;
    }

    public void setView2_01(String view2_01) {
        this.view2_01 = view2_01;
    }

    public String getView2_02() {
        return view2_02;
    }

    public void setView2_02(String view2_02) {
        this.view2_02 = view2_02;
    }

    public String getView2_03() {
        return view2_03;
    }

    public void setView2_03(String view2_03) {
        this.view2_03 = view2_03;
    }

    public String getView2_04() {
        return view2_04;
    }

    public void setView2_04(String view2_04) {
        this.view2_04 = view2_04;
    }

    public String getView2_05() {
        return view2_05;
    }

    public void setView2_05(String view2_05) {
        this.view2_05 = view2_05;
    }

    public String getView2_06() {
        return view2_06;
    }

    public void setView2_06(String view2_06) {
        this.view2_06 = view2_06;
    }

    public String getView3_01() {
        return view3_01;
    }

    public void setView3_01(String view3_01) {
        this.view3_01 = view3_01;
    }

    public String getView3_02() {
        return view3_02;
    }

    public void setView3_02(String view3_02) {
        this.view3_02 = view3_02;
    }

    public String getView3_03() {
        return view3_03;
    }

    public void setView3_03(String view3_03) {
        this.view3_03 = view3_03;
    }

    public String getView3_04() {
        return view3_04;
    }

    public void setView3_04(String view3_04) {
        this.view3_04 = view3_04;
    }

    public String getView3_05() {
        return view3_05;
    }

    public void setView3_05(String view3_05) {
        this.view3_05 = view3_05;
    }

    public String getView3_06() {
        return view3_06;
    }

    public void setView3_06(String view3_06) {
        this.view3_06 = view3_06;
    }

    public String getView3_07() {
        return view3_07;
    }

    public void setView3_07(String view3_07) {
        this.view3_07 = view3_07;
    }

    public long getView3_08() {
        return view3_08;
    }

    public void setView3_08(long view3_08) {
        this.view3_08 = view3_08;
    }

    public String getView4_01() {
        return view4_01;
    }

    public void setView4_01(String view4_01) {
        this.view4_01 = view4_01;
    }

    public String getView4_02() {
        return view4_02;
    }

    public void setView4_02(String view4_02) {
        this.view4_02 = view4_02;
    }

    public String getView4_03() {
        return view4_03;
    }

    public void setView4_03(String view4_03) {
        this.view4_03 = view4_03;
    }

    public String getView4_05() {
        return view4_05;
    }

    public void setView4_05(String view4_05) {
        this.view4_05 = view4_05;
    }

    public String getView4_07() {
        return view4_07;
    }

    public void setView4_07(String view4_07) {
        this.view4_07 = view4_07;
    }

    public String getView4_08() {
        return view4_08;
    }

    public void setView4_08(String view4_08) {
        this.view4_08 = view4_08;
    }

    public boolean getView4_09() {
        return view4_09;
    }

    public void setView4_09(boolean view4_09) {
        this.view4_09 = view4_09;
    }

    public String getView4_04() {
        return view4_04;
    }

    public void setView4_04(String view4_04) {
        this.view4_04 = view4_04;
    }

    public String getView4_06() {
        return view4_06;
    }

    public void setView4_06(String view4_06) {
        this.view4_06 = view4_06;
    }

    public String getView6_01() {
        return view6_01;
    }

    public void setView6_01(String view6_01) {
        this.view6_01 = view6_01;
    }

    public String getView6_02() {
        return view6_02;
    }

    public void setView6_02(String view6_02) {
        this.view6_02 = view6_02;
    }

    public String getView6_02_paterno() {
        return view6_02_paterno;
    }

    public void setView6_02_paterno(String view6_02) {
        this.view6_02_paterno = view6_02;
    }

    public String getView6_02_materno() {
        return view6_02_materno;
    }

    public void setView6_02_materno(String view6_02) {
        this.view6_02_materno = view6_02;
    }

    public String getView6_03() {
        return view6_03;
    }

    public void setView6_03(String view6_03) {
        this.view6_03 = view6_03;
    }

    public String getView6_04() {
        return view6_04;
    }

    public void setView6_04(String view6_04) {
        this.view6_04 = view6_04;
    }

    public String getView6_05() {
        return view6_05;
    }

    public void setView6_05(String view6_05) {
        this.view6_05 = view6_05;
    }

    public String getView6_06() {
        return view6_06;
    }

    public void setView6_06(String view6_06) {
        this.view6_06 = view6_06;
    }

    public String getView6_06_paterno() {
        return view6_06_paterno;
    }

    public void setView6_06_paterno(String view6_02) {
        this.view6_06_paterno = view6_02;
    }

    public String getView6_06_materno() {
        return view6_06_materno;
    }

    public void setView6_06_materno(String view6_02) {
        this.view6_06_materno = view6_02;
    }

    public String getView6_07() {
        return view6_07;
    }

    public void setView6_07(String view6_07) {
        this.view6_07 = view6_07;
    }

    public String getView6_08() {
        return view6_08;
    }

    public void setView6_08(String view6_08) {
        this.view6_08 = view6_08;
    }

    public String getView6_09() {
        return view6_09;
    }

    public void setView6_09(String view6_09) {
        this.view6_09 = view6_09;
    }

    public String getView6_10() {
        return view6_10;
    }

    public void setView6_10(String view6_10) {
        this.view6_10 = view6_10;
    }

    public String getView7_01() {
        return view7_01;
    }

    public void setView7_01(String view7_01) {
        this.view7_01 = view7_01;
    }

    public String getView7_02() {
        return view7_02;
    }

    public void setView7_02(String view7_02) {
        this.view7_02 = view7_02;
    }

    public String getView7_03() {
        return view7_03;
    }

    public void setView7_03(String view7_03) {
        this.view7_03 = view7_03;
    }

    public boolean isView5_01() {
        return view5_01;
    }

    public void setView5_01(boolean view5_01) {
        this.view5_01 = view5_01;
    }

    public boolean isView5_02() {
        return view5_02;
    }

    public void setView5_02(boolean view5_02) {
        this.view5_02 = view5_02;
    }

    public String getView5_03() {
        return view5_03;
    }

    public void setView5_03(String view5_03) {
        this.view5_03 = view5_03;
    }

    public ArrayList<test3.ncxchile.cl.greenDAO.FichaEstadoVisual> getView5(){ return view5; }

    public void setView5(ArrayList<test3.ncxchile.cl.greenDAO.FichaEstadoVisual> fichaEstadoVisualList){
        this.view5=fichaEstadoVisualList;
    }

    public ArrayList getView8_01() {
        return view8_01;
    }

    public void setView8_01(ArrayList view8_01) {
        this.view8_01 = view8_01;
    }

    public void setView4_00(String view4_00) {
        this.view4_00 = view4_00;
    }

    public String getView4_00() {
        return view4_00;
    }

}
