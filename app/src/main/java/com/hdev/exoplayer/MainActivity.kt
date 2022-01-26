package com.hdev.exoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        supportActionBar?.apply {
            subtitle = "ExoPlayer - smooth video player \n streaming in ViewPager2"
        }
    }

    /**
     * initialize all views
     */
    private fun initViews() {
        view_pager.apply {
            addItemDecoration(PageItemDecoration(this@MainActivity, R.dimen.viewpager_current_item, R.dimen.view_pager_margin_left))
            setPageTransformer(PageTransformer())
            offscreenPageLimit = getVideos().size
            adapter = ViewPagerAdapter(this@MainActivity, getVideos())
        }
    }
}