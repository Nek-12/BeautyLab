package com.nek12.beautylab.data.net

import com.nek12.androidutils.extensions.core.ApiResult
import com.nek12.beautylab.core.model.net.PageResponse

open class PagedResultSource<T: Any>(
    call: suspend (params: LoadParams<Int>) -> ApiResult<PageResponse<T>>,
): ResultSequentialPagingSource<T, PageResponse<T>>(
    call, { PageContent(it.content, it.previousPage, it.nextPage) },
)
