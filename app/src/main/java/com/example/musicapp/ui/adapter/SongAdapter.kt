package com.example.musicapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.musicapp.base.BaseAdapter
import com.example.musicapp.base.BaseViewHolder
import com.example.musicapp.base.OnItemClickListener
import com.example.musicapp.data.model.Song
import com.example.musicapp.databinding.CardSongBinding

class SongAdapter(
    private val context: Context,
    songList: List<Song>,
    private val itemClickListener: OnItemClickListener<Song>
) : BaseAdapter<Song>(songList) {

    override fun setViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Song> {
        val itemBinding = CardSongBinding.inflate(LayoutInflater.from(context), parent, false)
        return SongViewHolder(itemBinding)
    }

    private inner class SongViewHolder(
        val binding: CardSongBinding
    ) : BaseViewHolder<Song>(binding.root) {
        override fun bind(item: Song) = with(binding) {
            tvSongName.text = item.name
            itemView.setOnClickListener { itemClickListener.onItemClick(item) }
        }
    }
}