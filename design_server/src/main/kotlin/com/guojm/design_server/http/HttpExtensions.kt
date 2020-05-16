package com.guojm.design_server.http

import com.google.inject.Injector
import com.google.inject.Key
import com.guojm.design_server.commons.ErrorBody
import com.guojm.design_server.http.handler.AbstractSuspendRouteHandler
import com.guojm.design_server.injector
import io.vertx.core.AsyncResult
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerRequest
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KClass

fun HttpServerResponse.writeErrorBody(errorBody: ErrorBody){
    statusCode = errorBody.statusCode.convertToHttp()
    putHeader("Content-Type","application/json;charset=UTF-8")
    end(JsonObject.mapFrom(errorBody).toString())
}

fun HttpServerResponse.endJson(json: JsonObject){
    putHeader("Content-Type","application/json;charset=UTF-8")
    end(json.toString())
}

fun HttpServerResponse.endJson(result: Any){
    endJson(JsonObject.mapFrom(result))
}

fun <T> runElseThrow(async:AsyncResult<T>,handler: (T)->Unit){
    if (async.succeeded())
        handler(async.result())
    else throw async.cause()
}

fun Route.coroutineHandler(block: suspend (RoutingContext)-> Unit){
    handler{ context->
        VertxScope.launch(CoroutineExceptionHandler{ c,th->
            context.fail(th)
        }) {
            block(context)
        }
    }
}

object VertxScope: CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = getDefaultVertx().dispatcher()
}

fun getDefaultVertx(): Vertx =
    injector.getInstance(Key.get(Vertx::class.java))
