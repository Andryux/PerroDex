package com.example.perrodex.auth

import com.example.perrodex.api.ApiResponseStatus
import com.example.perrodex.api.DogApi
import com.example.perrodex.api.dto.SignUpDTO
import com.example.perrodex.api.dto.UserDTOMapper
import com.example.perrodex.api.makeNetworkCall
import com.example.perrodex.model.User

class AuthRepository {

    suspend fun signUp(email: String, password: String, passwordConfirmation: String): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = DogApi.retrofitService.signUp(signUpDTO)

        if (!signUpResponse.isSuccess) {
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }
}