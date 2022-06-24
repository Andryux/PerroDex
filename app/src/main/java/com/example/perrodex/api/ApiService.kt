package com.example.perrodex.api

import com.example.perrodex.*
import com.example.perrodex.api.dto.AddDogToUserDTO
import com.example.perrodex.api.dto.LogInDTO
import com.example.perrodex.api.dto.SignUpDTO
import com.example.perrodex.api.responses.DogListApiResponse
import com.example.perrodex.api.responses.AuthApiResponse
import com.example.perrodex.api.responses.DefaultResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(LOG_IN_URL)
    suspend fun logIn(@Body logInDTO: LogInDTO): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse
}

object DogApi {
    val retrofitService: ApiService by lazy {
        //lazy -> No crees esto hasta que se llame por primera vez
        retrofit.create(ApiService::class.java)
    }
}