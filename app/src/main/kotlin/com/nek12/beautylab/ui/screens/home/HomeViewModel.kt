package com.nek12.beautylab.ui.screens.home

import com.nek12.androidutils.extensions.core.fold
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.data.net.AuthManager
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.data.util.ApiError
import com.nek12.flowMVI.android.MVIViewModel

class HomeViewModel(
    private val repo: BeautyLabRepo,
    private val authManager: AuthManager,
) : MVIViewModel<HomeState, HomeIntent, HomeAction>() {

    override val initialState get() = HomeState.Loading
    override fun recover(from: Exception) = HomeState.Error(from.genericMessage)

    init {

        if (!authManager.isLoggedIn) {
            send(HomeAction.GoToLogIn)
        } else {
            launchLoadData()
        }

    }

    override suspend fun reduce(intent: HomeIntent): HomeState = when (intent) {
        is HomeIntent.ClickedBrand -> withState<HomeState.DisplayingContent> {
            send(HomeAction.GoToProductList(FiltersPayload(
                brandId = intent.item.id
            )))
            currentState
        }
        is HomeIntent.ClickedCategory -> withState<HomeState.DisplayingContent> {
            send(HomeAction.GoToProductList(FiltersPayload(
                categoryId = intent.item.id
            )))
            currentState
        }
        is HomeIntent.ClickedProduct -> withState<HomeState.DisplayingContent> {
            send(HomeAction.GoToProductDetails(intent.item.id))
            currentState
        }
    }

    private fun launchLoadData() = launchForState {
        repo.getMainScreen().fold(
            onSuccess = { HomeState.DisplayingContent(it) },
            onError = {
                when (it) {
                    is ApiError.Unauthorized -> {
                        send(HomeAction.GoToLogIn)
                        authManager.reset()
                    }
                }
                HomeState.Error(it.genericMessage)
            }
        )
    }
}
