package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.data.util.ApiError

@Composable
fun BLErrorView(
    message: String,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
) {
    Column(modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BLIcon(asset = GMRIcon.gmr_error_outline, size = 64.dp)
        Text(R.string.error_occurred.string())
        BLSpacer()
        Text(R.string.error_details_template.string(message))
        onRetry?.let {
            Button(onClick = it) {
                Text(R.string.retry.string())
            }
        }
    }
}


@Composable
@Preview(name = "BLErrorView", showSystemUi = false, showBackground = true)
private fun BLErrorViewPreview() {
    BLErrorView(ApiError.Unauthorized.genericMessage.string())
}
