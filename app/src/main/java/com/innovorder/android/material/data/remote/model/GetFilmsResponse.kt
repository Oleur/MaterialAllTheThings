package com.innovorder.android.material.data.remote.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class GetFilmsResponse(
    val count: Int,
    val next: Int?,
    val previous: Int?,
    val results: List<Film>
) : Parcelable
