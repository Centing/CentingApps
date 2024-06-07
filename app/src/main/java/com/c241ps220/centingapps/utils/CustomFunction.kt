package com.c241ps220.centingapps.utils

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

object CustomFunction {
    fun getInitials(name: String): String {
        val names = name.split(" ")
        var initials = ""

        // Jika hanya satu kata
        if (names.size == 1) {
            return names[0].first().toString()
        }

        // Jika lebih dari satu kata
        for (i in 0 until minOf(2, names.size)) {
            initials += names[i].first()
        }
        return initials
    }

    fun calculateAgeInMonths(birthDate: String): Int {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val birthLocalDate = LocalDate.parse(birthDate, formatter)
        val currentDate = LocalDate.now()
        val period = Period.between(birthLocalDate, currentDate)
        return period.years * 12 + period.months
    }
}