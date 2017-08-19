/*
 * Copyright (c) 2017. NKSystems
 *
 * Created on : 28-04-2017
 * Author     : Charmy Shah
 *
 * All rights reserved
 */

package nksystems.brainwave;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.DataObjectHolder> {

    private List<object_about_brainwave> mDataset;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView title;
        TextView content;

        public DataObjectHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtCardAboutBWTitle);
            content = (TextView) itemView.findViewById(R.id.txtCardAboutBWContent);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public CardViewAdapter(List<object_about_brainwave> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.about_brainwave_cardview_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getmText1());
        holder.content.setText(mDataset.get(position).getmText2());
        Log.i("crdvieqadapter",mDataset.get(position).getmText1());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
