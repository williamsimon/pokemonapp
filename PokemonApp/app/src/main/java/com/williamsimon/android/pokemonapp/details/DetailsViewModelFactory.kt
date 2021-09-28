package com.williamsimon.android.pokemonapp.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.williamsimon.android.pokemonapp.network.PokemonService

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(private val pokemonName: String, private val pokemonService: PokemonService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(
                pokemonName, pokemonService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}