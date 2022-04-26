#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

import ${PACKAGE_NAME}.${NAME}Action.*
import ${PACKAGE_NAME}.${NAME}Intent.*
import ${PACKAGE_NAME}.${NAME}State.* #end 
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import androidx.compose.foundation.layout.Column
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage

#parse("File Header.java")
@Composable
@Destination
fun ${NAME}Screen(
    navigator: DestinationsNavigator
) = MVIComposable(getViewModel<${NAME}ViewModel>()) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp() 
        }
    }
    
    ${NAME}Content(state)
}

@Composable
private fun MVIIntentScope<${NAME}Intent, ${NAME}Action>.${NAME}Content(state: ${NAME}State) {
    when (state) {
        is Empty -> BLEmptyView()
        is Error -> BLErrorView(state.e.genericMessage.string())
        is Loading -> Unit
    }
}

@Composable
@Preview(name="${NAME}", showSystemUi = true, showBackground = true)
private fun ${NAME}Preview() = ScreenPreview {
    Column {
        ${NAME}Content(state = Empty)
        ${NAME}Content(state = Error(Exception()))
    }
}
