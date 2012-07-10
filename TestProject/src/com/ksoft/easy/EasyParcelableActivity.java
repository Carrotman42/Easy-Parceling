package com.ksoft.easy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class EasyParcelableActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TestEasy te = new TestEasy(1, null, new TestEasy[0], "te");
        TestEasy[] tes = new TestEasy[]{new TestEasy(2, null, null, "tes1"),
                new TestEasy(3, null, null, "tes2")};
        TestEasy test = new TestEasy(4, te, tes, "test");
        
        Intent tent = new Intent(this, TestActivity.class);
        tent.putExtra("com.ksoft.easy.testEasy", test);
        startActivity(tent);
    }
}
