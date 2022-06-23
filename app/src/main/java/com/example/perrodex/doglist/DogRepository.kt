package com.example.perrodex.doglist

import com.example.perrodex.model.Dog
import com.example.perrodex.api.ApiResponseStatus
import com.example.perrodex.api.DogApi.retrofitService
import com.example.perrodex.api.dto.DogDTOMapper
import com.example.perrodex.api.makeNetworkCall

class DogRepository {

    //Suspend ya que esta dentro de una corrutina, los recupera y los devuelve al viewModel - MutableLiveData
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        //Descargar los datos desde una corrutina - IO (hilo secundario) indica en que ambito se va a utilizar la corrutina -> BBDD etc. - MAIN se ejecuta en el hilo principal
        //return withContext(Dispatchers.IO){
        val dogListApiResponse = retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }
}
