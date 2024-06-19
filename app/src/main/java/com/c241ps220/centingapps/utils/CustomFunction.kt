package com.c241ps220.centingapps.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.Period
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

object CustomFunction {

    val BASE_URL: String = "https://4b4c-45-126-187-3.ngrok-free.app/"

    fun convertApiDateToLocalDate(apiDateString: String): String {
        // Format tanggal dan waktu dari API
        val apiDateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

        // Parsing string tanggal dari API ke objek OffsetDateTime
        val offsetDateTime = OffsetDateTime.parse(apiDateString, apiDateTimeFormatter)

        // Mengubah OffsetDateTime ke LocalDate dengan zona waktu UTC
        val localDate = offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC).toLocalDate()

        // Format yang diinginkan
        val desiredDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        // Mengubah LocalDate ke string dengan format baru
        return localDate.format(desiredDateFormat)
    }

    fun convertDateFormat(dateString: String): String {
        // Format awal
        val originalFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        // Format yang diinginkan
        val targetFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Parsing string tanggal ke objek Date
        val date = originalFormat.parse(dateString)

        // Mengubah objek Date kembali ke string dengan format baru
        return targetFormat.format(date)
    }

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