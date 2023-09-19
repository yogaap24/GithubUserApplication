package com.example.githubuserapplication.data.retrofit

import com.example.githubuserapplication.data.response.GithubResponse
import com.example.githubuserapplication.data.response.ListUserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUsers(): Call<List<ListUserItem>>

    @GET("search/users")
    fun getGithubUsers(
        @Query ("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path ("username") username: String
    ): Call<ListUserItem>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path ("username") username: String
    ): Call<List<ListUserItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path ("username") username: String
    ): Call<List<ListUserItem>>
}