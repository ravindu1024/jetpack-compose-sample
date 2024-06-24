package com.ravindu1024.newsbrowser.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtils {

    companion object{
        fun serverTimeToDate(timestamp: String): LocalDateTime{
            return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME)
        }

        fun formatDate(date: LocalDateTime, format: String): String{
            return date.format(DateTimeFormatter.ofPattern(format))
        }

        fun fromString(date: String, format: String): LocalDateTime{
            val formatter = DateTimeFormatter.ofPattern(format)
            return LocalDateTime.parse(date, formatter)
        }
    }
}