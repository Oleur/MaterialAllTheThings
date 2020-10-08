package com.innovorder.android.material.domain.usecase

import com.innovorder.android.material.data.repository.SwapiRepository
import com.innovorder.android.material.domain.entity.FilmEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilmsUseCase @Inject constructor(
    private val swapiRepository: SwapiRepository
) {

    fun getFilms(): Flow<List<FilmEntity>> = swapiRepository.getFilms()
}
