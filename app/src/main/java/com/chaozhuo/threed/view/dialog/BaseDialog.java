package com.chaozhuo.threed.view.dialog;


import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.chaozhuo.threed.R;
import com.chaozhuo.threed.util.DensityUtil;


/**
 * Created by fewwind on 18-5-24.
 */

public abstract class BaseDialog extends DialogFragment {

    public static final String EXTRA_INFO = "extra_info";
    protected IDialogClick mListener;

    protected View mRootView;
    protected TextView mTvTitle;
    protected TextView mTvCancle;
    protected TextView mTvOk;

    protected DialogBean mDialogBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mDialogBean = (DialogBean) getArguments().getSerializable(EXTRA_INFO);
        setCancelable(false);
        return mRootView;
    }

    protected abstract int getLayoutId();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewByType(view);
        setContent();
        setOnClick(getEditInfo());

        mTvOk.requestFocus();
        mTvOk.requestFocusFromTouch();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            //设置弹框的占屏宽        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Point point = getDialogSize();
            dialog.getWindow().setLayout(point.x, point.y);
        }
    }

    protected Point getDialogSize() {
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.4f);
        return new Point(DensityUtil.dip2px(330), DensityUtil.dip2px(230));
    }

    protected void setContent() {
        if (mDialogBean != null) {
            if (mTvTitle != null)
                mTvTitle.setText(TextUtils.isEmpty(mDialogBean.title) ? "" : mDialogBean.title);

            if (mTvOk != null) {
                mTvOk.setText(TextUtils.isEmpty(mDialogBean.ok) ? getString(R.string.ok) : mDialogBean.ok);
                if (mDialogBean.okColorId > 0)
                    mTvOk.setTextColor(getResources().getColor(mDialogBean.okColorId));
            }

            if (mTvCancle != null) {
                mTvCancle.setText(TextUtils.isEmpty(mDialogBean.cancle) ? getString(R.string.cancle) : mDialogBean.cancle);
                if (mDialogBean.cancelColorId > 0)
                    mTvCancle.setTextColor(getResources().getColor(mDialogBean.cancelColorId));
            }
        }
    }

    protected abstract void initViewByType(View view);


    protected String getEditInfo() {
        return "";
    }

    public void setListener(IDialogClick mListener) {
        if (mListener != null) {
            this.mListener = mListener;
        }
    }

    protected void setOnClick(String result) {
        if (mTvOk != null) {
            mTvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.clickOk(getEditInfo());
                        if (mDialogBean != null && mDialogBean.isShowEdit) {
                            if (!TextUtils.isEmpty(getEditInfo())) {
                                dismiss();
                            }
                        } else {
                            dismiss();
                        }
                    }
                }
            });
        }
        if (mTvCancle != null) {
            mTvCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        dismiss();
                        mListener.clickCancle();
                    }
                }
            });
        }
    }

}
