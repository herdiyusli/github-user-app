package com.herdi.yusli.githubappherdiyusli.data.response

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val username: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("avatar_url")
    val img_user: String
)