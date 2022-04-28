package com.nek12.beautylab.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nek12.androidutils.extensions.core.toZDT
import com.nek12.beautylab.core.model.net.news.GetNewsResponse
import com.nek12.beautylab.ui.widgets.BLCaption
import com.nek12.beautylab.ui.widgets.BLSpacer
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

data class NewsCardItem(
    val title: String,
    val content: String,
    val imageUrl: String?,
    val createdAt: Instant,
    val id: UUID,
) {

    constructor(response: GetNewsResponse) : this(
        response.title,
        response.content,
        response.imageUrl,
        response.createdAt,
        response.id,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsCardItem(
    item: NewsCardItem,
    onClick: (NewsCardItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.padding(8.dp), onClick = { onClick(item) }) {

        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).crossfade(true).data(item.imageUrl).build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )

            Text(
                text = item.title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                maxLines = 2,
            )
            BLSpacer()

            Text(text = item.content, style = MaterialTheme.typography.body1)

            val formatter = remember { DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM) }
            BLCaption(item.createdAt.toZDT().format(formatter), Modifier.padding(top = 12.dp, bottom = 4.dp))
        }
    }
}


@Composable
@Preview(name = "NewsCardItem", showSystemUi = false, showBackground = true)
private fun NewsCardItemPreview() {

}
