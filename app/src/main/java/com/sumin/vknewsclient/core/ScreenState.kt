package com.sumin.vknewsclient.core

import android.database.sqlite.SQLiteException
import android.os.NetworkOnMainThreadException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.text.ParseException

sealed interface ScreenState<out T> {
    data object Loading : ScreenState<Nothing>

    data class Success<out T>(
        val data: T
    ) : ScreenState<T>

    data class Failure(
        val e: Throwable,
        val message: String?
    ) : ScreenState<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<ScreenState<T>> {
    return this
        .map<T, ScreenState<T>> { ScreenState.Success(it) }
        .onStart { emit(ScreenState.Loading) }
        .catch { ex ->
            emit(
                ScreenState.Failure(
                    e = ex,
                    message = handleException(ex)
                )
            )
        }
}

suspend fun <T> Flow<T>.collectAsResult(
    onSuccess: suspend (T) -> Unit = {},
    onError: suspend (exception: Throwable?, message: String?) -> Unit = { _, _ -> },
    onLoading: () -> Unit = {}
) {
    asResult().collect { result ->
        when (result) {
            is ScreenState.Success -> onSuccess(result.data)
            is ScreenState.Failure -> {
                val httpsError = handleException(result.e)
                onError(result.e, httpsError)
            }
            ScreenState.Loading -> onLoading()
        }
    }
}

fun handleException(exception : Throwable?) : String {
    return when(exception) {
        is HttpException -> parseHttpException(exception)
        is IOException -> "Произошла ошибка при загрузке данных, проверьте подключение к сети"
        is JsonSyntaxException,is JsonParseException -> "Ошибка при обработке данных"
        is NetworkOnMainThreadException -> "Сетевая операция на главном потоке"
        is SecurityException -> "Проблема с безопасностью"
        is IllegalArgumentException -> "Некорректные аргументы"
        is SQLiteException -> "Ошибка базы данных"
        is OutOfMemoryError -> "Недостаточно памяти"
        is ParseException -> "Ошибка при анализе данных"
        else -> "Неизвестная ошибка: ${exception?.localizedMessage}"
    }
}

private fun parseHttpException(exception : HttpException) : String {

    val statusCode = exception.code()
    val errorBody = exception.response()?.errorBody()?.string()

    val defaultErrorMessage = "Произошла ошибка, пожалуйста попробуйте позже."

    return when(statusCode) {
        HttpURLConnection.HTTP_BAD_REQUEST -> errorBody ?: "Неверный запрос."
        HttpURLConnection.HTTP_UNAUTHORIZED -> errorBody ?: "Пустой или неправильный токен."
        HttpURLConnection.HTTP_PAYMENT_REQUIRED,
        HttpURLConnection.HTTP_FORBIDDEN -> errorBody ?: "Превышен лимит запросов."

        HttpURLConnection.HTTP_NOT_FOUND -> errorBody ?: "Ресурс не найден."
        429 -> errorBody ?: "Слишком много запросов. Общий лимит - 20 запросов в секунду."
        in 300..399 -> errorBody ?: "Ошибка клиента: $statusCode."
        in 500..599 -> "Ошибка сервера: $statusCode."
        else -> defaultErrorMessage
    }

}
