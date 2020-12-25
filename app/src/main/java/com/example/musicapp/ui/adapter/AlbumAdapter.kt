package com.example.musicapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.musicapp.base.BaseAdapter
import com.example.musicapp.base.BaseViewHolder
import com.example.musicapp.data.model.Album
import com.example.musicapp.databinding.CardAlbumBinding

class AlbumAdapter(
    private val context: Context,
    private var albumList: List<Album>
) : BaseAdapter<Album>(albumList) {

    override fun setViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Album> {
        val itemBinding = CardAlbumBinding.inflate(LayoutInflater.from(context), parent, false)

        return AlbumViewHolder(itemBinding)
    }


    private inner class AlbumViewHolder(
        val binding: CardAlbumBinding) : BaseViewHolder<Album>(binding.root) {

        override fun bind(item: Album) = with(binding) {
            tvAlbumName.text = item.name
            tvAlbumDateRelease.text = item.date
        }

    }



}