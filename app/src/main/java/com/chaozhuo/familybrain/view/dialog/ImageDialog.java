package com.chaozhuo.familybrain.view.dialog;

import android.view.View;
import android.widget.ImageView;

import com.chaozhuo.familybrain.R;

/**
 * Created by fewwind on 18-9-5.
 */

public class ImageDialog extends BaseDialog {
    ImageView iv;
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_image;
    }

    @Override
    protected void initViewByType(View view) {
        iv = view.findViewById(R.id.dialog_iv);
        iv.setImageResource(mDialogBean.stateIcon);
    }
}
