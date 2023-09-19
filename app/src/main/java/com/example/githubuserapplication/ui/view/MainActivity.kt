package com.example.githubuserapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapplication.data.response.ListUserItem
import com.example.githubuserapplication.databinding.ActivityMainBinding
import com.example.githubuserapplication.ui.adapter.UserAdapter
import com.example.githubuserapplication.ui.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.listGithubUser.observe(this) { listGithubUser ->
            if (listGithubUser.isNotEmpty()) {
                setUserData(listGithubUser)
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val query = searchView.text.toString().trim()
                    searchBar.text = searchView.text
                    searchView.hide()
                    if (query.isNotEmpty()) {
                        mainViewModel.findUsers(query)
                    } else {
                        mainViewModel.initUsers()
                    }
                    false
                }
        }
        mainViewModel.initUsers()
    }

    private fun setUserData(listGithubUser: List<ListUserItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listGithubUser)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}