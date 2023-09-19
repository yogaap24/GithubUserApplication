package com.example.githubuserapplication.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapplication.ui.fragment.FollowFragment
import com.example.githubuserapplication.utils.Constant.EXTRA_POSITION
import com.example.githubuserapplication.utils.Constant.EXTRA_USER
import com.example.githubuserapplication.utils.Constant.TAB_TITLES

class DetailAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putString(EXTRA_USER, username)
            putInt(EXTRA_POSITION, position + 1)
        }
        return fragment
    }
}