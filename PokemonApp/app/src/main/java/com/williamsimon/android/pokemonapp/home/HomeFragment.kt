package com.williamsimon.android.pokemonapp.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.williamsimon.android.pokemonapp.*
import com.williamsimon.android.pokemonapp.databinding.FragmentHomeBinding
import com.williamsimon.android.pokemonapp.network.PokemonApi

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var viewModelFactory: HomeViewModelFactory

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home, container, false)

        //viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModelFactory =
            HomeViewModelFactory(
                (requireContext().applicationContext as PokemonApplication).pokemonService
            )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(HomeViewModel::class.java)

        binding.homeViewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.navigateToPokemonDetails.observe(this, Observer {pokemon ->
            pokemon?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                        pokemon
                    )
                )
                viewModel.onPokemonDetailsNavigated()
            }
        })

        linearLayoutManager = LinearLayoutManager(context)
        binding.pokemonRecycler.layoutManager = linearLayoutManager

        val adapter =
            PokemonAdapter(
                PokemonListener { pokemonName ->
                    viewModel.onPokemonClicked(pokemonName)
                })
        binding.pokemonRecycler.adapter = adapter

        binding.pokemonRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    viewModel.loadMorePokemons()
                }
            }
        })

        viewModel.pokemons.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.results)
            }
        })

        if(activity!! is MainActivity) {
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.home)
        }

        return binding.root
    }


}
