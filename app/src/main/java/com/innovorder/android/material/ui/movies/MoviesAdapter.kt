package com.innovorder.android.material.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.innovorder.android.material.R
import com.innovorder.android.material.databinding.ItemMainBinding
import com.innovorder.android.material.domain.entity.FilmEntity
import com.squareup.picasso.Picasso

class MoviesAdapter(
    private val callback: (movie: FilmEntity, cover: ImageView) -> Unit
) : ListAdapter<FilmEntity, MoviesAdapter.MainViewHolder>(POST_COMPARATOR) {

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
        ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        callback
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.bind(getItem(position))

    class MainViewHolder(
        private val binding: ItemMainBinding,
        private val callback: (movie: FilmEntity, cover: ImageView) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilmEntity) {
            with(binding) {
                name.text = item.title
                director.text = binding.root.context.getString(R.string.director, item.director)
                Picasso.get()
                    .load(getBackdrop(item.episodeId))
                    .fit()
                    .into(cover)

                cover.transitionName = item.episodeId.toString()
                root.setOnClickListener { callback(item, cover) }
            }
        }

        private fun getBackdrop(filmId: Int): Int = when (filmId) {
            1 -> R.drawable.starwars_backdrop_1
            2 -> R.drawable.starwars_backdrop_2
            3 -> R.drawable.starwars_backdrop_3
            4 -> R.drawable.starwars_backdrop_4
            5 -> R.drawable.starwars_backdrop_5
            else -> R.drawable.starwars_backdrop_6
        }

    }

}
