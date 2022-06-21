package com.example.perrodex.api

import com.example.perrodex.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Success(call())
        } catch (u: UnknownHostException) {
            ApiResponseStatus.Error(R.string.unknown_host_exception_error)
        } catch (e: Exception) {
            ApiResponseStatus.Error(R.string.unknown_error)
        }
    }
}