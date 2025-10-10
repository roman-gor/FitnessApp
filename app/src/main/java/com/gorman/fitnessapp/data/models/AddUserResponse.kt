package com.gorman.fitnessapp.data.models

import com.google.gson.annotations.SerializedName

data class AddUserResponse(
    @SerializedName("success")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("user_id")
    val userId: Int
)
