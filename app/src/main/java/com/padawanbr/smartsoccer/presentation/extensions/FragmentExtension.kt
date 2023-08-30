package com.padawanbr.alfred.app.presentation.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showShortToast(@StringRes textResId: Int) =
    Toast.makeText(
        requireContext(),
        textResId,
        Toast.LENGTH_LONG
    ).show()

fun Fragment.showShortToast(text: String) =
    Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_LONG
    ).show()


