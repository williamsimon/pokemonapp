package com.williamsimon.android.pokemonapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://pokeapi.co/api/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface PokemonApiService {

    @GET("pokemon")
    fun getPokemons(): Deferred<PokemonList>

    @GET("pokemon/{name}")
    fun getPokemonDetails(@Path("name") name: String): Deferred<Pokemon>

    @GET("pokemon")
    fun getPokemonsPage(@Query("limit") limit: Int, @Query("offset") offset: Int): Deferred<PokemonList>


}

object PokemonApi : PokemonService {
    private val retrofitService : PokemonApiService by lazy { retrofit.create(PokemonApiService::class.java) }

    override suspend fun getPokemons(): PokemonList {
        return retrofitService.getPokemons().await()
    }

    override suspend fun getPokemonDetails(name: String): Pokemon {
        return retrofitService.getPokemonDetails(name).await()
    }

    override suspend fun getPokemonsPage(limit: Int, offset: Int): PokemonList {
        return retrofitService.getPokemonsPage(limit, offset).await()
    }


}