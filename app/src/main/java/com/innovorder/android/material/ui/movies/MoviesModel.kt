package com.innovorder.android.material.ui.movies

import android.os.Parcelable
import com.innovorder.android.material.domain.entity.FilmEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesModel(
    val films: List<FilmEntity>?
) : Parcelable
