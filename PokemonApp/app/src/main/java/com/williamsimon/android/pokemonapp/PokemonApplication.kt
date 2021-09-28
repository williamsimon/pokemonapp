package com.williamsimon.android.pokemonapp

import android.app.Application
import com.williamsimon.android.pokemonapp.network.PokemonService

class PokemonApplication : Application(){

    val pokemonService: PokemonService
        get() = ServiceLocator.providePokemonService(this)

}