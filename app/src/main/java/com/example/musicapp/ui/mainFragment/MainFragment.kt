package com.example.musicapp.ui.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.data.DataSource
import com.example.musicapp.databinding.MainFragmentBinding
import com.example.musicapp.domain.Repository
import com.example.musicapp.ui.adapter.AlbumAdapter
import com.example.musicapp.valueObject.Resource
import com.example.musicapp.viewmodel.MainViewModel
import com.example.musicapp.viewmodel.factory.VMFactory


class MainFragment : Fragment() {

    private lateinit var albumAdapter: AlbumAdapter
    private val viewModel by viewModels<MainViewModel> {
        VMFactory(
            Repository(
                DataSource()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = MainFragmentBinding.bind(view)
        binding.message.text = "funciona"

        setupRecyclerView(binding.rvAlbum)

        viewModel.fetchAlbumList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    binding.message.text = "${it.data[0].name}"
                    albumAdapter = AlbumAdapter(requireContext(), it.data)
                    binding.rvAlbum.adapter = albumAdapter
                }
                is Resource.Failure -> { binding.message.text = "${it.exception.message}"}
            }
        }
    }

    fun setupRecyclerView(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL) )

    }

}