package com.example.perrodex.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perrodex.model.Dog
import com.example.perrodex.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {

    //Encapsulamiento, queremos que solo se pueda editar desde este ViewModel (MutableLiveData) pero no desde fuera de la clase(LiveData)
    //Por lo que creamos una copia
    //Observamos con el observer creado en DogListActivity
    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<Any>>()
    val status: LiveData<ApiResponseStatus<Any>>
        get() = _status

    //Este repository es llamado por la corrutina de downloadDogs
    private val dogRepository = DogRepository()

    init {
        getDogCollection()
    }

    fun addDogToUser(dogId: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            dogRepository.addDogToUser(dogId)
        }
    }

    private fun getDogCollection(){
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogCollection())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dogList.value = apiResponseStatus.data!!
        }

        _status.value = apiResponseStatus as ApiResponseStatus<Any>
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            getDogCollection()
        }

        _status.value = apiResponseStatus
    }


    /*Ejecuta una corrutina para recuperar los perros
    private fun downloadDogs() {
        //Crea una corrutina en un ViewModel, lanzamos esa corrutina y en downloadDogs descargamos la lista de perros
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.downloadDogs())
        }
    }*/
}