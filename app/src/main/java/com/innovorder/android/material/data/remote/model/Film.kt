package com.innovorder.android.material.data.remote.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Film(
    val title: String,
    @Json(name = "episode_id") val episodeId: Int,
    val director: String,
    val producer: String,
    val url: String
) : Parcelable
