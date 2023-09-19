package com.example.githubuserapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.githubuserapplication.data.response.ListUserItem
import com.example.githubuserapplication.databinding.ActivityDetailBinding
import com.example.githubuserapplication.ui.adapter.DetailAdapter
import com.example.githubuserapplication.ui.viewModel.DetailViewModel
import com.example.githubuserapplication.utils.Constant
import com.example.githubuserapplication.utils.Constant.TAB_TITLES
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(Constant.EXTRA_USER)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val detailAdapter = DetailAdapter(this, username!!)
        viewPager.adapter = detailAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.getDetailUser(username).observe(this) { user ->
            if (user != null) {
                setDetailUser(user)
            }
        }
    }

    private fun setDetailUser(user: ListUserItem) {
        with(binding) {
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .into(ivAvatar)
            tvName.text = user.name
            tvUserName.text = user.login
            tvCompany.text = user.company
            tvLocation.text = user.location
            tvTotalRepo.text = user.publicRepos.toString()
            tvTotalFollowers.text = user.followers.toString()
            tvTotalFollowing.text = user.following.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}