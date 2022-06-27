package com.example.perrodex.doglist

import com.example.perrodex.R
import com.example.perrodex.model.Dog
import com.example.perrodex.api.ApiResponseStatus
import com.example.perrodex.api.DogApi.retrofitService
import com.example.perrodex.api.dto.AddDogToUserDTO
import com.example.perrodex.api.dto.DogDTOMapper
import com.example.perrodex.api.makeNetworkCall
import com.example.perrodex.api.responses.DogListApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(Dispatchers.IO) {
            val allDogsListResponse = downloadDogs()
            val userDogsListResponse = getUserDogs()

            if (allDogsListResponse is ApiResponseStatus.Error) {
                allDogsListResponse
            } else if (userDogsListResponse is ApiResponseStatus.Error) {
                userDogsListResponse
            } else if (allDogsListResponse is ApiResponseStatus.Success && userDogsListResponse is ApiResponseStatus.Success) {
                ApiResponseStatus.Success(
                    getCollectionList(
                        allDogsListResponse.data,
                        userDogsListResponse.data
                    )
                )
            } else {
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>): List<Dog> =
        allDogList.map {
            if (userDogList.contains(it)) {
                it
            } else {
                Dog(
                    0,
                    it.index,
                    name = "",
                    type = "",
                    heightFemale = "",
                    heightMale = "",
                    imageUrl = "",
                    lifeExpectancy = "",
                    temperament = "",
                    weightFemale = "",
                    weightMale = ""
                )
            }
        }.sorted()


    //Suspend ya que esta dentro de una corrutina, los recupera y los devuelve al viewModel - MutableLiveData
    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        //Descargar los datos desde una corrutina - IO (hilo secundario) indica en que ambito se va a utilizar la corrutina -> BBDD etc. - MAIN se ejecuta en el hilo principal
        //return withContext(Dispatchers.IO){
        val dogListApiResponse = retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    suspend fun addDogToUser(dogIg: String): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogIg)
        val defaultResponse = retrofitService.addDogToUser(addDogToUserDTO)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getUserDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }
}
