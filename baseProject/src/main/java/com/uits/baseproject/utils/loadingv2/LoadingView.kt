package com.uits.baseproject.utils.loadingv2

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.uits.baseproject.R

class LoadingView private constructor() {
    companion object {
        private var dialogACProgressFlower: ACProgressFlower? = null
        private var dialog: Dialog? = null

        fun show(context: Context, message: String? = null) {
            if (dialog != null && dialog!!.isShowing) {
                // Already showing
                return
            }
            val builder = AlertDialog.Builder(context)
            val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
            builder.setView(view)
            builder.setCancelable(false)
            dialog = builder.create()
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (dialog?.isShowing == true)
                dialog?.show()
        }

        fun dismiss() {
            dialog?.dismiss()
            dialog = null
        }

        fun showLoadingACP(context: Context) {
            if (dialogACProgressFlower != null && dialogACProgressFlower!!.isShowing) {
                // Already showing
                return
            }

            dialogACProgressFlower = ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build()

            dialogACProgressFlower?.show()
        }

        fun dismissLoadingACP() {
            dialogACProgressFlower?.dismiss()
            dialogACProgressFlower = null
        }
    }
}