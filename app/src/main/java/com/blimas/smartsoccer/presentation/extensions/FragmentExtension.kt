package com.blimas.smartsoccer.presentation.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.blimas.smartsoccer.R

fun Fragment.showShortToast(@StringRes textResId: Int) =
    Toast.makeText(
        requireContext(),
        textResId,
        Toast.LENGTH_SHORT
    ).show()

fun Fragment.showShortToast(text: String) =
    Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_LONG
    ).show()

fun Fragment.showLoadingToast(text: String = "Carregando...") =
    Toast.makeText(
        requireContext(),
        text,
        Toast.LENGTH_LONG
    ).show()

fun Fragment.navControllerAndClearStack() =
    NavHostFragment.findNavController(this).navigate(
        R.id.nav_graph, null,
        NavOptions.Builder()
            .setPopUpTo(findNavController().graph.startDestinationId, true).build()
    )
