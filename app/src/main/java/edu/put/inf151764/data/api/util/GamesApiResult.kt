package edu.put.inf151764.data.api.util

import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response

sealed class GamesApiResult<out D> {
    data class Success<out D>(val data: D) : GamesApiResult<D>()
    data class Exception<out D>(val error: GamesApiError) : GamesApiResult<D>()
}

sealed class GamesApiError {
    data class Http(val code: Int, val msg: String, val throwable: Throwable) : GamesApiError()
    object Accepted : GamesApiError()
    data class Unknown(val msg: String?, val throwable: Throwable) : GamesApiError()
}

suspend fun <D> wrapApiCall(
    apiCall: suspend () -> Response<D>
): GamesApiResult<D> = try {
    val response = apiCall()
    if (response.code() == 202) {
        throw AcceptedResponse()
    }
    GamesApiResult.Success(data = response.body() ?: throw Exception("No body"))
} catch (t: Throwable) {
    GamesApiResult.Exception(
        error = when (t) {
            is HttpException -> GamesApiError.Http(t.code(), t.message(), t)
            is AcceptedResponse -> GamesApiError.Accepted
            else -> GamesApiError.Unknown(t.message, t)
        }
    )
}

class AcceptedResponse: Exception()