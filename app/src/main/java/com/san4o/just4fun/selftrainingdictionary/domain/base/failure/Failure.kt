package ru.sportmaster.android.driven.salespoint.domain.base.failure

sealed class Failure {

    object Empty : Failure()
    object NotFound : Failure()
    data class Error(val message: String = "Error") : Failure()
    data class Exception(val exception: kotlin.Exception) : Failure()

    /** * Extend this class for feature specific failures.*/
    open class Feature(val message: String = "Error") : Failure()

    class FeatureEnum(val failure: FeatureFailure) : Failure()

}

// for sealed class
interface FeatureFailure