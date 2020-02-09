package ru.sportmaster.android.driven.salespoint.domain.base.result

import ru.sportmaster.android.driven.salespoint.domain.base.failure.Failure

typealias CompleteResult = Result<Unit?>

sealed class Result<out R> {

    data class Fail(val failure: Failure) : Result<Nothing>()
    data class Success<out R>(val result: R) : Result<R>()

    val isFailure get() = this is Fail
    val isSuccess get() = this is Success<R>

    fun failure(failure: Failure) =
        Fail(failure)

    fun <R> success(r: R) =
        Success(r)

    fun handleFailure(onFailure: (Failure) -> Unit = {}): Any {
        return handle(onSuccess = {}, onFailure = onFailure)
    }

    fun handle(onSuccess: (R) -> Unit = {}, onFailure: (Failure) -> Unit = {}): Any =
        when (this) {
            is Fail -> onFailure(this.failure)
            is Success -> onSuccess(this.result)
        }

    companion object {
        fun Complete() = Result.Success(null)
        fun Error(message: String = "") = Result.Fail(Failure.Error(message))
        fun Exception(exception: Exception) = Result.Fail(Failure.Exception(exception))
    }
}
