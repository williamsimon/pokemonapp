package com.williamsimon.android.pokemonapp

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.williamsimon.android.pokemonapp.network.PokemonApi
import com.williamsimon.android.pokemonapp.network.PokemonService

object ServiceLocator {

    @Volatile
    var pokemonService: PokemonService? = null
        @VisibleForTesting set

    private val lock = Any()

    fun providePokemonService(context: Context): PokemonService {
        synchronized(this){
            return pokemonService ?: createPokemonService(context)
        }
    }

    private fun createPokemonService(context: Context): PokemonService {
        return PokemonApi
    }

    @VisibleForTesting
    fun resetService(){
        synchronized(lock){
            pokemonService = null
        }
    }

}