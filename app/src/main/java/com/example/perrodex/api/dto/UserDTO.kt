package com.example.perrodex.api.dto

import com.squareup.moshi.Json

class UserDTO (
    val id: String,
    val email: String,
    @field:Json(name = "authentication_token") val authenticationToken: String
)