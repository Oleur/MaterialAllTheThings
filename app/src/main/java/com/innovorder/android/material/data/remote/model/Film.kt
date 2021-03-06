package com.innovorder.android.material.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Film(
    val title: String,
    @SerialName("episode_id") val episodeId: Int,
    @SerialName("opening_crawl") val openingCrawl: String,
    val director: String,
    val producer: String,
    val url: String
)
