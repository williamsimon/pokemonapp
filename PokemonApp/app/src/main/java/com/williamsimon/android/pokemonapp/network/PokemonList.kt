package com.williamsimon.android.pokemonapp.network

data class PokemonList(
    var count: Int,
    val next: String?,
    val previous: String?,
    var results: List<ResultItem>) {

    fun addTo(newPokemonList: PokemonList?) : PokemonList {
        newPokemonList?.let {
            count += newPokemonList.count
            results = newPokemonList.results + results
        }
        return this
    }

}