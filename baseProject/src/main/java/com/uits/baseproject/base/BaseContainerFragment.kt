package com.uits.baseproject.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.uits.baseproject.R

/**
 * Copyright © 2018 SOFT ONE  CO., LTD
 * Created by PhuQuy on 3/11/19.
 */
abstract class BaseContainerFragment : BaseFragment() {

    val currentFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.mFrameContainer) as BaseFragment?

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = childFragmentManager.beginTransaction()
        // transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.setCustomAnimations(R.anim.slide_in_right, 0)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.replace(R.id.mFrameContainer, fragment)
        transaction.commit()
    }

    fun addFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = childFragmentManager.beginTransaction()
        // transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.setCustomAnimations(R.anim.slide_in_right, 0)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.add(R.id.mFrameContainer, fragment)
        transaction.commit()
        // event call onResume fragment change title and icon
        childFragmentManager.addOnBackStackChangedListener {
            if (childFragmentManager.backStackEntryCount == 0) {
                if (currentFragment != null) {
                    currentFragment!!.onResume()
                }
            }
        }
    }

    fun replaceFragment(fragment: Fragment, bundle: Bundle?, addToBackStack: Boolean) {
        val transaction = childFragmentManager.beginTransaction()
        // TODO USING LATE
        // transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.setCustomAnimations(R.anim.slide_in_right, 0)
        if (bundle != null) {
            fragment.arguments = bundle
        }
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.replace(R.id.mFrameContainer, fragment)
        transaction.commit()
    }

    /**
     * @return true if exist fragment in BackStack, false if don't have any fragment
     */
    fun popFragment(): Boolean {
        var isPop = false
        if (childFragmentManager.backStackEntryCount > 0) {
            isPop = true
            childFragmentManager.popBackStack()
        }
        return isPop
    }

    /**
     * Clear all fragments in BackStack
     */
    fun popAllFragments() {
        try {
            if (childFragmentManager.backStackEntryCount > 0) {
                //getChildFragmentManager().popBackStack(getChildFragmentManager().getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        } catch (ignored: IllegalStateException) {
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (currentFragment != null)
            currentFragment!!.onActivityResult(requestCode, resultCode, data)
    }
}
