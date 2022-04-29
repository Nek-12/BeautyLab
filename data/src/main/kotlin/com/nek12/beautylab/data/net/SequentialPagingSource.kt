package com.nek12.beautylab.data.net

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nek12.androidutils.extensions.core.ApiResult
import com.nek12.androidutils.extensions.core.fold
import com.nek12.beautylab.core.model.net.PageResponse

abstract class SequentialPagingSource<T: Any>: PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

data class PageContent<T>(
    val content: List<T>,
    val prevKey: Int?,
    val nextKey: Int?,
)

open class ResultSequentialPagingSource<T: Any, R>(
    private val call: suspend (params: LoadParams<Int>) -> ApiResult<R>,
    private val selector: (R) -> PageContent<T>,
): SequentialPagingSource<T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> = call(params).fold(
        onSuccess = {
            val (content, prev, next) = selector(it)
            LoadResult.Page(content, prev, next)
        },
        onError = { LoadResult.Error(it) }
    )

}

val <T> PageResponse<T>.previousPage get() = (pageable.pageNumber - 1).takeUnless { first }
val <T> PageResponse<T>.nextPage get() = (pageable.pageNumber + 1).takeUnless { last }
