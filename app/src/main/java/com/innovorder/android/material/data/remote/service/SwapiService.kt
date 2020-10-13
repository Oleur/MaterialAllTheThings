package com.innovorder.android.material.data.remote.service

import com.innovorder.android.material.data.remote.model.Film
import com.innovorder.android.material.data.remote.model.GetFilmsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SwapiService {

    @GET("api/films")
    suspend fun getFilms(): GetFilmsResponse

    @GET("api/films/{film_id}")
    suspend fun getFilm(@Path("film_id") filmId: Int): Film

}
