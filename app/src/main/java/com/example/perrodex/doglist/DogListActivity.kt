package com.example.perrodex.doglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perrodex.databinding.ActivityDogListBinding//

//Cuando iniciemos esta activity
class DogListActivity : AppCompatActivity() {

    //Instanciar el ViewModel -> despues se ejecuta init de DogListViewModel que va a recuperar la lista de perros
    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.dogRecycler
        recycler.layoutManager = LinearLayoutManager(this)

        val adapter = DogAdapter()
        recycler.adapter = adapter

        //Para leer un LiveData, debemos crear un observer desde esta activity que nos devolvera el valor que tiene dogList en ese momento
        dogListViewModel.dogList.observe(this){
            //Actualizamos la lista en el adapter, solo observamos ya que no podemos editar el viewModel
            dogList ->
            adapter.submitList(dogList)
        }
    }
}