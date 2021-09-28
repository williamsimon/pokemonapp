package com.williamsimon.android.pokemonapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.williamsimon.android.pokemonapp.network.PokemonService

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val pokemonService: PokemonService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                pokemonService
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}