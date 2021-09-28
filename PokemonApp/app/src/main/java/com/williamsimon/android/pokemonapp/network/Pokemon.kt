package com.williamsimon.android.pokemonapp.network

import com.squareup.moshi.Json

data class Pokemon(
    val id: Int,
    val name: String,
    @Json(name = "base_experience") val experience: Int,
    val height: Int,
    val weight: Int,
    val types: List<Type>,
    val abilities: List<Ability>,
    val forms: List<ResultItem>)
