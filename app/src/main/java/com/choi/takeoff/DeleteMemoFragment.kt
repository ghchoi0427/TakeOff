package com.choi.takeoff

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.choi.takeoff.ui.memo.NewMemoViewModel
import com.choi.takeoff.ui.memo.NewMemoViewModelFactory
import com.choi.takeoff.util.StorageManager

class DeleteMemoFragment(private val rowId: Int) : DialogFragment() {

    private val newMemoViewModel: NewMemoViewModel by viewModels {
        NewMemoViewModelFactory((activity?.application as GlobalApplication).repository)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_confirm_delete_memo)
                .setPositiveButton(R.string.confirm) { dialog, id ->
                    context?.let { context ->
                        StorageManager.deleteFile(
                            newMemoViewModel.memoById(rowId).picture,
                            context
                        )
                    }
                    newMemoViewModel.deleteMemoById(rowId)
                }
                .setNegativeButton(R.string.cancel) { dialog, id ->
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}