package com.herdi.yusli.githubappherdiyusli.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGithubConfig {
    companion object {
        fun getApiService(): ApiServiceGithub {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiServiceGithub::class.java)
        }
    }
}