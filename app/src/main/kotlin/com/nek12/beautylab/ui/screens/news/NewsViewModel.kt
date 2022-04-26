package com.nek12.beautylab.ui.screens.news

import com.nek12.beautylab.ui.screens.news.NewsState.*
import com.nek12.flowMVI.android.MVIViewModel

class NewsViewModel : MVIViewModel<NewsState, NewsIntent, NewsAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    override suspend fun reduce(intent: NewsIntent): NewsState = when (intent) {
        else -> TODO()
    }
}
