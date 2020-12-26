package com.uits.baseproject.base

import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.uits.baseproject.R
import com.uits.baseproject.widget.PFLoadingDialog
import com.uits.baseproject.widget.ProgressLoading

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 2/28/19.
 */
/**
 * BaseActivity
 */
abstract class BaseActivity : AppCompatActivity() {

    public val TAG = BaseActivity::class.java.getSimpleName()
    private var mLoading: ProgressLoading = ProgressLoading()
    private var mPFLoading: PFLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mPFLoading = PFLoadingDialog(this)
        onInit()
        onEvent()
    }

    abstract fun getLayoutId(): Int

    abstract fun onInit();

    abstract fun onEvent();

    open fun getLoading(): ProgressLoading = mLoading

    open fun getPFLoading(): PFLoadingDialog = this.mPFLoading!!

    protected fun showAlertDialog(msg: String) {
        AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show()
    }

    protected fun showAlertDialog(@StringRes resId: Int) {
        showAlertDialog(getString(resId))
    }

    /**
     * show option dialog
     */
    protected fun showMessageOptionDialog(message: String, listener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes) { dialog, id ->
                    listener?.onClick(dialog, id)
                }
                .setNegativeButton(
                        android.R.string.no
                ) { dialog, id -> dialog.cancel() }.show()
    }

    fun showMessage(title: String?, message: String) {
        // ignore show dialog if activity stopped
        if (isFinishing) {
            return
        }

        showDialogMessage(title!!, message)
    }

    /**
     * Open new fragment
     */
    public open fun replaceFragment(fragment: Fragment) {
        val fragmentTransactionAccount: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransactionAccount.replace(R.id.mFrameContainer, fragment)
        fragmentTransactionAccount.commit()
    }

    /**
     * check current fragment id
     *
     * @return
     */
    open fun getCurrentFragment(): BaseFragment? {
        return supportFragmentManager.findFragmentById(R.id.mFrameContainer) as BaseFragment?
    }

    /**
     *  if (!popFragment(getSupportFragmentManager()))
     */
    open fun popFragment(fm: FragmentManager?): Boolean {
        if (fm != null) {
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack()
                return true
            }
            val fragList: List<Fragment> = fm.getFragments()
            if (fragList != null && fragList.size > 0) {
                for (frag in fragList) {
                    if (frag == null) {
                        continue
                    }
                    if (frag.isVisible()) {
                        if (popFragment(frag.getChildFragmentManager())) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    /**
     * show message and title
     *
     * @param title
     * @param message
     */
    fun showDialogMessage(title: String, message: String) {
        if (!isFinishing) {
            AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(
                            android.R.string.ok
                    ) { dialog, which -> dialog.cancel() }.show()
        }
    }
}
