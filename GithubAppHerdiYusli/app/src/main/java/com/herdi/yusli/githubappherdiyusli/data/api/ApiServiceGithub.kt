package com.herdi.yusli.githubappherdiyusli.data.api


import com.herdi.yusli.githubappherdiyusli.BuildConfig
import com.herdi.yusli.githubappherdiyusli.data.response.DetailUserResponse
import com.herdi.yusli.githubappherdiyusli.data.response.User
import com.herdi.yusli.githubappherdiyusli.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceGithub {
    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getDetailUserProfile(
        @Path("username") username: String
    ): Call<DetailUserResponse>


    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}