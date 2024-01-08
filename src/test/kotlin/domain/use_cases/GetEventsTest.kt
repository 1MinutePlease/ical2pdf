package domain.use_cases

import core.util.FileNames
import di.TestModule
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import java.time.LocalDateTime

class GetEventsTest: KoinTest {

    val getEvents: GetEvents by inject()

    @Before
    fun setUp() {
        startKoin { modules(TestModule.testModule) }
    }

    @Test
    fun `GetEvents from kjg_intern between 01-01-24 and 31-12-24, total of 18`() {
        val events = getEvents.invoke(
            path = FileNames.kjgIntern,
            from = LocalDateTime.of(2024, 1, 1, 0, 0, 0),
            to = LocalDateTime.of(2024, 12, 31, 23, 59, 59),
        )

        assertEquals(18, events.size)
    }
}