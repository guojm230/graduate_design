package com.guojm.design_server.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.vertx.core.json.jackson.DatabindCodec
import io.vertx.core.json.jackson.JacksonCodec
import io.vertx.core.spi.JsonFactory
import io.vertx.core.spi.json.JsonCodec
import java.time.LocalDateTime

class JsonFactoryImpl: JsonFactory {

    @Volatile lateinit var CODEC: JacksonCodec

    override fun codec(): JsonCodec? {
        if (!this::CODEC.isInitialized){
            init()
        }
        return CODEC
    }

    private fun init(){
        synchronized(this){
            val simpleModule = SimpleModule()
            simpleModule.addSerializer(LocalDateTime::class.java,LocalDateTimeDeserializer())
            DatabindCodec.mapper().apply {
                registerKotlinModule()
                registerModule(simpleModule)
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            }
            DatabindCodec.prettyMapper().apply {
                registerKotlinModule()
                registerModule(simpleModule)
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            }
            CODEC = DatabindCodec()
        }
    }
}