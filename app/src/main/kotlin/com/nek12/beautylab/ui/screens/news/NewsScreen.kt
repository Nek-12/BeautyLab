package com.nek12.beautylab.ui.screens.news

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.screens.news.NewsAction.*
import com.nek12.beautylab.ui.screens.news.NewsState.*
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun NewsScreen(
    navigator: DestinationsNavigator,
) = MVIComposable(getViewModel<NewsViewModel>()) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
        }
    }

    NewsContent(state)
}

@Composable
private fun MVIIntentScope<NewsIntent, NewsAction>.NewsContent(state: NewsState) {
    when (state) {
        is Empty -> BLEmptyView()
        is Error -> BLErrorView(state.e.genericMessage.string())
        is Loading -> Unit
    }
}

@Composable
@Preview(name = "News", showSystemUi = true, showBackground = true)
private fun NewsPreview() = ScreenPreview {
    Column {
        NewsContent(state = Empty)
        NewsContent(state = Error(Exception()))
    }
}
