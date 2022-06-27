package com.example.perrodex.api

import com.example.perrodex.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response.error
import java.net.UnknownHostException

private const val UNAUTHORIZED_ERROR_CODE = 401

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> = withContext(Dispatchers.IO) {
    try {
        ApiResponseStatus.Success(call())
    } catch (u: UnknownHostException) {
        ApiResponseStatus.Error(R.string.unknown_host_exception_error)
    } catch (h: HttpException) {
        val errorMessage = if(h.code() == UNAUTHORIZED_ERROR_CODE){
            R.string.wrong_user_or_password
        } else {
            R.string.unknown_error
        }
        ApiResponseStatus.Error(errorMessage)
    } catch (e: Exception) {
        val errorMessage = when (e.message) {
            "sign_up_error" -> R.string.error_sign_up
            "sign_in_error" -> R.string.error_sign_in
            "user_already_exists" -> R.string.user_already_exists
            "error_adding_dog" -> R.string.error_adding_dog
            else -> R.string.unknown_error
        }
        ApiResponseStatus.Error(errorMessage)
    }
}

