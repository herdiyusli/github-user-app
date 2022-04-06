package com.herdi.yusli.githubappherdiyusli.fitur.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herdi.yusli.githubappherdiyusli.data.api.ApiGithubConfig
import com.herdi.yusli.githubappherdiyusli.data.database.FavUserRepository
import com.herdi.yusli.githubappherdiyusli.data.database.Favorite
import com.herdi.yusli.githubappherdiyusli.data.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private val mFavUserRepository: FavUserRepository = FavUserRepository(application)
    fun insert(id: Int, username: String, img_url: String) {
        mFavUserRepository.insertUserFav(username, id, img_url)
    }

    fun check(id: Int): LiveData<List<Favorite>> {
        return mFavUserRepository.checkIsFavorite(id)
    }

    fun delete(id: Int) {
        mFavUserRepository.deleteUserFav(id)
    }


    fun setUserDetailProfil(username: String) {
        ApiGithubConfig.getApiService()
            .getDetailUserProfile(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, thr: Throwable) {
                    Log.d("Failed To Set Data", thr.message!!)
                }

            })
    }

    fun getDetailUserProfile(): LiveData<DetailUserResponse> {
        return user
    }


}

