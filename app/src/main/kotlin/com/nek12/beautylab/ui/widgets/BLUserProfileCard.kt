package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.ui.theme.BeautyLabTheme

@Composable
fun BLUserProfileCard(
    name: String,
    balance: Double,
    onClickLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            BLCircleIcon(
                icon = GMRIcon.gmr_person,
                color = MaterialTheme.colors.primary,
                size = 80.dp,
                modifier = Modifier.padding(8.dp)
            )


            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = R.string.welcome_template.string(name),
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                )
                BLSpacer()

                Text(
                    text = R.string.bonus_balance_template.string(balance),
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    BLCircleIcon(
                        icon = GMRIcon.gmr_exit_to_app,
                        color = MaterialTheme.colors.primary,
                        onClick = onClickLogout
                    )
                }
            }
        }
    }
}


@Composable
@Preview(name = "BLUserProfileCard", showSystemUi = false, showBackground = true)
private fun BLUserProfileCardPreview() {
    BeautyLabTheme(false) {
        BLUserProfileCard(name = "User with very long name and surname and whatnot", balance = 17900.00, {})
    }
}
