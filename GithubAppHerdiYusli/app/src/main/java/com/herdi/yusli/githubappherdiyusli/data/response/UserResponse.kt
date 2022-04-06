package com.herdi.yusli.githubappherdiyusli.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("items")
    val list_items_user : ArrayList<User>,

    @SerializedName("total_count")
    val jumlah_pencarian : Int
)
