package com.nek12.beautylab.ui.screens.news

import androidx.paging.map
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.items.NewsCardItem
import com.nek12.beautylab.ui.screens.news.NewsState.*
import com.nek12.flowMVI.android.MVIViewModel
import kotlinx.coroutines.flow.map

class NewsViewModel(
    val repo: BeautyLabRepo,
) : MVIViewModel<NewsState, NewsIntent, NewsAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    init {
        loadNews()
    }

    override suspend fun reduce(intent: NewsIntent): NewsState = currentState

    private fun loadNews() = set(DisplayingNews(repo.getNews().map { data -> data.map { NewsCardItem(it) } }))
}
