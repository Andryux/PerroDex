package com.example.perrodex.doglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.perrodex.Dog
import com.example.perrodex.databinding.DogListItemBinding

class DogAdapter: ListAdapter<Dog, DogAdapter.DogViewHolder>(DiffCallback){
    //De que manera tiene que responder cuando se edita un elemento
    companion object DiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id
        }
    }

    //Creamos viewHolder, para cada perro se crea 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))
        return DogViewHolder(binding)
    }

    //Pintamos viewHolder
    override fun onBindViewHolder(dogViewHolder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        dogViewHolder.bind(dog)
    }

    //Setteamos los valores en el view, podemos hacerlo aqui o directamente en el xml del layout
    inner class DogViewHolder(private val binding: DogListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog){
            binding.dogName.text = dog.name
        }
    }
}