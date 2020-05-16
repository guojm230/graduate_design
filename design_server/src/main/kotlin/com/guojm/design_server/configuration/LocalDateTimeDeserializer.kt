package com.guojm.design_server.configuration

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.guojm.design_server.commons.safeFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeDeserializer: StdSerializer<LocalDateTime>(LocalDateTime::class.java) {

    override fun serialize(value: LocalDateTime?, gen: JsonGenerator?, provider: SerializerProvider?) {
        gen?.writeString(value?.safeFormat()?:"")
    }

}