package com.chanven.cptr.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        int a = add(0);
        Log.i("TAG","----->" + a);
    }

    private int add(int i){
        i++;
        if(i< 5){
            return add(i);
        }
        return i;
    }
}
