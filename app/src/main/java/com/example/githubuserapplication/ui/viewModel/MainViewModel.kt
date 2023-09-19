package com.example.githubuserapplication.ui.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapplication.data.response.GithubResponse
import com.example.githubuserapplication.data.response.ListUserItem
import com.example.githubuserapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listGithubUser = MutableLiveData<List<ListUserItem>>()
    val listGithubUser: LiveData<List<ListUserItem>> = _listGithubUser

    fun initUsers() {
        _isLoading.value = true
        ApiConfig.getApiService().getUsers().enqueue(object : Callback<List<ListUserItem>> {
            override fun onResponse(
                call: Call<List<ListUserItem>>,
                response: Response<List<ListUserItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listGithubUser.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ListUserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun findUsers(query: String? = null) {
        _isLoading.value = true
        ApiConfig.getApiService().getGithubUsers(query ?: "").enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listGithubUser.value = response.body()?.items as List<ListUserItem>?
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}