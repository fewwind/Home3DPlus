package com.chaozhuo.threed.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 *
 */

public abstract class CommonAdapterRV<T> extends RecyclerView.Adapter<ViewHolderRV> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;


    public CommonAdapterRV(Context context, List<T> datas, int layoutId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolderRV onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolderRV viewHolder = ViewHolderRV.createViewHolder(mContext, parent, mLayoutId);
        onViewHolderCreated(viewHolder, viewHolder.getConvertView(), viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderRV holder, int position) {
        convert(holder, mDatas.get(position));
    }

    public void onViewHolderCreated(ViewHolderRV holder, View itemView, int viewType) {

    }


    public abstract void convert(ViewHolderRV holder, T t);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}