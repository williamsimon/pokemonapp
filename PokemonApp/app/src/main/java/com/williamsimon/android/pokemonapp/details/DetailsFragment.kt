package com.williamsimon.android.pokemonapp.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.williamsimon.android.pokemonapp.MainActivity
import com.williamsimon.android.pokemonapp.PokemonApplication
import com.williamsimon.android.pokemonapp.details.DetailsFragmentArgs
import com.williamsimon.android.pokemonapp.R
import com.williamsimon.android.pokemonapp.databinding.FragmentDetailsBinding
import com.williamsimon.android.pokemonapp.network.PokemonApi

/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel

    private lateinit var viewModelFactory: DetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentDetailsBinding>(inflater,
            R.layout.fragment_details, container, false)

        val detailsFragmentArgs by navArgs<DetailsFragmentArgs>()

        if(activity!! is MainActivity) {
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.details)
        }

        viewModelFactory =
            DetailsViewModelFactory(
                detailsFragmentArgs.namePokemon,
                (requireContext().applicationContext as PokemonApplication).pokemonService
            )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailsViewModel::class.java)

        binding.detailsViewModel = viewModel
        binding.setLifecycleOwner(this)



        return binding.root
    }


}
