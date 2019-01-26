package com.tayfuncesur.moviepedia.base

import com.tayfuncesur.moviepedia.model.Status


data class Resource<out T>(val status: Status, val data: T?, val cause: String?) {
    companion object {
        fun <T> whenLoading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> whenSuccess(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> whenError(cause: String?): Resource<T> {
            return Resource(Status.ERROR, null, cause)
        }
    }
}