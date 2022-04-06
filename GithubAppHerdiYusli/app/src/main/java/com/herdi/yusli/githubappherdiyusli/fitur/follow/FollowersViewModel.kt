package com.herdi.yusli.githubappherdiyusli.fitur.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herdi.yusli.githubappherdiyusli.data.api.ApiGithubConfig
import com.herdi.yusli.githubappherdiyusli.data.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersViewModel : ViewModel() {
    val listUserFollowers = MutableLiveData<ArrayList<User>>()

    fun setListUserFollowers(username: String) {
        ApiGithubConfig.getApiService()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listUserFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, thr: Throwable) {
                    Log.d("Fail to set Followers", thr.message!!)
                }

            })
    }

    fun getListUserFollowers(): LiveData<ArrayList<User>> {
        return listUserFollowers
    }
}