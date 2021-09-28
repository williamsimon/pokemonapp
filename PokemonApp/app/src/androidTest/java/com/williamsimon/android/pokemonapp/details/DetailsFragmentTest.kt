package com.williamsimon.android.pokemonapp.details

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
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

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class DetailsFragmentTest {

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
    fun selectedPokemonDetails_DisplayedInUi() = runBlockingTest{
        // Given available pokemons
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

        // When details fragment launched to display selected pokemon
        //val bundle = DetailsFragmentArgs("PokemonFirst").toBundle()
        val bundle = bundleOf("namePokemon" to "PokemonFirst")
        launchFragmentInContainer<DetailsFragment>(bundle, R.style.AppTheme)

        // Then pokemon details are displayed on the screen
        onView(withId(R.id.pokemonName)).check(matches(isDisplayed()))
        onView(withId(R.id.pokemonName)).check(matches(withText("PokemonFirst")))
        onView(withId(R.id.experienceText)).check(matches(isDisplayed()))
        onView(withId(R.id.experienceText)).check(matches(withText("23")))
        onView(withId(R.id.heightText)).check(matches(isDisplayed()))
        onView(withId(R.id.heightText)).check(matches(withText("14")))
        onView(withId(R.id.weightText)).check(matches(isDisplayed()))
        onView(withId(R.id.weightText)).check(matches(withText("65")))
        onView(withId(R.id.typesText)).check(matches(isDisplayed()))
        onView(withId(R.id.typesText)).check(matches(withText("TypeFirst")))
        onView(withId(R.id.abilitiesText)).check(matches(isDisplayed()))
        onView(withId(R.id.abilitiesText)).check(matches(withText("AbilityFirst")))
        onView(withId(R.id.formsText)).check(matches(isDisplayed()))
        onView(withId(R.id.formsText)).check(matches(withText("FormFirst")))
    }

}