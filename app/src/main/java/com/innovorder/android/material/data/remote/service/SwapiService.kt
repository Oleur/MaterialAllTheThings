package com.innovorder.android.material.data.remote.service

import com.innovorder.android.material.data.remote.model.GetFilmsResponse
import retrofit2.http.GET

interface SwapiService {

    @GET("api/films")
    suspend fun getFilms(): GetFilmsResponse

}
