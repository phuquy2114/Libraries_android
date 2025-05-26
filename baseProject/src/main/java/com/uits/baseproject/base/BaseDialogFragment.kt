package com.uits.baseproject.base

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.uits.baseproject.R
import com.uits.baseproject.widget.PFLoadingDialog


/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 2/28/19.
 */
abstract class BaseDialogFragment : DialogFragment {
    public val TAG = BaseDialogFragment::class.java.simpleName

    protected abstract val layout: Int
    private var mPFLoading: PFLoadingDialog? = null
    protected abstract fun onCreateView(savedInstanceState: Bundle?)

    constructor(context: Context) {
        mPFLoading = PFLoadingDialog(context)
    }

    constructor(context: Context, themeResId: Int) {
        mPFLoading = PFLoadingDialog(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        val lp = WindowManager.LayoutParams()
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = dialog?.window!!
        window.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        window.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        lp.copyFrom(window.attributes)
        //This makes the dialog take up the full width
        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT
        lp.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        isCancelable = false
        onCreateView(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(view)
    }

    open fun onViewCreated(view: View) {
        onCreate()
        onInit()
        onEvent()
    }

    open fun onInit() {}

    open fun onEvent() {}

    open fun onCreate() {}

    open fun getPFLoading(): PFLoadingDialog = this.mPFLoading!!

    fun showBase() {
        try {
            dialog?.show()
        } catch (e: Exception) {
            Log.e(TAG, "show: " + e.message)
            e.printStackTrace()
        }

    }

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
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
            AlertDialog.Builder(ContextThemeWrapper(context, R.style.MyAlertDialogTheme))
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
            AlertDialog.Builder(ContextThemeWrapper(context, R.style.MyAlertDialogTheme))
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
        AlertDialog.Builder(ContextThemeWrapper(context, R.style.MyAlertDialogTheme))
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

