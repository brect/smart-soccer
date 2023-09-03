package com.padawanbr.smartsoccer.presentation.extensions

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun RecyclerView.attachHideShowFab(fab: FloatingActionButton) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy > 0 && fab.isShown) {
                // Scroll para baixo e o FloatingActionButton está visível, oculta o FAB.
                fab.hide()
            } else if (dy < 0 && !fab.isShown) {
                // Scroll para cima e o FloatingActionButton está oculto, mostra o FAB.
                fab.show()
            }
        }
    })
}


fun RecyclerView.initRecyclerView(
    adapter: RecyclerView.Adapter<*>,
    layoutManager: RecyclerView.LayoutManager? = null
) {
    this.layoutManager = layoutManager ?: this.layoutManager
    this.adapter = adapter
}
