package com.herdi.yusli.githubappherdiyusli.fitur.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herdi.yusli.githubappherdiyusli.data.api.ApiGithubConfig
import com.herdi.yusli.githubappherdiyusli.data.response.User
import com.herdi.yusli.githubappherdiyusli.data.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()


    fun setSearchUsers(query: String) {
        ApiGithubConfig.getApiService()
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.list_items_user)

                    }
                }

                override fun onFailure(call: Call<UserResponse>, thr: Throwable) {
                    thr.message?.let { Log.d("Gagal Mencari", it) }
                }

            })
    }



    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}