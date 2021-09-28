package com.williamsimon.android.pokemonapp.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.williamsimon.android.pokemonapp.getOrAwaitValue
import com.williamsimon.android.pokemonapp.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.hamcrest.core.IsEqual
import org.junit.After

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var pokemonService: FakePokemonService
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        pokemonService = FakePokemonService
        homeViewModel = HomeViewModel(pokemonService)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun selectPokemon_setsNavigateToDetailsEvent() {

        // When selecting a pokemon
        homeViewModel.onPokemonClicked("name")

        // Then the navigate to details event is triggered
        val value = homeViewModel.navigateToPokemonDetails.getOrAwaitValue()
        assertThat(value, `is`("name"))

    }

    @Test
    fun navigateToDetails_unsetsNavigateToDetailsEvent() {

        // When navigating to details
        homeViewModel.onPokemonDetailsNavigated()

        // Then the navigate to details event is completed
        val value = homeViewModel.navigateToPokemonDetails.getOrAwaitValue()
        assertThat(value, `is`(nullValue()))

    }

    @Test
    fun getPokemonsResponse_setsPokemonValuesAndStatusDone() = runBlockingTest {
        // Given available pokemons
        pokemonService.pokemonServiceData = listOf(
            Pokemon(1, "PokemonFirst", 23, 14, 65,
                listOf(Type(1, ResultItem("TypeFirst", ""))),
                listOf(Ability(1, false, ResultItem("AbilityFirst", ""))),
                listOf(ResultItem("FormFirst", ""))
                ),
            Pokemon(2, "PokemonSecond", 23, 14, 65,
                listOf(Type(1, ResultItem("TypeFirst", ""))),
                listOf(Ability(1, false, ResultItem("AbilityFirst", ""))),
                listOf(ResultItem("FormFirst", ""))
            ),
            Pokemon(3, "PokemonThird", 23, 14, 65,
                listOf(Type(1, ResultItem("TypeFirst", ""))),
                listOf(Ability(1, false, ResultItem("AbilityFirst", ""))),
                listOf(ResultItem("FormFirst", ""))
            )
        )

        // Then pokemons value is set and status value is done
        val pokemonsValue = homeViewModel.pokemons.getOrAwaitValue()
        assertThat(pokemonsValue, not(nullValue()))
        val statusValue = homeViewModel.status.getOrAwaitValue()
        assertThat(statusValue, `is`(PokemonApiStatus.DONE))
    }
}