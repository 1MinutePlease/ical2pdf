package core.util

import java.time.LocalDateTime
import java.time.OffsetTime

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofEpochSecond(
        this.div(1000),
        0,
        OffsetTime.now().offset
    )
}

/*
fun LocalDateTime.toIcalDateTime(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"))
}*/
