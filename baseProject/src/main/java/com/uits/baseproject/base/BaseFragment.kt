package com.uits.baseproject.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.uits.baseproject.widget.PFLoadingDialog
import com.uits.baseproject.widget.ProgressLoading
import java.lang.ref.WeakReference

/**
 * https://github.com/MindorksOpenSource/android-kotlin-mvp-architecture/blob/master/app/src/main/java/com/mindorks/framework/mvp/ui/feed/blog/view/BlogFragment.kt
 *
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 2/28/19.
 */
abstract class BaseFragment : Fragment() {
    public val TAG = BaseFragment::class.java.simpleName

    abstract fun getLayoutId(): Int

    private var mLoading: ProgressLoading = ProgressLoading()
    private var mWeakReference: WeakReference<Activity>? = null
    private var mPFLoading: PFLoadingDialog? = null
    private var parentActivity: BaseActivity? = null
    private var mAlertDialog: AlertDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.parentActivity = activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mWeakReference = WeakReference<Activity>(activity)
        mPFLoading = PFLoadingDialog(mWeakReference!!.get()!!)
        mAlertDialog = AlertDialog.Builder(getBaseActivity())
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, which -> dialog.cancel() }.create()
        onCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(view)
        onViewCreated()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (getView() != null) {
            getView()!!.setClickable(true);
        }
    }

    fun getBaseActivity(): Activity {
        return mWeakReference!!.get()!!
    }

    open fun onViewCreated() {
        onInit()
        onEvent()
    }

    abstract fun onViewCreated(view: View)
    
    open fun onInit() {}

    open fun onEvent() {}

    open fun onCreate() {}

    open fun getLoading(): ProgressLoading = mLoading

    open fun getPFLoading(): PFLoadingDialog = this.mPFLoading!!

    protected fun showSingleAlertDialog(message: String) {
        if (!mAlertDialog?.isShowing()!!) {
            mAlertDialog?.setMessage(message)
            mAlertDialog?.show()
        }
    }

    protected fun showAlertDialog(context: Context, msg: String) {
        AlertDialog.Builder(context)
            .setMessage(msg)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    protected fun showAlertDialog(context: Context, @StringRes resId: Int) {
        showAlertDialog(context, context.resources.getString(resId))
    }
}
