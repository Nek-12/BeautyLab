import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

fun generateGitPatchVersion(): Int {
    return "git rev-list HEAD --count".runCommand().trim().toInt()
}

fun String.runCommand(
    workingDir: File = File("."),
    timeoutAmount: Long = 10,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS,
): String = ProcessBuilder(split("\\s(?=(?:[^'\"`]*(['\"`])[^'\"`]*\\1)*[^'\"`]*$)".toRegex()))
    .directory(workingDir)
    .redirectOutput(ProcessBuilder.Redirect.PIPE)
    .redirectError(ProcessBuilder.Redirect.PIPE)
    .start()
    .apply { waitFor(timeoutAmount, timeoutUnit) }
    .run {
        val error = errorStream.bufferedReader().readText().trim()
        if (error.isNotEmpty()) {
            throw IOException(error)
        }
        inputStream.bufferedReader().readText().trim()
    }
