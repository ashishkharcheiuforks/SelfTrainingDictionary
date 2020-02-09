package ru.sportmaster.android.driven.salespoint.domain.base.result

import ru.sportmaster.android.driven.salespoint.domain.base.failure.Failure
import timber.log.Timber

suspend fun <R, T> Result<R>.andThen(onSuccess: suspend (R) -> T): Result<T> {
    if (this is Result.Success) {
        try {
            return Result.Success(onSuccess(this.result))
        } catch (e: Exception) {
            Timber.e(e, "andThen")
            return Result.Error(e.message ?: "Error")
        }
    }
    if (this is Result.Fail) {
        return Result.Fail(failure)
    }
    throw IllegalStateException("Unknown type ${this.javaClass.simpleName}")
}

suspend fun <R> Result<R>.andThenComplete(onSuccess: suspend (R) -> Unit): CompleteResult {
    if (this is Result.Success) {
        try {
            val result = this.result
            onSuccess(result)
            return Result.Complete()
        } catch (e: Exception) {
            Timber.e(e, "andThen")
            return Result.Error(e.message ?: "Error")
        }
    }
    if (this is Result.Fail) {
        return Result.Fail(failure)
    }
    throw IllegalStateException("Unknown type ${this.javaClass.simpleName}")
}

suspend fun <R> Result<R>.doOnSuccess(onSuccess: suspend (R) -> Unit): Result<R> {
    if (this is Result.Success) {
        try {
            val result = this.result

            onSuccess(result)
            return Result.Success(result)

        } catch (e: Exception) {
            Timber.e(e, "map")
            return Result.Error(e.message ?: "Error")
        }
    }
    if (this is Result.Fail) {
        return Result.Fail(failure)
    }
    throw IllegalStateException("Unknown type ${this.javaClass.simpleName}")
}

fun <R> Result<R>.doOnSuccess_(onSuccess: (R) -> Unit): Result<R> {
    if (this is Result.Success) {
        try {
            onSuccess(this.result)
            return Result.Success(this.result)
        } catch (e: Exception) {
            Timber.e(e, "map")
            return Result.Error(e.message ?: "Error")
        }
    }
    if (this is Result.Fail) {
        return Result.Fail(failure)
    }
    throw IllegalStateException("Unknown type ${this.javaClass.simpleName}")
}

suspend fun <R, T> Result<R>.andThenResult(onSuccess: suspend (R) -> Result<T>): Result<T> {
    if (this is Result.Success) {
        try {
            return onSuccess(this.result)
        } catch (e: Exception) {
            Timber.e(e, "andThenResult")
            return Result.Error(e.message ?: "Error")
        }
    }
    if (this is Result.Fail) {
        return Result.Fail(failure)
    }
    throw IllegalStateException("Unknown type ${this.javaClass.simpleName}")
}

fun <R> Result<R>.mapFail(onFailure: (Failure) -> Failure): Result<R> {
    if (this is Result.Fail) {
        val failure = onFailure(this.failure)
        if (failure == this.failure) {
            return this
        }
        return Result.Fail(failure)
    }
    return this
}

