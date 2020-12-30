package com.example.musicapp.ui.songFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.data.remote.NetworkDataSource
import com.example.musicapp.databinding.FragmentSongBinding
import com.example.musicapp.domain.Repository
import com.example.musicapp.ui.adapter.SongAdapter
import com.example.musicapp.utils.*
import com.example.musicapp.valueObject.Resource
import com.example.musicapp.viewmodel.MainViewModel
import com.example.musicapp.viewmodel.factory.VMFactory


class SongFragment : Fragment() {

    private lateinit var songAdapter: SongAdapter
    private val viewModel by activityViewModels<MainViewModel> {
        VMFactory(
            Repository(
                NetworkDataSource()
            )
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSongBinding.bind(view)

        setupSearchView(binding.searchView)
        setupRecyclerView(binding.rvSong)
        setupObservers(binding)
    }

    private fun setupObservers(binding: FragmentSongBinding) {
        viewModel.fetchSongList.observe(viewLifecycleOwner) { resource ->
            binding.progressBar.showIf { resource is Resource.Loading }
            when (resource) {
                is Resource.Loading -> { }
                is Resource.Success -> {
                    songAdapter = SongAdapter(requireContext(), resource.data)
                    binding.rvSong.adapter = songAdapter
                }
                is Resource.Failure -> {
                    showToast("There has been an error: ${resource.exception.message}")
                }
            }
        }
    }

    private fun setupRecyclerView(rv: RecyclerView) {
        rv.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun setupSearchView(sv: SearchView) {
        sv.queryHint = viewModel.searchViewText

        sv.onQueryTextChanged { artistName ->
            viewModel.searchViewText = artistName
            viewModel.setSearchByArtistName(artistName)
            findNavController().navigate(R.id.action_songFragment_to_artistFragment)
        }
    }
}