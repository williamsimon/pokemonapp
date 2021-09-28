package com.williamsimon.android.pokemonapp.network

import kotlinx.coroutines.Deferred

interface PokemonService {

    suspend fun getPokemons() : PokemonList

    suspend fun getPokemonDetails(name: String) : Pokemon

    suspend fun getPokemonsPage(limit: Int, offset: Int) : PokemonList

}