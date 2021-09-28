package com.williamsimon.android.pokemonapp.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.williamsimon.android.pokemonapp.home.PokemonApiStatus
import com.williamsimon.android.pokemonapp.network.Pokemon
import com.williamsimon.android.pokemonapp.network.PokemonApi
import com.williamsimon.android.pokemonapp.network.PokemonService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel(pokemonName: String, private val pokemonService: PokemonService) : ViewModel() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private var _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon>
        get() = _pokemon

    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus>
        get() = _status

    val types = Transformations.map(pokemon) { pokemon ->
        var result = ""
        for(i in 0 until pokemon.types?.size){
            if(i == 0){
                result = pokemon.types[i].type.name
            } else {
                result += " / " + pokemon.types[i].type.name
            }
        }
        result
    }

    val abilities = Transformations.map(pokemon) { pokemon ->
        var result = ""
        for(i in 0 until pokemon.abilities.size){
            if(i == 0){
                result = pokemon.abilities[i].ability.name
            } else {
                result += " / " + pokemon.abilities[i].ability.name
            }
        }
        result
    }

    val forms = Transformations.map(pokemon) { pokemon ->
        var result = ""
        for(i in 0 until pokemon.forms.size){
            if(i == 0){
                result = pokemon.forms[i].name
            } else {
                result += " / " + pokemon.forms[i].name
            }
        }
        result
    }

    init {
        getPokemon(pokemonName)
    }

    private fun getPokemon(name: String) {
        viewModelScope.launch {
            try {
                _status.value = PokemonApiStatus.LOADING
                var detailsResult = pokemonService.getPokemonDetails(name)
                _status.value = PokemonApiStatus.DONE
                _pokemon.value = detailsResult
                Log.i("retrofit", detailsResult.toString())
            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR
                Log.i("retrofit", e.message)
            }
        }
    }

}