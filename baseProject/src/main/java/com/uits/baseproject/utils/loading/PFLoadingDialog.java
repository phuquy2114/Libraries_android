package com.uits.baseproject.utils.loading;

import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import vn.uits.ytsk.R;

/**
 * @author PhuQuy
 */
public class PFLoadingDialog extends PFDialog implements OnTouchListener {

    private LinearLayout contentLayout;
    private boolean isCancelable;
    private int width;
    private int height;

    public PFLoadingDialog(Context context, Point layoutSize) {
        super(context, android.R.style.Theme_Holo_NoActionBar);
        init(layoutSize);
    }

    public PFLoadingDialog(Context context) {
        super(context, android.R.style.Theme_Holo_NoActionBar);
        int size = context.getResources().getDimensionPixelSize(R.dimen.loading_dialog_size);
        Point layoutSize = new Point(size, size);
        init(layoutSize);
    }

    private void init(Point layoutSize) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.width = layoutSize.x;
        this.height = layoutSize.y;
        isCancelable = false;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initRootView() {
        RelativeLayout layout = new RelativeLayout(getContext());
        drawBackgroundFullScreen(layout);
        drawDarkView(layout);
        drawContentView(layout);
        setContentView(layout);
    }

    private void drawBackgroundFullScreen(RelativeLayout layout) {
        layout.setBackgroundColor(getContext().getResources().getColor(
                android.R.color.transparent));
        setContentView(layout);
    }

    private void drawDarkView(RelativeLayout layout) {
        View darkView = new View(getContext());
        darkView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        darkView.setBackgroundColor(getContext().getResources().getColor(
                android.R.color.black));
        darkView.setAlpha(0.3f);
        darkView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (isCancelable) {
                    dismiss();
                }
                return true;
            }
        });
        layout.addView(darkView);
    }

    private void drawContentView(RelativeLayout layout) {
        LayoutParams params = new LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        initContentView(params);
        layout.addView(contentLayout);
        ProgressBar bar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyle);
        contentLayout.addView(bar);
    }

    private void initContentView(LayoutParams params) {
        contentLayout = new LinearLayout(getContext());
        contentLayout.setLayoutParams(params);
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);
        contentLayout.setGravity(Gravity.CENTER);
        contentLayout.setBackgroundResource(R.drawable.background_loading_dialog);
        contentLayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
    }

    @Override
    protected void initUIComponents(View rootView) {

    }

    @Override
    protected void setListeners() {
        contentLayout.setOnTouchListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        if (isCancelable) {
            super.onBackPressed();
        }
    }

    public void setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isCancelable) {
            dismiss();
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
