package com.innovorder.android.material.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.innovorder.android.material.databinding.ItemMainBinding
import com.innovorder.android.material.domain.entity.FilmEntity

class MoviesAdapter : ListAdapter<FilmEntity, MoviesAdapter.MainViewHolder>(POST_COMPARATOR) {

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<FilmEntity>() {

            override fun areContentsTheSame(
                oldItem: FilmEntity,
                newItem: FilmEntity
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: FilmEntity,
                newItem: FilmEntity
            ): Boolean = oldItem.episodeId == newItem.episodeId

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(
        ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainViewHolder(
        private val binding: ItemMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmEntity) {
            binding.name.text = item.title
        }

    }

}
