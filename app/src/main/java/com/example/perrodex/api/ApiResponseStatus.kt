package com.example.perrodex.api

import com.example.perrodex.Dog

//Al incluir <T> indicamos que ApiResponseStatus va a funcionar para cualquier tipo de datos que metamos
//Creamos un ApiResponseStatus ya no de una lista de perros si no de una clase T genérica
sealed class ApiResponseStatus<T> {
    class Success<T>(val data: T): ApiResponseStatus<T>()
    class Loading<T>: ApiResponseStatus<T>()
    class Error<T>(val messageId: Int): ApiResponseStatus<T>()
}

/*
enum class ApiResponseStatus
{
    LOADING,
    ERROR,
    SUCCESS
}
*/