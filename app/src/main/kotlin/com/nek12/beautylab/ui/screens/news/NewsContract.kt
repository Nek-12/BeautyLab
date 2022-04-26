package com.nek12.beautylab.ui.screens.news

import androidx.compose.runtime.Immutable
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class NewsState : MVIState {

    object Loading : NewsState()
    object Empty : NewsState()
    data class Error(val e: Throwable?) : NewsState()
}

@Immutable
sealed class NewsIntent : MVIIntent

@Immutable
sealed class NewsAction : MVIAction {

    object GoBack : NewsAction()
}
