package com.example.perrodex.api

import com.example.perrodex.*
import com.example.perrodex.api.dto.LogInDTO
import com.example.perrodex.api.dto.SignUpDTO
import com.example.perrodex.api.responses.DogListApiResponse
import com.example.perrodex.api.responses.AuthApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService{
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(LOG_IN_URL)
    suspend fun logIn(@Body logInDTO: LogInDTO): AuthApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse
}

object DogApi {
    val retrofitService: ApiService by lazy {
        //lazy -> No crees esto hasta que se llame por primera vez
        retrofit.create(ApiService::class.java)
    }
}