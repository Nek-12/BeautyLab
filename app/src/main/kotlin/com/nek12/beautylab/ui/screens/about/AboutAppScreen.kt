package com.nek12.beautylab.ui.screens.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.nek12.androidutils.compose.string
import com.nek12.androidutils.extensions.android.openBrowser
import com.nek12.beautylab.R
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.ui.screens.about.AboutAppAction.GoBack
import com.nek12.beautylab.ui.screens.about.AboutAppAction.OpenLink
import com.nek12.beautylab.ui.screens.about.AboutAppAction.ShowMessage
import com.nek12.beautylab.ui.screens.about.AboutAppIntent.BrowserNotFound
import com.nek12.beautylab.ui.screens.about.AboutAppIntent.ClickedAuthorGithub
import com.nek12.beautylab.ui.screens.about.AboutAppIntent.ClickedAuthorWebsite
import com.nek12.beautylab.ui.screens.about.AboutAppIntent.ClickedProjectGithub
import com.nek12.beautylab.ui.screens.about.AboutAppIntent.ClickedProjectInstagram
import com.nek12.beautylab.ui.screens.about.AboutAppState.DisplayingAbout
import com.nek12.beautylab.ui.widgets.BLCircleIcon
import com.nek12.beautylab.ui.widgets.BLTopBar
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun AboutAppScreen(
    navigator: DestinationsNavigator
) = MVIComposable(getViewModel<AboutAppViewModel>()) { state ->

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
            is OpenLink -> context.openBrowser(context.getString(action.id).toUri()) { send(BrowserNotFound) }
            is ShowMessage -> launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    action.text.string(context),
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

    AboutAppContent(state, scaffoldState)
}

@Composable
private fun MVIIntentScope<AboutAppIntent, AboutAppAction>.AboutAppContent(
    state: AboutAppState,
    scaffoldState: ScaffoldState
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { BLTopBar(R.string.app_name) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                DisplayingAbout -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .heightIn(min = 300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            BLCircleIcon(
                                icon = GMRIcon.gmr_spa,
                                size = 72.dp,
                                color = MaterialTheme.colors.primary,
                                elevation = 4.dp
                            )
                        }

                        val aboutApp = R.string.about_app_description.string()
                        val license = R.string.about_app_license.string()

                        Text(
                            text = buildAnnotatedString {
                                withStyle(ParagraphStyle()) {
                                    append(aboutApp)
                                }

                                withStyle(ParagraphStyle()) {
                                    append(license)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Justify,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Text(
                            text = R.string.projects_links.string(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            DrawableIcon(id = R.drawable.ic_github_64, onClick = { send(ClickedProjectGithub) })
                            // DrawableIcon(id = R.drawable.ic_web_64, onClick = { send(ClickedProjectWebsite) })
                            DrawableIcon(id = R.drawable.ic_instagram_64, onClick = { send(ClickedProjectInstagram) })
                        }

                        Text(
                            text = R.string.authors_links.string(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            DrawableIcon(id = R.drawable.ic_github_64, onClick = { send(ClickedAuthorGithub) })
                            DrawableIcon(id = R.drawable.ic_web_64, onClick = { send(ClickedAuthorWebsite) })
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun DrawableIcon(id: Int, onClick: () -> Unit, size: Dp = 64.dp) {
    Icon(
        contentDescription = null,
        painter = painterResource(id = id),
        modifier = Modifier
            .size(size)
            .clickable(onClick = onClick)
            .padding(8.dp)
    )
}

@Composable
@Preview(name = "AboutApp", showSystemUi = true, showBackground = true)
private fun AboutAppPreview() = ScreenPreview {
    AboutAppContent(state = DisplayingAbout, scaffoldState = rememberScaffoldState())
}
