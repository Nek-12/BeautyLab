package com.nek12.beautylab.ui.screens.home

import com.nek12.androidutils.extensions.core.fold
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.data.net.AuthManager
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.data.util.ApiError
import com.nek12.beautylab.ui.screens.home.HomeAction.GoToAboutApp
import com.nek12.beautylab.ui.screens.home.HomeAction.GoToLogIn
import com.nek12.beautylab.ui.screens.home.HomeAction.GoToProductDetails
import com.nek12.beautylab.ui.screens.home.HomeAction.GoToProductList
import com.nek12.beautylab.ui.screens.home.HomeIntent.ClickedBrand
import com.nek12.beautylab.ui.screens.home.HomeIntent.ClickedCategory
import com.nek12.beautylab.ui.screens.home.HomeIntent.ClickedInfo
import com.nek12.beautylab.ui.screens.home.HomeIntent.ClickedLogout
import com.nek12.beautylab.ui.screens.home.HomeIntent.ClickedProduct
import com.nek12.beautylab.ui.screens.home.HomeIntent.ClickedRetry
import com.nek12.beautylab.ui.screens.home.HomeIntent.EnteredHome
import com.nek12.beautylab.ui.screens.home.HomeState.DisplayingContent
import com.nek12.beautylab.ui.screens.home.HomeState.Loading
import com.nek12.flowMVI.android.MVIViewModel
import com.nek12.flowMVI.currentState
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val repo: BeautyLabRepo,
    private val authManager: AuthManager,
): MVIViewModel<HomeState, HomeIntent, HomeAction>(Loading) {

    override fun recover(from: Exception) = HomeState.Error(from.genericMessage)

    override suspend fun reduce(intent: HomeIntent): HomeState = when (intent) {
        is ClickedBrand -> withState<DisplayingContent> {
            send(
                GoToProductList(
                    FiltersPayload(
                        brandId = intent.item.id
                    )
                )
            )
            currentState
        }
        is ClickedCategory -> withState<DisplayingContent> {
            send(
                GoToProductList(
                    FiltersPayload(
                        categoryId = intent.item.id
                    )
                )
            )
            currentState
        }
        is ClickedProduct -> withState<DisplayingContent> {
            send(GoToProductDetails(intent.item.id))
            currentState
        }
        is ClickedRetry -> {
            launchLoadData()
            Loading
        }
        ClickedLogout -> {
            repo.logOut()
            send(GoToLogIn)
            Loading
        }
        is EnteredHome -> {
            launchLoadData()
            Loading
        }
        is ClickedInfo -> {
            send(GoToAboutApp)
            currentState
        }
    }

    private fun launchLoadData() = launchForState(Dispatchers.IO) {
        repo.getMainScreen().fold(
            onSuccess = { DisplayingContent(it) },
            onError = {
                when (it) {
                    is ApiError.Unauthorized -> {
                        authManager.reset()
                        send(GoToLogIn)
                    }
                }
                HomeState.Error(it.genericMessage)
            }
        )
    }
}
