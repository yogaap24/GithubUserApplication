package com.example.githubuserapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapplication.data.response.ListUserItem
import com.example.githubuserapplication.databinding.FragmentFollowBinding
import com.example.githubuserapplication.ui.adapter.UserAdapter
import com.example.githubuserapplication.ui.viewModel.DetailViewModel
import com.example.githubuserapplication.utils.Constant.EXTRA_POSITION
import com.example.githubuserapplication.utils.Constant.EXTRA_USER


class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailViewModel
    private lateinit var userAdapter: UserAdapter

    private var username: String? = null
    private var position: Int? = null

    private val userList = MutableLiveData<List<ListUserItem>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(EXTRA_USER)
            position = it.getInt(EXTRA_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        userAdapter = UserAdapter()

        if (position == 1) {
            viewModel.getUserFollowers(username!!)
        } else {
            viewModel.getUserFollowing(username!!)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.listFollow.observe(viewLifecycleOwner) { listFollow ->
            userAdapter.submitList(listFollow)
        }

        // Set up RecyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.layoutManager = layoutManager
        binding.rvFollow.adapter = userAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}