package domain.use_cases

import com.github.timrs2998.pdfbuilder.*
import com.github.timrs2998.pdfbuilder.style.Alignment
import com.github.timrs2998.pdfbuilder.style.Border
import com.github.timrs2998.pdfbuilder.style.Orientation
import com.github.timrs2998.pdfbuilder.style.Padding
import core.util.getBestTextColor
import core.util.toJavaColor
import domain.model.EventGroup
import org.apache.pdfbox.pdmodel.font.Standard14Fonts
import java.awt.Color
import java.time.DateTimeException
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class GeneratePdf {

    operator fun invoke(
        title: String,
        eventGroups: List<EventGroup>
    ) = document {
        padding = Padding(
            top = 30f,
            bottom = 10f,
            left = 30f,
            right = 30f
        )
        orientation = Orientation.LANDSCAPE
        fontSize = 10f

        text(title) {
            horizontalAlignment = Alignment.CENTER
            fontSize = 18f
            padding = Padding(bottom = 8f)
            fontName = Standard14Fonts.FontName.TIMES_BOLD
        }

        val events = eventGroups.flatMap { group ->
            group.events.map { it.startDateTime.toLocalDate() to (group.acronym to group.color) }
        }.toMap()

        table {
            padding = Padding(bottom = 8f)

            header {
                listOf("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember").forEach { month ->
                    text(month) {
                        border = Border(1f, 1f, 1f, 1f)
                        padding = Padding(1f, 1f, 3f, 3f)
                        fontName = Standard14Fonts.FontName.TIMES_BOLD
                    }
                }
            }

            (1..31).forEach { dayOfMonth ->
                row {
                    (1..12).forEach { month ->
                        try {
                            val date = LocalDate.of(2024, month, dayOfMonth)
                            calendarCell(
                                date = date,
                                event = events[date]?.first,
                                color = events[date]?.second?.toJavaColor() ?: Color.WHITE
                            )
                        } catch (e: DateTimeException) {
                            text("")
                            text("")
                        }
                    }
                }
            }

            row { text("") }
            eventGroups.chunked(5).forEach {
                row {
                    it.forEach { group ->
                        weights.replaceAll { .2f }
                        text(group.acronym) {
                            padding = Padding(1f, 1f, 3f, 3f)
                            fontName = Standard14Fonts.FontName.TIMES_BOLD
                            backgroundColor = group.color?.toJavaColor() ?: Color.WHITE
                            fontColor = group.color?.toJavaColor()?.getBestTextColor() ?: Color.BLACK
                        }
                        text(group.name) {
                            padding = Padding(1f, 1f, 3f, 3f)
                            fontName = Standard14Fonts.FontName.TIMES_BOLD
                            backgroundColor = group.color?.toJavaColor() ?: Color.WHITE
                            fontColor = group.color?.toJavaColor()?.getBestTextColor() ?: Color.BLACK
                        }
                    }
                }
            }
        }
    }

    private fun RowElement.calendarCell(
        date: LocalDate,
        event: String? = null,
        color: Color = Color.WHITE
    ) {
        val day = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.GERMANY).dropLast(1)
        val borderWidth = .5f
        val paddingWidth = 2f

        text("$day ${date.dayOfMonth}") {
            padding = Padding(paddingWidth,0f,paddingWidth,paddingWidth)
            border = Border(borderWidth, 0f, borderWidth, borderWidth)
            backgroundColor = color
            fontColor = color.getBestTextColor()
        }
        text(event ?: "") {
            padding = Padding(paddingWidth,paddingWidth,paddingWidth,0f)
            border = Border(borderWidth, borderWidth, borderWidth, 0f)
            backgroundColor = color
            fontColor = color.getBestTextColor()
        }
    }
}