package com.williamsimon.android.pokemonapp.home

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.williamsimon.android.pokemonapp.R
import com.williamsimon.android.pokemonapp.ServiceLocator
import com.williamsimon.android.pokemonapp.network.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    private lateinit var service: FakeAndroidPokemonService

    @Before
    fun initRepository() {
        service = FakeAndroidPokemonService
        ServiceLocator.pokemonService = service
    }

    @After
    fun cleanup() = runBlockingTest {
        ServiceLocator.resetService()
    }

    @Test
    fun clickPokemon_navigateToDetailFragment() = runBlockingTest {

        // Given available pokemons
        addPokemonsData()

        // Given on the home screen
        val scenario = launchFragmentInContainer<HomeFragment>(Bundle(), R.style.AppTheme)
        val navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // When click on the first list item
        onView(withId(R.id.pokemonRecycler))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("PokemonFirst")), click()))


        // THEN - Verify that we navigate to the first detail screen
        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment( "PokemonFirst")
        )

    }


    private fun addPokemonsData(){
        service.pokemonServiceData = listOf(
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

    }

}