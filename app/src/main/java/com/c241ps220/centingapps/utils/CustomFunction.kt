package com.c241ps220.centingapps.utils

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
}