package com.hdev.exoplayer

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (activity: AppCompatActivity , private val bannerList: List<String>) : FragmentStateAdapter(activity){
    override fun getItemCount() = bannerList.size
    override fun createFragment(position: Int): Fragment {
        return VideoPlayerFragment.newInstance(bannerList[position])
    }
}