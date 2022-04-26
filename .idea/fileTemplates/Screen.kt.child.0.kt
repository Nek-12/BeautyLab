#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

import ${PACKAGE_NAME}.${NAME}Action.*
import ${PACKAGE_NAME}.${NAME}Intent.*
import ${PACKAGE_NAME}.${NAME}State.* #end 
import com.nek12.flowMVI.android.MVIViewModel

class ${NAME}ViewModel(

) : MVIViewModel<${NAME}State, ${NAME}Intent, ${NAME}Action>() {

    override val initialState get() = Loading 
    override fun recover(from: Exception) = Error(from)

    override suspend fun reduce(intent: ${NAME}Intent): ${NAME}State = when (intent) {
        else -> TODO()
    }
}