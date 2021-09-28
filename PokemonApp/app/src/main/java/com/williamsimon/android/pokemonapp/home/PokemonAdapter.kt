package com.williamsimon.android.pokemonapp.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.williamsimon.android.pokemonapp.databinding.ListItemPokemonBinding
import com.williamsimon.android.pokemonapp.network.ResultItem

class PokemonAdapter(val clickListener: PokemonListener): ListAdapter<ResultItem, PokemonAdapter.ViewHolder>(
    PokemonDiffCallback()
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    class ViewHolder private constructor (val binding: ListItemPokemonBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: PokemonListener, item: ResultItem){
            binding.pokemon = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPokemonBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }

    }

}

class PokemonDiffCallback : DiffUtil.ItemCallback<ResultItem>() {
    override fun areItemsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
        return oldItem == newItem
    }
}

class PokemonListener(val clickListener: (pokemonName: String) -> Unit) {
    fun onClick(pokemon: ResultItem) = clickListener(pokemon.name)
}