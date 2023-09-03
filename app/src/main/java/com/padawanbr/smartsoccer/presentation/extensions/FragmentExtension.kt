package com.padawanbr.smartsoccer.presentation.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.padawanbr.smartsoccer.R

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

fun Fragment.navControllerAndClearStack() =
    NavHostFragment.findNavController(this).navigate(
        R.id.nav_graph, null,
        NavOptions.Builder()
            .setPopUpTo(findNavController().graph.startDestinationId, true).build()
    )

fun RecyclerView.initRecyclerView(
    adapter: RecyclerView.Adapter<*>,
    layoutManager: RecyclerView.LayoutManager? = null
) {
    this.layoutManager = layoutManager ?: this.layoutManager
    this.adapter = adapter
}
