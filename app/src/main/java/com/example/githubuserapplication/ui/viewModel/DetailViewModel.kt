package com.example.githubuserapplication.ui.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapplication.data.response.ListUserItem
import com.example.githubuserapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val user = MutableLiveData<ListUserItem>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollow = MutableLiveData<List<ListUserItem>>()
    val listFollow: LiveData<List<ListUserItem>> = _listFollow

    fun getDetailUser(username: String): MutableLiveData<ListUserItem> {
        _isLoading.value = true
        ApiConfig.getApiService().getDetailUser(username).enqueue(object : Callback<ListUserItem> {
            override fun onResponse(
                call: Call<ListUserItem>,
                response: Response<ListUserItem>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    user.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListUserItem>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return user
    }

    fun getUserFollowers(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getFollowers(username).enqueue(object : Callback<List<ListUserItem>> {
            override fun onResponse(call: Call<List<ListUserItem>>, response: Response<List<ListUserItem>>) {
                _isLoading.value = false
                val list = response.body()
                if (list.isNullOrEmpty()) {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                } else {
                    _listFollow.postValue(list)
                }
            }

            override fun onFailure(call: Call<List<ListUserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUserFollowing(username: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getFollowing(username).enqueue(object : Callback<List<ListUserItem>> {
            override fun onResponse(call: Call<List<ListUserItem>>, response: Response<List<ListUserItem>>) {
                _isLoading.value = false
                val list = response.body()
                if (list.isNullOrEmpty()) {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                } else {
                    _listFollow.postValue(list)
                }
            }

            override fun onFailure(call: Call<List<ListUserItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}