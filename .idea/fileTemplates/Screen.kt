#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}#end

import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState
import androidx.compose.runtime.Immutable

#parse("File Header.java")
@Immutable
sealed class ${NAME}State : MVIState {
    object Loading : ${NAME}State()
    object Empty : ${NAME}State()
    data class Error(val e: Throwable?) : ${NAME}State()
}

@Immutable
sealed class ${NAME}Intent : MVIIntent {

}

@Immutable
sealed class ${NAME}Action : MVIAction {
    object GoBack : ${NAME}Action() 
}
