package com.nek12.beautylab.data.net

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
) : SequentialPagingSource<GetProductResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetProductResponse> {
        return api.products(request, params.key, params.loadSize, sort.value, direction.value).fold(
            onSuccess = { LoadResult.Page(it.content, it.previousPage, it.nextPage) },
            onError = { LoadResult.Error(it) }
        )
    }
}
