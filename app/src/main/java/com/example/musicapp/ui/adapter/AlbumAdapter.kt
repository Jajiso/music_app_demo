package com.example.musicapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.musicapp.base.BaseAdapter
import com.example.musicapp.base.BaseViewHolder
import com.example.musicapp.base.OnItemClickListener
import com.example.musicapp.data.model.Album
import com.example.musicapp.databinding.CardAlbumBinding

class AlbumAdapter(
    private val context: Context,
    albumList: List<Album>,
    private val itemClickListener: OnItemClickListener<Album>
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
            Glide.with(context)
                .load(item.image)
                .centerCrop()
                .into(imageView)
            tvAlbumName.text = item.name
            tvAlbumDateRelease.text = item.releaseDate
            itemView.setOnClickListener { itemClickListener.onItemClick(item) }
        }
    }
}