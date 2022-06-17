package com.example.perrodex.doglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perrodex.Dog
import kotlinx.coroutines.launch

class DogListViewModel: ViewModel() {

    //Encapsulamiento, queremos que solo se pueda editar desde este ViewModel (MutableLiveData) pero no desde fuera de la clase(LiveData)
    //Por lo que creamos una copia
    //Observamos con el observer creado en DogListActivity
    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>>
        get() = _dogList

    //Este repository es llamado por la corrutina de downloadDogs
    private val dogRepository = DogRepository()

    init {
        downloadDogs()
    }

    //Ejecuta una corrutina para recuperar los perros
    private fun downloadDogs() {
        //Crea una corrutina en un ViewModel, lanzamos esa corrutina y en downloadDogs descargamos la lista de perros
        viewModelScope.launch {
            _dogList.value = dogRepository.downloadDogs()
        }
    }
}