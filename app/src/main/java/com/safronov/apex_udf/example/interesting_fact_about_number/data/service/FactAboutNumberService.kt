package com.safronov.apex_udf.example.interesting_fact_about_number.data.service

import com.safronov.apex_udf.example.interesting_fact_about_number.data.model.output.FactAboutNumberDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FactAboutNumberService {

    @GET("/{number}?json")
    suspend fun getFactAboutNumber(@Path("number") number: Long): Response<FactAboutNumberDTO>

    @GET("/random/math?json")
    suspend fun getFactAboutRandomNumber(): Response<FactAboutNumberDTO>

    companion object {
        const val BASE_URL = "http://numbersapi.com"
    }

}