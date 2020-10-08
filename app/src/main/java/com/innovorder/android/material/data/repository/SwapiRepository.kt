package com.innovorder.android.material.data.repository

import com.innovorder.android.material.data.remote.mapper.toFilmEntity
import com.innovorder.android.material.data.remote.model.Film
import com.innovorder.android.material.data.remote.service.SwapiService
import com.innovorder.android.material.domain.entity.FilmEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SwapiRepository @Inject constructor(
    private val remote: SwapiService
) {

    fun getFilms(): Flow<List<FilmEntity>> = flow {
        val films = remote.getFilms().results
        emit(films.map(Film::toFilmEntity))
    }.flowOn(Dispatchers.IO)

}
