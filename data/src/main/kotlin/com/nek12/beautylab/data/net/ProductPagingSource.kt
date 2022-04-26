package com.nek12.beautylab.data.net

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nek12.androidutils.extensions.core.fold
import com.nek12.beautylab.core.model.net.SortDirection
import com.nek12.beautylab.core.model.net.product.GetProductResponse
import com.nek12.beautylab.core.model.net.product.GetProductsFilteredRequest
import com.nek12.beautylab.core.model.net.product.ProductSort
import com.nek12.beautylab.data.net.api.BeautyLabApi


class ProductPagingSource(
    private val sort: ProductSort,
    private val direction: SortDirection,
    private val request: GetProductsFilteredRequest,
    private val api: BeautyLabApi,
) : PagingSource<Int, GetProductResponse>() {

    override fun getRefreshKey(state: PagingState<Int, GetProductResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetProductResponse> {
        return api.products(request, params.key, params.loadSize, sort.value, direction.value).fold(
            onSuccess = {
                LoadResult.Page(it.content,
                    (it.pageable.pageNumber - 1).takeUnless { _ -> it.first },
                    (it.pageable.pageNumber + 1).takeUnless { _ -> it.last }
                )
            },
            onError = { LoadResult.Error(it) }
        )
    }
}
