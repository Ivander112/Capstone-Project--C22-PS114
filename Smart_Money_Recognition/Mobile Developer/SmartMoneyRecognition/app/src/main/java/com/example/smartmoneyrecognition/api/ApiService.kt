package com.example.smartmoneyrecognition.api

import com.example.smartmoneyrecognition.responses.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @Multipart
    @POST("upload")
    fun postImage(
        @Part img: MultipartBody.Part
    ): Call<ApiResponse>
}