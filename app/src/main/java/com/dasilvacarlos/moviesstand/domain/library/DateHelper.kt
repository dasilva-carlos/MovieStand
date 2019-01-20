package com.dasilvacarlos.moviesstand.domain.library

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class DateHelper {

    enum class DateFormatType(val format: String) {
        OMDB_FORMAT("dd MMM yyyy"),
        DISPLAY_CALENDAR_FORMAT("dd/MM/yyyy")
    }

    companion object {
        fun convertDateFormat(date: String, input: DateFormatType, output: DateFormatType): String?{
            val inputFormatter = SimpleDateFormat(input.format, Locale.US)
            val outputFormatter = SimpleDateFormat(output.format, Locale.getDefault())

            try {
                val parsedDate = inputFormatter.parse(date)
                return outputFormatter.format(parsedDate)
            } catch (e: Exception) {
                print(e.localizedMessage)
            }
            return null
        }
    }
}