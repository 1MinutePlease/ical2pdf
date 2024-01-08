package core.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class LocalDateTimeKtTest {

    @Test
    fun `Long to LocalDateTime, same LocalDateTime`() {
        val epochMillis = 1214405100000L // 2008-06-25T15:45:00, UTC
        val localDateTime = LocalDateTime.of(2008, 6, 25,  15, 45, 0) // UTC

        assertEquals(localDateTime, epochMillis.toLocalDateTime())
    }

    @Test
    fun `LocalDateTime to IcalFormat, Same String`() {
        val localDateTime = LocalDateTime.of(2008, 6, 25,  15, 45, 0) // UTC
        val epochMillis = "20080625T154500"

        assertEquals(epochMillis, localDateTime.toIcalDateTime())
    }
}