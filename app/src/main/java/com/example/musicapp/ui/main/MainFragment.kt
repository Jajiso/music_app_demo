package com.example.musicapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicapp.R
import com.example.musicapp.data.DataSource
import com.example.musicapp.databinding.MainFragmentBinding
import com.example.musicapp.domain.Repository
import com.example.musicapp.valueObject.Resource
import com.example.musicapp.viewmodel.MainViewModel
import com.example.musicapp.viewmodel.factory.VMFactory


class MainFragment : Fragment() {

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

        viewModel.fetchAlbumList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> { binding.message.text = "${it.data[0].name}" }
                is Resource.Failure -> { binding.message.text = "${it.exception.message}"}
            }
        }
    }

}