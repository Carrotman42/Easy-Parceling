package com.ksoft.easy;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TestEasy test = getIntent().getExtras().getParcelable("com.ksoft.easy.testEasy");
        
        TestEasy te = test.getT2();
        
        TestEasy[] tes = test.getT3();
        
        Throwable e = null;
        try {
            assert false;
        } catch (Throwable ex) {
            e = ex;
        }
        
        if (e == null) {
            throw new RuntimeException(
                    "Assert statements not enabled! To enable, do an adb shell to "
                            + "your device and do \"setprop debug.assert 1\"");
        }
        
        assert test.getT1() == 4;
        assert test.getS().equals("test");
        
        assert te.getT1() == 1;
        assert te.getT2() == null;
        assert te.getT3().length == 0;
        assert te.getS().equals("te");
        
        assert tes.length == 2;
        assert tes[0].getT1() == 2;
        assert tes[0].getT2() == null;
        assert tes[0].getT3() == null;
        assert tes[0].getS().equals("tes1");
        
        assert tes[1].getT1() == 3;
        assert tes[1].getT2() == null;
        assert tes[1].getT3() == null;
        assert tes[1].getS().equals("tes2");
        
        new AlertDialog.Builder(this).setMessage("Passed tests!").setPositiveButton("Nifty!", null)
                .show();
    }
}
