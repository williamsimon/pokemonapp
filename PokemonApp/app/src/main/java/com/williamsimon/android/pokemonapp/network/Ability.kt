package com.williamsimon.android.pokemonapp.network

import com.squareup.moshi.Json

data class Ability(
    val slot: Int,
    @Json(name = "is_hidden")
    val isHidden: Boolean,
    val ability: ResultItem)

