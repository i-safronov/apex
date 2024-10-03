package com.safronov.apex_udf.example.interesting_fact_about_number.data.dao

import com.safronov.apex_udf.example.interesting_fact_about_number.data.model.output.FactAboutNumberDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NumberDao {

    @GET("http://numbersapi.com/{number}")
    fun getFactAboutNumber(@Path("number") number: Long): Response<FactAboutNumberDTO>

}