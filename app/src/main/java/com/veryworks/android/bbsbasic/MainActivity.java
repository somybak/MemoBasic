package com.veryworks.android.bbsbasic;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    ListFragment list;
    DetailFragment detail;

    FrameLayout main;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = ListFragment.newInstance(1);
        detail = DetailFragment.newInstance();
        main = (FrameLayout) findViewById(R.id.activity_main);

        manager = getSupportFragmentManager();

        setList();
    }

    private void setList(){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_main, list);
        transaction.commit();
    }

    private void goDetail(){

    }

    private void goList(){

    }
}
