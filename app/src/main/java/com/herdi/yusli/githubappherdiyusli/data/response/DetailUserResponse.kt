package com.herdi.yusli.githubappherdiyusli.data.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @SerializedName("login")
    val username: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("avatar_url")
    val img_user: String,

    @SerializedName("following_url")
    val following_url: String,

    @SerializedName("followers_url")
    val followers_url: String,

    @SerializedName("name")
    val nama_user: String,

    @SerializedName("company")
    val kantor: String,

    @SerializedName("public_repos")
    val repository: Int,

    @SerializedName("location")
    val alamat: String,

    @SerializedName("following")
    val jml_following: Int,

    @SerializedName("followers")
    val jml_followers: Int
)