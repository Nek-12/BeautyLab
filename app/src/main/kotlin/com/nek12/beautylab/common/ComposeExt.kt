package com.nek12.beautylab.common

import android.content.Context
import android.os.Parcelable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.ITypeface
import com.mikepenz.iconics.typeface.library.googlematerial.RoundedGoogleMaterial
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.core.model.net.Color
import com.nek12.beautylab.core.model.net.SortDirection
import com.nek12.beautylab.core.model.net.product.GetProductsFilteredRequest
import com.nek12.beautylab.core.model.net.product.ProductSort
import com.nek12.beautylab.ui.theme.BeautyLabTheme
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.android.compose.EmptyScope
import com.nek12.flowMVI.android.compose.MVIIntentScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.util.*
import kotlin.experimental.ExperimentalTypeInference

typealias GMRIcon = RoundedGoogleMaterial.Icon

val surfaceTint @Composable get() = ColorFilter.tint(MaterialTheme.colors.onSurface)

fun ITypeface.randomIcon(): IIcon = getIcon(characters.keys.random()) //use static lazy map provided by ITypeface

fun CoroutineScope.snackbar(
    text: String,
    scaffoldState: ScaffoldState,
    duration: SnackbarDuration = SnackbarDuration.Short,
) = launch {
    scaffoldState.snackbarHostState.showSnackbar(text, duration = duration)
}

fun CoroutineScope.snackbar(
    context: Context,
    text: Text,
    scaffoldState: ScaffoldState,
    duration: SnackbarDuration = SnackbarDuration.Short,
) = snackbar(text.string(context), scaffoldState, duration)


fun Color.toComposeColor() = androidx.compose.ui.graphics.Color(value)

@OptIn(ExperimentalTypeInference::class)
@Composable
inline fun <A: MVIAction, I: MVIIntent> ScreenPreview(
    darkMode: Boolean = false,
    @BuilderInference
    crossinline content: @Composable MVIIntentScope<I, A>.() -> Unit,
) = EmptyScope {
    BeautyLabTheme(darkMode) {
        content()
    }
}


@Parcelize
data class FiltersPayload(
    val sort: ProductSort = ProductSort.Name,
    val direction: SortDirection = SortDirection.Desc,
    val brandId: UUID? = null,
    val categoryId: UUID? = null,
    val minDiscount: Float = 0f,
    val maxDiscount: Float = 1f,
    val minPrice: Double = 0.0,
    val maxPrice: Double? = null,
    val isActive: Boolean = true,
    val createdBefore: Instant = Instant.now(),
    val createdAfter: Instant? = null,
    val minAmountAvailable: Long = 0,
): Parcelable {

    fun toRequest() = GetProductsFilteredRequest(
        brandId,
        categoryId,
        minDiscount,
        maxDiscount,
        minPrice,
        maxPrice,
        isActive,
        createdBefore,
        createdAfter,
        minAmountAvailable
    )

    @Composable
    fun sortRepresentation(): String = R.string.sort_template.string(
        sort.representation().lowercase(),
        direction.representation().lowercase()
    )
}


@Composable
fun ProductSort.representation() = when (this) {
    ProductSort.Name -> R.string.sort_name.string()
    ProductSort.Amount -> R.string.sort_amount.string()
    ProductSort.Price -> R.string.sort_price.string()
    ProductSort.CreatedAt -> R.string.sort_date.string()
}


@Composable
fun SortDirection.representation() = when (this) {
    SortDirection.Asc -> R.string.descending.string()
    SortDirection.Desc -> R.string.ascending.string()
}
