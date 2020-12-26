package com.uits.baseproject.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.KeyEvent
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import com.uits.baseproject.R
import com.uits.baseproject.widget.PFLoadingDialog


/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 2/28/19.
 */
abstract class BaseDialog : Dialog {
    public val TAG = BaseDialog::class.java.simpleName

    protected abstract val layout: Int
    private var mPFLoading: PFLoadingDialog? = null
    protected abstract fun onCreateView(savedInstanceState: Bundle?)

    constructor(context: Context) : super(context) {
        mPFLoading = PFLoadingDialog(context)
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        mPFLoading = PFLoadingDialog(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lp = WindowManager.LayoutParams()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = window!!
        window.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        window.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        lp.copyFrom(window.attributes)
        //This makes the dialog take up the full width
        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT
        lp.height = RelativeLayout.LayoutParams.MATCH_PARENT
        window.attributes = lp
        setContentView(layout)
        setCancelable(false)
        onCreateView(savedInstanceState)

        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }


    open fun getPFLoading(): PFLoadingDialog = this.mPFLoading!!

    fun showBase() {
        try {
            super.show()
        } catch (e: Exception) {
            Log.e(TAG, "show: " + e.message)
            e.printStackTrace()
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss()
        }
        return true
    }

    fun dismissBase() {
        super.dismiss()
    }

    protected fun onStopBase() {
        super.onStop()
    }

    /**
     * show message dialog
     *
     * @param msg
     */
    protected fun showAlertDialog(msg: String) {
        try {
            AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_AppCompat_Light_Dialog_Alert))
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * show message dialog
     *
     * @param msg
     */
    protected fun showAlertDialog(title: String, msg: String) {
        try {
            AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_AppCompat_Light_Dialog_Alert))
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * show alertDialog option
     *
     * @param msg
     * @param listener
     */
    protected fun showAlertDialog(msg: String, listener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_AppCompat_Light_Dialog_Alert))
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
                override fun onClick(dialogInterface: DialogInterface, i: Int) {
                    if (listener != null) {
                        listener.onClick(dialogInterface, i)
                    }
                    dialogInterface.dismiss()
                }
            }).show()
    }
}

