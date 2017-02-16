package com.veryworks.android.bbsbasic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veryworks.android.bbsbasic.domain.Memo;
import com.veryworks.android.bbsbasic.interfaces.ListInterface;

import java.util.List;



public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    List<Memo> datas;
    ListInterface listc;


    public ListAdapter(Context context, List<Memo> datas) {
        this.context = context;
        this.datas = datas;
        this.listc = (ListInterface) context;

        // get method in ListInterface -Main A. goDetail

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Memo memo = datas.get(position);

        holder.textView.setText(memo.getMemo());
        holder.textView2.setText(memo.getDate()+"");

        holder.position = position;

    }

    @Override
    public int getItemCount() {

        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textView;
        TextView textView2;

        int position;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
            textView2 = (TextView) view.findViewById(R.id.textView2);
            cardView = (CardView) view.findViewById(R.id.cardView);

            cardView.setOnClickListener(listener);

        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(listc!=null) {
                    Memo memo = datas.get(position);
                    listc.goDetail(memo.getId());

                }


            }

        };

    }


}
