package presentation

import androidx.compose.runtime.mutableStateOf
import java.io.File

class CalendarViewModel {

    private var _sourceFile = mutableStateOf<File?>(null)
    val sourceFile = _sourceFile

    fun selectSourceFile(path: String) {
        _sourceFile.value = File(path).run { if (!isFile) null else this }
    }
}