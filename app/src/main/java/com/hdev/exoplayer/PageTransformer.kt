package com.hdev.exoplayer

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class PageTransformer: ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val resources = page.context.resources
        val nextItemVisibleMarginInPx = resources.getDimension(R.dimen.viewpager_next_item)
        val currentItemMarginInPx = resources.getDimension(R.dimen.viewpager_next_item)
        val pageTranslationX = nextItemVisibleMarginInPx + currentItemMarginInPx
        page.translationX = - pageTranslationX * position
        //page.scaleY = 1 - (0.15f * abs(position))
    }
}