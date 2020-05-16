package com.guojm.design_server.ioc

import com.google.inject.Injector
import com.guojm.design_server.injector
import kotlin.reflect.KClass

inline fun <reified T> Injector.getInstance() = injector.getInstance<T>(T::class.java)
fun <T: Any> Injector.getInstance(key: KClass<T>) = injector.getInstance(key.java)
operator fun <T: Any> Injector.get(key: KClass<T>) = injector.getInstance(key.java)