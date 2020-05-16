package com.guojm.design_server.commons

import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val defaultDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

fun LocalDateTime.defaultFormat() = defaultDateTimeFormatter.format(this)

fun LocalDateTime.safeFormat(pattern: String? = null): String{
    try {
        if (pattern == null){
            return defaultFormat()
        } else {
            return this.format(DateTimeFormatter.ofPattern(pattern))
        }
    } catch (e: Exception){
        return ""
    }
}