package com.nek12.beautylab.data.net

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nek12.beautylab.core.model.net.PageResponse

abstract class SequentialPagingSource<T : Any> : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

val <T> PageResponse<T>.previousPage get() = (pageable.pageNumber - 1).takeUnless { first }
val <T> PageResponse<T>.nextPage get() = (pageable.pageNumber + 1).takeUnless { last }
