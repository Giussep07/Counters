package com.cornershop.counterstest.presentation.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.cornershop.counterstest.R

object AlertDialogUtil {

    fun generalDialog(
        context: Context,
        title: String?,
        message: String,
        isCancelable: Boolean = true,
        textButtonAccept: String = context.resources.getString(R.string.delete),
        textButtonCancel: String = context.resources.getString(R.string.cancel),
        action: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setCancelable(isCancelable)
            .setMessage(message)
            .setNegativeButton(textButtonCancel) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(textButtonAccept) { dialog, _ ->
                action()
                dialog.dismiss()
            }
            .show()
    }
}