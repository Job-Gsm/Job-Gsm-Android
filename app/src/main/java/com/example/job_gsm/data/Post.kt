package com.example.job_gsm.data

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("board_id") val boardId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("majors") val majors: List<String>,
    @SerializedName("deadline") val deadLine: String,
    @SerializedName("url") val url: String?,
    @SerializedName("view") val view: Int
)
