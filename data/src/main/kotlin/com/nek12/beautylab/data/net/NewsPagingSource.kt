package com.nek12.beautylab.data.net

import com.nek12.androidutils.extensions.core.fold
import com.nek12.beautylab.core.model.net.news.GetNewsResponse
import com.nek12.beautylab.data.net.api.BeautyLabApi

class NewsPagingSource(
    val api: BeautyLabApi,
) : SequentialPagingSource<GetNewsResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetNewsResponse> = api
        .news(params.key, params.loadSize)
        .fold(
            onSuccess = { LoadResult.Page(it.content, it.previousPage, it.nextPage) },
            onError = { LoadResult.Error(it) }
        )
}
