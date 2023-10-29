package com.example.fetech.`interface`

import com.example.fetech.models.items_data
import retrofit2.Response
import retrofit2.http.GET

interface FetechAPI {
    @GET("/hiring.json")
    suspend fun getData() : Response<items_data>

}