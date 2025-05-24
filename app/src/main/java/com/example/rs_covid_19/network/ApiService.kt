package com.example.rs_covid_19.network

import com.example.rs_covid_19.model.Hospital
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/id/covid19/hospitals")
    fun getHospitals(): Call<List<Hospital>>
}
