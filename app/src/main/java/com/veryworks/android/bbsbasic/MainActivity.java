package com.veryworks.android.bbsbasic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.veryworks.android.bbsbasic.data.DBHelper;
import com.veryworks.android.bbsbasic.domain.Memo;
import com.veryworks.android.bbsbasic.interfaces.DetailInterface;
import com.veryworks.android.bbsbasic.interfaces.ListInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListInterface, DetailInterface{
    private static final String TAG="MemoMain";
    ListFragment list;
    DetailFragment detail;

    FrameLayout main;
    FragmentManager manager;

    DBHelper dbHelper;

    List<Memo> datas = new ArrayList<>();
    Dao<Memo, Integer> memoDao;

    FragmentManager fm ;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = ListFragment.newInstance(1);
        detail = DetailFragment.newInstance();

        main = (FrameLayout) findViewById(R.id.activity_main);
        manager = getSupportFragmentManager();
        try {
            loadData();
        }catch (SQLException e){
            Log.e(TAG, e+"============================");
        }

        list.setData(datas);
        setList();

        fm = getSupportFragmentManager();

    }

    public void loadData() throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        memoDao = dbHelper.getMemoDao();
        datas = memoDao.queryForAll();
        Log.e(TAG, "data size============================"+datas.size());
    }

    // 목록 프래그먼트 FrameLayout 에 add
    private void setList(){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_main, list);
        transaction.commit();
    }

    @Override
    public void goDetail(){
        FragmentTransaction transaction = manager.beginTransaction();
        detail.setPos(-1);
        transaction.add(R.id.activity_main, detail);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void goDetail(int getId) {
        FragmentTransaction transaction = manager.beginTransaction();
        detail.setPos(getId);
        transaction.add(R.id.activity_main, detail);
        transaction.addToBackStack(null);

        transaction.commit();
    }


    @Override
    public void backToList() {
        super.onBackPressed();
    }

    @Override
    public void saveToList(Memo memo) throws SQLException {

        Log.i(TAG,"memo============================================"+memo.getMemo());

        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        memoDao = dbHelper.getMemoDao();
        memoDao.create(memo);
        loadData();
        list.setData(datas);


        super.onBackPressed();
        list.refreshAdapter();
    }


}
