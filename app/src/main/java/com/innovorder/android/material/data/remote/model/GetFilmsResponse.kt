package com.innovorder.android.material.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GetFilmsResponse(
    val count: Int,
    val next: Int?,
    val previous: Int?,
    val results: List<Film>
)
