package com.williamsimon.android.pokemonapp.network

object FakeAndroidPokemonService : PokemonService {

    var pokemonServiceData: List<Pokemon> = ArrayList<Pokemon>()

    override suspend fun getPokemons(): PokemonList {
        val pokemonList = PokemonList(
            pokemonServiceData.size,
            "",
            "",
            pokemonServiceData.map {
                ResultItem(it.name, "")
            }
        )
        return pokemonList
    }

    override suspend fun getPokemonDetails(name: String): Pokemon {
        val pokemon = pokemonServiceData.firstOrNull { it.name == name }
        return pokemon!!
    }

    override suspend fun getPokemonsPage(limit: Int, offset: Int): PokemonList {
        val pokemonList = PokemonList(
            pokemonServiceData.size,
            "",
            "",
            pokemonServiceData.map {
                ResultItem(it.name, "")
            }
        )
        return pokemonList
    }

}