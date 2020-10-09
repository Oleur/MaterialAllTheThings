package com.innovorder.android.material.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovorder.android.material.domain.usecase.GetFilmsUseCase
import com.innovorder.android.material.ui.Empty
import com.innovorder.android.material.ui.Error
import com.innovorder.android.material.ui.Result
import com.innovorder.android.material.ui.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MoviesViewModel @ViewModelInject constructor(
    private val getFilmsUseCase: GetFilmsUseCase
) : ViewModel() {

    val state = MutableStateFlow<Result<MoviesModel>>(Empty)

    fun getFilms() {
        viewModelScope.launch {
            getFilmsUseCase.getFilms()
                .map { MoviesModel(it) }
                .catch { exception -> state.value = Error(exception) }
                .collect { mainModel -> state.value = Success(mainModel) }
        }
    }

}
