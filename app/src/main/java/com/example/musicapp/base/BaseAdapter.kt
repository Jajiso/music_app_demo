package com.example.musicapp.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(private var dataList: List<T>): RecyclerView.Adapter<BaseViewHolder<T>>() {

    abstract fun setViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    fun updateList(dataList: List<T> ) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return setViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}