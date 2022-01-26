package com.hdev.exoplayer

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class PageItemDecoration(context: Context, @DimenRes horizontalMarginTopInDp: Int, @DimenRes horizontalMarginLeft: Int) : RecyclerView.ItemDecoration() {
    private val marginInPx = context.resources.getDimension(horizontalMarginTopInDp).toInt()
    private val marginInPxLeft = context.resources.getDimension(horizontalMarginLeft).toInt()
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = marginInPx
        outRect.left = marginInPxLeft
    }
}