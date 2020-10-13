package com.innovorder.android.material.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovorder.android.material.domain.entity.FilmEntity
import com.innovorder.android.material.domain.usecase.GetFilmsUseCase
import com.innovorder.android.material.ui.Empty
import com.innovorder.android.material.ui.Error
import com.innovorder.android.material.ui.Result
import com.innovorder.android.material.ui.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MovieViewModel @ViewModelInject constructor(
        private val getFilmsUseCase: GetFilmsUseCase
) : ViewModel() {

    val state = MutableStateFlow<Result<FilmEntity>>(Empty)

    fun getFilm(filmId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getFilmsUseCase.getFilm(filmId)
                    .catch { exception ->
                        state.value = Error(exception)
                    }
                    .collect { film ->
                        state.value = Success(film)
                    }
        }
    }
}