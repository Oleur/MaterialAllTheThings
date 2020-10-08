package com.innovorder.android.material.ui

sealed class Result<out T : Any>
object Empty : Result<Nothing>()
data class Success<out T : Any>(val data: T) : Result<T>()
data class Error(val exception: Throwable) : Result<Nothing>()
