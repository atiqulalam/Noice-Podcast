package com.noice.ui.view

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.noice.R

class LoaderDialog(context: Context) {

    private val dialogLoading: Dialog
    private val view: View

    init {
        val builder = AlertDialog.Builder(context)
        view = View.inflate(context, R.layout.loading_dialog, null)
        builder.setView(view)
        dialogLoading = builder.create()
        dialogLoading.setCancelable(true)
        dialogLoading.setCanceledOnTouchOutside(false)
    }

    fun show() {
        dialogLoading.setCancelable(false)
        dialogLoading.show()
    }

    fun hide() {
        dialogLoading.dismiss()
    }
}