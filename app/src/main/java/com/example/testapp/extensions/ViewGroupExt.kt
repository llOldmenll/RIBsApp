package com.example.testapp.extensions

import android.app.AlertDialog
import android.view.ViewGroup
import com.example.testapp.R

fun ViewGroup.showErrorDialog(error: Throwable, onDismiss: () -> Unit) {
    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.error))
        .setMessage(error.message)
        .setCancelable(false)
        .setPositiveButton(context.getString(android.R.string.ok), null)
        .setOnDismissListener { onDismiss() }
        .show()
}