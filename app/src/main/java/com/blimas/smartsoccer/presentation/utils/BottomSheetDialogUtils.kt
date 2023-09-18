package com.blimas.smartsoccer.presentation.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetDialogUtils(private val context: Context, private val layoutInflater: LayoutInflater) {

    private fun createBottomSheetDialog(binding: ViewBinding): BottomSheetDialog {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(binding.root)
        return bottomSheetDialog
    }


    private fun setButtonClickListener(button: Button, bottomSheetDialog: BottomSheetDialog?, onClickListener: () -> Unit) {
        button.setOnClickListener {
            onClickListener.invoke()
            bottomSheetDialog?.dismiss()
        }
    }

}
