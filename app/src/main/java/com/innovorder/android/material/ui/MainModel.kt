package com.innovorder.android.material.ui

import android.os.Parcelable
import com.innovorder.android.material.domain.entity.FilmEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainModel(
    val films: List<FilmEntity>?
) : Parcelable
