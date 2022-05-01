package com.nek12.beautylab.ui.screens.profile

import com.nek12.androidutils.extensions.core.orThrow
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.screens.profile.ProfileAction.GoToCancelOrder
import com.nek12.beautylab.ui.screens.profile.ProfileAction.GoToOrderHistory
import com.nek12.beautylab.ui.screens.profile.ProfileAction.GoToProductDetails
import com.nek12.beautylab.ui.screens.profile.ProfileIntent.ClickedCancelOrder
import com.nek12.beautylab.ui.screens.profile.ProfileIntent.ClickedOrder
import com.nek12.beautylab.ui.screens.profile.ProfileIntent.ClickedSeeHistory
import com.nek12.beautylab.ui.screens.profile.ProfileState.DisplayingProfile
import com.nek12.beautylab.ui.screens.profile.ProfileState.Error
import com.nek12.beautylab.ui.screens.profile.ProfileState.Loading
import com.nek12.flowMVI.android.MVIViewModel
import kotlinx.coroutines.async

class ProfileViewModel(
    val repo: BeautyLabRepo,
): MVIViewModel<ProfileState, ProfileIntent, ProfileAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    init {
        loadData()
    }

    override suspend fun reduce(intent: ProfileIntent): ProfileState = when (intent) {
        is ClickedCancelOrder -> {
            send(GoToCancelOrder(intent.item.id))
            currentState
        }
        ClickedSeeHistory -> {
            send(GoToOrderHistory)
            currentState
        }
        is ClickedOrder -> {
            intent.item.productId?.let {
                send(GoToProductDetails(it))
            }
            currentState
        }
    }

    private fun loadData() = launchForState {
        val user = async { repo.getUser() }
        val orders = async { repo.getPendingTransactions() }

        DisplayingProfile(user.await().orThrow(), orders.await().orThrow())
    }
}
