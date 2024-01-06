package presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import java.io.File

class CalendarViewModel: ViewModel() {

    private var _sourceFile = MutableStateFlow<File?>(null)
    val sourceFile = _sourceFile.asStateFlow()

    fun selectSourceFile(path: String) {
        _sourceFile.value = File(path).run { if (!isFile) null else this }
    }
}