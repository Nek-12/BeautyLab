#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}#end


import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

#parse("File Header.java")
@Composable
fun ${NAME}(
    modifier: Modifier = Modifier
) {
    
}


@Composable
@Preview(name="${NAME}", showSystemUi = false, showBackground = true)
private fun ${NAME}Preview() {
    ${NAME}()
}