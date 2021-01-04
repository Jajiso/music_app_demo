package com.example.musicapp.ui.artistFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.base.OnItemClickListener
import com.example.musicapp.data.local.AppDatabase
import com.example.musicapp.data.local.LocalDataSource
import com.example.musicapp.data.remote.NetworkDataSource
import com.example.musicapp.data.model.Artist
import com.example.musicapp.databinding.FragmentArtistBinding
import com.example.musicapp.domain.Repository
import com.example.musicapp.ui.adapter.ArtistAdapter
import com.example.musicapp.utils.*
import com.example.musicapp.valueObject.Resource
import com.example.musicapp.viewmodel.MainViewModel
import com.example.musicapp.viewmodel.factory.VMFactory


class ArtistFragment : Fragment() {

    private lateinit var artistAdapter: ArtistAdapter
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_artist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtistBinding.bind(view)

        setupSearchView(binding.searchView)
        setupRecyclerView(binding.rvArtist)
        setupObservers(binding)
    }

    private fun setupObservers(binding: FragmentArtistBinding) {
        viewModel.fetchArtistList.observe(viewLifecycleOwner) { resource ->
            binding.progressBar.showIf { resource is Resource.Loading }
            when (resource) {
                is Resource.Loading -> { }
                is Resource.Success -> {
                    artistAdapter = ArtistAdapter(requireContext(), resource.data, object: OnItemClickListener<Artist> {
                        override fun onItemClick(item: Artist) {
                            viewModel.setArtistId(item.artistId)
                            viewModel.searchViewText = item.name
                            findNavController().navigate(R.id.action_artistFragment_to_albumFragment)
                        }

                    })
                    binding.rvArtist.adapter = artistAdapter
                }
                is Resource.Failure -> {
                    showToast("There has been an error: ${resource.exception.message}")
                }
            }
        }
    }

    private fun setupRecyclerView(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupSearchView(sv: SearchView) {
        sv.queryHint = viewModel.searchViewText
        sv.onQueryTextChanged { artistName ->
            viewModel.searchViewText = artistName
            viewModel.setSearchByArtistName(artistName)
        }
    }

}