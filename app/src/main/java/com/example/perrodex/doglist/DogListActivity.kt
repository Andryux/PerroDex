package com.example.perrodex.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perrodex.api.ApiResponseStatus
import com.example.perrodex.databinding.ActivityDogListBinding//
import com.example.perrodex.dogdetail.DogDetailActivity
import com.example.perrodex.dogdetail.DogDetailActivity.Companion.DOG_KEY

private const val GRID_SPAN_COUNT = 3

//Cuando iniciemos esta activity
class DogListActivity : AppCompatActivity() {

    //Instanciar el ViewModel -> despues se ejecuta init de DogListViewModel que va a recuperar la lista de perros
    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingWheel = binding.loadingWheel

        val recycler = binding.dogRecycler
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)

        val adapter = DogAdapter()

        adapter.setOnItemClickListener {
            //Pasar el Dog a DogDetailActivity. Para pasar un objeto entre activities ese objeto debe ser parcelable
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }

        adapter.setLongOnItemClickListener {
            dogListViewModel.addDogToUser(it.id.toString())
        }
        recycler.adapter = adapter


        //Para leer un LiveData, debemos crear un observer desde esta activity que nos devolvera el valor que tiene dogList en ese momento
        dogListViewModel.dogList.observe(this) {
            //Actualizamos la lista en el adapter, solo observamos ya que no podemos editar el viewModel
                dogList ->
            adapter.submitList(dogList)
        }

        dogListViewModel.status.observe(this) { status ->

            when (status) {
                is ApiResponseStatus.Error -> {
                    loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }
                is ApiResponseStatus.Loading -> loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> loadingWheel.visibility = View.GONE
            }
        }
    }
}