package com.williamsimon.android.pokemonapp.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.williamsimon.android.pokemonapp.network.PokemonApi
import com.williamsimon.android.pokemonapp.network.PokemonList
import com.williamsimon.android.pokemonapp.network.PokemonService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private val LIMIT = 20

enum class PokemonApiStatus { LOADING, ERROR, DONE }

class HomeViewModel(private val pokemonService: PokemonService): ViewModel() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private var offset = 0
    private var isLast = false

    private val _pokemons = MutableLiveData<PokemonList>()
    val pokemons: LiveData<PokemonList>
        get() = _pokemons

    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus>
        get() = _status

    private val _navigateToPokemonDetails = MutableLiveData<String>()
    val navigateToPokemonDetails
        get() = _navigateToPokemonDetails

    init {
        getPokemons()
    }

    fun onPokemonClicked(name: String) {
        _navigateToPokemonDetails.value = name
        _status.value = PokemonApiStatus.DONE
    }

    fun onPokemonDetailsNavigated() {
        _navigateToPokemonDetails.value = null
    }

    private fun getPokemons() {
        viewModelScope.launch {
            try {
                _status.value = PokemonApiStatus.LOADING
                var listResult = pokemonService.getPokemons()
                _status.value = PokemonApiStatus.DONE
                _pokemons.value = listResult
                offset += 20
                if(listResult.next == null){
                    isLast = true
                }
            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR
            }
        }
    }

    fun loadMorePokemons() {
        if(_status.value != PokemonApiStatus.LOADING && !isLast){
            viewModelScope.launch {
                try {
                    _status.value = PokemonApiStatus.LOADING
                    var listResult = pokemonService.getPokemonsPage(LIMIT, offset)
                    _status.value = PokemonApiStatus.DONE
                    _pokemons.value = listResult.addTo(_pokemons.value)
                    offset += 20
                } catch (e: Exception) {
                    _status.value = PokemonApiStatus.ERROR
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}