package com.guojm.design_server.ioc

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scope
import com.google.inject.Singleton
import com.guojm.design_server.http.handler.AuthenticationHandler
import com.guojm.design_server.http.handler.AuthenticationHandlerImpl
import com.guojm.design_server.http.handler.LoginHandler
import com.guojm.design_server.http.handler.ResourceHandler
import com.guojm.design_server.http.security.BCryptPasswordEncoder
import com.guojm.design_server.http.security.PasswordEncoder
import io.vertx.core.Vertx
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.kotlin.ext.auth.jwt.jwtAuthOptionsOf
import io.vertx.kotlin.ext.auth.pubSecKeyOptionsOf
import javax.inject.Provider

class MainModule: AbstractModule() {

    override fun configure() {
        bind(Vertx::class.java)
            .toProvider(Provider { Vertx.vertx() })
            .`in`(Singleton::class.java)

        bind(LoginHandler::class.java).`in`(Singleton::class.java)
        bind(PasswordEncoder::class.java).to(BCryptPasswordEncoder::class.java).`in`(Singleton::class.java)
        bind(ResourceHandler::class.java).`in`(Singleton::class.java)

        //非单例模式
        bind(AuthenticationHandler::class.java).to(AuthenticationHandlerImpl::class.java)
    }

    @Provides
    @Singleton
    fun jwtAuth(vertx: Vertx): JWTAuth {
        return JWTAuth.create(Vertx.vertx(), jwtAuthOptionsOf(
            pubSecKeys = listOf(
                pubSecKeyOptionsOf(
                    algorithm = "HS256",
                    publicKey = "guojm_public_key",
                    symmetric = true,
                    secretKey = "guojm_private_key"
                )
            )
        )
        )
    }
}