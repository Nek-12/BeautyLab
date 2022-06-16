package com.nek12.beautylab.ui.screens.about

import com.nek12.androidutils.extensions.android.Text
import com.nek12.beautylab.R
import com.nek12.beautylab.ui.screens.about.AboutAppAction.OpenLink
import com.nek12.beautylab.ui.screens.about.AboutAppAction.ShowMessage
import com.nek12.beautylab.ui.screens.about.AboutAppIntent.BrowserNotFound
import com.nek12.beautylab.ui.screens.about.AboutAppIntent.ClickedAuthorGithub
import com.nek12.beautylab.ui.screens.about.AboutAppIntent.ClickedAuthorWebsite
import com.nek12.beautylab.ui.screens.about.AboutAppIntent.ClickedProjectGithub
import com.nek12.beautylab.ui.screens.about.AboutAppIntent.ClickedProjectInstagram
import com.nek12.beautylab.ui.screens.about.AboutAppState.DisplayingAbout
import com.nek12.flowMVI.android.MVIViewModel
import com.nek12.flowMVI.currentState

class AboutAppViewModel: MVIViewModel<AboutAppState, AboutAppIntent, AboutAppAction>(DisplayingAbout) {

    override suspend fun reduce(intent: AboutAppIntent): AboutAppState {
        val action = when (intent) {
            BrowserNotFound -> ShowMessage(Text.Resource(R.string.browser_not_found))
            ClickedAuthorGithub -> OpenLink(R.string.author_gh_link)
            ClickedAuthorWebsite -> OpenLink(R.string.author_site_link)
            ClickedProjectGithub -> OpenLink(R.string.project_gh_link)
            ClickedProjectInstagram -> OpenLink(R.string.project_ig_link)
        }
        send(action)
        return currentState
    }
}
