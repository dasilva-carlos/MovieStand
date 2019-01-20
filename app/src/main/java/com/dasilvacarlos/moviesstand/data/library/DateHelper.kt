package com.dasilvacarlos.moviesstand.data.library

import java.text.SimpleDateFormat
import java.util.*


class DateHelper {

    enum class DateFormatType(val format: String) {
        OMDB_FORMAT("dd MMM yyyy"),
        DISPLAY_CALENDAR_FORMAT("dd/MM/yyyy"),
        DATABASE_FORMAT("yyyy-MM-dd"),
        YEAR_FORMAT("yyyy")
    }

    companion object {
        fun convertDateFormat(date: String, input: DateFormatType, output: DateFormatType): String?{
            val inputFormatter = SimpleDateFormat(input.format, Locale.US)
            val outputFormatter = SimpleDateFormat(output.format, Locale.US)

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