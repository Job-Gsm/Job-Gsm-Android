package com.example.job_gsm.data

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("content") val content: List<Post>
)