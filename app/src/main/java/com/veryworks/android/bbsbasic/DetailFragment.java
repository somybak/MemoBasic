package com.veryworks.android.bbsbasic;


import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao;
import com.veryworks.android.bbsbasic.data.DBHelper;
import com.veryworks.android.bbsbasic.domain.Memo;
import com.veryworks.android.bbsbasic.interfaces.DetailInterface;

import java.sql.SQLException;
import java.util.Date;

public class DetailFragment extends Fragment implements View.OnClickListener{
    private static final int NEW_MEMO = 100;
    private static DetailFragment detailFragment;
    Context context = null;
    DetailInterface detailInterface = null;
    int getId;

    View view = null;

    Button btnSave, btnCancel;
    EditText editMemo;


    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance() {
        if(detailFragment == null)
            detailFragment = new DetailFragment();

        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

   //     if(view == null) {
            view = inflater.inflate(R.layout.fragment_detail, container, false);
            editMemo = (EditText) view.findViewById(R.id.editMemo);
            btnSave = (Button) view.findViewById(R.id.btnSave);
            btnCancel = (Button) view.findViewById(R.id.btnCancel);

            btnSave.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
   //     }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.detailInterface = (DetailInterface) context;
    }

    // ger position
    public void setPos(int getId){
        this.getId = getId;
    }

    private void setEditMemo() throws SQLException {

            DBHelper dbHelper = new DBHelper(getContext()); // I will use system resource
            Dao<Memo, Integer> memoDao = dbHelper.getDao(Memo.class);

            Memo memo = memoDao.queryForId(getId);
            editMemo.setText(memo.getMemo());
            dbHelper.close();

    }






    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                try {
                    if(getId!=-1) {
                        updateMemo();

                    } else {
                        newMemo();

                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

                break;
            case R.id.btnCancel:
                detailInterface.backToList();
                break;
        }
    }

    private void newMemo() throws SQLException {

        Memo memo = new Memo();
        String string = editMemo.getText().toString();
        memo.setMemo(string);

        editMemo.setText("");

        memo.setDate(new Date(System.currentTimeMillis()));
        detailInterface.saveToList(memo);

    }

    private void updateMemo() throws SQLException {

    DBHelper dbHelper = new DBHelper(getContext());
    Dao<Memo, Integer> memoDao = dbHelper.getDao(Memo.class);

    Memo memo = memoDao.queryForId(getId);

    String string = editMemo.getText().toString();
    memo.setMemo(string);
        memo.setDate(new Date(System.currentTimeMillis()));


    detailInterface.saveToList(memo);
 //   dbHelper.close();

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("dasddasd", "--------------------------------");

        try {
            if(getId == NEW_MEMO) {
                Log.i("dasddasd", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                editMemo.setText("");
            }
            else
                setEditMemo();

        } catch (Exception e) { e.printStackTrace(); }

    }

}
