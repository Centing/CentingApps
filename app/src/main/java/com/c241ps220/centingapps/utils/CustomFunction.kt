package com.c241ps220.centingapps.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object CustomFunction {

    val BASE_URL: String = "https://44bf-114-10-124-18.ngrok-free.app/"

    fun getCurrentDateTimeFormatted(): String {
        // Mendapatkan tanggal dan waktu saat ini
        val current = LocalDateTime.now()

        // Menentukan format yang diinginkan
        val formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")

        // Memformat tanggal dan waktu saat ini sesuai dengan format yang ditentukan
        return current.format(formatter)
    }
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

    fun generateTimeLapse(input: String): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val targetDate = LocalDate.parse(input, formatter)

        // Tanggal sekarang
        val currentDate = LocalDate.now()

        // Menghitung perbedaan hari
        val daysDifference = ChronoUnit.DAYS.between(targetDate, currentDate).toInt()

        // Jika perbedaan hari kurang dari 30
        if (daysDifference < 30) {
            return "$daysDifference Hari yang lalu"
        } else {
            // Menghitung perbedaan bulan
            val monthsDifference = ChronoUnit.MONTHS.between(targetDate, currentDate).toInt()
            return if (monthsDifference == 1) {
                "1 Bulan yang lalu"
            } else {
                "$monthsDifference Bulan yang lalu"
            }
        }
    }
}