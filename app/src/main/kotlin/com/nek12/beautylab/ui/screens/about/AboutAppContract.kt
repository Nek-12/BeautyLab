package com.nek12.beautylab.ui.screens.about

import androidx.compose.runtime.Immutable
import com.nek12.androidutils.extensions.android.Text
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class AboutAppState: MVIState {

    object DisplayingAbout: AboutAppState()
}

@Immutable
sealed class AboutAppIntent: MVIIntent {

    object ClickedProjectInstagram: AboutAppIntent()
    object ClickedAuthorWebsite: AboutAppIntent()
    object ClickedProjectGithub: AboutAppIntent()
    object ClickedAuthorGithub: AboutAppIntent()
    object BrowserNotFound: AboutAppIntent()
}

@Immutable
sealed class AboutAppAction: MVIAction {

    data class OpenLink(val id: Int): AboutAppAction()
    object GoBack: AboutAppAction()
    data class ShowMessage(val text: Text): AboutAppAction()
}
