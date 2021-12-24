package com.choi.takeoff

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class DeleteMemoFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_confirm_delete_memo)
                .setPositiveButton(R.string.confirm) { dialog, id ->
                    Toast.makeText(context,
                        "confirmed",
                        Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(R.string.cancel) { dialog, id ->
                    Toast.makeText(context,
                        "canceled",
                        Toast.LENGTH_SHORT).show()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}