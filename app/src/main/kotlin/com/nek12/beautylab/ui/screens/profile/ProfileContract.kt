package com.nek12.beautylab.ui.screens.profile

import androidx.compose.runtime.Immutable
import com.nek12.beautylab.core.model.net.transaction.GetTransactionResponse
import com.nek12.beautylab.core.model.net.user.GetUserResponse
import com.nek12.beautylab.ui.items.TransactionItem
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState
import java.util.*

@Immutable
sealed class ProfileState: MVIState {

    object Loading: ProfileState()
    object Empty: ProfileState()
    data class Error(val e: Throwable?): ProfileState()
    data class DisplayingProfile(
        val name: String,
        val bonusBalance: Double,
        val pendingTransactions: List<TransactionItem>
    ): ProfileState() {

        constructor(user: GetUserResponse, orders: List<GetTransactionResponse>): this(
            name = user.name,
            bonusBalance = user.bonusBalance,
            pendingTransactions = orders.map { TransactionItem(it) }
        )
    }
}

@Immutable
sealed class ProfileIntent: MVIIntent {

    object ClickedLogout: ProfileIntent()
    object ClickedSeeHistory: ProfileIntent()
    data class ClickedCancelOrder(val item: TransactionItem): ProfileIntent()
    data class ClickedOrder(val item: TransactionItem): ProfileIntent()
}

@Immutable
sealed class ProfileAction: MVIAction {

    data class GoToCancelOrder(val id: UUID): ProfileAction()
    data class GoToProductDetails(val productId: UUID): ProfileAction()
    object GoToOrderHistory: ProfileAction()
    object GoBack: ProfileAction()
    object GoToLogIn: ProfileAction()
}
