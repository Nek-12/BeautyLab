package com.nek12.beautylab.ui.screens.news

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.nek12.beautylab.ui.items.NewsCardItem
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState
import kotlinx.coroutines.flow.Flow

@Immutable
sealed class NewsState : MVIState {

    object Loading : NewsState()
    data class Error(val e: Throwable?) : NewsState()
    data class DisplayingNews(
        val data: Flow<PagingData<NewsCardItem>>,
    ) : NewsState()
}

@Immutable
sealed class NewsIntent : MVIIntent

@Immutable
sealed class NewsAction : MVIAction {

    object GoBack : NewsAction()
}
