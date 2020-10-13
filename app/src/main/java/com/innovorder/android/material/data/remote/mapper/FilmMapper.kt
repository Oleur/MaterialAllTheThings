package com.innovorder.android.material.data.remote.mapper

import com.innovorder.android.material.data.remote.model.Film
import com.innovorder.android.material.domain.entity.FilmEntity

fun Film.toFilmEntity() = FilmEntity(title, episodeId, openingCrawl, director, producer, url)