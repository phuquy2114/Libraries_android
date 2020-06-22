package com.uits.baseproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * PFDialog
 *
 * @author PhuQuy
 */
public abstract class PFDialog extends Dialog {
    private ViewGroup contentView;
    private PFLoadingDialog mLoadingDialog;
    private AtomicInteger numberLoadingDialog;

    public PFDialog(Context context) {
        super(context);
    }

    public PFDialog(Context context, int theme) {
        super(context, theme);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contentView = (ViewGroup) getWindow().getDecorView().findViewById(
                android.R.id.content);
        super.onCreate(savedInstanceState);
        initData();
        initRootView();
        ViewGroup rootView = ((ViewGroup) contentView.getChildAt(0));
        initUIComponents(rootView);
        setListeners();
        loadData();
        mLoadingDialog = new PFLoadingDialog(getContext());
        numberLoadingDialog = new AtomicInteger();
    }

    protected abstract void initData();

    protected abstract void initRootView();

    protected abstract void initUIComponents(View rootView);

    protected abstract void setListeners();

    protected abstract void loadData();

    public void setBackground(int color) {
        contentView.setBackgroundColor(color);
        contentView.invalidate();
    }

    protected void showLoadingDialog() {
        if (numberLoadingDialog.incrementAndGet() == 1) {
            mLoadingDialog.show();
        } else {
            numberLoadingDialog.decrementAndGet();
        }
    }

    protected void hideLoadingDialog() {
        if (numberLoadingDialog.decrementAndGet() == 0) {
            mLoadingDialog.hide();
        }
    }


}
