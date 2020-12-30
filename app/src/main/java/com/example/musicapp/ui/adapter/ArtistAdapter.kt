package com.example.musicapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.musicapp.base.BaseAdapter
import com.example.musicapp.base.BaseViewHolder
import com.example.musicapp.base.OnItemClickListener
import com.example.musicapp.data.model.Artist
import com.example.musicapp.databinding.CardArtistBinding

class ArtistAdapter(
    private val context: Context,
    artistList: List<Artist>,
    private val itemClickListener: OnItemClickListener<Artist>
) : BaseAdapter<Artist>(artistList) {

    override fun setViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Artist> {
        val itemBinding = CardArtistBinding.inflate(LayoutInflater.from(context), parent, false)
        return ArtistViewHolder(itemBinding)
    }

    private inner class ArtistViewHolder(
        val binding: CardArtistBinding
    ) : BaseViewHolder<Artist>(binding.root) {
        override fun bind(item: Artist) = with(binding) {
            tvArtistName.text = item.name
            tvArtistGenre.text = item.genre
            itemView.setOnClickListener { itemClickListener.onItemClick(item) }
        }
    }
}