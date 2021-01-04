package com.example.musicapp.ui.songListFragment

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
import com.example.musicapp.base.OnItemClickListener
import com.example.musicapp.data.local.AppDatabase
import com.example.musicapp.data.local.LocalDataSource
import com.example.musicapp.data.model.Song
import com.example.musicapp.data.remote.NetworkDataSource
import com.example.musicapp.databinding.FragmentSongListBinding
import com.example.musicapp.domain.Repository
import com.example.musicapp.ui.adapter.SongAdapter
import com.example.musicapp.utils.*
import com.example.musicapp.valueObject.Resource
import com.example.musicapp.viewmodel.MainViewModel
import com.example.musicapp.viewmodel.SongViewModel
import com.example.musicapp.viewmodel.factory.VMFactory


class SongListFragment : Fragment() {

    private lateinit var songAdapter: SongAdapter
    private val viewModel by activityViewModels<MainViewModel> {
        VMFactory(
            Repository(
                NetworkDataSource(),
                LocalDataSource(
                    AppDatabase.getDatabase( requireContext() )
                )
            )
        )
    }
    private val songViewModel by activityViewModels<SongViewModel> {
        VMFactory(
            Repository(
                NetworkDataSource(),
                LocalDataSource(
                    AppDatabase.getDatabase( requireContext() )
                )
            )
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSongListBinding.bind(view)

        setupSearchView(binding.searchView)
        setupRecyclerView(binding.rvSong)
        setupObservers(binding)
    }

    private fun setupObservers(binding: FragmentSongListBinding) {
        viewModel.fetchSongList.observe(viewLifecycleOwner) { resource ->
            binding.progressBar.showIf { resource is Resource.Loading }
            when (resource) {
                is Resource.Loading -> { }
                is Resource.Success -> {
                    songAdapter = SongAdapter(requireContext(), resource.data, object: OnItemClickListener<Song> {
                        override fun onItemClick(item: Song) {
                            songViewModel.setSong(item)
                            findNavController().navigate(R.id.action_songListFragment_to_songFragment)
                        }
                    })
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
            findNavController().navigate(R.id.action_songListFragment_to_artistFragment)
        }
    }
}