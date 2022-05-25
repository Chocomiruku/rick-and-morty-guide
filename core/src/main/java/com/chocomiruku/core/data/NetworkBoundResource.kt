package com.chocomiruku.core.data

import com.chocomiruku.core.data.Resource.Success
import com.chocomiruku.core.data.Resource.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

abstract class NetworkBoundResource<T>() {

    fun asFlow(): Flow<Resource<T>> = flow {
        // check if should fetch data from remote or not
        if (shouldFetch()) {
            if (shouldFetchWithLocalData()) { // should emit cached date with loading state or not
                emit(Success(localFetch()))
            }

            when (val remoteResponse = remoteFetch()) {
                is Success -> {
                    remoteResponse.data?.let {
                        saveFetchResult(remoteResponse.data)
                    }
                    emit(Success(remoteResponse.data))
                }
                is Error -> {
                    emit(Error(Exception("Remote data source fetch failed")))
                }
                else -> throw IllegalStateException()
            }
        } else {
            // get from cache
            emit(Success(localFetch()))
        }
    }

    abstract suspend fun remoteFetch(): Resource<T>?
    abstract suspend fun saveFetchResult(data: T)
    abstract suspend fun localFetch(): T
    open fun shouldFetch() = true
    open fun shouldFetchWithLocalData() = false
}