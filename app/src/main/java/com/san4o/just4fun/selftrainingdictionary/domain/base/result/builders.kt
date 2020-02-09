package ru.sportmaster.android.driven.salespoint.domain.base.result

import ru.sportmaster.android.driven.salespoint.domain.base.failure.Failure
import timber.log.Timber

suspend fun runSequantly(
    runner1: suspend () -> CompleteResult,
    runner2: suspend () -> CompleteResult
): CompleteResult {
    val result1 = runner1()
    if (result1.isFailure) {
        return result1
    }

    val result2 = runner2()
    if (result2.isFailure) {
        return result2
    }

    return Result.Success(null)
}

inline fun runComplete(
    name: String = "runComplete",
    run: () -> Unit
): CompleteResult {
    return runCompleteResult(
        name = name,
        run = {
            run.invoke()
            Result.Success(null)
        }
    )
}

inline fun runComplete(
    name: String = "runComplete",
    run: () -> Unit,
    catchException: (Throwable) -> Failure = { Failure.Error() }
): CompleteResult {
    return runCompleteResult(
        name = name,
        run = {
            run.invoke()
            Result.Success(null)
        },
        catchException = catchException
    )
}

inline fun runCompleteResult(
    name: String = "runCompleteResult",
    run: () -> Result<Unit?>,
    catchException: (Throwable) -> Failure = { Failure.Error() }
): CompleteResult {
    return runResult(name, run, catchException)
}

inline fun <T> runSuccess(
    name: String = "runResult ERROR",
    run: () -> T
): Result<T> {
    return runResult(
        name = name,
        run = {
            Result.Success(run())
        }
    )
}

inline fun <T> runSuccess(
    name: String = "runResult ERROR",
    run: () -> T,
    catchException: (Throwable) -> Failure = { Failure.Error() }
): Result<T> {
    return runResult(
        name = name,
        run = {
            Result.Success(run())
        },
        catchException = catchException
    )
}

inline fun <T> runResult(
    name: String = "runResult ERROR",
    run: () -> Result<T>
): Result<T> {
    return try {
        run()
    } catch (e: Throwable) {
        Timber.e(e, name)
        Result.Fail(Failure.Error())
    }
}

inline fun <T> runResult(
    name: String = "runResult ERROR",
    run: () -> Result<T>,
    catchException: (Throwable) -> Failure = { Failure.Error() }
): Result<T> {
    return try {
        run()
    } catch (e: Throwable) {
        Timber.e(e, name)
        Result.Fail(catchException(e))
    }
}

inline fun <T> runNonNullResult(
    name: String = "runResult ERROR",
    run: () -> T?
): Result<T> {
    return try {
        val t = run()
        if (t == null) {
            return Result.Fail(Failure.NotFound)
        }
        return Result.Success(t)
    } catch (e: Throwable) {
        Timber.e(e, name)
        Result.Fail(Failure.Error())
    }
}

inline fun <T, R> runMapResult(
    name: String = "runNullMapResult ERROR",
    run: () -> T?,
    map: (T) -> R
): Result<R> {
    return try {
        val r = run()
        if (r == null) {
            return Result.Fail(Failure.NotFound)
        }
        Result.Success(map(r))
    } catch (e: Throwable) {
        Timber.e(e, name)
        Result.Fail(Failure.Error())
    }
}

