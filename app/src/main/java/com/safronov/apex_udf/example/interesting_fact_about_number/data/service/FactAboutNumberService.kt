package com.safronov.apex_udf.example.interesting_fact_about_number.data.service

import com.safronov.apex_udf.example.interesting_fact_about_number.data.model.output.FactAboutNumberDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FactAboutNumberService {

    @GET("$API/{number}")
    fun getFactAboutNumber(@Path("number") number: Long): Response<FactAboutNumberDTO>

    @GET("$API/random/math")
    fun getFactAboutRandomNumber(): Response<FactAboutNumberDTO>

    companion object {
        private const val API = "http://numbersapi.com"
    }

}