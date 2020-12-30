package com.example.musicapp.ui.albumFragment

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
import com.example.musicapp.data.remote.NetworkDataSource
import com.example.musicapp.data.model.Album
import com.example.musicapp.databinding.FragmentAlbumBinding
import com.example.musicapp.domain.Repository
import com.example.musicapp.ui.adapter.AlbumAdapter
import com.example.musicapp.utils.*
import com.example.musicapp.valueObject.Resource
import com.example.musicapp.viewmodel.MainViewModel
import com.example.musicapp.viewmodel.factory.VMFactory


class AlbumFragment : Fragment() {

    private lateinit var albumAdapter: AlbumAdapter
    private val viewModel by activityViewModels<MainViewModel> {
        VMFactory(
            Repository(
                NetworkDataSource()
            )
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAlbumBinding.bind(view)

        setupSearchView(binding.searchView)
        setupRecyclerView(binding.rvAlbum)
        setupObservers(binding)
    }

    private fun setupObservers(binding: FragmentAlbumBinding) {
        viewModel.fetchAlbumList.observe(viewLifecycleOwner) { resource ->
            binding.progressBar.showIf { resource is Resource.Loading }
            when (resource) {
                is Resource.Loading -> { }
                is Resource.Success -> {
                    albumAdapter = AlbumAdapter(requireContext(), resource.data, object: OnItemClickListener<Album> {
                        override fun onItemClick(item: Album) {
                            viewModel.setAlbumId(item.albumId)
                            viewModel.searchViewText = item.name
                            findNavController().navigate(R.id.action_albumFragment_to_songFragment)
                        }

                    })
                    binding.rvAlbum.adapter = albumAdapter
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
            findNavController().navigate(R.id.action_albumFragment_to_artistFragment)
        }
    }

}