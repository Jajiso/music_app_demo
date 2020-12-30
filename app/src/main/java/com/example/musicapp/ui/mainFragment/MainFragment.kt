package com.example.musicapp.ui.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.musicapp.R
import com.example.musicapp.data.remote.NetworkDataSource
import com.example.musicapp.databinding.MainFragmentBinding
import com.example.musicapp.utils.*
import com.example.musicapp.domain.Repository
import com.example.musicapp.viewmodel.MainViewModel
import com.example.musicapp.viewmodel.factory.VMFactory


class MainFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel> {
        VMFactory(
                Repository(
                        NetworkDataSource()
                )
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = MainFragmentBinding.bind(view)

        setupSearchView(binding.searchView)
    }

    private fun setupSearchView(sv: SearchView) {
        sv.queryHint = viewModel.searchViewText
        sv.onQueryTextChanged { artistName ->
            viewModel.searchViewText = artistName
            viewModel.setSearchByArtistName(artistName)
            findNavController().navigate(R.id.action_mainFragment_to_artistFragment)
        }
    }

}